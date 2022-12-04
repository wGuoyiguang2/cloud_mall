package com.hqtc.cms.model.service.impl;

import com.hqtc.cms.config.BaseConfig;
import com.hqtc.cms.model.bean.flashsale.*;
import com.hqtc.cms.model.service.BmsService;
import com.hqtc.cms.model.service.FlashSaleService;
import com.hqtc.common.config.ErrorCode;
import com.hqtc.common.response.ResultData;
import com.hqtc.common.utils.Tools;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @Description:
 * @Author: WangBin
 * @Date: 2018/8/16 16:29
 */
@Service
public class FlashSaleServiceImpl implements FlashSaleService {
    Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private BmsService bmsService;
    @Autowired
    private BaseConfig baseConfig;
    private Map<String,Object> mapExecutor=new HashMap<>();

    /**
     * 秒杀开始接口
     *
     * @return
     */
    @Override
    public ResultData flashSaleStart(String venderId, String activityId) {
        ResultData resultData = Tools.getThreadResultData();
        Boolean isOpened=(Boolean)this.getFromHashRedis(baseConfig.getFlashSaleMapKey(),venderId,activityId,baseConfig.getIsOpenedKey());
        if (isOpened!=null&&isOpened) {
            this.startCreateOrderThread(venderId, activityId);
            this.startPayOrderThread(venderId,activityId);
            resultData.setMsg("秒杀接口开启成功！");
            return resultData;
        }
        FlashSaleBean vo= this.flashSaleList(venderId, activityId);
        if(System.currentTimeMillis()<vo.getStartTime()||System.currentTimeMillis()>vo.getEndTime()){
            resultData.setMsg("当前时间未在活动时间内！");
            resultData.setError(ErrorCode.FALI);
            return resultData;
        }
        this.putToHashRedis(baseConfig.getFlashSaleMapKey(),venderId,activityId,baseConfig.getStartTimeKey(),vo.getStartTime());
        this.putToHashRedis(baseConfig.getFlashSaleMapKey(),venderId,activityId,baseConfig.getEndTimeKey(),vo.getEndTime());
        List<FlashSaleProductBean> list=vo.getFlashSaleProductBeanList();
        if(list!=null){
            for (FlashSaleProductBean bean:list) {
                String sku=bean.getSku();
                int count=bean.getCount();
                double price=bean.getPrice();
                this.putToHashRedis(baseConfig.getFlashSaleMapKey(),venderId,activityId,sku+":"+baseConfig.getPriceKey(),price);
                this.putToHashRedis(baseConfig.getFlashSaleMapKey(),venderId,activityId,sku+":"+baseConfig.getCountKey(),count);
            }
        }
        this.startCreateOrderThread(venderId, activityId);
        this.startPayOrderThread(venderId,activityId);
        this.putToHashRedis(baseConfig.getFlashSaleMapKey(),venderId,activityId,baseConfig.getIsOpenedKey(),true);
        resultData.setMsg("秒杀接口开启成功！");
        return resultData;
    }

    /**
     * 检查30分钟内未支付的订单
     * @param venderId
     * @param activityId
     */
    private void startPayOrderThread(String venderId, String activityId) {
        ThreadPoolTaskExecutor executor =this.getExecutorFromMap(venderId,activityId,baseConfig.getExecutorPayKey());
        if(executor!=null&&executor.getThreadPoolExecutor()!=null&&!executor.getThreadPoolExecutor().isShutdown()){
            logger.info("检查支付状态线程已开启！");
            return;
        }
        if(executor == null){
            executor= new ThreadPoolTaskExecutor();
            executor.setBeanName("PayOrder"+":"+venderId+":"+activityId);
            executor.initialize();
        }
        if (executor.getThreadPoolExecutor()==null||executor.getThreadPoolExecutor().isShutdown()) {
            executor.initialize();
        }
        this.setExecutorToMap(venderId,activityId,baseConfig.getExecutorPayKey(),executor);
        executor.execute(new Runnable() {
            @Override
            public void run() {
                checkPayOrder(venderId, activityId);
            }
        });
    }
    private void checkPayOrder(String venderId,String activityId){
        while(true) {
            PayOrderBean firstOrder = (PayOrderBean) redisTemplate.opsForList().index(this.getVenderActivityKey(venderId,activityId,baseConfig.getFlashSalePayOrderListKey()), 0);
            Boolean isOpened=(Boolean)this.getFromHashRedis(baseConfig.getFlashSaleMapKey(),venderId,activityId,baseConfig.getIsOpenedKey());
            if ((isOpened==null||!isOpened)&& firstOrder == null) {
                return;
            }
            if(firstOrder==null||System.currentTimeMillis()-firstOrder.getTime()<baseConfig.getPayOrderMinutes()*60*1000){
                continue;
            }
            PayOrderBean order = (PayOrderBean) redisTemplate.opsForList().rightPop(this.getVenderActivityKey(venderId,activityId,baseConfig.getFlashSalePayOrderListKey()));
            if(StringUtils.isEmpty(order.getOrderNo())){
                continue;
            }
            try {
                //查询支付状态
                ResultData<GetOrderDetailResponse> resultData = bmsService.orderDetail(order.getOrderNo(), "mac",order.getCookie());
                GetOrderDetailResponse response = resultData.getData();
                if (response != null) {
                    if (response.getPayStatus() == 0) {
                        this.updateStockCount(venderId, activityId, order.getOrderNo(), order.getCount(), 1);
                    }
                }
            }catch (Exception e){
                logger.info("查询订单支付状态失败:"+e.getMessage()+"订单号："+order.getOrderNo());
                e.printStackTrace();
            }
        }
    }
    /**
     * 下单线程
     */
    private void startCreateOrderThread(String venderId, String activityId) {
        ThreadPoolTaskExecutor executor =this.getExecutorFromMap(venderId,activityId,baseConfig.getExecutorKey());
        if(executor!=null&&executor.getThreadPoolExecutor()!=null&&!executor.getThreadPoolExecutor().isShutdown()){
            logger.info("下单线程已开启！");
            return;
        }
        if(executor == null){
            executor=this.getThreadPoolTaskExecutor(venderId,activityId,baseConfig.getThreadPoolSize(),baseConfig.getThreadPoolMaxSize(),baseConfig.getAwaitTerminationSeconds(),"createOrderExecutor");
            executor.initialize();
        }
        if (executor.getThreadPoolExecutor()==null||executor.getThreadPoolExecutor().isShutdown()) {
            executor.initialize();
        }
        this.setExecutorToMap(venderId,activityId,baseConfig.getExecutorKey(),executor);
        for (int i = 0; i < baseConfig.getThreadPoolMaxSize(); i++) {
            executor.execute(new Runnable() {
                @Override
                public void run() {
                    createOrder(venderId, activityId);
                }
            });
        }
    }

    private void createOrder(String venderId, String activityId) {
        while (true) {
            OrderParams vo = (OrderParams) redisTemplate.opsForList().rightPop(this.getVenderActivityKey(venderId, activityId, baseConfig.getFlashSaleListKey()));
            Boolean isOpened=(Boolean)this.getFromHashRedis(baseConfig.getFlashSaleMapKey(),venderId,activityId,baseConfig.getIsOpenedKey());
            if ((isOpened==null||!isOpened)&& vo == null) {
                return;
            }
            if (vo == null) {
                continue;
            }
            try {
                ResultData<OrderResponse> order = bmsService.createOrder(vo);
                if (order.getError() != ErrorCode.SUCCESS) {
                    logger.info("秒杀异步下单失败,下单信息:" + vo.toString());
                    //更新库存量
                    this.updateStockCount(vo.getVenderId(),vo.getActivityId(),vo.getSku(),vo.getCount(), 1);
                } else {
                    logger.info("秒杀异步下单成功,下单信息:" + vo.toString());
                    //根据requestId设置下单成功状态
                    this.putToHashRedis(baseConfig.getFlashSaleOrderState(),venderId,activityId,vo.getRequestId(),1);
                    //30分钟后如果未支付，更新库存量
                    OrderResponse orderResponse=order.getData();
                    String orderNo="";
                    if(orderResponse!=null){
                        orderNo=orderResponse.getOrderSn();
                    }
                    PayOrderBean payOrderBean=new PayOrderBean();
                    payOrderBean.setOrderNo(orderNo);
                    payOrderBean.setTime(System.currentTimeMillis());
                    payOrderBean.setCount(vo.getCount());
                    payOrderBean.setCookie(vo.getCookie());
                    redisTemplate.opsForList().leftPush(this.getVenderActivityKey(vo.getVenderId(),vo.getActivityId(),baseConfig.getFlashSalePayOrderListKey()),payOrderBean);
                }
            } catch (Exception e) {
                e.printStackTrace();
                logger.info("秒杀异步下单失败:"+e.getMessage()+",下单信息:" + vo.toString());
                //更新库存量
                this.updateStockCount(vo.getVenderId(),vo.getActivityId(),vo.getSku(),vo.getCount(), 1);
            }
        }
    }
    /**
     * 初始化秒杀后台配置到redis TODO
     *
     * @return
     */
    private FlashSaleBean flashSaleList(String venderId, String activityId) {
        FlashSaleBean vo=new FlashSaleBean();
        List<FlashSaleProductBean> list=new ArrayList<>();
        FlashSaleProductBean bean1=new FlashSaleProductBean();
        bean1.setSku("5378771");
        bean1.setPrice(100d);
        bean1.setCount(10);
        FlashSaleProductBean bean2=new FlashSaleProductBean();
        bean2.setSku("5378772");
        bean2.setPrice(200d);
        bean2.setCount(20000);
        FlashSaleProductBean bean3=new FlashSaleProductBean();
        bean3.setSku("5378773");
        bean3.setPrice(300d);
        bean3.setCount(30000);
        list.add(bean1);
        list.add(bean2);
        list.add(bean3);
        vo.setFlashSaleProductBeanList(list);
        vo.setVenderId(venderId);
        vo.setActivityId(activityId);
        vo.setStartTime(System.currentTimeMillis()-24*60*60*1000);
        vo.setEndTime(System.currentTimeMillis()+24*60*60*1000);
        return vo;
    }

    /**
     * 获取库存数量
     *
     * @param venderId
     * @param activityId
     * @param sku
     * @return
     */
    private int getStockCount(String venderId, String activityId, String sku) {
        Integer count =(Integer)this.getFromHashRedis(baseConfig.getFlashSaleMapKey(),venderId,activityId,sku+":"+baseConfig.getCountKey());
        return count == null ? 0 : count;
    }

    /**
     * 设置库存数量
     *
     * @param venderId
     * @param activityId
     * @param sku
     * @param count
     */
    private void setStockCount(String venderId, String activityId, String sku, int count) {
        this.putToHashRedis(baseConfig.getFlashSaleMapKey(),venderId,activityId,sku+":"+baseConfig.getCountKey(),count);
    }

    /**
     * 关闭线程
     *
     * @param venderId
     * @return
     */
    @Override
    public ResultData flashSaleEnd(String venderId, String activityId) {
        ResultData resultData = Tools.getThreadResultData();
        //如果队列不为空，则不予关闭
        int size = redisTemplate.opsForList().size(this.getVenderActivityKey(venderId, activityId, baseConfig.getFlashSaleListKey())).intValue();
        if (size > 0) {
            resultData.setError(ErrorCode.FALI);
            resultData.setMsg("秒杀队列不为空，不予关闭！");
            return resultData;
        }
        ThreadPoolTaskExecutor createOrderExecutor =this.getExecutorFromMap(venderId,activityId,baseConfig.getExecutorKey());
        if (createOrderExecutor == null|| createOrderExecutor.getThreadPoolExecutor()==null||createOrderExecutor.getThreadPoolExecutor().isShutdown()) {
            resultData.setMsg("下单线程已关闭！");
            return resultData;
        }
        try {
            createOrderExecutor.shutdown();
        } catch (Exception e) {
            e.printStackTrace();
            logger.info("下单线程关闭失败");
        }
        this.setExecutorToMap(venderId,activityId,baseConfig.getExecutorKey(),createOrderExecutor);
        this.putToHashRedis(baseConfig.getFlashSaleMapKey(),venderId,activityId,baseConfig.getIsOpenedKey(),false);
        //清除秒杀基本数据
        redisTemplate.delete(this.getVenderActivityKey(venderId,activityId,baseConfig.getFlashSaleMapKey()));
        redisTemplate.delete(this.getVenderActivityKey(venderId,activityId,baseConfig.getFlashSaleOrderState()));
        redisTemplate.opsForSet().remove(this.getVenderActivityKey(venderId,activityId,baseConfig.getRequestId()));
        //关闭支付线程
        ThreadPoolTaskExecutor payOrderExecutor =this.getExecutorFromMap(venderId,activityId,baseConfig.getExecutorPayKey());
        if (payOrderExecutor == null|| payOrderExecutor.getThreadPoolExecutor()==null||payOrderExecutor.getThreadPoolExecutor().isShutdown()) {
            resultData.setMsg("检查支付状态线程已关闭！");
            return resultData;
        }
        try {
            payOrderExecutor.shutdown();
        } catch (Exception e) {
            e.printStackTrace();
            logger.info("检查支付状态线程关闭失败");
        }
        this.setExecutorToMap(venderId,activityId,baseConfig.getExecutorPayKey(),payOrderExecutor);
        resultData.setMsg("秒杀接口关闭成功！");
        return resultData;
    }

    @Override
    public ResultData flashSale(OrderParams vo) {
        ResultData resultData= Tools.getThreadResultData();
        //判断是否已经关闭
        Boolean isOpened=(Boolean)this.getFromHashRedis(baseConfig.getFlashSaleMapKey(),vo.getVenderId(),vo.getActivityId(),baseConfig.getIsOpenedKey());
        ThreadPoolTaskExecutor createOrderExecutor =this.getExecutorFromMap(vo.getVenderId(),vo.getActivityId(),baseConfig.getExecutorKey());
        if (isOpened==null||!isOpened||createOrderExecutor == null|| createOrderExecutor.getThreadPoolExecutor()==null||createOrderExecutor.getThreadPoolExecutor().isShutdown()) {
            resultData.setMsg("秒杀活动未开启！");
            resultData.setError(ErrorCode.SERVER_EXCEPTION);
            return resultData;
        }
        //设置价格
        Double price=(Double) this.getFromHashRedis(baseConfig.getFlashSaleMapKey(),vo.getVenderId(),vo.getActivityId(),vo.getSku()+":"+baseConfig.getPriceKey());
        vo.setPrice(new BigDecimal(price==null?0:price));
        //判断时间
        Long startTime=(Long)this.getFromHashRedis(baseConfig.getFlashSaleMapKey(),vo.getVenderId(),vo.getActivityId(),baseConfig.getStartTimeKey());
        Long endTime=(Long)this.getFromHashRedis(baseConfig.getFlashSaleMapKey(),vo.getVenderId(),vo.getActivityId(),baseConfig.getEndTimeKey());
        if(System.currentTimeMillis()<(startTime==null?0l:startTime)||System.currentTimeMillis()>(endTime==null?0l:endTime)){
            resultData.setMsg("当前时间未在活动时间内！");
            resultData.setError(ErrorCode.FALI);
            return resultData;
        }
        //获取库存数量
        int count=this.getStockCount(vo.getVenderId(),vo.getActivityId(),vo.getSku());
        if (count - vo.getCount() < 0) {
            resultData.setMsg("手速慢了,没抢到！");
            resultData.setError(ErrorCode.NO_PRODUCT);
            return resultData;
        }
        boolean result = this.push(vo);
        //返回
        if (!result) {
            resultData.setMsg("手速慢了,没抢到！");
            resultData.setError(ErrorCode.NO_PRODUCT);
        }
        resultData.setMsg("排队中...");
        return resultData;
    }

    @Override
    public ResultData getOrderState(String venderId, String activityId, String requestId) {
        ResultData resultData=Tools.getThreadResultData();
        Integer orderState = (Integer)this.getFromHashRedis(baseConfig.getFlashSaleOrderState(),venderId,activityId,requestId);
        if(orderState==1){
            resultData.setMsg("抢购成功，请在"+baseConfig.getPayOrderMinutes()+"分钟内支付");
        }else{
            resultData.setError(ErrorCode.FALI);
            resultData.setMsg("排队中...");
        }
        return resultData;
    }

    /**
     * 入队
     *
     * @param vo
     * @return
     */
    private boolean push(OrderParams vo) {
        //表单重复提交校验
        int flag=redisTemplate.opsForSet().add(this.getVenderActivityKey(vo.getVenderId(),vo.getActivityId(),baseConfig.getRequestId()),vo.getRequestId()).intValue();
        if(flag==0){
            return false;
        }
        int result = redisTemplate.opsForList().leftPush( this.getVenderActivityKey(vo.getVenderId(),vo.getActivityId(),baseConfig.getFlashSaleListKey()), vo).intValue();
        if (result > 0) {
            this.updateStockCount(vo.getVenderId(),vo.getActivityId(),vo.getSku(),vo.getCount(), -1);
            return true;
        }
        return false;
    }


    /**
     * 更新总量
     * @param venderId
     * @param activityId
     * @param sku
     * @param pCount
     * @param type
     */
    private synchronized void updateStockCount(String venderId,String activityId,String sku,Integer pCount, int type) {
        int count = 0;
        if (type == -1) {
            count = this.getStockCount(venderId, activityId, sku) - pCount;
        } else if (type == 1) {
            count = this.getStockCount(venderId, activityId, sku) + pCount;
        }
        this.setStockCount(venderId,activityId,sku, count);
    }

    /**
     * 初始化线程池执行器
     * @param venderId
     * @param activityId
     * @return
     */
    private ThreadPoolTaskExecutor getThreadPoolTaskExecutor(String venderId,String activityId,int poolSize,int poolMaxSize,int awaitTerminationSeconds,String beanName) {
        ThreadPoolTaskExecutor pool = new ThreadPoolTaskExecutor();
        pool.setCorePoolSize(poolSize);//线程池基本大小
        pool.setMaxPoolSize(poolMaxSize);//线程池最大大小
        pool.setWaitForTasksToCompleteOnShutdown(false);//设置线程池关闭的时候等待所有任务都完成再继续销毁其他的Bean
        pool.setAwaitTerminationSeconds(awaitTerminationSeconds);//设置线程池中任务的等待时间，如果超过这个时候还没有销毁就强制销毁，以确保应用最后能够被关闭，而不是阻塞住。
        pool.setBeanName(beanName+":"+venderId+":"+activityId);
        return pool;
    }
    private String getVenderActivityKey(String venderId,String activityId,String key){
        return key+":"+venderId+":"+activityId;
    }
    private Object getFromHashRedis(String h,String venderId,String activityId,String hkey){
        return redisTemplate.opsForHash().get(this.getVenderActivityKey(venderId,activityId,h),hkey);
    }
    private void putToHashRedis(String h,String venderId,String activityId,String hkey,Object obj){
        redisTemplate.opsForHash().put(this.getVenderActivityKey(venderId,activityId,h),hkey,obj);
    }
    private ThreadPoolTaskExecutor getExecutorFromMap(String venderId,String activityId,String key){
        ThreadPoolTaskExecutor executor=(ThreadPoolTaskExecutor)mapExecutor.get(key+":"+venderId+":"+activityId);
        return executor;
    }
    private void setExecutorToMap(String venderId,String activityId,String key,Object executor){
        mapExecutor.put(key+":"+venderId+":"+activityId,executor);
    }
}

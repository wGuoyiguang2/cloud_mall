package com.hqtc.bms.service.impl;

import com.hqtc.bms.controller.InvoiceController;
import com.hqtc.bms.model.base.SendMessageDao;
import com.hqtc.bms.model.mapper.AfterSaleTaskBatchMapper;
import com.hqtc.bms.model.mapper.AfterSaleTaskMapper;
import com.hqtc.bms.model.params.*;
import com.hqtc.bms.model.rpc.ProductsOriginInfoBean;
import com.hqtc.bms.service.AfterSaleJdService;
import com.hqtc.bms.service.AfterSaleTaskService;
import com.hqtc.bms.service.BmsOrderSercice;
import com.hqtc.bms.service.ImsAddressService;
import com.hqtc.bms.service.rpc.RPCProductInfoService;
import com.hqtc.bms.task.AftersaleTask;
import com.hqtc.common.response.ResultData;
import com.hqtc.common.utils.Tools;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import java.util.*;
import static com.hqtc.common.utils.Tools.getThreadResultData;

/**
 * description:售后物流定时任务
 * Created by laiqingchuang on 18-7-25 .
 */
@Service
public class AfterSaleTaskServiceImpl implements AfterSaleTaskService {
    private Logger logger  = LoggerFactory.getLogger(getClass());
    @Autowired
    AfterSaleTaskMapper asMapper;
    @Autowired
    AfterSaleTaskBatchMapper asBatchMapper;
    @Autowired
    ImsAddressService imsAddressService;
    @Autowired
    RPCProductInfoService productService;
    @Autowired
    BmsOrderSercice bmsOrderSercice;
    @Autowired
    InvoiceController invoice;
    @Autowired
    SendMessageDao sendMessageDao;
    @Autowired
    AfterSaleJdService afterSaleJdService;
    @Autowired
    AftersaleTask aftersaleTask;
    @Value("${service.config.serviceSize}")
    private int serviceSize;

    /**
     * 获取待收货子订单
     */
    @Override
    public List<OrderProductBean> getOrderProducBeanList() {
        List<OrderProductBean> list=asMapper.getOrderProducBeanList();
        return list;
    }

    /**
     * 更新订单状态
     * @param list
     */
    @Override
    public int updateOrderStat(List<OrderProductBean> list) {
        int num=asBatchMapper.updateOrderState(list);
        /*String childTradeNos="";
        for(OrderProductBean bean:list){
            childTradeNos+=bean.getChildTradeNo()+",";
        }
        childTradeNos=childTradeNos.substring(0,childTradeNos.length() - 1);
        batchInvoice(childTradeNos,"","1");*/
        return num;
    }

    /**
     * 获取订单信息
     * @param jdOrderId
     */
    @Override
    public OrderInfoResponseParams getOrderInfo(Long jdOrderId) {
        OrderInfoResponseParams orderInfo=asMapper.getOrderInfo(jdOrderId);
        OrderInfoResponseParams addreBean=imsAddressService.getAddressInfo(orderInfo.getCountyId(),orderInfo.getTownId()).getData();
        orderInfo.setProvinceName(addreBean.getProvinceName());
        orderInfo.setCityName(addreBean.getCityName());
        orderInfo.setCountyName(addreBean.getCountyName());
        orderInfo.setTownName(addreBean.getTownName());
        return orderInfo;
    }

    /**
     * 添加售后流水信息
     * @param
     */
    @Override
    public ResultData addOrderAftersale(OrderAftersaleBean bean,int maxSeq,int skuNum) {
        ResultData result = getThreadResultData();
        //获取商品信息
        OrderAftersaleBean productInfo=asMapper.getProductInfo(bean);
        bean.setOrderSn(productInfo.getOrderSn());        //平台订单号
        bean.setProductName(productInfo.getProductName());//商品名称
        bean.setPrice(productInfo.getPrice());            //商品价格
        bean.setVenderid(productInfo.getVenderid());
        bean.setUserId(productInfo.getUserId());
        //获取订单售后最大序号
        int tbMaxSeq=asMapper.getTbMaxSeq(bean.getChildTradeNo());
        if(tbMaxSeq > maxSeq){
            maxSeq=tbMaxSeq;
        }
        int size=maxSeq+skuNum;
        ArrayList<Integer> seqList = new ArrayList<>();
        for(int i=maxSeq+1;i<=size;i++){
            seqList.add(i);
        }
        //添加售后流水
        int num=asBatchMapper.addOrderAftersale(bean,seqList);
        //同步售后流水
        if(num>0){
            Timer timer=new Timer();
            timer.schedule(new TimerTask(){
                public void run(){
                    List<OrderAftersaleBean> orderAftersaleBeans = new ArrayList<>();
                    for(int seq:seqList){
                        OrderAftersaleBean orderAftersaleBean = new OrderAftersaleBean();
                        orderAftersaleBean.setChildTradeNo(bean.getChildTradeNo());
                        orderAftersaleBean.setSeq(seq);
                        orderAftersaleBeans.add(orderAftersaleBean);
                    }
                    aftersaleTask.updateServiceNos(orderAftersaleBeans);
                    this.cancel();}
            },1500);
            result.setData(true);
        }else {
            result.setMsg("申请售后失败");
            result.setData(false);
        }
        return result;
    }

    /**
     * 更新取消服务单信息
     * @param requestParams
     */
    @Override
    public int updateCancelService(ServiceRequestParams requestParams) {
        int num=asBatchMapper.updateCancelService(requestParams);
        return num;
    }

    /**
     * 获取需要更新的记录
     */
    @Override
    public List<Integer> getNeedUpdateList() {
        int num=asMapper.updateCompleteServices();
        return asMapper.getNeedUpdateList();
    }

    /**
     * 更新售后流水
     */
    @Override
    public int updateAftersaleState(List<OrderAftersaleBean> list) {
        int num=asBatchMapper.updateAftersaleState(list);

        String serviceNos="";
        for(OrderAftersaleBean bean: list){
            if(bean.getStatus().equals(40)){
                serviceNos+=String.valueOf(bean.getServiceNo())+",";
            }
        }
        List<OrderAftersaleBean> refundList=null;
        if(!serviceNos.equals("")){
            serviceNos=serviceNos.substring(0,serviceNos.length() - 1);
            refundList=asMapper.getServices(serviceNos);
        }
        if(refundList!=null && refundList.size()!=0){
            for(OrderAftersaleBean bean: refundList){
                RefundUserParams refundParam = new RefundUserParams();
                refundParam.setOrderSn(bean.getOrderSn());
                refundParam.setVenderId(bean.getVenderid());
                refundParam.setRefundReason(bean.getReason());
                refundParam.setEventId(Tools.md5(String.valueOf(bean.getServiceNo())+"aftersale"));
                Map<String, Integer> map = new HashMap<String, Integer>();
                map.put(bean.getProductId().toString(),bean.getCount());
                refundParam.setProducts(map);
                //退款给大客户和用户
                ResultData rd=bmsOrderSercice.refund(refundParam);
                logger.info("售后退款order_sn= " + bean.getOrderSn());
            }
        }
        //batchInvoice("", serviceNos, "2");
        return num;
    }

    /**
     * 根据订单号获取售后列表
     */
    @Override
    public List<OrderAftersaleBean> getAftersaleListByOrderSn(String orderSn, Integer type) {
        List<OrderAftersaleBean> list=asMapper.getAftersaleListByOrderSn(orderSn,type);
        if(list.size() !=0){
            String sku="";
            for(OrderAftersaleBean bean: list){
                sku+=bean.getProductId()+",";
            }
            List<ProductsOriginInfoBean> homeList = productService.getHomeRecommend(sku).getData();
            for(OrderAftersaleBean bean: list){
                if(homeList !=null && homeList.size() !=0){
                    for(ProductsOriginInfoBean home: homeList){
                        if(bean.getProductId().equals(home.getSku())){
                            bean.setImagePath(home.getImagePath());
                        }
                    }
                }
            }
        }
        return list;
    }

    /**
     * 根据用户id获取售后数量
     */
    @Override
    public int getAftersaleCountByUserId(Integer userId) {
        return asMapper.getAftersaleCountByUserId(userId);
    }

    /**
     * 根据用户id获取售后列表
     */
    @Override
    public List<AftersaleBean> getAftersaleListByUserId(Integer userId, Integer offset, Integer limit) {
        List<AftersaleBean> list=asMapper.getAftersaleListByUserId(userId,offset,limit);
        if(list.size() !=0){
            String sku="";
            for(AftersaleBean bean: list){
                sku+=bean.getProductId()+",";
            }
            List<ProductsOriginInfoBean> homeList = productService.getHomeRecommend(sku).getData();
            for(AftersaleBean bean: list){
                if(homeList !=null && homeList.size() !=0){
                    for(ProductsOriginInfoBean home: homeList){
                        if(bean.getProductId().equals(home.getSku())){
                            bean.setImagePath(home.getImagePath());
                        }
                    }
                }
            }
        }
        return list;
    }

    /**
     * 更新服务单号
     * @param list
     */
    @Override
    public int updateServiceNos(List<OrderAftersaleBean> list) {

        return asBatchMapper.updateServiceNos(list);
    }

    /**
     * 获取需要更新的服务单
     * @return
     */
    @Override
    public List<OrderAftersaleBean> getUpdateAftersaleList() {

        return asMapper.getUpdateAftersaleList();
    }

    /**
     * 校验是否是该用户下的订单
     * @return
     */
    @Override
    public Boolean checkJdOrderId(Integer userId, Long jdOrderId) {
        int count=asMapper.getJdOrderIdCount(userId,jdOrderId);
        if(count==0){
            return false;
        }
        return true;
    }

    /**
     * 根据JD订单号获取售后列表
     * @return
     */
    @Override
    public List<AftersaleForeignBean> getAftersaleListByJdOrderId(Long jdOrderId, Integer type) {
        List<AftersaleForeignBean> list = asMapper.getAftersaleListByJdOrderId(jdOrderId, type);
        if(list.size() !=0){
            String sku="";
            for(AftersaleForeignBean bean: list){
                sku+=bean.getProductId()+",";
            }
            List<ProductsOriginInfoBean> homeList = productService.getHomeRecommend(sku).getData();
            if(homeList !=null && homeList.size() !=0){
                for(AftersaleForeignBean bean: list){
                    for(ProductsOriginInfoBean home: homeList){
                        if(bean.getProductId().equals(home.getSku())){
                            bean.setImagePath(home.getImagePath());
                        }
                    }
                }
            }
        }
        return list;
    }

    /**
     * 批量开发票
     * @param childTradeNos
     * @param serviceNos
     * @param kptype
     */
    private void batchInvoice(String childTradeNos, String serviceNos, String kptype) {
        //开蓝票(订单完成时)
        if(kptype.equals("1") && childTradeNos !=null && !childTradeNos.equals("")){
            //查询需要开发票的订单
            List<OrderMainBean> mainBeanList=asMapper.getMainBeanList(childTradeNos);
            if(mainBeanList !=null && mainBeanList.size() !=0){
                for(OrderMainBean bean: mainBeanList){
                    OrderMainBean inv=imsAddressService.getInvoiceInfo(bean.getUserId()).getData();
                    if(inv !=null){
                        ResultData r = invoice.addInvoice(bean.getOrderSn(), "1", inv.getInvoiceHead(), inv.getPhone(), inv.getEmail(), inv.getTaxnum());
                    }
                }
            }
        }
        //开红票(售后退货退款完成时)
        if(kptype.equals("2") && serviceNos !=null && !serviceNos.equals("")){
            //查询需要开发票的订单
            List<OrderMainBean> mainBeanList=asMapper.getMainBeanList2(serviceNos);
            if(mainBeanList !=null && mainBeanList.size() !=0){
                for(OrderMainBean bean: mainBeanList){
                    ResultData r = invoice.addInvoice(bean.getOrderSn(), "2", "", "", "", "");
                }
            }
        }
    }

}
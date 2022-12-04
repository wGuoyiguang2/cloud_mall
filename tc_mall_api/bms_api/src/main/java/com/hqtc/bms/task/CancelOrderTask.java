package com.hqtc.bms.task;

import com.hqtc.bms.service.OrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


/**
 * Created by wanghaoyang on 18-9-13.
 * 此定时任务用来处理那些在一定时间内没有支付的订单
 * 如果在规定时间内未支付,则把此订单置为已取消,通知jd释放库存
 */
@Component
public class CancelOrderTask {

    @Value("${order.cancel.expireTime}")
    private int expireTime;

    @Value("${order.cancel.eachSize}")
    private int eachSize;

    @Autowired
    private OrderService orderService;

    private Logger logger = LoggerFactory.getLogger("CancelOrderTask");

    @Scheduled(fixedRate = 1000 * 60 * 60)
    public void cancelOrder(){
        logger.info("开始处理过期未支付的订单:{}", this.getcurrentTime());
        List<String> orderSns = this.getUnPayedOrder();
        if(null == orderSns){
            logger.error("查询未支付订单失败");
            return;
        }
        if(orderSns.isEmpty()){
            logger.info("未查询到过期未支付的订单需要处理");
            return;
        }
        for (String orderSn: orderSns){
            boolean resultData = orderService.cancelOrder(orderSn);
            if(!resultData){
                logger.error("处理过期未支付的订单失败:{}", orderSn);
            }else {
                logger.info("处理过期未支付的订单:{}:{}", orderSn,  resultData);
            }
        }

    }

    private List<String> getUnPayedOrder(){
        return orderService.getUnPayedOrderSnLimit(eachSize, expireTime);
    }

    private String  getcurrentTime(){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return simpleDateFormat.format(new Date());
    }
}

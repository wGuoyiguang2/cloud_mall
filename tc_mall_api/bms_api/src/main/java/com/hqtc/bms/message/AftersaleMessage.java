package com.hqtc.bms.message;

import com.hqtc.bms.model.params.MessageInfo;
import com.hqtc.bms.model.params.OrderAftersaleBean;
import com.hqtc.bms.model.params.ServiceInfoDto;
import com.hqtc.bms.model.params.ServiceListPageResponseParams;
import com.hqtc.bms.service.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * description:售后接收消息
 * Created by laiqingchuang on 18-9-5 .
 */
//@EnableBinding({MessageSink.class})
public class AftersaleMessage {

    Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    SendMessageService sendMessageService;
    @Autowired
    AfterSaleJdService afterSaleJdService;
    @Autowired
    AfterSaleTaskService afterSaleTaskService;

    //@StreamListener(MessageSink.INPUT_BMS_AFTERSALE)
    public void listen_bms_aftersale(MessageInfo messageInfo) {
        logger.info("售后开始处理消息.............");
        Map<String, ServiceListPageResponseParams> serviceMap = afterSaleJdService.getServiceListPage(Long.valueOf(messageInfo.getChildTradeNo()), 1, 2000);
        ServiceListPageResponseParams serviceParam = serviceMap.get("biz_afterSale_serviceListPage_query_response");
        int maxSeq = 0;
        if (serviceParam != null && serviceParam.getResult() != null && serviceParam.getResult().getServiceInfoList() != null && serviceParam.getResult().getServiceInfoList().size() > 0) {
            maxSeq = serviceParam.getResult().getServiceInfoList().size();
        }
        if (maxSeq >= messageInfo.getSize()) {
            List<ServiceInfoDto> serviceInfoList = serviceParam.getResult().getServiceInfoList();
            sort(serviceInfoList);
            OrderAftersaleBean orderAftersaleBean;
            ArrayList<OrderAftersaleBean> orderAftersaleBeanList = new ArrayList<OrderAftersaleBean>();
            for (int seq : messageInfo.getSeqList()) {
                orderAftersaleBean=new OrderAftersaleBean();
                orderAftersaleBean.setChildTradeNo(messageInfo.getChildTradeNo());
                orderAftersaleBean.setSeq(seq);
                orderAftersaleBean.setServiceNo(serviceInfoList.get(seq - 1).getAfsServiceId());
                orderAftersaleBeanList.add(orderAftersaleBean);
            }
            int num = afterSaleTaskService.updateServiceNos(orderAftersaleBeanList);
            logger.info("售后处理消息成功:更新服务单号数量为" + num);
        } else {
            logger.info("售后重新发送消息...............");
            Timer timer=new Timer();
            timer.schedule(new TimerTask(){
                public void run(){
                    boolean result = sendMessageService.sendAftersaleMessage(messageInfo);
                    logger.info("售后重新发送消息:" + result);
                    this.cancel();}
            },1000);
        }
    }

    /**
     * 服务单按照时间升序排列
     * @param list
     * @return
     */
    private void sort(List<ServiceInfoDto> list) {
        Collections.sort(list, new Comparator<ServiceInfoDto>() {
            @Override
            public int compare(ServiceInfoDto o1, ServiceInfoDto o2) {
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                try {
                    Date dt1 = format.parse(o1.getAfsApplyTime());
                    Date dt2 = format.parse(o2.getAfsApplyTime());
                    if (dt1.getTime() > dt2.getTime()) {
                        return 1;
                    } else if (dt1.getTime() < dt2.getTime()) {
                        return -1;
                    } else {
                        return 0;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return 0;
            }
        });
    }

}
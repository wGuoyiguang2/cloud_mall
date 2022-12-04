package com.hqtc.bms.task;

import com.google.gson.Gson;
import com.hqtc.bms.model.params.*;
import com.hqtc.bms.service.AfterSaleJdService;
import com.hqtc.bms.service.AfterSaleTaskService;
import com.hqtc.bms.service.OrderService;
import com.hqtc.common.utils.JsonTools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * description:物流定时任务
 * Created by laiqingchuang on 18-7-23 .
 */
@Component
public class OrderTrackTask {

    @Autowired
    AfterSaleTaskService asService;
    @Autowired
    OrderService orderService;
    @Autowired
    AfterSaleJdService afterSaleJdService;
    /**
     * 更新订单状态
     */
    @Scheduled(fixedRate = 1000*60*60)
    public void updateOrderStat() {
        //获取待收货子订单 1:待支付|2:已取消|3:待收货|4:拒收|5:已完成
        List<OrderProductBean> orderProducBeanList = asService.getOrderProducBeanList();

        List<OrderProductBean> list = new ArrayList<>();
        if(orderProducBeanList !=null && orderProducBeanList.size() !=0)
            for(OrderProductBean bean: orderProducBeanList){
                //物流状态 0是新建 1是妥投 2是拒收
                Integer state=getState(bean.getChildTradeNo());
                if(state !=null && (state==1 || state==2)){
                    list.add(getOrderProductBean(bean.getChildTradeNo(),state));
                }
            }
            if(list.size() !=0){
                int result=asService.updateOrderStat(list);
                System.out.println("定时任务updateOrderStat---------result="+result);
            }
    }

    /**
     * 获取物流状态
     */
    private Integer getState(String childTradeNo) {
        Integer state=0;
        try{
            String orderResponseParams = orderService.getJdOrderInfo(childTradeNo);
            Map map1 = new Gson().fromJson(orderResponseParams, Map.class);
            String str=JsonTools.getInstance().objectToString(map1.get("jd_kpl_open_selectjdorder_query_response"));
            Object o = JsonTools.getInstance().stringToObject(str, OrderStatResponseParams.class);
            Map<String,OrderStatDto> map3 =JsonTools.getInstance().convertObjToMap(o);
            if(map3.get("result")==null){
                return 0;
            }
            state=map3.get("result").getState();
        }catch (Exception e){
            return state;
        }
        return state;
    }

    /**
     * 添加bean
     */
    private OrderProductBean getOrderProductBean(String childTradeNo,Integer state) {
        OrderProductBean bean = new OrderProductBean();
        bean.setChildTradeNo(childTradeNo);
        if(state==1){
            bean.setOrderState(5);
        }else{
            bean.setOrderState(4);
        }
        return bean;
    }

    /**
     * 更新售后流水
     */
    @Scheduled(fixedRate = 1000*60*60*2)
    public void updateAftersaleState() {
        //获取需要更新的记录
        List<Integer> needUpdateList = asService.getNeedUpdateList();
        List<OrderAftersaleBean> list = new ArrayList<>();
        for(Integer serviceNO:needUpdateList){
            OrderAftersaleBean bean=getOrderAftersaleBean(serviceNO);
            if(bean !=null){
                list.add(bean);
            }
        }
        if(list!=null && list.size()!=0){
            int result=asService.updateAftersaleState(list);
            System.out.println("定时任务updateAftersaleState---------result="+result);
        }
    }

    private OrderAftersaleBean getOrderAftersaleBean(Integer serviceNO) {
        OrderAftersaleBean bean=new OrderAftersaleBean();
        try{
            ServiceRequestParams param = new ServiceRequestParams();
            param.setAfsServiceId(Long.valueOf(String.valueOf(serviceNO)));
            List<Integer> appendInfoSteps = new ArrayList<>();
            appendInfoSteps.add(1);
            appendInfoSteps.add(4);
            param.setAppendInfoSteps(appendInfoSteps);
            Map<String,ServiceDetailInfoResponseParams> map =afterSaleJdService.getServiceDetail(param);
            ServiceDetailInfo detailInfo = map.get("biz_afterSale_serviceDetailInfo_query_response").getResult();
            bean.setServiceNo(detailInfo.getAfsServiceId().intValue());
            bean.setStatus(detailInfo.getAfsServiceStep());
            List<ServiceTrackInfoDTO> list=detailInfo.getServiceTrackInfoDTOs();
            bean.setRemarks(list.get(list.size()-1).getContext());
        }catch (Exception e){
            return null;
        }
        return bean;
    }


}

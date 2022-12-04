package com.hqtc.bms.controller;

import com.google.gson.Gson;
import com.hqtc.bms.config.OrderStatEnum;
import com.hqtc.bms.config.Router;
import com.hqtc.bms.config.VenderRouter;
import com.hqtc.bms.model.params.*;
import com.hqtc.bms.service.AfterSaleJdService;
import com.hqtc.bms.service.OrderService;
import com.hqtc.common.config.ErrorCode;
import com.hqtc.common.response.ResultData;
import com.hqtc.common.utils.JsonTools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.Map;
import static com.hqtc.common.utils.Tools.getThreadResultData;

/**
 * description:查询配送信息
 * Created by laiqingchuang on 18-7-13 .
 */
@RestController
public class OrderTrackController {

    @Autowired
    AfterSaleJdService afterSaleJdService;
    @Autowired
    OrderService orderService;

    @RequestMapping(value= {Router.ORDERTRACK_INFO, VenderRouter.ROUTER_VENDER_TRACK}, method = RequestMethod.POST)
    public ResultData getOrderTrack(OrderTrackRequestParams param){
        ResultData result = getThreadResultData();
        if(param.getJdOrderId()==null || param.getJdOrderId().equals("")){
            result.setError(ErrorCode.MISS_PARAM);
            result.setMsg("缺少参数");
            return result;
        }
        Map<String, Object> map = new HashMap<>();
        //物流信息
        Map<String,OrderTrackResponseParams> map0 = afterSaleJdService.getOrderTrack(param);
        if(map0==null || map0.get("biz_order_orderTrack_query_response")==null || map0.get("biz_order_orderTrack_query_response").getResult()==null || map0.get("biz_order_orderTrack_query_response").getResult().getOrderTrack()==null){
            result.setError(ErrorCode.NO_ORDER);
            result.setMsg("订单不存在");
            return result;
        }
        map.put("orderTrack",map0.get("biz_order_orderTrack_query_response").getResult().getOrderTrack());

        //订单状态
        String orderResponseParams = orderService.getJdOrderInfo(param.getJdOrderId());
        Map map1 = new Gson().fromJson(orderResponseParams, Map.class);
        if(map1==null || map1.get("jd_kpl_open_selectjdorder_query_response")==null){
            result.setError(ErrorCode.NO_ORDER);
            result.setMsg("订单不存在");
            return result;
        }
        String str=JsonTools.getInstance().objectToString(map1.get("jd_kpl_open_selectjdorder_query_response"));
        Object o = JsonTools.getInstance().stringToObject(str, OrderStatResponseParams.class);
        Map<String,OrderStatDto> map3 =JsonTools.getInstance().convertObjToMap(o);
        if(map3==null || map3.get("result")==null || map3.get("result").getJdOrderState()==null){
            result.setError(ErrorCode.NO_ORDER);
            result.setMsg("订单不存在");
            return result;
        }
        String d=map3.get("result").getJdOrderState();
        String jdOrderState= String.valueOf((new Double(Double.parseDouble(d))).intValue());
        JdOrderStateDto orderStatDto = new JdOrderStateDto();
        orderStatDto.setJdOrderState(jdOrderState);
        Map<String,String> ordStatMap=ordStatMap();
        orderStatDto.setJdOrderStateDesc(ordStatMap.get(jdOrderState));
        orderStatDto.setJdOrderId(param.getJdOrderId());
        map.put("orderStat",orderStatDto);

        //物流状态  1生成订单 2等待发货 3运输中 4已完成
        double stat=Double.parseDouble(orderStatDto.getJdOrderState());
        TrackStatDto trackStatDto = new TrackStatDto();
        String jdTrackState="";
        String jdOTrackStateDesc="";
        if(stat>=1 && stat<=6){
            jdTrackState="1";
            jdOTrackStateDesc="生成订单";
        }
        if(stat >=7 && stat<=10){
            jdTrackState="2";
            jdOTrackStateDesc="等待发货";
        }
        if(stat >=11 && stat<=18){
            jdTrackState="3";
            jdOTrackStateDesc="运输中";
        }
        if(stat==19){
            jdTrackState="4";
            jdOTrackStateDesc="已完成";
        }
        trackStatDto.setJdTrackState(jdTrackState);
        trackStatDto.setJdOTrackStateDesc(jdOTrackStateDesc);
        map.put("trackStat",trackStatDto);
        result.setData(map);
        return result;
    }

    private Map<String,String> ordStatMap() {
        Map<String, String> map = new HashMap<>();
        map.put(OrderStatEnum.ORDER_STAT_1.getKey(),OrderStatEnum.ORDER_STAT_1.getValue());
        map.put(OrderStatEnum.ORDER_STAT_2.getKey(),OrderStatEnum.ORDER_STAT_2.getValue());
        map.put(OrderStatEnum.ORDER_STAT_3.getKey(),OrderStatEnum.ORDER_STAT_3.getValue());
        map.put(OrderStatEnum.ORDER_STAT_4.getKey(),OrderStatEnum.ORDER_STAT_4.getValue());
        map.put(OrderStatEnum.ORDER_STAT_5.getKey(),OrderStatEnum.ORDER_STAT_5.getValue());
        map.put(OrderStatEnum.ORDER_STAT_6.getKey(),OrderStatEnum.ORDER_STAT_6.getValue());
        map.put(OrderStatEnum.ORDER_STAT_7.getKey(),OrderStatEnum.ORDER_STAT_7.getValue());
        map.put(OrderStatEnum.ORDER_STAT_8.getKey(),OrderStatEnum.ORDER_STAT_8.getValue());
        map.put(OrderStatEnum.ORDER_STAT_9.getKey(),OrderStatEnum.ORDER_STAT_9.getValue());
        map.put(OrderStatEnum.ORDER_STAT_10.getKey(),OrderStatEnum.ORDER_STAT_10.getValue());
        map.put(OrderStatEnum.ORDER_STAT_11.getKey(),OrderStatEnum.ORDER_STAT_11.getValue());
        map.put(OrderStatEnum.ORDER_STAT_12.getKey(),OrderStatEnum.ORDER_STAT_12.getValue());
        map.put(OrderStatEnum.ORDER_STAT_13.getKey(),OrderStatEnum.ORDER_STAT_13.getValue());
        map.put(OrderStatEnum.ORDER_STAT_14.getKey(),OrderStatEnum.ORDER_STAT_14.getValue());
        map.put(OrderStatEnum.ORDER_STAT_16.getKey(),OrderStatEnum.ORDER_STAT_16.getValue());
        map.put(OrderStatEnum.ORDER_STAT_17.getKey(),OrderStatEnum.ORDER_STAT_17.getValue());
        map.put(OrderStatEnum.ORDER_STAT_18.getKey(),OrderStatEnum.ORDER_STAT_18.getValue());
        map.put(OrderStatEnum.ORDER_STAT_19.getKey(),OrderStatEnum.ORDER_STAT_19.getValue());
        map.put(OrderStatEnum.ORDER_STAT_21.getKey(),OrderStatEnum.ORDER_STAT_21.getValue());
        map.put(OrderStatEnum.ORDER_STAT_22.getKey(),OrderStatEnum.ORDER_STAT_22.getValue());
        map.put(OrderStatEnum.ORDER_STAT_29.getKey(),OrderStatEnum.ORDER_STAT_29.getValue());
        map.put(OrderStatEnum.ORDER_STAT_30.getKey(),OrderStatEnum.ORDER_STAT_30.getValue());
        map.put(OrderStatEnum.ORDER_STAT_31.getKey(),OrderStatEnum.ORDER_STAT_31.getValue());
        return map;
    }
}

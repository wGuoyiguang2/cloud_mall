package com.hqtc.bms.service.impl;

import com.google.gson.Gson;
import com.hqtc.bms.model.database.TOrderProductBean;
import com.hqtc.bms.model.mapper.TOrderMapper;
import com.hqtc.bms.model.mapper.TOrderProductMapper;
import com.hqtc.bms.model.params.OrderStatDto;
import com.hqtc.bms.model.params.OrderStatResponseParams;
import com.hqtc.bms.model.rpc.OrderRequestParams;
import com.hqtc.bms.service.AsyncTaskService;
import com.hqtc.bms.service.rpc.RPCJDService;
import com.hqtc.common.utils.JsonTools;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Created by wanghaoyang on 18-7-18.
 */
@Service("AsyncTaskServiceImpl")
public class AsyncTaskServiceImpl implements AsyncTaskService {

    private Logger logger = LoggerFactory.getLogger("AsyncTaskServiceImpl");

    @Autowired
    private RPCJDService rpcjdService;

    @Autowired
    private TOrderMapper orderMapper;

    @Autowired
    private TOrderProductMapper tOrderProductMapper;

//    @Async
//    @Override
//    public void syncOrderState(List<String> jdOrderIds){
//        for (String jdTradeNo: jdOrderIds){
//            OrderRequestParams orderRequestParams = new OrderRequestParams();
//            orderRequestParams.setJdOrderId(jdTradeNo);
//            orderRequestParams.setQueryExts("jdOrderState");
//            String res = rpcjdService.getOrderInfo(orderRequestParams);
//            if(null == res){
//                logger.error("同步京东订单信息失败:"+jdTradeNo);
//                break;
//            }
//            Map map1 = new Gson().fromJson(res, Map.class);
//            String str= JsonTools.getInstance().objectToString(map1.get("jd_kpl_open_selectjdorder_query_response"));
//            Object o = JsonTools.getInstance().stringToObject(str, OrderStatResponseParams.class);
//            Map<String,OrderStatDto> map3 =JsonTools.getInstance().convertObjToMap(o);
//            String jdOrderState=map3.get("result").getJdOrderState();
//            if(19 ==((int)Float.parseFloat(jdOrderState))){
//                orderMapper.updateOrderStateByTradeNo(4, jdTradeNo);
//            }
//        }
//    }

    /**
     * 通过查看子订单的状态来改变父订单的状态
     * add by wanghaoyang at 20180827
     * 当子订单状态全部高于待收货时, 则更该父订单状态为已完成
     * 当有一个子订单是未收货时, 则父订单仍然为待收货
     * */
    @Async
    @Override
    public void syncOrderState(List<String> OrderSns){
        for (String orderSn: OrderSns){
            List<TOrderProductBean> tOrderProductBeans = tOrderProductMapper.getUnFinishedOrder(orderSn);
            if(null != tOrderProductBeans && tOrderProductBeans.size() == 0){
                orderMapper.updateOrderStateByOrderSn(4, orderSn);
            }
        }
    }
}

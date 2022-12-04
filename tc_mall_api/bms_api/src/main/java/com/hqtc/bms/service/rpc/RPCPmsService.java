package com.hqtc.bms.service.rpc;

import com.hqtc.bms.config.RPCRouter;
import com.hqtc.bms.model.params.AlipayRefundQueryParams;
import com.hqtc.bms.model.params.TcmallRefundQueryBaseInfo;
import com.hqtc.bms.model.params.WechatRefundQueryParams;
import com.hqtc.bms.model.response.PmsResultData;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Map;

/**
 * Created by wanghaoyang on 18-7-6.
 */
@FeignClient(name = "tv-pms" ,url="${RPC.pmsHost}", fallback = RPCPmsServiceFallback.class)
//@FeignClient(name = "tv-pms" ,url="http://localhost:8080", fallback = RPCPmsServiceFallback.class)
public interface RPCPmsService {

    @RequestMapping(value = RPCRouter.ROUTER_PMS_ORDER, method = RequestMethod.POST)
    Map<String, Object> createQrCode(@RequestBody String body);

    @RequestMapping(value = RPCRouter.ROUTER_TC_MALL_PAY_REFUND, method = RequestMethod.POST)
    Map<String, Object> userRefund(@RequestBody String body);

    @RequestMapping(value = RPCRouter.ROUTER_PMS_ORDER_AUTH, method = RequestMethod.POST)
    Map<String, Object> createAuth(@RequestBody String body);

    @RequestMapping(value = RPCRouter.ROUTER_REFUND_WECHAT_QUERY, method = RequestMethod.POST)
    PmsResultData<TcmallRefundQueryBaseInfo> wechatRefundSearch(@RequestBody WechatRefundQueryParams body);

    @RequestMapping(value = RPCRouter.ROUTER_REFUND_ALIPAY_QUERY, method = RequestMethod.POST)
    PmsResultData<TcmallRefundQueryBaseInfo> aliPayRefundSearch(@RequestBody AlipayRefundQueryParams body);

}

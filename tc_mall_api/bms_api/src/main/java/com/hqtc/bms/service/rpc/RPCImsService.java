package com.hqtc.bms.service.rpc;

import com.hqtc.bms.config.RPCRouter;
import com.hqtc.bms.model.rpc.AddressBean;
import com.hqtc.bms.model.rpc.CommonAddressBean;
import com.hqtc.common.config.FeignClientService;
import com.hqtc.common.response.ResultData;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.*;

/**
 * Created by wanghaoyang on 18-7-7.
 */
@FeignClient(name = FeignClientService.IMSAPI, fallback = RPCImsServiceFallback.class)
public interface RPCImsService {
    @RequestMapping(value= RPCRouter.V1_ADDRESSDETAIL,method = RequestMethod.POST)
    ResultData<AddressBean> getAddressById(@RequestParam("id") Integer id);

    @RequestMapping(value = RPCRouter.V1_ADDRESS_INFO, method = RequestMethod.GET)
    ResultData<CommonAddressBean> getCommonAddressById(@RequestParam("townId")int townId,
                                                       @RequestParam("countyId")int countyId,
                                                       @RequestParam("cityId")int cityId,
                                                       @RequestParam("provinceId")int provinceId);

    @RequestMapping(value= RPCRouter.ROUTE_CART_DELETE_BY_USERID,method = RequestMethod.POST)
    ResultData deleteCartProduct(@RequestParam("userId") Integer userId, @RequestParam("sku") String sku);
}

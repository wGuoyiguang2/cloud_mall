package com.hqtc.bms.service.rpc;

import com.hqtc.bms.model.rpc.AddressBean;
import com.hqtc.bms.model.rpc.CommonAddressBean;
import com.hqtc.common.response.ResultData;
import org.springframework.stereotype.Component;

/**
 * Created by wanghaoyang on 18-7-7.
 */
@Component
public class RPCImsServiceFallback implements RPCImsService {

    @Override
    public ResultData<AddressBean> getAddressById(Integer id){
        return null;
    }

    @Override
    public ResultData<CommonAddressBean> getCommonAddressById(int townId,int countyId,int cityId,int provinceId){
        return null;
    }

    @Override
    public ResultData deleteCartProduct(Integer userId, String sku){
        return null;
    }
}

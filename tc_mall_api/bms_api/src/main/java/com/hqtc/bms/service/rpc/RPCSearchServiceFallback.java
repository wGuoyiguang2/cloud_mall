package com.hqtc.bms.service.rpc;

import com.hqtc.bms.model.params.SkuSearchParamsBean;
import com.hqtc.common.response.ResultData;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Created by wanghaoyang on 18-10-10.
 */
@Component
public class RPCSearchServiceFallback implements RPCSearchService {

    @Override
    public ResultData searchProduct(@RequestParam("venderId")int venderId, @RequestParam("skus")String skus
            , @RequestParam("pageNum")int pageNum, @RequestParam("pageSize")int pageSize){
        return null;
    }
}

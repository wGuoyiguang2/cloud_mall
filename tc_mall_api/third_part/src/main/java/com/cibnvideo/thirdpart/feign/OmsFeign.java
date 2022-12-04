package com.cibnvideo.thirdpart.feign;

import com.cibnvideo.thirdpart.config.Router;
import com.cibnvideo.thirdpart.feign.fallback.OmsFallbackFactory;
import com.cibnvideo.thirdpart.model.bean.ProductRemove;
import com.hqtc.common.config.FeignClientService;
import com.hqtc.common.response.ResultData;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Description: OMS调用接口
 * @Author: WangBin
 * @Date: 2019/1/21 18:26
 */
@FeignClient(value=FeignClientService.OMSAPI,fallbackFactory = OmsFallbackFactory.class)
public interface OmsFeign {
    /**
     * 下架
     * @param productRemoves
     * @return
     */
    @PostMapping(Router.V1_OMS_PRODUCT_REMOVE_BATCH_ADD)
    ResultData removeProducts(@RequestBody List<ProductRemove> productRemoves);

    /**
     * 上架
     * @param productRemoves
     * @return
     */
    @PostMapping(Router.V1_OMS_PRODUCT_REMOVE_BATCH_REMOVE_BY_SKUS)
    ResultData addProducts(@RequestBody List<ProductRemove> productRemoves);
}

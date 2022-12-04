package com.hqtc.ims.favorite.service;

import com.hqtc.common.config.FeignClientService;
import com.hqtc.common.response.ResultData;
import com.hqtc.ims.common.constant.PathConstants;
import com.hqtc.ims.favorite.model.bean.ProductInfo;
import com.hqtc.ims.favorite.service.impl.FeignClientFallBackFactory;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @Description:
 * @Author: WangBin
 * @Date: 2018/6/25 20:00
 */
@FeignClient(name= FeignClientService.JDSYNC,fallbackFactory = FeignClientFallBackFactory.class)
public interface FavoriteFeignClient {
    @RequestMapping(value=PathConstants.FEIGN_USER_FAVORITE_LIST,method= RequestMethod.POST)
    public ResultData<List<ProductInfo>> listUserFavorites(@RequestBody List<Long> skus);
}

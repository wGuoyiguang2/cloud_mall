package com.hqtc.ims.favorite.service.impl;

import com.hqtc.common.config.ErrorCode;
import com.hqtc.common.response.ResultData;
import com.hqtc.common.utils.Tools;
import com.hqtc.ims.favorite.service.FavoriteFeignClient;
import feign.hystrix.FallbackFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @Description:
 * @Author: WangBin
 * @Date: 2018/6/25 20:36
 */
@Component
public class FeignClientFallBackFactory implements FallbackFactory<FavoriteFeignClient>{
    private Logger logger  = LoggerFactory.getLogger(getClass());
    @Override
    public FavoriteFeignClient create(Throwable throwable) {
        return new FavoriteFeignClient() {
            @Override
            public ResultData listUserFavorites(List<Long> skus) {
                logger.error("FeignClient Request JDSYNC Failed, "+"skus="+skus);
                ResultData result = Tools.getThreadResultData();
                result.setMsg("FeignClient Request JDSYNC Failed");
                result.setError(ErrorCode.SERVER_EXCEPTION);
                return result;
            }
        };
    }
}

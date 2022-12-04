package com.cibnvideo.thirdpart.feign.fallback;

import com.cibnvideo.thirdpart.feign.SearchFeign;
import com.cibnvideo.thirdpart.model.bean.ProductBean;
import com.cibnvideo.thirdpart.model.bean.ProductListBean;
import com.hqtc.common.config.ErrorCode;
import com.hqtc.common.response.ResultData;
import feign.hystrix.FallbackFactory;
import org.springframework.stereotype.Component;

import java.util.List;

import static com.hqtc.common.utils.Tools.getThreadResultData;


/**
 * @Description:
 * @Author: WangBin
 * @Date: 2019/1/21 17:23
 */
@Component
public class SearchFallbackFactory implements FallbackFactory<SearchFeign> {

    @Override
    public SearchFeign create(Throwable throwable) {
        return new SearchFeign() {

            @Override
            public ResultData<ProductListBean<List<ProductBean>>> getBatchPrice(String skus, Integer vendor,Integer pageSize) {
                return errorResponse();
            }

            private ResultData errorResponse() {
                ResultData result = getThreadResultData();
                result.setMsg("FeignClient Request search Failed");
                result.setError(ErrorCode.SERVER_EXCEPTION);
                return result;
            }

        };
    }
}

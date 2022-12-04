package com.cibnvideo.fallback.jdsyncapi;

import com.cibnvideo.tcmalladmin.jdsyncapi.CategoryApi;
import com.cibnvideo.tcmalladmin.model.bean.CategoryResultInfo;
import com.hqtc.common.config.ErrorCode;
import com.hqtc.common.response.DataList;
import com.hqtc.common.response.ResultData;
import feign.hystrix.FallbackFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

import static com.hqtc.common.utils.Tools.getThreadResultData;


@Component
public class CategoryFallbackFactory implements FallbackFactory<CategoryApi> {
    private Logger logger  = LoggerFactory.getLogger(getClass());
    @Override
    public CategoryApi create(Throwable throwable) {
        return new CategoryApi(){

            @Override
            public ResultData<DataList<List<CategoryResultInfo>>> categoryList(Map<String, Object> params) {
                return errorResponse("jdsync categoryList FeignClient Request Failed");
            }

            @Override
            public ResultData<List<CategoryResultInfo>> categoryListByCatIds(Integer[] catIds) {
                return errorResponse("jdsync categoryListByCatIds FeignClient Request Failed");
            }

            @Override
            public ResultData<CategoryResultInfo> categoryGet(Integer catId) {
                return errorResponse("jdsync categoryGet FeignClient Request Failed");
            }

            private ResultData errorResponse(String msg){
                ResultData result = getThreadResultData();
                result.setMsg(msg);
                result.setError(ErrorCode.SERVER_EXCEPTION);
                return result;
            }
        };
    }
}

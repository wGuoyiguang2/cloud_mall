package com.hqtc.cms.fallback;

import com.hqtc.cms.model.bean.ProductSynchronizationBean;
import com.hqtc.cms.model.bean.search.ProductCtime;
import com.hqtc.cms.model.service.JdSyncService;
import com.hqtc.common.config.ErrorCode;
import com.hqtc.common.response.DataList;
import com.hqtc.common.response.ResultData;
import feign.hystrix.FallbackFactory;
import org.springframework.stereotype.Component;

import java.util.List;

import static com.hqtc.common.utils.Tools.getThreadResultData;

/**
 * @Description:
 * @Author: WangBin
 * @Date: 2018/7/18 11:47
 */
@Component
public class JdSyncFallbackFactory implements FallbackFactory<JdSyncService> {

    @Override
    public JdSyncService create(Throwable throwable) {
        return new JdSyncService() {

            @Override
            public ResultData<List<ProductCtime>> getProductCtime(List<Long> skus) {
                return errorResponse();
            }

            @Override
            public ResultData<List<ProductSynchronizationBean>> getProductDetailList(String skus) {
                return errorResponse();
            }

            private ResultData errorResponse() {
                ResultData result = getThreadResultData();
                result.setMsg("FeignClient Request JDSYNC Failed");
                result.setError(ErrorCode.SERVER_EXCEPTION);
                return result;
            }
        };
    }
}

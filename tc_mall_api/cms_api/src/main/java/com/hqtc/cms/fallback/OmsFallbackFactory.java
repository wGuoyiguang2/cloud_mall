package com.hqtc.cms.fallback;

import com.hqtc.cms.model.bean.*;
import com.hqtc.cms.model.bean.search.ProductRemove;
import com.hqtc.cms.model.service.OmsService;
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

/**
 * description:oms
 * Created by laiqingchuang on 18-7-16 .
 */
@Component
public class OmsFallbackFactory implements FallbackFactory<OmsService> {
    private Logger logger  = LoggerFactory.getLogger(getClass());
    @Override
    public OmsService create(Throwable throwable) {
        return new OmsService() {

            @Override
            public ResultData<Result<ProductCollectionBean>> getProductcollectionList(Integer venderId) {
                return errorResponse();
            }

            @Override
            public ResultData<ProductCollectionBean> getProductcollectionById(Integer id) {
                return errorResponse();
            }

            @Override
            public ResultData<Result<ProductOfCollectionBean>> getProductlistById(Integer collectionId) {
                return errorResponse();
            }

            @Override
            public ResultData<List<String>> getBrandNameById(Integer collectionId,String removeSkus) {
                return errorResponse();
            }

            @Override
            public ResultData<List<Long>> getRemoveSkus(Integer venderId) {
                return errorResponse();
            }

            @Override
            public ResultData<List<PriceBean>> getBatchPrice(Integer venderId, List<Long> skus) {
                return errorResponse();
            }

            @Override
            public ResultData<PriceBean> getPrice(Integer venderId, Long sku) {
                return errorResponse();
            }

            @Override
            public ResultData<Map<Integer, CategoryPictureBean>> getCategoryPicture(Integer venderId, List<Integer> catIds) {
                return errorResponse();
            }

            @Override
            public ResultData getHotSearch(Integer venderId) {
                return errorResponse();
            }

            @Override
            public ResultData getRemovedProducts(Long venderId) {
                return errorResponse();
            }

            private ResultData errorResponse() {
                ResultData result = new ResultData();
                result.setMsg("FeignClient Request OMS Failed");
                result.setError(ErrorCode.SERVER_EXCEPTION);
                logger.error("FeignClient Request OMS Failed");
                return result;
            }
        };
    }
}

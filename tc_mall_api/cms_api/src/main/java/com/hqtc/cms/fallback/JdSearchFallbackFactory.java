package com.hqtc.cms.fallback;

import com.hqtc.cms.model.service.SearchService;
import com.hqtc.common.config.ErrorCode;
import com.hqtc.common.response.ResultData;
import feign.hystrix.FallbackFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class JdSearchFallbackFactory implements FallbackFactory<SearchService> {
    private Logger logger  = LoggerFactory.getLogger(getClass());
    @Override
    public SearchService create(Throwable throwable) {
        return new SearchService() {
            @Override
            public ResultData searchProduct(Long venderId, String keyword, String brandName, Integer cat0, Integer cat1, Integer cat2, Integer pageNum, Integer pageSize, Integer isNew, Integer isSales, Integer isPrice) {
                return errorResponse();
            }

            @Override
            public ResultData getCatlist(Integer venderId, String catClass, Integer parentId) {
                return errorResponse();
            }

            @Override
            public ResultData idsQuery(Integer venderId, String skus, Integer state, String brandName, Integer pageNum, Integer pageSize) {
                return errorResponse();
            }

            @Override
            public ResultData syncSearch(Integer venderId, Integer pageNum, Integer pageSize) {
                return errorResponse();
            }

            @Override
            public ResultData brandListByCat(Integer venderId, Integer cat0, Integer cat1, Integer cat2, Integer limit) {
                return errorResponse();
            }

            @Override
            public ResultData brandListBySearch(Integer venderId, Integer cat0, Integer cat1, Integer cat2, String keyword, Integer limit) {
                return errorResponse();
            }

            @Override
            public ResultData brandListByCollectionId(Integer venderId, Integer collectionId, Integer limit) {
                return errorResponse();
            }

            @Override
            public ResultData collectionProduct(Integer venderId, Integer collectionId, String brandName, Integer pageNum, Integer pageSize) {
                return errorResponse();
            }

            @Override
            public ResultData idsQuery(Integer venderId, String skus, Integer pageNum, Integer pageSize) {
                return errorResponse();
            }

            private ResultData errorResponse() {
                ResultData result = new ResultData();
                result.setMsg("FeignClient Request SEARCH Failed");
                result.setError(ErrorCode.SERVER_EXCEPTION);
                logger.error("FeignClient Request SEARCH Failed");
                return result;
            }
        };
    }
}

package com.hqtc.cms.fallback;

import com.hqtc.cms.model.bean.*;
import com.hqtc.cms.model.service.GoodService;
import com.hqtc.common.config.ErrorCode;
import com.hqtc.common.response.ResultData;
import feign.hystrix.FallbackFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import java.util.List;
import static com.hqtc.common.utils.Tools.getThreadResultData;

/**
 * description:商品分类容错
 * Created by laiqingchuang on 18-6-30 .
 */
@Component
public class GoodFallbackFactory implements FallbackFactory<GoodService> {
    private Logger logger  = LoggerFactory.getLogger(getClass());
    @Override
    public GoodService create(Throwable throwable) {
        return new GoodService() {

            @Override
            public ResultData<Result<GoodClassificationBean>> getClassificationList(Integer catId, Integer parentId, String name, String catClass, String state, Integer offset, Integer limit) {
                return errorResponse();
            }

            @Override
            public ResultData<Result<GoodDetailinfoListBean>> getGoodDetailinfoList(Long sku, String name, String brandName, String state, String category, Integer cat0, Integer cat1, Integer cat2, Integer offset, Integer limit, String sort, String order) {
                return errorResponse();
            }

            @Override
            public ResultData<GoodDetailinfoBean> getGoodDetailinfo(Long sku) {
                return errorResponse();
            }

            @Override
            public ResultData<Result<GoodClassificationCatBean>> getGoodClassificationCatList(Integer parentId, Integer catClass, String state) {
                return errorResponse();
            }

            @Override
            public ResultData<List<String>> getBranknamebycat(String keyword,Integer cat0, Integer cat1, Integer cat2,String removedSkus, String state, Integer offset, Integer limit) {
                return errorResponse();
            }

            @Override
            public ResultData<PicturePcBean> getPicturePc(Long sku) {
                return errorResponse();
            }

            @Override
            public ResultData<StyleMobileBean> getPictureMobile(Long sku) {
                return errorResponse();
            }

            @Override
            public ResultData<StylePcBean> getStylePc(Long sku) {
                return errorResponse();
            }

            @Override
            public ResultData<StyleMobileBean> getStyleMobile(Long sku) {
                return errorResponse();
            }

            @Override
            public ResultData getDetailinfobyskus(String skus) {
                return errorResponse();
            }

            @Override
            public ResultData<Result<GoodDetailinfoListBean>> getProductListSearch(ProductSearchBean requestBean) {
                return errorResponse();
            }

            @Override
            public ResultData<List<GoodClassificationBean>> getAllCategoryByCatId(Integer[] catIds) {
                return errorResponse();
            }
        };
    }

    private ResultData errorResponse() {
        ResultData result = new ResultData();
        result.setMsg("FeignClient Request JDSYNC Failed");
        result.setError(ErrorCode.SERVER_EXCEPTION);
        logger.error("FeignClient Request JDSYNC Failed");
        return result;
    }

}
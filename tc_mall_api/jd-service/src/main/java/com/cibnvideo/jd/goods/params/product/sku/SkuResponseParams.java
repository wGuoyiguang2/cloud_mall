package com.cibnvideo.jd.goods.params.product.sku;

import com.cibnvideo.jd.common.params.BaseResponseParams;

/**
 * @Description:
 * @Author: WangBin
 * @Date: 2018/6/29 11:45
 */
public class SkuResponseParams {
    private SkuResponseVo biz_product_sku_query_response;

    public SkuResponseVo getBiz_product_sku_query_response() {
        return biz_product_sku_query_response;
    }

    public void setBiz_product_sku_query_response(SkuResponseVo biz_product_sku_query_response) {
        this.biz_product_sku_query_response = biz_product_sku_query_response;
    }
    class SkuResponseVo extends BaseResponseParams {
        private String result;

        public String getResult() {
            return result;
        }

        public void setResult(String result) {
            this.result = result;
        }
    }
}

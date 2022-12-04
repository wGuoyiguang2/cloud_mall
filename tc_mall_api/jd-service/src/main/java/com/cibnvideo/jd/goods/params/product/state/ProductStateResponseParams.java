package com.cibnvideo.jd.goods.params.product.state;

import com.cibnvideo.jd.common.params.BaseResponseParams;

import java.util.List;

/**
 * @Description:
 * @Author: WangBin
 * @Date: 2018/6/29 17:44
 */
public class ProductStateResponseParams {

    private ProductVo biz_product_state_query_response;

    public ProductVo getBiz_product_state_query_response() {
        return biz_product_state_query_response;
    }

    public void setBiz_product_state_query_response(ProductVo biz_product_state_query_response) {
        this.biz_product_state_query_response = biz_product_state_query_response;
    }

    class ProductVo extends BaseResponseParams {

        private List<ProductResponseParams> result;

        public List<ProductResponseParams> getResult() {
            return result;
        }

        public void setResult(List<ProductResponseParams> result) {
            this.result = result;
        }

        class ProductResponseParams {
            private Long sku;
            private Integer state;

            public Long getSku() {
                return sku;
            }

            public void setSku(Long sku) {
                this.sku = sku;
            }

            public Integer getState() {
                return state;
            }

            public void setState(Integer state) {
                this.state = state;
            }
        }
    }
}

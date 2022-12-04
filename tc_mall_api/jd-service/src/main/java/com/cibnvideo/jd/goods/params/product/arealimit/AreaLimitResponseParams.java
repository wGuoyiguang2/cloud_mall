package com.cibnvideo.jd.goods.params.product.arealimit;

import com.cibnvideo.jd.common.params.BaseResponseParams;

import java.util.List;

/**
 * @Description:
 * @Author: WangBin
 * @Date: 2018/6/29 20:08
 */
public class AreaLimitResponseParams {

    private ProductVo biz_product_checkAreaLimit_query_response;

    public ProductVo getBiz_product_checkAreaLimit_query_response() {
        return biz_product_checkAreaLimit_query_response;
    }

    public void setBiz_product_checkAreaLimit_query_response(ProductVo biz_product_checkAreaLimit_query_response) {
        this.biz_product_checkAreaLimit_query_response = biz_product_checkAreaLimit_query_response;
    }

    class ProductVo extends BaseResponseParams {

        private List<ProductRepVo> result;

        public List<ProductRepVo> getResult() {
            return result;
        }

        public void setResult(List<ProductRepVo> result) {
            this.result = result;
        }

        class ProductRepVo {
            private Long skuId;//	Long	商品编号
            private Boolean isAreaRestrict;//	Boolean	True 区域限制 false 不受区域限制

            public Long getSkuId() {
                return skuId;
            }

            public void setSkuId(Long skuId) {
                this.skuId = skuId;
            }

            public Boolean getAreaRestrict() {
                return isAreaRestrict;
            }

            public void setAreaRestrict(Boolean areaRestrict) {
                isAreaRestrict = areaRestrict;
            }
        }
    }
}

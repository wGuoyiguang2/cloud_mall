package com.cibnvideo.jd.goods.params.product.sellcheck;

import com.cibnvideo.jd.common.params.BaseResponseParams;

import java.util.List;

/**
 * @Description:
 * @Author: WangBin
 * @Date: 2018/6/29 21:41
 */
public class SellCheckResponseParams {


    private ProductVo biz_product_sku_check_response;

    public ProductVo getBiz_product_sku_check_response() {
        return biz_product_sku_check_response;
    }

    public void setBiz_product_sku_check_response(ProductVo biz_product_sku_check_response) {
        this.biz_product_sku_check_response = biz_product_sku_check_response;
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
            private Long skuId;//	 Long	商品编号
            private String name;//	String	商品名称
            private Integer saleState;//	Int	 是否可售，1：是，0：否
            private Integer isCanVAT;//	Int	 是否可开增票，1：支持，0：不支持
            private Integer is7ToReturn;//	Int	是否支持7天退货，1：是，0：不支持

            public Long getSkuId() {
                return skuId;
            }

            public void setSkuId(Long skuId) {
                this.skuId = skuId;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public Integer getSaleState() {
                return saleState;
            }

            public void setSaleState(Integer saleState) {
                this.saleState = saleState;
            }

            public Integer getIsCanVAT() {
                return isCanVAT;
            }

            public void setIsCanVAT(Integer isCanVAT) {
                this.isCanVAT = isCanVAT;
            }

            public Integer getIs7ToReturn() {
                return is7ToReturn;
            }

            public void setIs7ToReturn(Integer is7ToReturn) {
                this.is7ToReturn = is7ToReturn;
            }
        }
    }
}

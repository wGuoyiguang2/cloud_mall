package com.cibnvideo.jd.goods.params.product.comment;

import com.cibnvideo.jd.common.params.BaseResponseParams;

import java.math.BigDecimal;
import java.util.List;

/**
 * @Description:
 * @Author: WangBin
 * @Date: 2018/6/29 18:32
 */
public class ProductCommentsResponseParams {


    private ProductVo biz_product_commentSummarys_query_response;

    public ProductVo getBiz_product_commentSummarys_query_response() {
        return biz_product_commentSummarys_query_response;
    }

    public void setBiz_product_commentSummarys_query_response(ProductVo biz_product_commentSummarys_query_response) {
        this.biz_product_commentSummarys_query_response = biz_product_commentSummarys_query_response;
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
            private BigDecimal averageScore;//	BigDecimal	商品评分 (5颗星，4颗星)
            private BigDecimal goodRate;//	BigDecimal	好评度
            private BigDecimal generalRate;//	BigDecimal	中评度
            private BigDecimal poorRate;//	BigDecimal	差评度
            private Long skuId;//	Long	商品编号

            public BigDecimal getAverageScore() {
                return averageScore;
            }

            public void setAverageScore(BigDecimal averageScore) {
                this.averageScore = averageScore;
            }

            public BigDecimal getGoodRate() {
                return goodRate;
            }

            public void setGoodRate(BigDecimal goodRate) {
                this.goodRate = goodRate;
            }

            public BigDecimal getGeneralRate() {
                return generalRate;
            }

            public void setGeneralRate(BigDecimal generalRate) {
                this.generalRate = generalRate;
            }

            public BigDecimal getPoorRate() {
                return poorRate;
            }

            public void setPoorRate(BigDecimal poorRate) {
                this.poorRate = poorRate;
            }

            public Long getSkuId() {
                return skuId;
            }

            public void setSkuId(Long skuId) {
                this.skuId = skuId;
            }
        }
    }
}

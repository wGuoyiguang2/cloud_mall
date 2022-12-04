package com.cibnvideo.jd.goods.params.price;

import com.cibnvideo.jd.common.params.BaseResponseParams;

import java.math.BigDecimal;
import java.util.List;

/**
 * @Description:
 * @Author: WangBin
 * @Date: 2018/6/23 15:15
 */
public class PriceResponseParams {
    private PriceResponseVo biz_price_sellPrice_get_response;

    public PriceResponseVo getBiz_price_sellPrice_get_response() {
        return biz_price_sellPrice_get_response;
    }

    public void setBiz_price_sellPrice_get_response(PriceResponseVo biz_price_sellPrice_get_response) {
        this.biz_price_sellPrice_get_response = biz_price_sellPrice_get_response;
    }
    class PriceResponseVo extends BaseResponseParams {

        private List<PriceResultResponseParams> result;

        public List<PriceResultResponseParams> getResult() {
            return result;
        }

        public void setResult(List<PriceResultResponseParams> result) {
            this.result = result;
        }
        class PriceResultResponseParams {
            private Long skuId;//	Long	商品ID
            private BigDecimal jdPrice;//	BigDecimal	商品京东价格（非京东当前售价，无参考价值）
            private BigDecimal price;//	BigDecimal	商品协议价格

            public Long getSkuId() {
                return skuId;
            }

            public void setSkuId(Long skuId) {
                this.skuId = skuId;
            }

            public BigDecimal getJdPrice() {
                return jdPrice;
            }

            public void setJdPrice(BigDecimal jdPrice) {
                this.jdPrice = jdPrice;
            }

            public BigDecimal getPrice() {
                return price;
            }

            public void setPrice(BigDecimal price) {
                this.price = price;
            }
        }
    }
}

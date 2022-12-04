package com.cibnvideo.jd.goods.params.price;

import com.cibnvideo.jd.common.params.BaseResponseParams;

import java.math.BigDecimal;
import java.util.List;

/**
 * @Description:
 * @Author: WangBin
 * @Date: 2018/6/23 16:19
 */
public class PriceTaxResponseParams {
    private PriceTaxResponseVo jd_kpl_open_getsellprice_query_response;

    public PriceTaxResponseVo getJd_kpl_open_getsellprice_query_response() {
        return jd_kpl_open_getsellprice_query_response;
    }

    public void setJd_kpl_open_getsellprice_query_response(PriceTaxResponseVo jd_kpl_open_getsellprice_query_response) {
        this.jd_kpl_open_getsellprice_query_response = jd_kpl_open_getsellprice_query_response;
    }
    class PriceTaxResponseVo extends BaseResponseParams {
        private List<PriceTaxVo> result;

        public List<PriceTaxVo> getResult() {
            return result;
        }

        public void setResult(List<PriceTaxVo> result) {
            this.result = result;
        }

        class PriceTaxVo{
            private Long skuId;//	Long	商品ID
            private BigDecimal jdPrice;//	BigDecimal	商品京东价格（非京东当前售价，无参考价值）
            private BigDecimal price;//	BigDecimal	商品协议价格
            private BigDecimal tax;//	BigDecimal	商品税率
            private BigDecimal taxPrice;//	BigDecimal	商品税额
            private BigDecimal nakedPrice;//	BigDecimal	商品祼价
            private BigDecimal marketPrice;//	BigDecimal	市场价格

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

            public BigDecimal getTax() {
                return tax;
            }

            public void setTax(BigDecimal tax) {
                this.tax = tax;
            }

            public BigDecimal getTaxPrice() {
                return taxPrice;
            }

            public void setTaxPrice(BigDecimal taxPrice) {
                this.taxPrice = taxPrice;
            }

            public BigDecimal getNakedPrice() {
                return nakedPrice;
            }

            public void setNakedPrice(BigDecimal nakedPrice) {
                this.nakedPrice = nakedPrice;
            }

            public BigDecimal getMarketPrice() {
                return marketPrice;
            }

            public void setMarketPrice(BigDecimal marketPrice) {
                this.marketPrice = marketPrice;
            }
        }
    }
}

package com.cibnvideo.jd.goods.params.product.freight;

import com.cibnvideo.jd.common.params.BaseResponseParams;

import java.math.BigDecimal;

/**
 * @Description:
 * @Author: WangBin
 * @Date: 2018/6/29 21:16
 */
public class FreightResponseParams {


    private FreightVo biz_order_freight_get_response;

    public FreightVo getBiz_order_freight_get_response() {
        return biz_order_freight_get_response;
    }

    public void setBiz_order_freight_get_response(FreightVo biz_order_freight_get_response) {
        this.biz_order_freight_get_response = biz_order_freight_get_response;
    }

    class FreightVo extends BaseResponseParams {

        private GiftRepVo result;

        public GiftRepVo getResult() {
            return result;
        }

        public void setResult(GiftRepVo result) {
            this.result = result;
        }

        class GiftRepVo {
            private BigDecimal freight;//	bigdecimal   	总运费
            private BigDecimal baseFreight;//	bigdecimal   	基础运费
            private BigDecimal remoteRegionFreight;//	bigdecimal   	偏远运费
            private String remoteSku;//	string	需收取偏远运费的sku

            public BigDecimal getFreight() {
                return freight;
            }

            public void setFreight(BigDecimal freight) {
                this.freight = freight;
            }

            public BigDecimal getBaseFreight() {
                return baseFreight;
            }

            public void setBaseFreight(BigDecimal baseFreight) {
                this.baseFreight = baseFreight;
            }

            public BigDecimal getRemoteRegionFreight() {
                return remoteRegionFreight;
            }

            public void setRemoteRegionFreight(BigDecimal remoteRegionFreight) {
                this.remoteRegionFreight = remoteRegionFreight;
            }

            public String getRemoteSku() {
                return remoteSku;
            }

            public void setRemoteSku(String remoteSku) {
                this.remoteSku = remoteSku;
            }
        }
    }
}

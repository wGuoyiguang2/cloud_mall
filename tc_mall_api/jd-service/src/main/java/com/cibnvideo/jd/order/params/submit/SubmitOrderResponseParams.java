package com.cibnvideo.jd.order.params.submit;

import com.cibnvideo.jd.common.params.BaseResponseParams;

import java.util.List;

/**
 * @Description:
 * @Author: WangBin
 * @Date: 2018/7/4 15:48
 */
public class SubmitOrderResponseParams {


    private OrderVo biz_order_unite_submit_response;

    public OrderVo getBiz_order_unite_submit_response() {
        return biz_order_unite_submit_response;
    }

    public void setBiz_order_unite_submit_response(OrderVo biz_order_unite_submit_response) {
        this.biz_order_unite_submit_response = biz_order_unite_submit_response;
    }

    class OrderVo extends BaseResponseParams {

        private OrderRepVo2 result;

        public OrderRepVo2 getResult() {
            return result;
        }

        public void setResult(OrderRepVo2 result) {
            this.result = result;
        }

            class OrderRepVo2{
                private String jdOrderId;
                private String orderPrice;//": 11,
                private String orderNakedPrice;//": 11,
                private String orderTaxPrice;//": 11,
                private String freight;//": 5
                private List<SkuVo> sku;

                public String getJdOrderId() {
                    return jdOrderId;
                }

                public void setJdOrderId(String jdOrderId) {
                    this.jdOrderId = jdOrderId;
                }

                public String getOrderPrice() {
                    return orderPrice;
                }

                public void setOrderPrice(String orderPrice) {
                    this.orderPrice = orderPrice;
                }

                public String getOrderNakedPrice() {
                    return orderNakedPrice;
                }

                public void setOrderNakedPrice(String orderNakedPrice) {
                    this.orderNakedPrice = orderNakedPrice;
                }

                public String getOrderTaxPrice() {
                    return orderTaxPrice;
                }

                public void setOrderTaxPrice(String orderTaxPrice) {
                    this.orderTaxPrice = orderTaxPrice;
                }

                public String getFreight() {
                    return freight;
                }

                public void setFreight(String freight) {
                    this.freight = freight;
                }

                public List<SkuVo> getSku() {
                    return sku;
                }

                public void setSku(List<SkuVo> sku) {
                    this.sku = sku;
                }

                class SkuVo{
                    private String skuId;//": 759498,
                    private String num;//": 1,
                    private String category;//": 832,
                    private String price;//": 3889,
                    private String name;//": "尼康（Nikon） D5200 单反套机（AF-S DX 18-55mm f/3.5-5.6G VR尼克尔镜头）青铜色",
                    private String tax;//": 17,
                    private String taxPrice;//": 565.07,
                    private String nakedPrice;//": 3323.93,
                    private String type;//": 1,
                    private String oid;//": 348135

                    public String getSkuId() {
                        return skuId;
                    }

                    public void setSkuId(String skuId) {
                        this.skuId = skuId;
                    }

                    public String getNum() {
                        return num;
                    }

                    public void setNum(String num) {
                        this.num = num;
                    }

                    public String getCategory() {
                        return category;
                    }

                    public void setCategory(String category) {
                        this.category = category;
                    }

                    public String getPrice() {
                        return price;
                    }

                    public void setPrice(String price) {
                        this.price = price;
                    }

                    public String getName() {
                        return name;
                    }

                    public void setName(String name) {
                        this.name = name;
                    }

                    public String getTax() {
                        return tax;
                    }

                    public void setTax(String tax) {
                        this.tax = tax;
                    }

                    public String getTaxPrice() {
                        return taxPrice;
                    }

                    public void setTaxPrice(String taxPrice) {
                        this.taxPrice = taxPrice;
                    }

                    public String getNakedPrice() {
                        return nakedPrice;
                    }

                    public void setNakedPrice(String nakedPrice) {
                        this.nakedPrice = nakedPrice;
                    }

                    public String getType() {
                        return type;
                    }

                    public void setType(String type) {
                        this.type = type;
                    }

                    public String getOid() {
                        return oid;
                    }

                    public void setOid(String oid) {
                        this.oid = oid;
                    }
                }
            }

    }
}

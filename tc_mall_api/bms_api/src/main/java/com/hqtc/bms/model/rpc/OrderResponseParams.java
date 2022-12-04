package com.hqtc.bms.model.rpc;

import java.util.List;

/**
 * @Description:
 * @Author: WangBin
 * @Date: 2018/6/25 15:08
 */
public class OrderResponseParams {
    private OrderResponseVo jd_kpl_open_selectjdorder_query_response;

    public OrderResponseVo getJd_kpl_open_selectjdorder_query_response() {
        return jd_kpl_open_selectjdorder_query_response;
    }

    public void setJd_kpl_open_selectjdorder_query_response(OrderResponseVo jd_kpl_open_selectjdorder_query_response) {
        this.jd_kpl_open_selectjdorder_query_response = jd_kpl_open_selectjdorder_query_response;
    }
    public class OrderResponseVo extends BaseResponseParams {

        private OrderVo result;

        public OrderVo getResult() {
            return result;
        }

        public void setResult(OrderVo result) {
            this.result = result;
        }

        public class OrderVo{
            private String pOrder;
            private String jdOrderState;
            private String orderState;
            private String jdOrderId;
            private String freight;
            private String state;
            private String submitState;
            private String orderPrice;
            private String orderNakedPrice;
            private String type;
            private String orderTaxPrice;
            private List<Sku> sku;
            public class Sku{
                private String category;
                private String num;
                private String price;
                private String tax;
                private String oid;
                private String name;
                private String taxPrice;
                private String skuId;
                private String nakedPrice;
                private String type;

                public String getCategory() {
                    return category;
                }

                public void setCategory(String category) {
                    this.category = category;
                }

                public String getNum() {
                    return num;
                }

                public void setNum(String num) {
                    this.num = num;
                }

                public String getPrice() {
                    return price;
                }

                public void setPrice(String price) {
                    this.price = price;
                }

                public String getTax() {
                    return tax;
                }

                public void setTax(String tax) {
                    this.tax = tax;
                }

                public String getOid() {
                    return oid;
                }

                public void setOid(String oid) {
                    this.oid = oid;
                }

                public String getName() {
                    return name;
                }

                public void setName(String name) {
                    this.name = name;
                }

                public String getTaxPrice() {
                    return taxPrice;
                }

                public void setTaxPrice(String taxPrice) {
                    this.taxPrice = taxPrice;
                }

                public String getSkuId() {
                    return skuId;
                }

                public void setSkuId(String skuId) {
                    this.skuId = skuId;
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
            }

            public List<Sku> getSku() {
                return sku;
            }

            public void setSku(List<Sku> sku) {
                this.sku = sku;
            }

            public String getpOrder() {
                return pOrder;
            }

            public void setpOrder(String pOrder) {
                this.pOrder = pOrder;
            }

            public String getJdOrderState() {
                return jdOrderState;
            }

            public void setJdOrderState(String jdOrderState) {
                this.jdOrderState = jdOrderState;
            }

            public String getOrderState() {
                return orderState;
            }

            public void setOrderState(String orderState) {
                this.orderState = orderState;
            }

            public String getJdOrderId() {
                return jdOrderId;
            }

            public void setJdOrderId(String jdOrderId) {
                this.jdOrderId = jdOrderId;
            }

            public String getFreight() {
                return freight;
            }

            public void setFreight(String freight) {
                this.freight = freight;
            }

            public String getState() {
                return state;
            }

            public void setState(String state) {
                this.state = state;
            }

            public String getSubmitState() {
                return submitState;
            }

            public void setSubmitState(String submitState) {
                this.submitState = submitState;
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

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }

            public String getOrderTaxPrice() {
                return orderTaxPrice;
            }

            public void setOrderTaxPrice(String orderTaxPrice) {
                this.orderTaxPrice = orderTaxPrice;
            }
        }

    }
}

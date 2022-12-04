package com.hqtc.bms.model.response.jdorderinfo;

import java.util.List;

/**
 * @Description:
 * @Author: WangBin
 * @Date: 2018/7/23 21:51
 */
public class OrderVo {
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

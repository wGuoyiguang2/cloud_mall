package com.hqtc.bms.model.response.jdorderinfo;

/**
 * @Description:
 * @Author: WangBin
 * @Date: 2018/7/23 21:50
 */
public class Sku {
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

package com.hqtc.bms.model.rpc;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by wanghaoyang on 18-7-9.
 */
public class JdOrderSubmitResponse {
    private String code;
    private String resultCode;
    private boolean success;
    private String resultMessage;
    private JdResult result;

    public class ProductInfo{
        private Long skuId;
        private int num;
        private int category;
        private int price;
        private String name;
        private int tax;
        private BigDecimal taxPrice;
        private BigDecimal nakedPrice;
        private int type;
        private int old;

        public long getSkuId() {
            return skuId;
        }

        public void setSkuId(long skuId) {
            this.skuId = skuId;
        }

        public int getNum() {
            return num;
        }

        public void setNum(int num) {
            this.num = num;
        }

        public int getCategory() {
            return category;
        }

        public void setCategory(int category) {
            this.category = category;
        }

        public int getPrice() {
            return price;
        }

        public void setPrice(int price) {
            this.price = price;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getTax() {
            return tax;
        }

        public void setTax(int tax) {
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

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public int getOld() {
            return old;
        }

        public void setOld(int old) {
            this.old = old;
        }
    }

    public class JdResult {
        private int jdOrderId;
        private List<ProductInfo> sku;

        public int getJdOrderId() {
            return jdOrderId;
        }

        public void setJdOrderId(int jdOrderId) {
            this.jdOrderId = jdOrderId;
        }

        public List<ProductInfo> getSku() {
            return sku;
        }

        public void setSku(List<ProductInfo> sku) {
            this.sku = sku;
        }
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getResultCode() {
        return resultCode;
    }

    public void setResultCode(String resultCode) {
        this.resultCode = resultCode;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getResultMessage() {
        return resultMessage;
    }

    public void setResultMessage(String resultMessage) {
        this.resultMessage = resultMessage;
    }

    public JdResult getResult() {
        return result;
    }

    public void setResult(JdResult result) {
        this.result = result;
    }
}

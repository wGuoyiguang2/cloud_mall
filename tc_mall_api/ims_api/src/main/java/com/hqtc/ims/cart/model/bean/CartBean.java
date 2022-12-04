package com.hqtc.ims.cart.model.bean;

import java.io.Serializable;

/**
 * description: 购物车bean
 * Created by sunjianqiang  18-9-26
 */
public class CartBean implements Serializable {
    private  Integer total;
    private  Long sku;
    private  Integer count;

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public Long getSku() {
        return sku;
    }

    public void setSku(Long sku) {
        this.sku = sku;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }
}

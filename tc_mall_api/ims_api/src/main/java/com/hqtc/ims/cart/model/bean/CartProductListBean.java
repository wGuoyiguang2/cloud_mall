package com.hqtc.ims.cart.model.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by sunjianqiang  18-9-25
 */
public class CartProductListBean implements Serializable {
    private long total = 0;
    private List<CartProductBean> dataList= new ArrayList<CartProductBean>();

    public List<CartProductBean> getDataList() {
        return dataList;
    }

    public void setDataList(List<CartProductBean> dataList) {
        this.dataList = dataList;
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }
}

package com.hqtc.search.model.bean;

import java.io.Serializable;

/**
 * Created by makuan on 18-8-13.
 */
public class ProductListBean<T> implements Serializable {
    private Integer total = 0;
    private T dataList;

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public T getDataList() {
        return dataList;
    }

    public void setDataList(T dataList) {
        this.dataList = dataList;
    }
}

package com.cibnvideo.thirdpart.model.bean;


/**
 * @Description:
 * @Author: WangBin
 * @Date: 2019/1/21 17:23
 */
public class ProductListBean<T>{
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

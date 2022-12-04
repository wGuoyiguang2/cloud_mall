package com.hqtc.cms.model.bean;

import java.io.Serializable;
import java.util.List;

/**
 * description:
 * Created by laiqingchuang on 18-7-7 .
 */
public class GoodDetailinfoListResultBean implements Serializable {
    private int totalRows;
    private List<GoodDetailinfoListBean> dataList; //商品列表
    private List<String> brandList;                      //品牌列表

    public int getTotalRows() {
        return totalRows;
    }

    public List<GoodDetailinfoListBean> getDataList() {
        return dataList;
    }

    public void setDataList(List<GoodDetailinfoListBean> dataList) {
        this.dataList = dataList;
    }

    public void setTotalRows(int totalRows) {
        this.totalRows = totalRows;
    }

    public List<String> getBrandList() {
        return brandList;
    }

    public void setBrandList(List<String> brandList) {
        this.brandList = brandList;
    }
}
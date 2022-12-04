package com.hqtc.ims.address.model.bean;

import java.io.Serializable;
import java.util.List;

/**
 * description:
 * Created by laiqingchuang on 18-7-9 .
 */
public class Resultlist<T> implements Serializable{
    private int totalRows;      //总行数
    private List<T> dataList;   //列表集合

    public int getTotalRows() {
        return totalRows;
    }

    public void setTotalRows(int totalRows) {
        this.totalRows = totalRows;
    }

    public List<T> getDataList() {
        return dataList;
    }

    public void setDataList(List<T> dataList) {
        this.dataList = dataList;
    }
}

package com.hqtc.cms.model.bean;

import java.io.Serializable;
import java.util.List;

/**
 * description:查询列表接口工具类
 * Created by laiqingchuang on 18-6-25 .
 */
public class Result<T> implements Serializable {
    private int totalRows;  //总行数
    private List<T> data;   //列表集合

    public int getTotalRows() {
        return totalRows;
    }

    public void setTotalRows(int totalRows) {
        this.totalRows = totalRows;
    }

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }
}


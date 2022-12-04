package com.hqtc.search.model.bean;

import java.io.Serializable;

/**
 * Created by makuan on 18-8-10.
 */
public class PageBean implements Serializable {
    private int pageNum = 1;
    private int pageSize;

    public int getPageNum() {
        return pageNum;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }
}

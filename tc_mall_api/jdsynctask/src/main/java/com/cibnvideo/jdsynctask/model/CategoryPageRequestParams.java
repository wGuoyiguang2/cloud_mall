package com.cibnvideo.jdsynctask.model;

/**
 * @Description:
 * @Author: WangBin
 * @Date: 2018/6/30 11:02
 */
public class CategoryPageRequestParams {
    private Integer catClass;
    private Integer pageNo;
    private Integer pageSize;
    private Integer parentId;

    public Integer getCatClass() {
        return catClass;
    }

    public void setCatClass(Integer catClass) {
        this.catClass = catClass;
    }

    public Integer getPageNo() {
        return pageNo;
    }

    public void setPageNo(Integer pageNo) {
        this.pageNo = pageNo;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }
}

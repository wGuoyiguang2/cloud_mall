package com.cibnvideo.jd.goods.params.product.category;

/**
 * @Description:
 * @Author: WangBin
 * @Date: 2018/6/30 11:02
 */
public class CategoryPageRequestParams {
    private String catClass;
    private String pageNo;
    private String pageSize;
    private String parentId;

    public String getCatClass() {
        return catClass;
    }

    public void setCatClass(String catClass) {
        this.catClass = catClass;
    }

    public String getPageNo() {
        return pageNo;
    }

    public void setPageNo(String pageNo) {
        this.pageNo = pageNo;
    }

    public String getPageSize() {
        return pageSize;
    }

    public void setPageSize(String pageSize) {
        this.pageSize = pageSize;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }
}

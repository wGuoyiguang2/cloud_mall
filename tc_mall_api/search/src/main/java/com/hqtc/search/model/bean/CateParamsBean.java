package com.hqtc.search.model.bean;

/**
 * Created by makuan on 18-8-16.
 */
public class CateParamsBean {
    private Integer catClass = 0;
    private Integer parentId;
    private Integer venderId;

    public Integer getCatClass() {
        return catClass;
    }

    public void setCatClass(Integer catClass) {
        this.catClass = catClass;
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public Integer getVenderId() {
        return venderId;
    }

    public void setVenderId(Integer venderId) {
        this.venderId = venderId;
    }
}

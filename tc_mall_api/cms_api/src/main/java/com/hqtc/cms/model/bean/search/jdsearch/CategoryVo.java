package com.hqtc.cms.model.bean.search.jdsearch;

/**
 * @Description:
 * @Author: WangBin
 * @Date: 2018/7/7 16:12
 */

public class CategoryVo{
    private Integer catId;
    private Integer count;
    private String name;
    private Double weight;

    public Integer getCatId() {
        return catId;
    }

    public void setCatId(Integer catId) {
        this.catId = catId;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getWeight() {
        return weight;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }
}

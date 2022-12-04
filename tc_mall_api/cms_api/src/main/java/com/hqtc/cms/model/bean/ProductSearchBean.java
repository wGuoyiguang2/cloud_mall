package com.hqtc.cms.model.bean;

import java.util.List;

/**
 * description:
 * Created by laiqingchuang on 18-7-18 .
 */
public class ProductSearchBean {
    private List<Integer> isin;
    private List<Integer> notin;
    private String name;
    private String brandName;
    private String state;       //上下架状态 0:下架 1:上架
    private String category;   //三级分类信息，以分号“;”分开
    private Long sku;
    private Integer cat0;
    private Integer cat1;
    private Integer cat2;
    private Integer offset;
    private String sort;
    private String order;
    private Integer limit;

    public List<Integer> getIsin() {
        return isin;
    }
    public void setIsin(List<Integer> isin) {
        this.isin = isin;
    }

    public String getBrandName() {
        return brandName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public Long getSku() {
        return sku;
    }

    public void setSku(Long sku) {
        this.sku = sku;
    }

    public Integer getOffset() {
        return offset;
    }

    public void setOffset(Integer offset) {
        this.offset = offset;
    }

    public List<Integer> getNotin() {
        return notin;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Integer getCat0() {
        return cat0;
    }

    public void setCat0(Integer cat0) {
        this.cat0 = cat0;
    }

    public Integer getCat1() {
        return cat1;
    }

    public void setCat1(Integer cat1) {
        this.cat1 = cat1;
    }

    public Integer getCat2() {
        return cat2;
    }

    public void setCat2(Integer cat2) {
        this.cat2 = cat2;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    public void setNotin(List<Integer> notin) {
        this.notin = notin;
    }

    public Integer getLimit() {
        return limit;
    }

    public void setLimit(Integer limit) {
        this.limit = limit;
    }
}

package com.hqtc.cms.model.bean.search;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @Description:
 * @Author: WangBin
 * @Date: 2018/8/17 17:53
 */

public class SearchParamsVo implements Serializable {
    private Long venderId;
    private String keyword;
    private String brandName;
    private String cat0;
    private String cat1;
    private String cat2;
    private Integer pageNum;
    private Integer pageSize;
    private Integer isNew;
    private Integer isSales;
    private Integer isPrice;

    public Long getVenderId() {
        return venderId;
    }

    public void setVenderId(Long venderId) {
        this.venderId = venderId;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public String getCat0() {
        return cat0;
    }

    public void setCat0(String cat0) {
        this.cat0 = cat0;
    }

    public String getCat1() {
        return cat1;
    }

    public void setCat1(String cat1) {
        this.cat1 = cat1;
    }

    public String getCat2() {
        return cat2;
    }

    public void setCat2(String cat2) {
        this.cat2 = cat2;
    }

    public Integer getPageNum() {
        return pageNum;
    }

    public void setPageNum(Integer pageNum) {
        this.pageNum = pageNum;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getIsNew() {
        return isNew;
    }

    public void setIsNew(Integer isNew) {
        this.isNew = isNew;
    }

    public Integer getIsSales() {
        return isSales;
    }

    public void setIsSales(Integer isSales) {
        this.isSales = isSales;
    }

    public Integer getIsPrice() {
        return isPrice;
    }

    public void setIsPrice(Integer isPrice) {
        this.isPrice = isPrice;
    }
}


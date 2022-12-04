package com.hqtc.cms.model.bean.search;

import java.io.Serializable;

/**
 * Created by makuan on 18-7-5.
 */
public class SearchParamsBean implements Serializable {
    private String keyword;    //搜索关键字，需要编码
    private String catId;      //分类Id,只支持三级类目Id
    private String brands;     //品牌搜索 多个品牌以逗号分隔，需要编码
    private Integer pageSize;  //当前页显示数量
    private Integer pageIndex;//当前第几页
    private String min;        //价格区间搜索，低价
    private String Max;        //价格区间搜索，高价

    public String getMin() {
        return min;
    }

    public void setMin(String min) {
        this.min = min;
    }

    public String getMax() {
        return Max;
    }

    public void setMax(String max) {
        Max = max;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public String getCatId() {
        return catId;
    }

    public void setCatId(String catId) {
        this.catId = catId;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public String getBrands() {
        return brands;
    }

    public void setBrands(String brands) {
        this.brands = brands;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getPageIndex() {
        return pageIndex;
    }

    public void setPageIndex(Integer pageIndex) {
        this.pageIndex = pageIndex;
    }
}

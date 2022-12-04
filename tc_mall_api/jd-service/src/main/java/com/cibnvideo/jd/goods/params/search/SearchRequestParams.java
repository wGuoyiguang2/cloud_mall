package com.cibnvideo.jd.goods.params.search;

/**
 * description:搜索参数
 * Created by laiqingchuang on 18-6-28 .
 */
public class SearchRequestParams {
    private String keyword;    //搜索关键字，需要编码
    private String catId;      //分类Id,只支持三级类目Id
    private String Brands;     //品牌搜索 多个品牌以逗号分隔，需要编码
    private String min;        //价格区间搜索，低价
    private String Max;        //价格区间搜索，高价
    private Integer pageIndex; //当前第几页
    private Integer pageSize;  //当前页显示数量

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

    public Integer getPageIndex() {
        return pageIndex;
    }

    public void setPageIndex(Integer pageIndex) {
        this.pageIndex = pageIndex;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

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

    public String getBrands() {
        return Brands;
    }

    public void setBrands(String brands) {
        Brands = brands;
    }
}
package com.hqtc.cms.model.bean.search.jdsearch;

import java.util.List;

/**
 * @Description:
 * @Author: WangBin
 * @Date: 2018/7/7 16:08
 */

public class SearchVo{
    private Integer resultCount;
    private Integer pageIndex;
    private Integer pageCount;
    private Integer pageSize;
    private BrandAggregateVo brandAggregate;
    private List<PriceIntervalAggregateVo> priceIntervalAggregate;
    private CategoryAggregateVo categoryAggregate;
    private List<HitResultVo> hitResult;

    public Integer getPageCount() {
        return pageCount;
    }

    public void setPageCount(Integer pageCount) {
        this.pageCount = pageCount;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getResultCount() {
        return resultCount;
    }

    public void setResultCount(Integer resultCount) {
        this.resultCount = resultCount;
    }

    public Integer getPageIndex() {
        return pageIndex;
    }

    public void setPageIndex(Integer pageIndex) {
        this.pageIndex = pageIndex;
    }

    public BrandAggregateVo getBrandAggregate() {
        return brandAggregate;
    }

    public void setBrandAggregate(BrandAggregateVo brandAggregate) {
        this.brandAggregate = brandAggregate;
    }

    public List<PriceIntervalAggregateVo> getPriceIntervalAggregate() {
        return priceIntervalAggregate;
    }

    public void setPriceIntervalAggregate(List<PriceIntervalAggregateVo> priceIntervalAggregate) {
        this.priceIntervalAggregate = priceIntervalAggregate;
    }

    public CategoryAggregateVo getCategoryAggregate() {
        return categoryAggregate;
    }

    public void setCategoryAggregate(CategoryAggregateVo categoryAggregate) {
        this.categoryAggregate = categoryAggregate;
    }

    public List<HitResultVo> getHitResult() {
        return hitResult;
    }

    public void setHitResult(List<HitResultVo> hitResult) {
        this.hitResult = hitResult;
    }
}

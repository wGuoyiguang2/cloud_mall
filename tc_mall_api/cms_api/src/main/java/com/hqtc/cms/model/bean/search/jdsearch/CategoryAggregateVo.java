package com.hqtc.cms.model.bean.search.jdsearch;

import java.util.List;

/**
 * @Description:
 * @Author: WangBin
 * @Date: 2018/7/7 16:11
 */
public class CategoryAggregateVo{
    private List<CategoryVo> firstCategory;
    private List<CategoryVo> secondCategory;
    private List<CategoryVo> thirdCategory;

    public List<CategoryVo> getFirstCategory() {
        return firstCategory;
    }

    public void setFirstCategory(List<CategoryVo> firstCategory) {
        this.firstCategory = firstCategory;
    }

    public List<CategoryVo> getSecondCategory() {
        return secondCategory;
    }

    public void setSecondCategory(List<CategoryVo> secondCategory) {
        this.secondCategory = secondCategory;
    }

    public List<CategoryVo> getThirdCategory() {
        return thirdCategory;
    }

    public void setThirdCategory(List<CategoryVo> thirdCategory) {
        this.thirdCategory = thirdCategory;
    }

}

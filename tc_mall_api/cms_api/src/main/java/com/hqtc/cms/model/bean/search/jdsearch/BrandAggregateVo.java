package com.hqtc.cms.model.bean.search.jdsearch;

import java.util.List;

/**
 * @Description:
 * @Author: WangBin
 * @Date: 2018/7/7 16:10
 */

public class BrandAggregateVo{
    private List<BrandVo> brandList;
    private List<String> pinyinAggr;

    public List<BrandVo> getBrandList() {
        return brandList;
    }

    public void setBrandList(List<BrandVo> brandList) {
        this.brandList = brandList;
    }

    public List<String> getPinyinAggr() {
        return pinyinAggr;
    }

    public void setPinyinAggr(List<String> pinyinAggr) {
        this.pinyinAggr = pinyinAggr;
    }

}
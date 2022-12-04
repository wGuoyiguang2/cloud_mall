package com.hqtc.cms.model.bean;

import java.util.List;

/**
 * description:
 * Created by laiqingchuang on 18-7-18 .
 */
public class SimilarGoodsVo {

    private String dim;                  //维度
    private String saleName;             //销售名称
    private List<GoodsVo> saleAttrList;  //商品销售标签

    public String getDim() {
        return dim;
    }

    public void setDim(String dim) {
        this.dim = dim;
    }

    public String getSaleName() {
        return saleName;
    }

    public void setSaleName(String saleName) {
        this.saleName = saleName;
    }

    public List<GoodsVo> getSaleAttrList() {
        return saleAttrList;
    }

    public void setSaleAttrList(List<GoodsVo> saleAttrList) {
        this.saleAttrList = saleAttrList;
    }
}
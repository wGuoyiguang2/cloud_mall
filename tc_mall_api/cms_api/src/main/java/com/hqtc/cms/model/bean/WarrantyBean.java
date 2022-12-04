package com.hqtc.cms.model.bean;

import java.io.Serializable;
import java.util.List;

/**
 * description:
 * Created by laiqingchuang on 18-8-14 .
 */
public class WarrantyBean implements Serializable {
    private String categoryCode;  //保障服务类别名称
    private String detailUrl;     //保障服务类别静态页详情url
    private String displayName;   //保障服务分类名称
    private Integer displayNo;    //保障服务分类编码
    private String imgUrl;        //保障服务类别显示图标url
    private Integer mainSkuId;    //主商品的sku
    private List<FuwuSkuDetailBean> fuwuSkuDetailList;  //保障服务商品详情列表

    public String getCategoryCode() {
        return categoryCode;
    }

    public void setCategoryCode(String categoryCode) {
        this.categoryCode = categoryCode;
    }

    public String getDetailUrl() {
        return detailUrl;
    }

    public void setDetailUrl(String detailUrl) {
        this.detailUrl = detailUrl;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public Integer getDisplayNo() {
        return displayNo;
    }

    public void setDisplayNo(Integer displayNo) {
        this.displayNo = displayNo;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public Integer getMainSkuId() {
        return mainSkuId;
    }

    public void setMainSkuId(Integer mainSkuId) {
        this.mainSkuId = mainSkuId;
    }

    public List<FuwuSkuDetailBean> getFuwuSkuDetailList() {
        return fuwuSkuDetailList;
    }

    public void setFuwuSkuDetailList(List<FuwuSkuDetailBean> fuwuSkuDetailList) {
        this.fuwuSkuDetailList = fuwuSkuDetailList;
    }
}

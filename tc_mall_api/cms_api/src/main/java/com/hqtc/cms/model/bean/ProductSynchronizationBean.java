package com.hqtc.cms.model.bean;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * description:商品全量同步bean
 * Created by laiqingchuang on 19-1-10 .
 */
public class ProductSynchronizationBean implements Serializable {
    private Long sku;             //sku id
    private String name;	      //名称
    private String brandName;     //品牌名称
    private String category;      //三级分类信息
    private String imagePath;     //图片地址
    private String videoPath="";  //视频地址
    private BigDecimal jdPrice;   //京东价
    private BigDecimal price;	  //零售价
    private Integer state=1;	  //是否可用 0:下架 1:上架
    private Integer cat0;         //一级分类id
    private Integer cat1;         //二级分类id
    private Integer cat2;         //三级分类id
    private String weight;        //重量
    private String productArea;   //产地
    private String upc;           //条形号
    private String saleUnit;      //销售单位
    private String wareQD;        //商品清单
    private String introduction;  //pc端商品简介
    private String appintroduce;  //app端商品简介
    private String param;         //商品参数
    private String propCode;      //规格参数
    private String service;       //服务
    private String wReadMe;       //readme
    private String shouhou;       //售后
    private String wdis;          //商品详情
    private String jsContent;     //js内容
    private String cssContent;    //css内容
    private String htmlContent;   //html内容

    public Long getSku() {
        return sku;
    }

    public void setSku(Long sku) {
        this.sku = sku;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public String getVideoPath() {
        return videoPath;
    }

    public void setVideoPath(String videoPath) {
        this.videoPath = videoPath;
    }

    public BigDecimal getJdPrice() {
        return jdPrice;
    }

    public void setJdPrice(BigDecimal jdPrice) {
        this.jdPrice = jdPrice;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
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

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getProductArea() {
        return productArea;
    }

    public void setProductArea(String productArea) {
        this.productArea = productArea;
    }

    public String getUpc() {
        return upc;
    }

    public void setUpc(String upc) {
        this.upc = upc;
    }

    public String getSaleUnit() {
        return saleUnit;
    }

    public void setSaleUnit(String saleUnit) {
        this.saleUnit = saleUnit;
    }

    public String getWareQD() {
        return wareQD;
    }

    public void setWareQD(String wareQD) {
        this.wareQD = wareQD;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public String getAppintroduce() {
        return appintroduce;
    }

    public void setAppintroduce(String appintroduce) {
        this.appintroduce = appintroduce;
    }

    public String getParam() {
        return param;
    }

    public void setParam(String param) {
        this.param = param;
    }

    public String getPropCode() {
        return propCode;
    }

    public void setPropCode(String propCode) {
        this.propCode = propCode;
    }

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }

    public String getwReadMe() {
        return wReadMe;
    }

    public void setwReadMe(String wReadMe) {
        this.wReadMe = wReadMe;
    }

    public String getShouhou() {
        return shouhou;
    }

    public void setShouhou(String shouhou) {
        this.shouhou = shouhou;
    }

    public String getWdis() {
        return wdis;
    }

    public void setWdis(String wdis) {
        this.wdis = wdis;
    }

    public String getJsContent() {
        return jsContent;
    }

    public void setJsContent(String jsContent) {
        this.jsContent = jsContent;
    }

    public String getCssContent() {
        return cssContent;
    }

    public void setCssContent(String cssContent) {
        this.cssContent = cssContent;
    }

    public String getHtmlContent() {
        return htmlContent;
    }

    public void setHtmlContent(String htmlContent) {
        this.htmlContent = htmlContent;
    }
}

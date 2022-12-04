package com.cibnvideo.jd.goods.params.yanbao;

/**
 * description:延保
 * Created by laiqingchuang on 18-8-14 .
 */
public class YanbaoRequestParams {
    private String skuIds;    //商品编号(多个用英文逗号隔开)
    private Integer province; //省id
    private Integer city;     //市id
    private Integer county;   //县id
    private Integer town;     //乡镇id

    public String getSkuIds() {
        return skuIds;
    }

    public void setSkuIds(String skuIds) {
        this.skuIds = skuIds;
    }

    public Integer getProvince() {
        return province;
    }

    public void setProvince(Integer province) {
        this.province = province;
    }

    public Integer getCity() {
        return city;
    }

    public void setCity(Integer city) {
        this.city = city;
    }

    public Integer getCounty() {
        return county;
    }

    public void setCounty(Integer county) {
        this.county = county;
    }

    public Integer getTown() {
        return town;
    }

    public void setTown(Integer town) {
        this.town = town;
    }
}

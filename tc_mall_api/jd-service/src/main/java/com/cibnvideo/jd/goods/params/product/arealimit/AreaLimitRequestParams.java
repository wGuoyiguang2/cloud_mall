package com.cibnvideo.jd.goods.params.product.arealimit;

/**
 * @Description:
 * @Author: WangBin
 * @Date: 2018/6/29 20:06
 */
public class AreaLimitRequestParams {
    private String skuIds;
    private Integer province;
    private Integer city;
    private Integer county;
    private Integer Town;

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
        return Town;
    }

    public void setTown(Integer town) {
        Town = town;
    }
}

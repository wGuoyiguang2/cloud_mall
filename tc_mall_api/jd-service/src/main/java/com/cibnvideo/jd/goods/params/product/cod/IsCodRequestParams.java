package com.cibnvideo.jd.goods.params.product.cod;

/**
 * @Description:
 * @Author: WangBin
 * @Date: 2018/6/29 20:27
 */
public class IsCodRequestParams {
    private String skuIds;//	String	Y	商品编号，支持批量，以【，】分割（最高支持50个）
    private Integer province;//	Integer	Y	京东一级地址编号
    private Integer city;//	Integer	Y	京东二级地址编号
    private Integer county;//	Integer	Y	京东三级地址编号
    private Integer town;//	Integer	Y	京东编号

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

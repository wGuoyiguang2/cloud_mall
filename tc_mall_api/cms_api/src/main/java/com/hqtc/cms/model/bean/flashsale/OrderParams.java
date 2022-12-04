package com.hqtc.cms.model.bean.flashsale;

import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Map;

/**
 * @Description: 秒杀异步下单参数
 * @Author: WangBin
 * @Date: 2018/8/16 11:44
 */
public class OrderParams implements Serializable{
    private BigDecimal price;
    @NotEmpty(message = "表单id不能为空")
    private String requestId;
    @NotNull(message = "活动id不能为空")
    private String activityId;
    private Integer userId;
    @NotNull(message = "大客户id不能为空")
    private String venderId;
    @NotNull(message = "省份id不能为空")
    private Integer provinceId;
    @NotNull(message = "城市id不能为空")
    private Integer cityId;
    @NotNull(message = "区/县id不能为空")
    private Integer countyId;
    private Integer townId = 0;
    @NotEmpty(message="地址不能为空")
    private String detail;
    @NotEmpty(message="姓名不能为空")
    private String name;
    @NotEmpty(message="电话不能为空")
    private String phone;
    private Integer invoice = 0;
    @NotEmpty(message="商品id不能为空")
    private String sku;
    @NotNull(message="商品数量不能为空")
    private Integer count;
    private String cookie;
    private Map<String, Integer> products;

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public String getCookie() {
        return cookie;
    }

    public void setCookie(String cookie) {
        this.cookie = cookie;
    }

    public String getActivityId() {
        return activityId;
    }

    public void setActivityId(String activityId) {
        this.activityId = activityId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getVenderId() {
        return venderId;
    }

    public void setVenderId(String venderId) {
        this.venderId = venderId;
    }

    public Integer getProvinceId() {
        return provinceId;
    }

    public void setProvinceId(Integer provinceId) {
        this.provinceId = provinceId;
    }

    public Integer getCityId() {
        return cityId;
    }

    public void setCityId(Integer cityId) {
        this.cityId = cityId;
    }

    public Integer getCountyId() {
        return countyId;
    }

    public void setCountyId(Integer countyId) {
        this.countyId = countyId;
    }

    public Integer getTownId() {
        return townId;
    }

    public void setTownId(Integer townId) {
        this.townId = townId;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Integer getInvoice() {
        return invoice;
    }

    public void setInvoice(Integer invoice) {
        this.invoice = invoice;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public Map<String, Integer> getProducts() {
        return products;
    }

    public void setProducts(Map<String, Integer> products) {
        this.products = products;
    }

    @Override
    public String toString() {
        return "OrderParams{" +
                "activityId='" + activityId + '\'' +
                ", userId=" + userId +
                ", venderId='" + venderId + '\'' +
                ", provinceId=" + provinceId +
                ", cityId=" + cityId +
                ", countyId=" + countyId +
                ", townId=" + townId +
                ", detail='" + detail + '\'' +
                ", name='" + name + '\'' +
                ", phone='" + phone + '\'' +
                ", invoice=" + invoice +
                ", sku='" + sku + '\'' +
                ", count=" + count +
                ", products=" + products +
                '}';
    }
}

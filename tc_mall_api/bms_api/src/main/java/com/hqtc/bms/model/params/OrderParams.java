package com.hqtc.bms.model.params;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;
import java.util.Map;

/**
 * Created by wanghaoyang on 18-7-4.
 */
public class OrderParams {

//    @NotNull(message = "请传入正确的userId")
//    @Min(value = 1, message = "请传入正确的userId")
    private int userId;

//    @NotNull(message = "请传入正确的venderId")
//    @Min(value = 1, message = "请传入正确的venderId")
    private int venderId;

    //第一版用此参数,后续版本以products替代
    @Size(min = 1, max = 50, message = "productIds数量在1到50")
    private List<String> productIds;

    @NotNull(message = "请传入正确的provinceId")
    @Min(value = 1, message = "请传入正确的provinceId")
    private int provinceId;

    @NotNull(message = "请传入正确的cityId")
    @Min(value = 1, message = "请传入正确的cityId")
    private int cityId;

    @NotNull(message = "请传入正确的countyId")
    @Min(value = 1, message = "请传入正确的countyId")
    private int countyId;

    @NotNull(message = "请传入正确的townId")
    @Min(value = 0, message = "请传入正确的townId")
    private int townId;

    @NotNull(message = "请传入正确的detail")
    @NotBlank(message = "请传入正确的detail")
    private String detail;

    @NotNull(message = "请传入正确的name")
    @NotBlank(message = "请传入正确的name")
    private String name;

    @NotNull(message = "请传入正确的phone")
    @NotBlank(message = "请传入正确的phone")
    private String phone;

    @NotNull(message = "请传入正确的invoice")
    @Min(value = 0, message = "请传入正确的invoice")
    @Max(value = 1, message = "请传入正确的invoice")
    private int invoice = 0;

//    @NotEmpty(message = "请传入正确的products")
//    @Size(min = 1, max = 50, message = "products数量在1到50")
    private Map<String, Integer> products;

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getVenderId() {
        return venderId;
    }

    public void setVenderId(int venderId) {
        this.venderId = venderId;
    }

    public List<String> getProductIds() {
        return productIds;
    }

    public void setProductIds(List<String> productIds) {
        this.productIds = productIds;
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

    public int getProvinceId() {
        return provinceId;
    }

    public void setProvinceId(int provinceId) {
        this.provinceId = provinceId;
    }

    public int getCityId() {
        return cityId;
    }

    public void setCityId(int cityId) {
        this.cityId = cityId;
    }

    public int getCountyId() {
        return countyId;
    }

    public void setCountyId(int countyId) {
        this.countyId = countyId;
    }

    public int getTownId() {
        return townId;
    }

    public void setTownId(int townId) {
        this.townId = townId;
    }

    public int getInvoice() {
        return invoice;
    }

    public void setInvoice(int invoice) {
        this.invoice = invoice;
    }

    public Map<String, Integer> getProducts() {
        return products;
    }

    public void setProducts(Map<String, Integer> products) {
        this.products = products;
    }
}

package com.hqtc.bms.model.params;

import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.beans.factory.annotation.Value;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;
import java.util.Map;

/**
 * Created by wanghaoyang on 18-7-17.
 */
public class StockDetailParams {

    private Map<Long, Integer> productIds;

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

    public Map<Long, Integer> getProductIds() {
        return productIds;
    }

    public void setProductIds(Map<Long, Integer> productIds) {
        this.productIds = productIds;
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

    public int getTownId() {
        return townId;
    }

    public void setTownId(int townId) {
        this.townId = townId;
    }

    public int getCountyId() {
        return countyId;
    }

    public void setCountyId(int countyId) {
        this.countyId = countyId;
    }
}

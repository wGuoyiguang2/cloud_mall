package com.cibnvideo.oms.tcmallcustomer.bean;

import java.io.Serializable;
import java.math.BigDecimal;

public class PricePolicyCategory implements Serializable {
    private BigDecimal percent;
    private int cat0;
    private int cat1;
    private int cat2;

    public BigDecimal getPercent() {
        return percent;
    }

    public void setPercent(BigDecimal percent) {
        this.percent = percent;
    }

    public int getCat0() {
        return cat0;
    }

    public void setCat0(int cat0) {
        this.cat0 = cat0;
    }

    public int getCat1() {
        return cat1;
    }

    public void setCat1(int cat1) {
        this.cat1 = cat1;
    }

    public int getCat2() {
        return cat2;
    }

    public void setCat2(int cat2) {
        this.cat2 = cat2;
    }
}

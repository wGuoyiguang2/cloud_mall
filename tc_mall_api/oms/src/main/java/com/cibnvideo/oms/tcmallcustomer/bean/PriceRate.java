package com.cibnvideo.oms.tcmallcustomer.bean;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class PriceRate implements Serializable {
    private Integer id;
    private Integer venderid;
    private Integer policyid;
    private BigDecimal startRate;
    private BigDecimal endRate;
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date ctime;
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date utime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getVenderid() {
        return venderid;
    }

    public void setVenderid(Integer venderid) {
        this.venderid = venderid;
    }

    public Integer getPolicyid() {
        return policyid;
    }

    public void setPolicyid(Integer policyid) {
        this.policyid = policyid;
    }

    public BigDecimal getStartRate() {
        return startRate;
    }

    public void setStartRate(BigDecimal startRate) {
        this.startRate = startRate;
    }

    public BigDecimal getEndRate() {
        return endRate;
    }

    public void setEndRate(BigDecimal endRate) {
        this.endRate = endRate;
    }

    public Date getCtime() {
        return ctime;
    }

    public void setCtime(Date ctime) {
        this.ctime = ctime;
    }

    public Date getUtime() {
        return utime;
    }

    public void setUtime(Date utime) {
        this.utime = utime;
    }

    public boolean isOverFlow(PriceRate priceRate) {
        if(this.venderid != priceRate.getVenderid()) {
            return false;
        }
        if(this.startRate == null && this.endRate == null) {
            return true;
        }
        BigDecimal maxStartRate = maxStartRate(this.startRate, priceRate.getStartRate());
        BigDecimal minEndRate = minEndRate(this.endRate, priceRate.getEndRate());
        if(maxStartRate == null || minEndRate == null) {
            return true;
        }
        if(maxStartRate.compareTo(minEndRate) == -1) {
            return true;
        } else {
            return false;
        }


    }

    private BigDecimal maxStartRate(BigDecimal... rates) {
        BigDecimal result = null;
        for(BigDecimal rate:rates) {
            if(rate != null) {
                if(result != null) {
                    if(rate.compareTo(result) == 1) {
                        result = rate;
                    }
                } else {
                    result = rate;
                }
            }
        }
        return result;
    }

    private BigDecimal minEndRate(BigDecimal... rates) {
        BigDecimal result = null;
        for(BigDecimal rate:rates) {
            if(rate != null) {
                if(result != null) {
                    if(rate.compareTo(result) == -1) {
                        result = rate;
                    }
                } else {
                    result = rate;
                }
            }
        }
        return result;
    }
}

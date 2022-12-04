package com.hqtc.cms.model.bean.search.jdsearch;

import java.math.BigDecimal;

/**
 * @Description:
 * @Author: WangBin
 * @Date: 2018/7/7 16:08
 */

public class PriceIntervalAggregateVo{
    private BigDecimal max;
    private BigDecimal min;

    public BigDecimal getMax() {
        return max;
    }

    public void setMax(BigDecimal max) {
        this.max = max;
    }

    public BigDecimal getMin() {
        return min;
    }

    public void setMin(BigDecimal min) {
        this.min = min;
    }
}

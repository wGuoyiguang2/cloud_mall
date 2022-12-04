package com.hqtc.bms.model.params;

import java.math.BigDecimal;

/**
 * @Description:
 * @Author: WangBin
 * @Date: 2018/8/9 10:41
 */
public class SalesAmountVo {
    private BigDecimal salesAmount=new BigDecimal(0);//销售总金额
    private BigDecimal sumFreight=new BigDecimal(0);//大客户支出总运费
    private BigDecimal gainsAmount=new BigDecimal(0);//盈利总金额

    public BigDecimal getSumFreight() {
        return sumFreight;
    }

    public void setSumFreight(BigDecimal sumFreight) {
        this.sumFreight = sumFreight;
    }

    public BigDecimal getSalesAmount() {
        return salesAmount;
    }

    public void setSalesAmount(BigDecimal salesAmount) {
        this.salesAmount = salesAmount;
    }

    public BigDecimal getGainsAmount() {
        return gainsAmount;
    }

    public void setGainsAmount(BigDecimal gainsAmount) {
        this.gainsAmount = gainsAmount;
    }
}

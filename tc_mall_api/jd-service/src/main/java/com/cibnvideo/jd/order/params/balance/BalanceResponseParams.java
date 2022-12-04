package com.cibnvideo.jd.order.params.balance;

import com.cibnvideo.jd.common.params.BaseResponseParams;

import java.math.BigDecimal;

/**
 * @Description:
 * @Author: WangBin
 * @Date: 2018/7/13 16:20
 */
public class BalanceResponseParams {
    private BalanceVo biz_price_balance_get_response;

    public BalanceVo getBiz_price_balance_get_response() {
        return biz_price_balance_get_response;
    }

    public void setBiz_price_balance_get_response(BalanceVo biz_price_balance_get_response) {
        this.biz_price_balance_get_response = biz_price_balance_get_response;
    }

    class BalanceVo extends BaseResponseParams{
        private BigDecimal result;

        public BigDecimal getResult() {
            return result;
        }

        public void setResult(BigDecimal result) {
            this.result = result;
        }
    }
}

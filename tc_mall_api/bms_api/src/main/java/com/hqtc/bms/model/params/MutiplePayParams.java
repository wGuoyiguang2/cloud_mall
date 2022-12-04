package com.hqtc.bms.model.params;

import java.util.List;

/**
 * Created by wanghaoyang on 18-9-20.
 */
public class MutiplePayParams extends QrCodeParams {
    private List<String> cardNo;
    private String userCode;

    public List<String> getCardNo() {
        return cardNo;
    }

    public void setCardNo(List<String> cardNo) {
        this.cardNo = cardNo;
    }

    public String getUserCode() {
        return userCode;
    }

    public void setUserCode(String userCode) {
        this.userCode = userCode;
    }
}

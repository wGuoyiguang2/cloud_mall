package com.hqtc.bms.model.response.jdorderinfo;

/**
 * @Description:
 * @Author: WangBin
 * @Date: 2018/7/23 21:51
 */
public class OrderResponseVo{

    protected String code;
    protected String resultCode;
    protected String success;
    protected String resultMessage;
    private OrderVo result;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getResultCode() {
        return resultCode;
    }

    public void setResultCode(String resultCode) {
        this.resultCode = resultCode;
    }

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public String getResultMessage() {
        return resultMessage;
    }

    public void setResultMessage(String resultMessage) {
        this.resultMessage = resultMessage;
    }

    public OrderVo getResult() {
        return result;
    }

    public void setResult(OrderVo result) {
        this.result = result;
    }

}
package com.cibnvideo.jdsynctask.model;

public class BaseResponseParams {
    protected String code;
    protected String resultCode;
    protected String success;
    protected String resultMessage;

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

    @Override
    public String toString(){
        return "{\"code=\"\"" + code + "\" \"resultCode\"=\"" + resultCode + "\" \"success\"=\"" + success + "\" \"resultMessage\"=\"" + resultMessage + "\"";
    }

}

package com.cibnvideo.jd.common.params;

/**
 * @Description:
 * @Author: WangBin
 * @Date: 2018/6/22 19:03
 */
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

}

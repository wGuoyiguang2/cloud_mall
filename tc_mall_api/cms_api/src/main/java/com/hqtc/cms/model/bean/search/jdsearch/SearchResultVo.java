package com.hqtc.cms.model.bean.search.jdsearch;

/**
 * @Description:
 * @Author: WangBin
 * @Date: 2018/7/7 16:07
 */

public class SearchResultVo{
    private String success;
    private String resultMessage;
    private String resultCode;
    private SearchVo result;
    private String code;

    public SearchVo getResult() {
        return result;
    }

    public void setResult(SearchVo result) {
        this.result = result;
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

    public String getResultCode() {
        return resultCode;
    }

    public void setResultCode(String resultCode) {
        this.resultCode = resultCode;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}

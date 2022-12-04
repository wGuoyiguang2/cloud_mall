package com.cibnvideo.jd.common.params;

/**
 * @Description:
 * @Author: WangBin
 * @Date: 2018/10/12 11:24
 */
public class ErrorResponse {
    private ErrorResponseInner errorResponse;
    public ErrorResponseInner getErrorResponse() {
        return errorResponse;
    }

    public void setErrorResponse(ErrorResponseInner errorResponse) {
        this.errorResponse = errorResponse;
    }
}

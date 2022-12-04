package com.cibnvideo.jdsynctask.model;

import java.io.Serializable;

public class StyleOfMobileResponse implements Serializable {
    public StyleOfMobileResult getDetail() {
        return detail;
    }

    public void setDetail(StyleOfMobileResult detail) {
        this.detail = detail;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    private StyleOfMobileResult detail;
    private Integer code;
}

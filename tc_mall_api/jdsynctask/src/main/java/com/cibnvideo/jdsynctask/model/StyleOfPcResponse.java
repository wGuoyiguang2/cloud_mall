package com.cibnvideo.jdsynctask.model;

import java.io.Serializable;

public class StyleOfPcResponse implements Serializable {
    public StyleOfPcResult getDetail() {
        return detail;
    }

    public void setDetail(StyleOfPcResult detail) {
        this.detail = detail;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    private StyleOfPcResult detail;
    private Integer code;
}

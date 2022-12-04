package com.hqtc.bms.model.params;

import java.io.Serializable;

/**
 * description:开发票返回结果
 * Created by laiqingchuang on 18-7-12 .
 */
public class InvoiceaddResponseParams implements Serializable {

    private String status;  //状态
    private String message; //描述
    private String fpqqlsh; //发票请求流水号

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getFpqqlsh() {
        return fpqqlsh;
    }

    public void setFpqqlsh(String fpqqlsh) {
        this.fpqqlsh = fpqqlsh;
    }
}

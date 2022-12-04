package com.hqtc.bms.model.rpc;

import java.io.Serializable;
import java.util.List;

/**
 * description:开票结果查询bean
 * Created by laiqingchuang on 18-7-4 .
 */
public class InvoiceResultRequestBean implements Serializable {
    private String identity;      //身份认证
    private List<String> fpqqlsh; //发票请求流水号

    public String getIdentity() {
        return identity;
    }

    public void setIdentity(String identity) {
        this.identity = identity;
    }

    public List<String> getFpqqlsh() {
        return fpqqlsh;
    }

    public void setFpqqlsh(List<String> fpqqlsh) {
        this.fpqqlsh = fpqqlsh;
    }
}

package com.hqtc.bms.model.rpc;

import java.io.Serializable;

/**
 * Created by makuan on 18-7-21.
 */
public class AfterSaleReasonBean implements Serializable {
    private String key = "";
    private String value = "";

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}

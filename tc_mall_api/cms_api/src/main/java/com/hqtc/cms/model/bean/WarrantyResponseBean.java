package com.hqtc.cms.model.bean;

import java.io.Serializable;
import java.util.Map;

/**
 * description:延保
 * Created by laiqingchuang on 18-8-14 .
 */
public class WarrantyResponseBean implements Serializable {
    private Map<String,Object> result;

    public Map<String, Object> getResult() {
        return result;
    }

    public void setResult(Map<String, Object> result) {
        this.result = result;
    }
}

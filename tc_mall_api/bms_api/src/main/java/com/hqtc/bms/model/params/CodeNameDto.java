package com.hqtc.bms.model.params;

import java.io.Serializable;

/**
 * description:
 * Created by laiqingchuang on 18-7-12 .
 */
public class CodeNameDto implements Serializable {
    private String code;    //退货(10)、换货(20)、维修(30)
    private String name;    //服务类型名称

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

package com.cibnvideo.jd.token.constants;

/**
 * @Description:
 * @Author: WangBin
 * @Date: 2018/6/22 16:58
 */
public enum GrantTypeEnum {
    PASSWORD("password"),REFRESH_TOKEN("refresh_token");
    private GrantTypeEnum(String value){
        this.value = value;
    }
    private String value;
    public String getValue(){
        return this.value;
    }
}

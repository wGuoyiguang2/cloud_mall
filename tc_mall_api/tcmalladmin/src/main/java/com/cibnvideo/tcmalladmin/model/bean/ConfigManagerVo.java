package com.cibnvideo.tcmalladmin.model.bean;

/**
 * @Description:
 * @Author: WangBin
 * @Date: 2018/8/8 14:44
 */
public class ConfigManagerVo {
    private Integer id;
    private Long venderid;
    private String key;
    private String value;
    private Integer type;
    private Integer status;
    private Integer position;
    private String ctime;
    private String utime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Long getVenderid() {
        return venderid;
    }

    public void setVenderid(Long venderid) {
        this.venderid = venderid;
    }

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

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getPosition() {
        return position;
    }

    public void setPosition(Integer position) {
        this.position = position;
    }

    public String getCtime() {
        return ctime;
    }

    public void setCtime(String ctime) {
        this.ctime = ctime;
    }

    public String getUtime() {
        return utime;
    }

    public void setUtime(String utime) {
        this.utime = utime;
    }
}

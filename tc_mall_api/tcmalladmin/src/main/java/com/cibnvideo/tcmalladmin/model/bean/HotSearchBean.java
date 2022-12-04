package com.cibnvideo.tcmalladmin.model.bean;

/**
 * @Description:
 * @Author: WangBin
 * @Date: 2018/8/13 14:26
 */
public class HotSearchBean {
    private Integer id;
    private Integer venderid;
    private String keyword;
    private Integer position;
    private Integer status;
    private String ctime;
    private String utime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getVenderid() {
        return venderid;
    }

    public void setVenderid(Integer venderid) {
        this.venderid = venderid;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public Integer getPosition() {
        return position;
    }

    public void setPosition(Integer position) {
        this.position = position;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
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

    @Override
    public String toString() {
        return "HotSearchBean{" +
                "id=" + id +
                ", venderid=" + venderid +
                ", keyword='" + keyword + '\'' +
                ", position=" + position +
                ", status=" + status +
                ", ctime=" + ctime +
                ", utime=" + utime +
                '}';
    }
}

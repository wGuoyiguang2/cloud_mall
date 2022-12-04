package com.hqtc.ims.favorite.model.bean;

import java.util.Date;

/**
 * @Description:
 * @Author: WangBin
 * @Date: 2018/6/25 19:14
 */
public class FavoriteBean {
    private Integer id;
    private Integer userId;
    private Long sourceId;
    private Date ctime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Long getSourceId() {
        return sourceId;
    }

    public void setSourceId(Long sourceId) {
        this.sourceId = sourceId;
    }

    public Date getCtime() {
        return ctime;
    }

    public void setCtime(Date ctime) {
        this.ctime = ctime;
    }
}

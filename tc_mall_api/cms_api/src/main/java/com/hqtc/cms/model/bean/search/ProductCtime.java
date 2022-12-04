package com.hqtc.cms.model.bean.search;

import java.util.Date;

/**
 * @Description: 查询入库时间VO
 * @Author: WangBin
 * @Date: 2018/7/18 11:26
 */
public class ProductCtime {
    private Long sku;
    private Date ctime;

    public Long getSku() {
        return sku;
    }

    public void setSku(Long sku) {
        this.sku = sku;
    }

    public Date getCtime() {
        return ctime;
    }

    public void setCtime(Date ctime) {
        this.ctime = ctime;
    }
}

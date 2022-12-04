package com.hqtc.bms.model.params;

/**
 * @Description:
 * @Author: WangBin
 * @Date: 2018/7/24 20:24
 */
public class InvoiceInfoVo {
    private Integer opened;
    private Integer canceled;
    private Integer count;

    public Integer getOpened() {
        return opened;
    }

    public void setOpened(Integer opened) {
        this.opened = opened;
    }

    public Integer getCanceled() {
        return canceled;
    }

    public void setCanceled(Integer canceled) {
        this.canceled = canceled;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }
}

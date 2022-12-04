package com.hqtc.bms.model.params;

/**
 * Created by wanghaoyang on 18-7-21.
 */
public class CheckProductStockResult {
    private int status;//-1:查询失败|0:有货|>0:商品id,表示此商品无货
    private String msg;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}

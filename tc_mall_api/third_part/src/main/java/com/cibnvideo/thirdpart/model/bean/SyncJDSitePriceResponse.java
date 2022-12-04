package com.cibnvideo.thirdpart.model.bean;

/**
 * @Description:
 * @Author: WangBin
 * @Date: 2019/1/21 17:27
 */
public class SyncJDSitePriceResponse {
    private String id; //J_后为商品sku
    private String m;
    private String op;
    private String p; //京东官网价格

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getM() {
        return m;
    }

    public void setM(String m) {
        this.m = m;
    }

    public String getOp() {
        return op;
    }

    public void setOp(String op) {
        this.op = op;
    }

    public String getP() {
        return p;
    }

    public void setP(String p) {
        this.p = p;
    }
}

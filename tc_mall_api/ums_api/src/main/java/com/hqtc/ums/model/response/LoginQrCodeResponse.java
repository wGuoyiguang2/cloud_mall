package com.hqtc.ums.model.response;

/**
 * Created by wanghaoyang on 18-11-2.
 */
public class LoginQrCodeResponse {
    private int scanId;
    private String ticket;

    public int getScanId() {
        return scanId;
    }

    public void setScanId(int scanId) {
        this.scanId = scanId;
    }

    public String getTicket() {
        return ticket;
    }

    public void setTicket(String ticket) {
        this.ticket = ticket;
    }
}

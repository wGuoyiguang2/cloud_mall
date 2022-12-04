package com.hqtc.ums.model.response;

/**
 * Created by wanghaoyang on 18-11-27.
 */
public class QrCodeResponse {
    private String qrTicket;
    private String scanId;

    public String getQrTicket() {
        return qrTicket;
    }

    public void setQrTicket(String qrTicket) {
        this.qrTicket = qrTicket;
    }

    public String getScanId() {
        return scanId;
    }

    public void setScanId(String scanId) {
        this.scanId = scanId;
    }
}

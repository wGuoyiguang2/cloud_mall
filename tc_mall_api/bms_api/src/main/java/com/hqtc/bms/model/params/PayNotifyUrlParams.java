package com.hqtc.bms.model.params;

import org.springframework.web.bind.annotation.RequestHeader;

public class PayNotifyUrlParams {
    private String sign;
    private String out_trade_no;
    private String total_fee;
    private String pay_time;
    private String pay_type;
    private String order_sn;
    private String ip;
    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getOut_trade_no() {
        return out_trade_no;
    }

    public void setOut_trade_no(String out_trade_no) {
        this.out_trade_no = out_trade_no;
    }

    public String getTotal_fee() {
        return total_fee;
    }

    public void setTotal_fee(String total_fee) {
        this.total_fee = total_fee;
    }

    public String getPay_time() {
        return pay_time;
    }

    public void setPay_time(String pay_time) {
        this.pay_time = pay_time;
    }

    public String getPay_type() {
        return pay_type;
    }

    public void setPay_type(String pay_type) {
        this.pay_type = pay_type;
    }

    public String getOrder_sn() {
        return order_sn;
    }

    public void setOrder_sn(String order_sn) {
        this.order_sn = order_sn;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(@RequestHeader(value = "X-Real-IP",defaultValue = "00.00.00.00")String ip) {
        this.ip = ip;
    }
}

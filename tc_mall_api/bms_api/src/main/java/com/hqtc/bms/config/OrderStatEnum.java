package com.hqtc.bms.config;

/**
 * description:订单状态
 * Created by laiqingchuang on 18-7-17 .
 */
public enum OrderStatEnum {
    ORDER_STAT_1("1","新单"),
    ORDER_STAT_2("2","等待支付"),
    ORDER_STAT_3("3","等待支付确认"),
    ORDER_STAT_4("4","延迟付款确认"),
    ORDER_STAT_5("5","订单暂停"),
    ORDER_STAT_6("6","店长最终审核"),
    ORDER_STAT_7("7","等待打印"),
    ORDER_STAT_8("8","等待出库"),
    ORDER_STAT_9("9","等待打包"),
    ORDER_STAT_10("10","等待发货"),
    ORDER_STAT_11("11","自提途中"),
    ORDER_STAT_12("12","上门提货"),
    ORDER_STAT_13("13","自提退货"),
    ORDER_STAT_14("14","确认自提"),
    ORDER_STAT_16("16","等待确认收货"),
    ORDER_STAT_17("17","配送退货"),
    ORDER_STAT_18("18","货到付款确认"),
    ORDER_STAT_19("19","已完成"),
    ORDER_STAT_21("21","收款确认"),
    ORDER_STAT_22("22","锁定"),
    ORDER_STAT_29("29","等待三方出库"),
    ORDER_STAT_30("30","等待三方发货"),
    ORDER_STAT_31("31","等待三方发货完成");

    private String key;
    private String value;

    OrderStatEnum(String key,String value){
        this.key=key;
        this.value=value;
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
}

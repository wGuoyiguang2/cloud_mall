package com.hqtc.bms.model.params;

import java.math.BigDecimal;
import java.util.Date;

/**
 * description:售后
 * Created by laiqingchuang on 18-8-13 .
 */
public class AftersaleBean {
    private String orderSn;      //平台订单号
    private String childTradeNo; //京东订单号
    private Integer serviceNo;   //服务单号
    private String name;         //联系人
    private Long productId;      //商品id
    private String productName;  //商品名称
    private BigDecimal price;    //商品价格
    private Integer count;       //商品数量
    private Integer serviceType; //服务类型 10:退货|20:换货|30:维修
    private Integer status;      //售后状态 10:申请阶段|20:审核不通过|21:客服审核|22:商家审核|31:京东收货|32:商家收货|33:京东处理|34:商家处理|40:用户确认|50:完成|60:取消
    private Date ctime;
    private String imagePath;    //图片地址

    public String getOrderSn() {
        return orderSn;
    }

    public void setOrderSn(String orderSn) {
        this.orderSn = orderSn;
    }

    public String getChildTradeNo() {
        return childTradeNo;
    }

    public void setChildTradeNo(String childTradeNo) {
        this.childTradeNo = childTradeNo;
    }

    public Integer getServiceNo() {
        return serviceNo;
    }

    public void setServiceNo(Integer serviceNo) {
        this.serviceNo = serviceNo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public Integer getServiceType() {
        return serviceType;
    }

    public void setServiceType(Integer serviceType) {
        this.serviceType = serviceType;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getCtime() {
        return ctime;
    }

    public void setCtime(Date ctime) {
        this.ctime = ctime;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }
}

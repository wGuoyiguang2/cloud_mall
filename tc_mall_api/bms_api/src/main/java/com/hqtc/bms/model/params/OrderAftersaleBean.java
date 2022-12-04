package com.hqtc.bms.model.params;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * description:售后流水
 * Created by laiqingchuang on 18-8-2 .
 */
public class OrderAftersaleBean implements Serializable {

    private Integer id;          //ID
    private Integer venderid;    //大客户id
    private Integer userId;      //用户id
    private String orderSn;      //平台订单号
    private String childTradeNo; //京东订单号
    private Integer serviceNo;   //服务单号
    private List<Integer> serviceNoList;   //服务单号
    private String name;         //联系人
    private String phone;        //电话
    private Long productId;      //商品id
    private String productName;  //商品名称
    private BigDecimal price;    //商品价格
    private Integer count;       //商品数量
    private Integer serviceType; //服务类型 10:退货|20:换货|30:维修
    private String reason;       //退货原因
    private Integer backType;    //退回方式 4:上门取件|40:客户发货|7:客户送货
    private Integer returnType;  //返件方式 10:自营配送|20:第三方配送
    private String backAddr;     //取货地址
    private String returnAddr;   //收货地址
    private Integer status;      //售后状态 10:申请阶段|20:审核不通过|21:客服审核|22:商家审核|31:京东收货|32:商家收货|33:京东处理|34:商家处理|40:用户确认|50:完成|60:取消|100:申请售后失败
    private String remarks;      //描述
    private String imagePath;    //图片地址
    private Integer seq;         //售后序号
    private Date ctime;
    private Date utime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getOrderSn() {
        return orderSn;
    }

    public List<Integer> getServiceNoList() {
        return serviceNoList;
    }

    public void setServiceNoList(List<Integer> serviceNoList) {
        this.serviceNoList = serviceNoList;
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

    public String getPhone() {
        return phone;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Integer getVenderid() {
        return venderid;
    }

    public void setVenderid(Integer venderid) {
        this.venderid = venderid;
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

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public Integer getBackType() {
        return backType;
    }

    public void setBackType(Integer backType) {
        this.backType = backType;
    }

    public Integer getReturnType() {
        return returnType;
    }

    public void setReturnType(Integer returnType) {
        this.returnType = returnType;
    }

    public String getBackAddr() {
        return backAddr;
    }

    public void setBackAddr(String backAddr) {
        this.backAddr = backAddr;
    }

    public String getReturnAddr() {
        return returnAddr;
    }

    public void setReturnAddr(String returnAddr) {
        this.returnAddr = returnAddr;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public Integer getSeq() {
        return seq;
    }

    public void setSeq(Integer seq) {
        this.seq = seq;
    }

    public Date getCtime() {
        return ctime;
    }

    public void setCtime(Date ctime) {
        this.ctime = ctime;
    }

    public Date getUtime() {
        return utime;
    }

    public void setUtime(Date utime) {
        this.utime = utime;
    }
}

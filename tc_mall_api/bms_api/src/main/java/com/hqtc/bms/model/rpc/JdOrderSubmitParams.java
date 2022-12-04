package com.hqtc.bms.model.rpc;

import java.util.List;
import java.util.Map;

/**
 * 京东统一下单接口参数
 * Created by wanghaoyang on 18-7-9.
 */
public class JdOrderSubmitParams {
    private String thirdOrder;
    private List<Map<String, Integer>> sku;
    private String name;
    private int province;
    private int city;
    private int county;
    private int town = 0;
    private String address;
    private int zip;
    private String phone = "";
    private String mobile;
    private String email = "";
    private String unpl = "";
    private String remark = "";
    private int invoiceState = 0;
    private String invoiceType = "";
    private String invoiceName = "";
    private String invoicePhone = "";
    private String invoiceProvice = "";
    private String invoiceCity = "";
    private String invoiceCounty = "";
    private String invoiceTown = "";
    private String invoiceAddress = "";
    private String regCompanyName = "";
    private String regCode = "";
    private String regAddr = "";
    private String regPhone = "";
    private String regBank = "";
    private String regBankAccount = "";
    private String selectedInvoiceTitle = "";
    private String companyName = "";
    private String invoiceContent = "";
    private int paymentType = 4;
    private int isUseBalance = 0;
    private int submitState = 0;
    private String doOrderPriceMode = "";
    private String orderPriceSnap = "";

    public String getThirdOrder() {
        return thirdOrder;
    }

    public void setThirdOrder(String thirdOrder) {
        this.thirdOrder = thirdOrder;
    }

    public List<Map<String, Integer>> getSku() {
        return sku;
    }

    public void setSku(List<Map<String, Integer>> sku) {
        this.sku = sku;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getProvince() {
        return province;
    }

    public void setProvince(int province) {
        this.province = province;
    }

    public int getCity() {
        return city;
    }

    public void setCity(int city) {
        this.city = city;
    }

    public int getCounty() {
        return county;
    }

    public void setCounty(int county) {
        this.county = county;
    }

    public int getTown() {
        return town;
    }

    public void setTown(int town) {
        this.town = town;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getZip() {
        return zip;
    }

    public void setZip(int zip) {
        this.zip = zip;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUnpl() {
        return unpl;
    }

    public void setUnpl(String unpl) {
        this.unpl = unpl;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public int getInvoiceState() {
        return invoiceState;
    }

    public void setInvoiceState(int invoiceState) {
        this.invoiceState = invoiceState;
    }

    public String getInvoiceType() {
        return invoiceType;
    }

    public void setInvoiceType(String invoiceType) {
        this.invoiceType = invoiceType;
    }

    public String getInvoiceName() {
        return invoiceName;
    }

    public void setInvoiceName(String invoiceName) {
        this.invoiceName = invoiceName;
    }

    public String getInvoicePhone() {
        return invoicePhone;
    }

    public void setInvoicePhone(String invoicePhone) {
        this.invoicePhone = invoicePhone;
    }

    public String getInvoiceProvice() {
        return invoiceProvice;
    }

    public void setInvoiceProvice(String invoiceProvice) {
        this.invoiceProvice = invoiceProvice;
    }

    public String getInvoiceCity() {
        return invoiceCity;
    }

    public void setInvoiceCity(String invoiceCity) {
        this.invoiceCity = invoiceCity;
    }

    public String getInvoiceCounty() {
        return invoiceCounty;
    }

    public void setInvoiceCounty(String invoiceCounty) {
        this.invoiceCounty = invoiceCounty;
    }

    public String getInvoiceTown() {
        return invoiceTown;
    }

    public void setInvoiceTown(String invoiceTown) {
        this.invoiceTown = invoiceTown;
    }

    public String getInvoiceAddress() {
        return invoiceAddress;
    }

    public void setInvoiceAddress(String invoiceAddress) {
        this.invoiceAddress = invoiceAddress;
    }

    public String getRegCompanyName() {
        return regCompanyName;
    }

    public void setRegCompanyName(String regCompanyName) {
        this.regCompanyName = regCompanyName;
    }

    public String getRegCode() {
        return regCode;
    }

    public void setRegCode(String regCode) {
        this.regCode = regCode;
    }

    public String getRegAddr() {
        return regAddr;
    }

    public void setRegAddr(String regAddr) {
        this.regAddr = regAddr;
    }

    public String getRegPhone() {
        return regPhone;
    }

    public void setRegPhone(String regPhone) {
        this.regPhone = regPhone;
    }

    public String getRegBank() {
        return regBank;
    }

    public void setRegBank(String regBank) {
        this.regBank = regBank;
    }

    public String getRegBankAccount() {
        return regBankAccount;
    }

    public void setRegBankAccount(String regBankAccount) {
        this.regBankAccount = regBankAccount;
    }

    public String getSelectedInvoiceTitle() {
        return selectedInvoiceTitle;
    }

    public void setSelectedInvoiceTitle(String selectedInvoiceTitle) {
        this.selectedInvoiceTitle = selectedInvoiceTitle;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getInvoiceContent() {
        return invoiceContent;
    }

    public void setInvoiceContent(String invoiceContent) {
        this.invoiceContent = invoiceContent;
    }

    public int getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(int paymentType) {
        this.paymentType = paymentType;
    }

    public int getIsUseBalance() {
        return isUseBalance;
    }

    public void setIsUseBalance(int isUseBalance) {
        this.isUseBalance = isUseBalance;
    }

    public int getSubmitState() {
        return submitState;
    }

    public void setSubmitState(int submitState) {
        this.submitState = submitState;
    }

    public String getDoOrderPriceMode() {
        return doOrderPriceMode;
    }

    public void setDoOrderPriceMode(String doOrderPriceMode) {
        this.doOrderPriceMode = doOrderPriceMode;
    }

    public String getOrderPriceSnap() {
        return orderPriceSnap;
    }

    public void setOrderPriceSnap(String orderPriceSnap) {
        this.orderPriceSnap = orderPriceSnap;
    }
}

package com.hqtc.bms.model.rpc;

/**
 * 统一下单接口发票信息
 * Created by wanghaoyang on 18-7-9.
 */
public class JdInvoiceInfoBean {
    private int invoiceState = 2;
    private int invoiceType = 2;
    private int selectedInvoiceTitle = 4;
    private String companyName = "";
    private int invoiceContent = 1;
    private int paymentType = 4;
    private int isUseBalance = 1;
    private int submitState = 1;

    public int getInvoiceState() {
        return invoiceState;
    }

    public void setInvoiceState(int invoiceState) {
        this.invoiceState = invoiceState;
    }

    public int getInvoiceType() {
        return invoiceType;
    }

    public void setInvoiceType(int invoiceType) {
        this.invoiceType = invoiceType;
    }

    public int getSelectedInvoiceTitle() {
        return selectedInvoiceTitle;
    }

    public void setSelectedInvoiceTitle(int selectedInvoiceTitle) {
        this.selectedInvoiceTitle = selectedInvoiceTitle;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public int getInvoiceContent() {
        return invoiceContent;
    }

    public void setInvoiceContent(int invoiceContent) {
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
}

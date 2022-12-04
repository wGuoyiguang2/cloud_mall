package com.hqtc.bms.model.params;

import java.io.Serializable;
import java.util.List;

/**
 * description:发票信息
 * Created by laiqingchuang on 18-7-12 .
 */
public class InvoiceresultResponseParams implements Serializable {
    private String result;
    private List<InvoiceresultDto> list;

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public List<InvoiceresultDto> getList() {
        return list;
    }

    public void setList(List<InvoiceresultDto> list) {
        this.list = list;
    }
}

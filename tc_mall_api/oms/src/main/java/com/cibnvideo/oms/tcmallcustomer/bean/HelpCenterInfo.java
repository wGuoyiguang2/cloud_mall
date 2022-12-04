package com.cibnvideo.oms.tcmallcustomer.bean;

import java.io.Serializable;
import java.util.List;

public class HelpCenterInfo implements Serializable {
    private List<ExplainBean> introList;
    private List<HelpCenter> dataList;

    public List<ExplainBean> getIntroList() {
        return introList;
    }

    public void setIntroList(List<ExplainBean> introList) {
        this.introList = introList;
    }

    public void setDataList(List<HelpCenter> dataList) {
        this.dataList = dataList;
    }

    public List<HelpCenter> getDataList() {
        return dataList;
    }
}

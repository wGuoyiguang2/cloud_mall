package com.cibnvideo.oms.tcmallcustomer.bean;

import java.io.Serializable;

/**
 * Created by makuan on 18-7-11.
 */
public class ExplainBean implements Serializable {
    private int id;
    private String intro = "";

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getIntro() {
        return intro;
    }

    public void setIntro(String intro) {
        this.intro = intro;
    }
}

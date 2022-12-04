package com.cibnvideo.oms.tcmallcustomer.bean;

import java.io.Serializable;

public class HelpCenter implements Serializable{
    private static final long serialVersionUID = 1L;

    //t_assistance
    private String title;
    private int id;

    public String getTitle(){return title;}
    public void setTitle(String title) { this.title = title; }

    public int getid() { return id; }
    public void setid(int id) { this.id = id; }

}

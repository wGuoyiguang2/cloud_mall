package com.cibnvideo.oms.tcmallcustomer.bean;

import java.io.Serializable;

public class CustomerInfo implements Serializable {
    private static final long serialVersionUID = 1L;
    private String url;
    private String hotline;

    public String  getUrl(){return url;}
    public void setUrl(String url){this.url = url;}

    public String  getHotline(){return hotline;}
    public void setHotline(String hotline){this.hotline = hotline;}

}

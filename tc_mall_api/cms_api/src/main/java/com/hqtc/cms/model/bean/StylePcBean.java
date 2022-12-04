package com.hqtc.cms.model.bean;

import java.io.Serializable;

/**
 * description:获取指定商品pc端样式接口
 * Created by laiqingchuang on 18-7-16 .
 */
public class StylePcBean extends PicturePcBean{

    private String jsContent;    //js内容
    private String cssContent;   //css内容
    private String htmlContent;  //html内容

    public String getJsContent() {
        return jsContent;
    }

    public void setJsContent(String jsContent) {
        this.jsContent = jsContent;
    }

    public String getCssContent() {
        return cssContent;
    }

    public void setCssContent(String cssContent) {
        this.cssContent = cssContent;
    }

    public String getHtmlContent() {
        return htmlContent;
    }

    public void setHtmlContent(String htmlContent) {
        this.htmlContent = htmlContent;
    }
}

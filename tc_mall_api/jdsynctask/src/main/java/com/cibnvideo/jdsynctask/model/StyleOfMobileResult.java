package com.cibnvideo.jdsynctask.model;

import org.apache.commons.codec.binary.StringUtils;

import java.io.Serializable;

public class StyleOfMobileResult implements Serializable {
    public Long getSku() {
        return sku;
    }

    public void setSku(Long sku) {
        this.sku = sku;
    }

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

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        } else if (obj instanceof StyleOfMobileResult) {
            StyleOfMobileResult styleOfPcObj = (StyleOfMobileResult) obj;
            if ((this.sku == null ? styleOfPcObj.getSku() == null : this.sku.equals(styleOfPcObj.getSku())) &&
                    StringUtils.equals(this.getJsContent(), styleOfPcObj.getJsContent()) &&
                    StringUtils.equals(this.getCssContent(), styleOfPcObj.getCssContent()) &&
                    StringUtils.equals(this.getHtmlContent(), styleOfPcObj.getHtmlContent())) {
                return true;
            }

        }
        return false;
    }

    private Long sku;
    private String jsContent;
    private String cssContent;
    private String htmlContent;
}

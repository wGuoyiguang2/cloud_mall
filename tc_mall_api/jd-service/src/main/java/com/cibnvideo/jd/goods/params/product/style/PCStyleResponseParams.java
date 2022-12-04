package com.cibnvideo.jd.goods.params.product.style;

/**
 * @Description:
 * @Author: WangBin
 * @Date: 2018/6/29 14:59
 */
public class PCStyleResponseParams {
    private PCStyleVo jd_kpl_open_item_getwarestyleandjsbywareid_response;

    public PCStyleVo getJd_kpl_open_item_getwarestyleandjsbywareid_response() {
        return jd_kpl_open_item_getwarestyleandjsbywareid_response;
    }

    public void setJd_kpl_open_item_getwarestyleandjsbywareid_response(PCStyleVo jd_kpl_open_item_getwarestyleandjsbywareid_response) {
        this.jd_kpl_open_item_getwarestyleandjsbywareid_response = jd_kpl_open_item_getwarestyleandjsbywareid_response;
    }

    class PCStyleVo{
        private PCStyleResponseVo detail;
        private String code;

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public PCStyleResponseVo getDetail() {
            return detail;
        }

        public void setDetail(PCStyleResponseVo detail) {
            this.detail = detail;
        }
    }
    class PCStyleResponseVo{
        private String jsContent;
        private String cssContent;
        private String sku;
        private String htmlContent;

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

        public String getSku() {
            return sku;
        }

        public void setSku(String sku) {
            this.sku = sku;
        }

        public String getHtmlContent() {
            return htmlContent;
        }

        public void setHtmlContent(String htmlContent) {
            this.htmlContent = htmlContent;
        }
    }
}

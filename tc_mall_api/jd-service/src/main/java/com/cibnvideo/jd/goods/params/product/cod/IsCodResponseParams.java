package com.cibnvideo.jd.goods.params.product.cod;

/**
 * @Description:
 * @Author: WangBin
 * @Date: 2018/6/29 20:28
 */
public class IsCodResponseParams {

    private CodVo biz_product_isCod_query_response;

    public CodVo getBiz_product_isCod_query_response() {
        return biz_product_isCod_query_response;
    }

    public void setBiz_product_isCod_query_response(CodVo biz_product_isCod_query_response) {
        this.biz_product_isCod_query_response = biz_product_isCod_query_response;
    }

    class CodVo {
        private Boolean result; //若验证所有商品都支持货到付款，则返回true; 除此之外返回false
        protected String resultCode;
        protected String success;
        protected String resultMessage;

        public Boolean getResult() {
            return result;
        }

        public void setResult(Boolean result) {
            this.result = result;
        }

        public String getResultCode() {
            return resultCode;
        }

        public void setResultCode(String resultCode) {
            this.resultCode = resultCode;
        }

        public String getSuccess() {
            return success;
        }

        public void setSuccess(String success) {
            this.success = success;
        }

        public String getResultMessage() {
            return resultMessage;
        }

        public void setResultMessage(String resultMessage) {
            this.resultMessage = resultMessage;
        }
    }
}

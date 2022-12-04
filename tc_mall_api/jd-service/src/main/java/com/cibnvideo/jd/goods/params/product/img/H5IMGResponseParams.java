package com.cibnvideo.jd.goods.params.product.img;

/**
 * @Description:
 * @Author: WangBin
 * @Date: 2018/6/29 16:49
 */
public class H5IMGResponseParams {
    private H5IMGVo jingdong_new_ware_mobilebigfield_get_response;

    public H5IMGVo getJingdong_new_ware_mobilebigfield_get_response() {
        return jingdong_new_ware_mobilebigfield_get_response;
    }

    public void setJingdong_new_ware_mobilebigfield_get_response(H5IMGVo jingdong_new_ware_mobilebigfield_get_response) {
        this.jingdong_new_ware_mobilebigfield_get_response = jingdong_new_ware_mobilebigfield_get_response;
    }

    class H5IMGVo{
        private String code;
        private String msg;
        private String result;

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getMsg() {
            return msg;
        }

        public void setMsg(String msg) {
            this.msg = msg;
        }

        public String getResult() {
            return result;
        }

        public void setResult(String result) {
            this.result = result;
        }
    }
}

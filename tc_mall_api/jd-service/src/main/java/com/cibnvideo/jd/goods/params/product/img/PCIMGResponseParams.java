package com.cibnvideo.jd.goods.params.product.img;

/**
 * @Description:
 * @Author: WangBin
 * @Date: 2018/6/29 16:33
 */
public class PCIMGResponseParams {
    private PCIMGVo jd_kepler_item_querybigfieldconvertsku_response;

    public PCIMGVo getJd_kepler_item_querybigfieldconvertsku_response() {
        return jd_kepler_item_querybigfieldconvertsku_response;
    }

    public void setJd_kepler_item_querybigfieldconvertsku_response(PCIMGVo jd_kepler_item_querybigfieldconvertsku_response) {
        this.jd_kepler_item_querybigfieldconvertsku_response = jd_kepler_item_querybigfieldconvertsku_response;
    }

    class PCIMGVo{
        private String propCode;//	String	是	规格参数
        private String wareQD;//		String	是	包装清单
        private String service;//		String	是	服务
        private String wReadMe;//		String	是	ReadMe
        private String shouhou;//		String	是	售后
        private String wdis;//		String	是	商品详情
        private String code	;//	String	是	系统错误码
        private String msg;//		String	否	系统错误码说明

        public String getPropCode() {
            return propCode;
        }

        public void setPropCode(String propCode) {
            this.propCode = propCode;
        }

        public String getWareQD() {
            return wareQD;
        }

        public void setWareQD(String wareQD) {
            this.wareQD = wareQD;
        }

        public String getService() {
            return service;
        }

        public void setService(String service) {
            this.service = service;
        }

        public String getwReadMe() {
            return wReadMe;
        }

        public void setwReadMe(String wReadMe) {
            this.wReadMe = wReadMe;
        }

        public String getShouhou() {
            return shouhou;
        }

        public void setShouhou(String shouhou) {
            this.shouhou = shouhou;
        }

        public String getWdis() {
            return wdis;
        }

        public void setWdis(String wdis) {
            this.wdis = wdis;
        }

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
    }
}

package com.cibnvideo.jd.goods.params.stock;

import com.cibnvideo.jd.common.params.BaseResponseParams;

import java.util.List;

/**
 * @Description:
 * @Author: WangBin
 * @Date: 2018/6/23 15:25
 */
public class StockResponseParams {
    private StockResponseVo biz_stock_forList_batget_response;

    public StockResponseVo getBiz_stock_forList_batget_response() {
        return biz_stock_forList_batget_response;
    }

    public void setBiz_stock_forList_batget_response(StockResponseVo biz_stock_forList_batget_response) {
        this.biz_stock_forList_batget_response = biz_stock_forList_batget_response;
    }
    class StockResponseVo extends BaseResponseParams {
        private List<StockVo> result;
        class StockVo{
            private String state;
            private String desc;
            private String area;
            private String sku;

            public String getState() {
                return state;
            }

            public void setState(String state) {
                this.state = state;
            }

            public String getDesc() {
                return desc;
            }

            public void setDesc(String desc) {
                this.desc = desc;
            }

            public String getArea() {
                return area;
            }

            public void setArea(String area) {
                this.area = area;
            }

            public String getSku() {
                return sku;
            }

            public void setSku(String sku) {
                this.sku = sku;
            }
        }
    }
}

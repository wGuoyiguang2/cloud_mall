package com.cibnvideo.jd.goods.params.stock;

import com.cibnvideo.jd.common.params.BaseResponseParams;

import java.util.List;

/**
 * @Description:
 * @Author: WangBin
 * @Date: 2018/6/23 15:45
 */
public class StockOrderResponseParams {
    private StockOrderResponseVo biz_stock_fororder_batget_response;

    public StockOrderResponseVo getBiz_stock_fororder_batget_response() {
        return biz_stock_fororder_batget_response;
    }

    public void setBiz_stock_fororder_batget_response(StockOrderResponseVo biz_stock_fororder_batget_response) {
        this.biz_stock_fororder_batget_response = biz_stock_fororder_batget_response;
    }
    class StockOrderResponseVo extends BaseResponseParams {
        private List<StockVo> result;

        public List<StockVo> getResult() {
            return result;
        }

        public void setResult(List<StockVo> result) {
            this.result = result;
        }

        class StockVo{
            private String areaId;//	String	配送地址ID
            private Long skuId;//	Long	商品ID
            private Integer stockStateId;//	Integer	库存状态编号 33,39,40,36,34
            private String stockStateDesc;//	String	库存状态描述
            private Integer remainNum;//	Integer	剩余数量 -1代表未知：

            public String getAreaId() {
                return areaId;
            }

            public void setAreaId(String areaId) {
                this.areaId = areaId;
            }

            public Long getSkuId() {
                return skuId;
            }

            public void setSkuId(Long skuId) {
                this.skuId = skuId;
            }

            public Integer getStockStateId() {
                return stockStateId;
            }

            public void setStockStateId(Integer stockStateId) {
                this.stockStateId = stockStateId;
            }

            public String getStockStateDesc() {
                return stockStateDesc;
            }

            public void setStockStateDesc(String stockStateDesc) {
                this.stockStateDesc = stockStateDesc;
            }

            public Integer getRemainNum() {
                return remainNum;
            }

            public void setRemainNum(Integer remainNum) {
                this.remainNum = remainNum;
            }
        }
    }
}

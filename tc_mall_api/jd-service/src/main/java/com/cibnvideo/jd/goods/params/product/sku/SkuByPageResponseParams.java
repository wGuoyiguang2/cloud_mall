package com.cibnvideo.jd.goods.params.product.sku;

import com.cibnvideo.jd.common.params.BaseResponseParams;

/**
 * @Description:
 * @Author: WangBin
 * @Date: 2018/6/29 17:08
 */
public class SkuByPageResponseParams {
    private SkuByPageVo jd_biz_product_getSkuByPage_response;

    public SkuByPageVo getJd_biz_product_getSkuByPage_response() {
        return jd_biz_product_getSkuByPage_response;
    }

    public void setJd_biz_product_getSkuByPage_response(SkuByPageVo jd_biz_product_getSkuByPage_response) {
        this.jd_biz_product_getSkuByPage_response = jd_biz_product_getSkuByPage_response;
    }

    class SkuByPageVo extends BaseResponseParams {
        private SkuVo result;

        public SkuVo getResult() {
            return result;
        }

        public void setResult(SkuVo result) {
            this.result = result;
        }

        class SkuVo{

            private Integer pageCount;
            private Long[] skuIds;

            public Integer getPageCount() {
                return pageCount;
            }

            public void setPageCount(Integer pageCount) {
                this.pageCount = pageCount;
            }

            public Long[] getSkuIds() {
                return skuIds;
            }

            public void setSkuIds(Long[] skuIds) {
                this.skuIds = skuIds;
            }
        }
    }
}

package com.cibnvideo.jd.goods.params.similar;

import com.cibnvideo.jd.common.params.BaseResponseParams;

import java.util.List;

/**
 * @Description:
 * @Author: WangBin
 * @Date: 2018/7/17 18:50
 */
public class SimilarGoodsResponseParams {
    private SimilarVo jd_biz_product_getSimilarSku_response;

    public SimilarVo getJd_biz_product_getSimilarSku_response() {
        return jd_biz_product_getSimilarSku_response;
    }

    public void setJd_biz_product_getSimilarSku_response(SimilarVo jd_biz_product_getSimilarSku_response) {
        this.jd_biz_product_getSimilarSku_response = jd_biz_product_getSimilarSku_response;
    }

    class SimilarVo extends BaseResponseParams{
        List<SimilarGoodsVo> result;

        public List<SimilarGoodsVo> getResult() {
            return result;
        }

        public void setResult(List<SimilarGoodsVo> result) {
            this.result = result;
        }

        class SimilarGoodsVo{
            private String dim;
            private String saleName;
            private List<GoodsVo> saleAttrList;

            public String getDim() {
                return dim;
            }

            public void setDim(String dim) {
                this.dim = dim;
            }

            public String getSaleName() {
                return saleName;
            }

            public void setSaleName(String saleName) {
                this.saleName = saleName;
            }

            public List<GoodsVo> getSaleAttrList() {
                return saleAttrList;
            }

            public void setSaleAttrList(List<GoodsVo> saleAttrList) {
                this.saleAttrList = saleAttrList;
            }

            class GoodsVo{
                private String imagePath;
                private String saleValue;
                private List<String> skuIds;

                public String getImagePath() {
                    return imagePath;
                }

                public void setImagePath(String imagePath) {
                    this.imagePath = imagePath;
                }

                public String getSaleValue() {
                    return saleValue;
                }

                public void setSaleValue(String saleValue) {
                    this.saleValue = saleValue;
                }

                public List<String> getSkuIds() {
                    return skuIds;
                }

                public void setSkuIds(List<String> skuIds) {
                    this.skuIds = skuIds;
                }
            }
        }
    }
}

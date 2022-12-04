package com.cibnvideo.jd.goods.params.product.productdetail;

import com.cibnvideo.jd.common.params.BaseResponseParams;

/**
 * @Description:
 * @Author: WangBin
 * @Date: 2018/6/29 14:46
 */
public class ProductDetailResponseNoBookParams{
    private ProductDetailResponseVoNoBook biz_product_detail_query_response;

    public ProductDetailResponseVoNoBook getBiz_product_detail_query_response() {
        return biz_product_detail_query_response;
    }

    public void setBiz_product_detail_query_response(ProductDetailResponseVoNoBook biz_product_detail_query_response) {
        this.biz_product_detail_query_response = biz_product_detail_query_response;
    }
    class ProductDetailResponseVoNoBook extends BaseResponseParams {
        private ProductDetailVo result;

        public ProductDetailVo getResult() {
            return result;
        }

        public void setResult(ProductDetailVo result) {
            this.result = result;
        }

        class ProductDetailVo{

            private String saleUnit;
            private String weight;
            private String productArea;
            private String wareQD;
            private String imagePath;
            private String param;
            private String state;
            private String sku;
            private String brandName;
            private String upc;
            private String category;
            private String name;
            private String introduction;

            public String getSaleUnit() {
                return saleUnit;
            }

            public void setSaleUnit(String saleUnit) {
                this.saleUnit = saleUnit;
            }

            public String getWeight() {
                return weight;
            }

            public void setWeight(String weight) {
                this.weight = weight;
            }

            public String getProductArea() {
                return productArea;
            }

            public void setProductArea(String productArea) {
                this.productArea = productArea;
            }

            public String getWareQD() {
                return wareQD;
            }

            public void setWareQD(String wareQD) {
                this.wareQD = wareQD;
            }

            public String getImagePath() {
                return imagePath;
            }

            public void setImagePath(String imagePath) {
                this.imagePath = imagePath;
            }

            public String getParam() {
                return param;
            }

            public void setParam(String param) {
                this.param = param;
            }

            public String getState() {
                return state;
            }

            public void setState(String state) {
                this.state = state;
            }

            public String getSku() {
                return sku;
            }

            public void setSku(String sku) {
                this.sku = sku;
            }

            public String getBrandName() {
                return brandName;
            }

            public void setBrandName(String brandName) {
                this.brandName = brandName;
            }

            public String getUpc() {
                return upc;
            }

            public void setUpc(String upc) {
                this.upc = upc;
            }

            public String getCategory() {
                return category;
            }

            public void setCategory(String category) {
                this.category = category;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getIntroduction() {
                return introduction;
            }

            public void setIntroduction(String introduction) {
                this.introduction = introduction;
            }
        }
    }
}

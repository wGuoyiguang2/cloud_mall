package com.cibnvideo.jd.goods.params.product.category;

import com.cibnvideo.jd.common.params.BaseResponseParams;

/**
 * @Description:
 * @Author: WangBin
 * @Date: 2018/6/30 10:33
 */
public class CategoryResponseParams {


    private Category jd_biz_product_getcategory_response;

    public Category getJd_biz_product_getcategory_response() {
        return jd_biz_product_getcategory_response;
    }

    public void setJd_biz_product_getcategory_response(Category jd_biz_product_getcategory_response) {
        this.jd_biz_product_getcategory_response = jd_biz_product_getcategory_response;
    }

    class Category extends BaseResponseParams {

        private ProductRepVo result;

        public ProductRepVo getResult() {
            return result;
        }

        public void setResult(ProductRepVo result) {
            this.result = result;
        }

        class ProductRepVo {
            private String catId;
            private String parentId;
            private String name;
            private String catClass;
            private String state;

            public String getCatId() {
                return catId;
            }

            public void setCatId(String catId) {
                this.catId = catId;
            }

            public String getParentId() {
                return parentId;
            }

            public void setParentId(String parentId) {
                this.parentId = parentId;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getCatClass() {
                return catClass;
            }

            public void setCatClass(String catClass) {
                this.catClass = catClass;
            }

            public String getState() {
                return state;
            }

            public void setState(String state) {
                this.state = state;
            }
        }
    }
}

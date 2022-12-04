package com.cibnvideo.jd.goods.params.product.category;

import com.cibnvideo.jd.common.params.BaseResponseParams;

import java.util.List;

/**
 * @Description:
 * @Author: WangBin
 * @Date: 2018/6/30 11:02
 */
public class CategoryPageResponseParams {

    private CategoryVo jd_biz_product_getcategorys_response;

    public CategoryVo getJd_biz_product_getcategorys_response() {
        return jd_biz_product_getcategorys_response;
    }

    public void setJd_biz_product_getcategorys_response(CategoryVo jd_biz_product_getcategorys_response) {
        this.jd_biz_product_getcategorys_response = jd_biz_product_getcategorys_response;
    }

    class CategoryVo extends BaseResponseParams {
        public CategoryRepVo getResult() {
            return result;
        }

        public void setResult(CategoryRepVo result) {
            this.result = result;
        }

        private CategoryRepVo result;

        class CategoryRepVo {
            private List<Category> categorys;
            private String totalRows;
            private String pageNo;
            private String pageSize;

            public String getTotalRows() {
                return totalRows;
            }

            public void setTotalRows(String totalRows) {
                this.totalRows = totalRows;
            }

            public String getPageNo() {
                return pageNo;
            }

            public void setPageNo(String pageNo) {
                this.pageNo = pageNo;
            }

            public String getPageSize() {
                return pageSize;
            }

            public void setPageSize(String pageSize) {
                this.pageSize = pageSize;
            }

            public List<Category> getCategorys() {
                return categorys;
            }

            public void setCategorys(List<Category> categorys) {
                this.categorys = categorys;
            }

            class Category {
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
}

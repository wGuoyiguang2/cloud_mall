package com.cibnvideo.jd.goods.params.product.pagenum;

import com.cibnvideo.jd.common.params.BaseResponseParams;

import java.util.List;

/**
 * @Description:
 * @Author: WangBin
 * @Date: 2018/6/29 11:18
 */
public class PageNumResponseParams {
    private PageNumResponseVo biz_product_PageNum_query_response;

    public PageNumResponseVo getBiz_product_PageNum_query_response() {
        return biz_product_PageNum_query_response;
    }

    public void setBiz_product_PageNum_query_response(PageNumResponseVo biz_product_PageNum_query_response) {
        this.biz_product_PageNum_query_response = biz_product_PageNum_query_response;
    }
    class PageNumResponseVo extends BaseResponseParams {

        private List<PageNumVo> result;

        public List<PageNumVo> getResult() {
            return result;
        }

        public void setResult(List<PageNumVo> result) {
            this.result = result;
        }

        class PageNumVo{
            private String name;//	String	商品池名称
            private String page_num;//	String	商品池编号

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getPage_num() {
                return page_num;
            }

            public void setPage_num(String page_num) {
                this.page_num = page_num;
            }
        }
    }

}

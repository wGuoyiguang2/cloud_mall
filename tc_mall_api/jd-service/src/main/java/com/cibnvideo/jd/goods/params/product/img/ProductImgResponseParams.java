package com.cibnvideo.jd.goods.params.product.img;

import com.cibnvideo.jd.common.params.BaseResponseParams;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @Description:
 * @Author: WangBin
 * @Date: 2018/6/29 18:07
 */
public class ProductImgResponseParams {


    private ProductVo biz_product_skuImage_query_response;

    public ProductVo getBiz_product_skuImage_query_response() {
        return biz_product_skuImage_query_response;
    }

    public void setBiz_product_skuImage_query_response(ProductVo biz_product_skuImage_query_response) {
        this.biz_product_skuImage_query_response = biz_product_skuImage_query_response;
    }

    class ProductVo extends BaseResponseParams {

        private Map<Long, List<ProductSkuImageDetailRepVo>> result;

        public Map<Long, List<ProductSkuImageDetailRepVo>> getResult() {
            return result;
        }

        public void setResult(Map<Long, List<ProductSkuImageDetailRepVo>> result) {
            this.result = result;
        }

        class ProductSkuImageDetailRepVo {
            private Integer isPrimary;//	Integer	1：主图 0：附图
            private Long id;//	Long	ID
            private String path;//	String	图片url
            //示例（http://img13.360buyimg.com/vc/ http://img13.360buyimg.com/vc/jfs/t3229/13/748264941/172560/96246790/57be4f1dN2eb3343f.jpg）
            private Integer type;//	Integer	类别
            private Long skuId;//	Long	商品编码
            private Date created;//	Date	创建日期
            private Date modified;//	Date	创建日期
            private Integer orderSort;//	Integer	排序
            private String features;//	String	特征
            private String position;//	Integer	位置
            private Integer yn;//	Integer	是否启用

            public Integer getIsPrimary() {
                return isPrimary;
            }

            public void setIsPrimary(Integer isPrimary) {
                this.isPrimary = isPrimary;
            }

            public Long getId() {
                return id;
            }

            public void setId(Long id) {
                this.id = id;
            }

            public String getPath() {
                return path;
            }

            public void setPath(String path) {
                this.path = path;
            }

            public Integer getType() {
                return type;
            }

            public void setType(Integer type) {
                this.type = type;
            }

            public Long getSkuId() {
                return skuId;
            }

            public void setSkuId(Long skuId) {
                this.skuId = skuId;
            }

            public Date getCreated() {
                return created;
            }

            public void setCreated(Date created) {
                this.created = created;
            }

            public Date getModified() {
                return modified;
            }

            public void setModified(Date modified) {
                this.modified = modified;
            }

            public Integer getOrderSort() {
                return orderSort;
            }

            public void setOrderSort(Integer orderSort) {
                this.orderSort = orderSort;
            }

            public String getFeatures() {
                return features;
            }

            public void setFeatures(String features) {
                this.features = features;
            }

            public String getPosition() {
                return position;
            }

            public void setPosition(String position) {
                this.position = position;
            }

            public Integer getYn() {
                return yn;
            }

            public void setYn(Integer yn) {
                this.yn = yn;
            }
        }
    }
}

package com.cibnvideo.jd.goods.params.yanbao;

import com.cibnvideo.jd.common.params.BaseResponseParams;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * description:延保
 * Created by laiqingchuang on 18-8-14 .
 */
public class YanbaoResponseParams{

    private YanbaoResponseVo biz_product_yanbao_sku_query_response;

    public YanbaoResponseVo getBiz_product_yanbao_sku_query_response() {
        return biz_product_yanbao_sku_query_response;
    }

    public void setBiz_product_yanbao_sku_query_response(YanbaoResponseVo biz_product_yanbao_sku_query_response) {
        this.biz_product_yanbao_sku_query_response = biz_product_yanbao_sku_query_response;
    }

    class YanbaoResponseVo extends BaseResponseParams {

        private Map<String,List<YanbaoVo>> result;

        public Map<String, List<YanbaoVo>> getResult() {
            return result;
        }

        public void setResult(Map<String, List<YanbaoVo>> result) {
            this.result = result;
        }
    }

    class YanbaoVo {
        private String categoryCode;  //保障服务类别名称
        private String detailUrl;     //保障服务类别静态页详情url
        private String displayName;   //保障服务分类名称
        private Integer displayNo;    //保障服务分类编码
        private String imgUrl;        //保障服务类别显示图标url
        private Long mainSkuId;    //主商品的sku
        private List<FuwuSkuDetail> fuwuSkuDetailList;  //保障服务商品详情列表

        public String getCategoryCode() {
            return categoryCode;
        }

        public void setCategoryCode(String categoryCode) {
            this.categoryCode = categoryCode;
        }

        public String getDetailUrl() {
            return detailUrl;
        }

        public void setDetailUrl(String detailUrl) {
            this.detailUrl = detailUrl;
        }

        public String getDisplayName() {
            return displayName;
        }

        public void setDisplayName(String displayName) {
            this.displayName = displayName;
        }

        public Integer getDisplayNo() {
            return displayNo;
        }

        public void setDisplayNo(Integer displayNo) {
            this.displayNo = displayNo;
        }

        public String getImgUrl() {
            return imgUrl;
        }

        public void setImgUrl(String imgUrl) {
            this.imgUrl = imgUrl;
        }

        public Long getMainSkuId() {
            return mainSkuId;
        }

        public void setMainSkuId(Long mainSkuId) {
            this.mainSkuId = mainSkuId;
        }

        public List<FuwuSkuDetail> getFuwuSkuDetailList() {
            return fuwuSkuDetailList;
        }

        public void setFuwuSkuDetailList(List<FuwuSkuDetail> fuwuSkuDetailList) {
            this.fuwuSkuDetailList = fuwuSkuDetailList;
        }

        class FuwuSkuDetail {
            private Long bindSkuId; //保障服务skuId 
            private String bindSkuName;//保障服务sku名称（6字内）
            private Boolean favor;     //是否是优惠保障服务
            private BigDecimal price;  //保障服务sku价格
            private Integer sortIndex; //显示排序
            private String tip;        //保障服务说明提示语（20字内）

            public Long getBindSkuId() {
                return bindSkuId;
            }

            public void setBindSkuId(Long bindSkuId) {
                this.bindSkuId = bindSkuId;
            }

            public String getBindSkuName() {
                return bindSkuName;
            }

            public void setBindSkuName(String bindSkuName) {
                this.bindSkuName = bindSkuName;
            }

            public Boolean getFavor() {
                return favor;
            }

            public void setFavor(Boolean favor) {
                this.favor = favor;
            }

            public BigDecimal getPrice() {
                return price;
            }

            public void setPrice(BigDecimal price) {
                this.price = price;
            }

            public Integer getSortIndex() {
                return sortIndex;
            }

            public void setSortIndex(Integer sortIndex) {
                this.sortIndex = sortIndex;
            }

            public String getTip() {
                return tip;
            }

            public void setTip(String tip) {
                this.tip = tip;
            }
        }
    }
}

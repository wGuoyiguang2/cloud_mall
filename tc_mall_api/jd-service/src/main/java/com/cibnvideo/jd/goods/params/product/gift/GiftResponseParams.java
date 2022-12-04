package com.cibnvideo.jd.goods.params.product.gift;

import com.cibnvideo.jd.common.params.BaseResponseParams;

import java.util.List;

/**
 * @Description:
 * @Author: WangBin
 * @Date: 2018/6/29 20:50
 */
public class GiftResponseParams {


    private GiftVo biz_product_skuGift_query_response;


    class GiftVo extends BaseResponseParams {

        private List<GiftRepVo> result;

        public List<GiftRepVo> getResult() {
            return result;
        }

        public void setResult(List<GiftRepVo> result) {
            this.result = result;
        }

        class GiftRepVo {
            private List<Gift> gifts;
            private Integer maxNum;
            private Integer minNum;
            private Long promoStartTime;
            private Long promoEndTime;

            public List<Gift> getGifts() {
                return gifts;
            }

            public void setGifts(List<Gift> gifts) {
                this.gifts = gifts;
            }

            public Integer getMaxNum() {
                return maxNum;
            }

            public void setMaxNum(Integer maxNum) {
                this.maxNum = maxNum;
            }

            public Integer getMinNum() {
                return minNum;
            }

            public void setMinNum(Integer minNum) {
                this.minNum = minNum;
            }

            public Long getPromoStartTime() {
                return promoStartTime;
            }

            public void setPromoStartTime(Long promoStartTime) {
                this.promoStartTime = promoStartTime;
            }

            public Long getPromoEndTime() {
                return promoEndTime;
            }

            public void setPromoEndTime(Long promoEndTime) {
                this.promoEndTime = promoEndTime;
            }

            class Gift{
                private Long skuId;
                private Integer num;
                private Integer giftType;

                public Long getSkuId() {
                    return skuId;
                }

                public void setSkuId(Long skuId) {
                    this.skuId = skuId;
                }

                public Integer getNum() {
                    return num;
                }

                public void setNum(Integer num) {
                    this.num = num;
                }

                public Integer getGiftType() {
                    return giftType;
                }

                public void setGiftType(Integer giftType) {
                    this.giftType = giftType;
                }
            }
        }
    }
}

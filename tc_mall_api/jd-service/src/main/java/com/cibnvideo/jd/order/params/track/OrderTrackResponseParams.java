package com.cibnvideo.jd.order.params.track;

import com.cibnvideo.jd.common.params.BaseResponseParams;

import java.util.List;

/**
 * @Description:
 * @Author: WangBin
 * @Date: 2018/7/13 18:02
 */
public class OrderTrackResponseParams {
    private OrderTrackVo biz_order_orderTrack_query_response;

    public OrderTrackVo getBiz_order_orderTrack_query_response() {
        return biz_order_orderTrack_query_response;
    }

    public void setBiz_order_orderTrack_query_response(OrderTrackVo biz_order_orderTrack_query_response) {
        this.biz_order_orderTrack_query_response = biz_order_orderTrack_query_response;
    }

    class OrderTrackVo extends BaseResponseParams{
        private OrderVo result;

        public OrderVo getResult() {
            return result;
        }

        public void setResult(OrderVo result) {
            this.result = result;
        }

        class OrderVo{
            private String jdOrderId;
            private List<TrackVo> orderTrack;

            public List<TrackVo> getOrderTrack() {
                return orderTrack;
            }

            public void setOrderTrack(List<TrackVo> orderTrack) {
                this.orderTrack = orderTrack;
            }

            public String getJdOrderId() {
                return jdOrderId;
            }

            public void setJdOrderId(String jdOrderId) {
                this.jdOrderId = jdOrderId;
            }

            class TrackVo{
                private String msgTime;
                private String content;
                private String operator;

                public String getMsgTime() {
                    return msgTime;
                }

                public void setMsgTime(String msgTime) {
                    this.msgTime = msgTime;
                }

                public String getContent() {
                    return content;
                }

                public void setContent(String content) {
                    this.content = content;
                }

                public String getOperator() {
                    return operator;
                }

                public void setOperator(String operator) {
                    this.operator = operator;
                }
            }
        }
    }
}

package com.cibnvideo.jd.goods.params.message;

import com.cibnvideo.jd.common.params.BaseResponseParams;

/**
 * @Description:
 * @Author: WangBin
 * @Date: 2018/7/2 20:36
 */
public class DeleteMessageResponseParams {
    private MessageVo biz_message_del_response;

    public MessageVo getBiz_message_del_response() {
        return biz_message_del_response;
    }

    public void setBiz_message_del_response(MessageVo biz_message_del_response) {
        this.biz_message_del_response = biz_message_del_response;
    }

    class MessageVo extends BaseResponseParams{
        private Boolean result;

        public Boolean getResult() {
            return result;
        }

        public void setResult(Boolean result) {
            this.result = result;
        }
    }
}

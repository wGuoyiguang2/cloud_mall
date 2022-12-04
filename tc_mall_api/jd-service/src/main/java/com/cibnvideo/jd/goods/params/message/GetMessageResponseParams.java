package com.cibnvideo.jd.goods.params.message;

import com.cibnvideo.jd.common.params.BaseResponseParams;

import java.util.List;
import java.util.Map;

/**
 * @Description:
 * @Author: WangBin
 * @Date: 2018/7/2 19:17
 */
public class GetMessageResponseParams {
    private MessageVo biz_message_get_response;

    public MessageVo getBiz_message_get_response() {
        return biz_message_get_response;
    }

    public void setBiz_message_get_response(MessageVo biz_message_get_response) {
        this.biz_message_get_response = biz_message_get_response;
    }

    class MessageVo extends BaseResponseParams{
        private List<MessageRepVo> result;

        public List<MessageRepVo> getResult() {
            return result;
        }

        public void setResult(List<MessageRepVo> result) {
            this.result = result;
        }

        class MessageRepVo{
            private String id;
            private String time;
            private String type;
            private Map<String,String> result;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getTime() {
                return time;
            }

            public void setTime(String time) {
                this.time = time;
            }

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }

            public Map<String, String> getResult() {
                return result;
            }

            public void setResult(Map<String, String> result) {
                this.result = result;
            }
        }
    }
}

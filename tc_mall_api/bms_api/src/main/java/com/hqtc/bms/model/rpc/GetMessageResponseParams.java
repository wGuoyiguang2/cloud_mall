package com.hqtc.bms.model.rpc;

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
}

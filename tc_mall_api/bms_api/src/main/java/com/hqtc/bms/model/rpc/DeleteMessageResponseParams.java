package com.hqtc.bms.model.rpc;

/**
 * @Description:
 * @Author: WangBin
 * @Date: 2018/7/2 20:36
 */
public class DeleteMessageResponseParams {
    private DeleteMessageVo biz_message_del_response;

    public DeleteMessageVo getBiz_message_del_response() {
        return biz_message_del_response;
    }

    public void setBiz_message_del_response(DeleteMessageVo biz_message_del_response) {
        this.biz_message_del_response = biz_message_del_response;
    }
}

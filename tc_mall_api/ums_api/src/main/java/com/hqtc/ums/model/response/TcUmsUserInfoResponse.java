package com.hqtc.ums.model.response;

/**
 * Created by wanghaoyang on 18-11-20.
 */
public class TcUmsUserInfoResponse {
    private int userId;
    private String nickName;
    private String header;

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }
}

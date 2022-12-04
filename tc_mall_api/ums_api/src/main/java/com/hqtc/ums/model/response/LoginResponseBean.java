package com.hqtc.ums.model.response;

/**
 * Created by wanghaoyang on 18-10-11.
 */
public class LoginResponseBean {
    private int userId;
    private String nickName;
    private String userHeader;

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

    public String getUserHeader() {
        return userHeader;
    }

    public void setUserHeader(String userHeader) {
        this.userHeader = userHeader;
    }
}

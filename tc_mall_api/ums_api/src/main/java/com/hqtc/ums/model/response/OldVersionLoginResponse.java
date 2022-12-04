package com.hqtc.ums.model.response;

/**
 * Created by wanghaoyang on 18-11-20.
 */
public class OldVersionLoginResponse {
    private int cibnUserId = 0;
    private String cibnUserToken = "";
    private int coinnumber = 0;
    private int endTime = 0;
    private boolean member = false;
    private int memberType = 0;
    private int newCoin = 0;
    private String userToken = "";

    private String nickName;
    private String userName;
    private String userHeader;
    private int userId;

    public int getCibnUserId() {
        return cibnUserId;
    }

    public void setCibnUserId(int cibnUserId) {
        this.cibnUserId = cibnUserId;
    }

    public String getCibnUserToken() {
        return cibnUserToken;
    }

    public void setCibnUserToken(String cibnUserToken) {
        this.cibnUserToken = cibnUserToken;
    }

    public int getCoinnumber() {
        return coinnumber;
    }

    public void setCoinnumber(int coinnumber) {
        this.coinnumber = coinnumber;
    }

    public int getEndTime() {
        return endTime;
    }

    public void setEndTime(int endTime) {
        this.endTime = endTime;
    }

    public boolean isMember() {
        return member;
    }

    public void setMember(boolean member) {
        this.member = member;
    }

    public int getMemberType() {
        return memberType;
    }

    public void setMemberType(int memberType) {
        this.memberType = memberType;
    }

    public int getNewCoin() {
        return newCoin;
    }

    public void setNewCoin(int newCoin) {
        this.newCoin = newCoin;
    }

    public String getUserToken() {
        return userToken;
    }

    public void setUserToken(String userToken) {
        this.userToken = userToken;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserHeader() {
        return userHeader;
    }

    public void setUserHeader(String userHeader) {
        this.userHeader = userHeader;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}

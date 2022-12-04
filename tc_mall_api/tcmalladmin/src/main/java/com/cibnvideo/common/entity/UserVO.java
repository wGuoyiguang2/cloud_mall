package com.cibnvideo.common.entity;

public class UserVO {
    public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public String getPwdOld() {
		return pwdOld;
	}
	public void setPwdOld(String pwdOld) {
		this.pwdOld = pwdOld;
	}
	public String getPwdNew() {
		return pwdNew;
	}
	public void setPwdNew(String pwdNew) {
		this.pwdNew = pwdNew;
	}
	/**
     * 更新的用户对象
     */
    private User user = new User();
    /**
     * 旧密码
     */
    private String pwdOld;
    /**
     * 新密码
     */
    private String pwdNew;

}

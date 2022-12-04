package com.cibnvideo.jd.mail.model;

/**
 * @Description:
 * @Author: WangBin
 * @Date: 2019/1/11 14:05
 */
public class MailRequestParam {
    /*收件人*/
    private String mail;
    /*主题*/
    private String title;
    /*内容*/
    private String content;
    /*认证key*/
    private String key;

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}

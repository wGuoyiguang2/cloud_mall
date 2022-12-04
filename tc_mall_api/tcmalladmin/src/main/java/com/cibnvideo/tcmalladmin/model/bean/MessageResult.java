package com.cibnvideo.tcmalladmin.model.bean;

import java.io.Serializable;

public class MessageResult implements Serializable {
    private String className;//定时任务类名
    private int status;//执行结果 0:失败 1:成功
    private String msg;//执行结果信息

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    @Override
    public String toString() {
        return "MessageResult{" +
                "className='" + className + '\'' +
                ", status=" + status +
                ", msg='" + msg + '\'' +
                '}';
    }
}

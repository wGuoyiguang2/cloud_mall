package com.hqtc.searchtask.model.bean;


import java.io.Serializable;

public class JobMessageInfo implements Serializable {

    private String taskCalssName;//任务classname

    private String type;//任务类型

    private String params;//任务参数


    public String getTaskCalssName() {
        return taskCalssName;
    }

    public void setTaskCalssName(String taskCalssName) {
        this.taskCalssName = taskCalssName;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getParams() {
        return params;
    }

    public void setParams(String params) {
        this.params = params;
    }

    @Override
    public String toString() {
        return "taskCalssName:" + taskCalssName + "\n"
                + "type:" + type + "\n"
                + "params:" + params;
    }
}

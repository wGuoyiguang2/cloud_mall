package com.hqtc.bms.model.rpc;

import java.util.Map;

/**
 * Created by wanghaoyang on 18-8-10.
 */
public class MessageRepVo {

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

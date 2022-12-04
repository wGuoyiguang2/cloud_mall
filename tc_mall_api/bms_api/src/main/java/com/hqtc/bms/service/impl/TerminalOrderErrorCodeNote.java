package com.hqtc.bms.service.impl;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by wanghaoyang on 18-10-8.
 */
@Component
@ConfigurationProperties(prefix = "errorCodeMessage")
public class TerminalOrderErrorCodeNote {
    private Map<Integer, String> jdOrderCodeNote = new HashMap<>();

    public Map<Integer, String> getJdOrderCodeNote() {
        return jdOrderCodeNote;
    }

    public void setJdOrderCodeNote(Map<Integer, String> jdOrderCodeNote) {
        this.jdOrderCodeNote = jdOrderCodeNote;
    }
}

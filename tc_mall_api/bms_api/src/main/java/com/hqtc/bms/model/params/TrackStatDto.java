package com.hqtc.bms.model.params;

import java.io.Serializable;

/**
 * description:物流状态
 * Created by laiqingchuang on 18-7-20 .
 */
public class TrackStatDto implements Serializable {

    private String jdTrackState;      //1 生成订单  2 等待发货   3 运输中   4 已完成
    private String jdOTrackStateDesc;

    public String getJdTrackState() {
        return jdTrackState;
    }

    public void setJdTrackState(String jdTrackState) {
        this.jdTrackState = jdTrackState;
    }

    public String getJdOTrackStateDesc() {
        return jdOTrackStateDesc;
    }

    public void setJdOTrackStateDesc(String jdOTrackStateDesc) {
        this.jdOTrackStateDesc = jdOTrackStateDesc;
    }
}

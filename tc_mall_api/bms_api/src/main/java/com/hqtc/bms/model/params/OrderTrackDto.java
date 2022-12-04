package com.hqtc.bms.model.params;

import java.io.Serializable;
import java.util.List;

/**
 * description:物流
 * Created by laiqingchuang on 18-7-13 .
 */
public class OrderTrackDto implements Serializable {

    private String jdOrderId;         //JD订单号
    private List<TrackDto> orderTrack;

    public String getJdOrderId() {
        return jdOrderId;
    }

    public void setJdOrderId(String jdOrderId) {
        this.jdOrderId = jdOrderId;
    }

    public List<TrackDto> getOrderTrack() {
        return orderTrack;
    }

    public void setOrderTrack(List<TrackDto> orderTrack) {
        this.orderTrack = orderTrack;
    }
}

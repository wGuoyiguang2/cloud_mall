package com.cibnvideo.jdsync.bean;

import java.io.Serializable;

/**
 * @Author: likai
 * @description description
 * @Date: 18-9-18 下午7:16
 */
public class ProductVideo implements Serializable {
    private Long sku;
    private String videoPath;

    public Long getSku() {
        return sku;
    }

    public void setSku(Long sku) {
        this.sku = sku;
    }

    public String getVideoPath() {
        return videoPath;
    }

    public void setVideoPath(String videoPath) {
        this.videoPath = videoPath;
    }
}

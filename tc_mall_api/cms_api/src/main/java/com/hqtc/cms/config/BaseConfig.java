package com.hqtc.cms.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @Description:
 * @Author: WangBin
 * @Date: 2018/8/17 14:36
 */
@Component
public class BaseConfig {
    @Value("${service.config.flashSaleListKey}")
    private String flashSaleListKey;
    @Value("${service.config.flashSalePayOrderListKey}")
    private String flashSalePayOrderListKey;
    @Value("${service.config.flashSaleMapKey}")
    private String flashSaleMapKey;
    @Value("${service.config.flashSaleOrderStateKey}")
    private String flashSaleOrderState;
    @Value("${service.config.threadPoolSize}")
    private int threadPoolSize;
    @Value("${service.config.threadPoolMaxSize}")
    private int threadPoolMaxSize;
    @Value("${service.config.awaitTerminationSeconds}")
    private int awaitTerminationSeconds;
    @Value("${service.config.payOrderMinutes}")
    private int payOrderMinutes;
    private final String isOpenedKey = "isOpened";
    private final String executorKey = "executor";
    private final String startTimeKey="startTime";
    private final String endTimeKey="endTime";
    private final String priceKey="price";
    private final String countKey="count";
    private final String executorPayKey="payExecutor";
    private final String requestId="requestId";

    public String getRequestId() {
        return requestId;
    }

    public int getPayOrderMinutes() {
        return payOrderMinutes;
    }

    public String getFlashSaleListKey() {
        return flashSaleListKey;
    }

    public String getFlashSalePayOrderListKey() {
        return flashSalePayOrderListKey;
    }

    public String getFlashSaleMapKey() {
        return flashSaleMapKey;
    }

    public String getFlashSaleOrderState() {
        return flashSaleOrderState;
    }

    public int getThreadPoolSize() {
        return threadPoolSize;
    }

    public int getThreadPoolMaxSize() {
        return threadPoolMaxSize;
    }

    public int getAwaitTerminationSeconds() {
        return awaitTerminationSeconds;
    }

    public String getIsOpenedKey() {
        return isOpenedKey;
    }

    public String getExecutorKey() {
        return executorKey;
    }

    public String getStartTimeKey() {
        return startTimeKey;
    }

    public String getEndTimeKey() {
        return endTimeKey;
    }

    public String getPriceKey() {
        return priceKey;
    }

    public String getCountKey() {
        return countKey;
    }

    public String getExecutorPayKey() {
        return executorPayKey;
    }
}

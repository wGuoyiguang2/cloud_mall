package com.hqtc.bms.model.params;

import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;

/**
 * Created by wanghaoyang on 18-8-27.
 * 退款(既退给大客户，又退给用户)
 */
public class MultipleRefundParams extends RefundUserParams {

    @NotNull(message = "eventId不能为空")
    @NotBlank(message = "eventId不能为空")
    private String eventId;

    public String getEventId() {
        return eventId;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }
}

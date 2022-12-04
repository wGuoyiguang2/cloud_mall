package com.hqtc.bms.model.params;

import java.io.Serializable;

/**
 * description:服务单明细
 * Created by laiqingchuang on 18-7-13 .
 */
public class ServiceDetailInfoResponseParams extends BaseResponseParams implements Serializable {

   private ServiceDetailInfo result;

    public ServiceDetailInfo getResult() {
        return result;
    }

    public void setResult(ServiceDetailInfo result) {
        this.result = result;
    }
}

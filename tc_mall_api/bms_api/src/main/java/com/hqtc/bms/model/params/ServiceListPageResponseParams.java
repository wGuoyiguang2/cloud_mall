package com.hqtc.bms.model.params;

import java.io.Serializable;
import java.util.List;

/**
 * description:根据客户账号和订单号分页查询服务单概要信息
 * Created by laiqingchuang on 18-7-11 .
 */
public class ServiceListPageResponseParams extends BaseResponseParams implements Serializable {
    private ServiceDto result;

    public ServiceDto getResult() {
        return result;
    }

    public void setResult(ServiceDto result) {
        this.result = result;
    }
}

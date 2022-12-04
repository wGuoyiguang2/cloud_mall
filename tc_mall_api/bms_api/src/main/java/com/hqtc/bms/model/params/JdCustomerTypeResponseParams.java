package com.hqtc.bms.model.params;

import java.io.Serializable;
import java.util.List;

/**
 * description:根据订单号、商品编号查询支持的服务类型
 * Created by laiqingchuang on 18-7-11 .
 */
public class JdCustomerTypeResponseParams extends BaseResponseParams implements Serializable{

    private List<CodeNameDto> result;

    public List<CodeNameDto> getResult() {
        return result;
    }

    public void setResult(List<CodeNameDto> result) {
        this.result = result;
    }
}

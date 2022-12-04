package com.cibnvideo.jd.aftersale.params.servicetype;

import com.cibnvideo.jd.common.params.BaseResponseParams;

import java.util.List;

/**
 * description:3.4 根据订单号、商品编号查询支持的服务类型
 * Created by laiqingchuang on 18-7-11 .
 */
public class JdCustomerTypeResponseParams {
    private OrderVo biz_afterSale_customerExpectComp_query_response;

    public OrderVo getBiz_afterSale_customerExpectComp_query_response() {
        return biz_afterSale_customerExpectComp_query_response;
    }

    public void setBiz_afterSale_customerExpectComp_query_response(OrderVo biz_afterSale_customerExpectComp_query_response) {
        this.biz_afterSale_customerExpectComp_query_response = biz_afterSale_customerExpectComp_query_response;
    }

    class OrderVo extends BaseResponseParams {
        private List<ResultVo> result;

        public List<ResultVo> getResult() {
            return result;
        }

        public void setResult(List<ResultVo> result) {
            this.result = result;
        }
    }

    class ResultVo {
        private String code;    //退货(10)、换货(20)、维修(30)
        private String name;    //服务类型名称

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}

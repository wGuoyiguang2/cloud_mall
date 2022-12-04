package com.cibnvideo.jd.aftersale.params.warereturntype;

import com.cibnvideo.jd.common.params.BaseResponseParams;

import java.util.List;

/**
 * description:根据订单号、商品编号查询支持的商品返回京东方式
 * Created by laiqingchuang on 18-7-11 .
 */
public class JdMethodResponseParams {
    private OrderVo biz_afterSale_wareReturnJdComp_query_response;

    public OrderVo getBiz_afterSale_wareReturnJdComp_query_response() {
        return biz_afterSale_wareReturnJdComp_query_response;
    }

    public void setBiz_afterSale_wareReturnJdComp_query_response(OrderVo biz_afterSale_wareReturnJdComp_query_response) {
        this.biz_afterSale_wareReturnJdComp_query_response = biz_afterSale_wareReturnJdComp_query_response;
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
        private String code; //服务类型(上门取件(4)、客户发货(40)、客户送货(7))
        private String name; //服务类型名称

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

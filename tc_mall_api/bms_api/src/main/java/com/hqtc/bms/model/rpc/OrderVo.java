package com.hqtc.bms.model.rpc;

/**
 * Created by wanghaoyang on 18-7-13.
 */
public class OrderVo extends BaseResponseParams{

        private OrderRepVo2 result;

        public OrderRepVo2 getResult() {
            return result;
        }

        public void setResult(OrderRepVo2 result) {
            this.result = result;
        }
}

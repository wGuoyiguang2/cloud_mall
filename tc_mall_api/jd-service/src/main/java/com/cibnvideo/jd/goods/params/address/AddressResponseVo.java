package com.cibnvideo.jd.goods.params.address;

import com.cibnvideo.jd.common.params.BaseResponseParams;

import java.util.Map;

/**
 * @Description:
 * @Author: WangBin
 * @Date: 2018/6/23 10:52
 */
public class AddressResponseVo extends BaseResponseParams{
    private Map<String,Integer> result;

    public Map<String, Integer> getResult() {
        return result;
    }

    public void setResult(Map<String, Integer> result) {
        this.result = result;
    }
}

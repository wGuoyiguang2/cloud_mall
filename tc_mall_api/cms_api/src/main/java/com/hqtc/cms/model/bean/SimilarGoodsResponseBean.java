package com.hqtc.cms.model.bean;

import java.util.List;

/**
 * description:查询同类商品
 * Created by laiqingchuang on 18-7-18 .
 */
public class SimilarGoodsResponseBean extends BaseResponseBean{
    private List<SimilarGoodsVo> result;

    public List<SimilarGoodsVo> getResult() {
        return result;
    }

    public void setResult(List<SimilarGoodsVo> result) {
        this.result = result;
    }
}

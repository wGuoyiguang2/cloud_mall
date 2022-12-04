package com.hqtc.cms.model.bean;

import java.io.Serializable;
import java.util.List;

/**
 * description:一级分类对应的所有二三级分类
 * Created by laiqingchuang on 18-7-7 .
 */
public class GoodClassificationCatBean extends GoodClassificationBean implements Serializable {

    private List<GoodClassificationBean> list;

    public List<GoodClassificationBean> getList() {
        return list;
    }

    public void setList(List<GoodClassificationBean> list) {
        this.list = list;
    }
}
package com.cibnvideo.oms.tcmallcustomer.service;

import com.cibnvideo.oms.tcmallcustomer.bean.ContactUsBean;

import java.util.List;

/**
 * @Description:
 * @Author: WangBin
 * @Date: 2018/7/10 21:08
 */
public interface ContactUsService {
    List<ContactUsBean> getByVenderId(Long venderId);

    ContactUsBean getById(Integer id);

    Integer update(ContactUsBean contactUsBean);

    int add(ContactUsBean contactUsBean);
}

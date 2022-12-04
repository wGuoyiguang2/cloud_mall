package com.cibnvideo.oms.tcmallcustomer.service.impl;

import com.cibnvideo.oms.tcmallcustomer.bean.ContactUsBean;
import com.cibnvideo.oms.tcmallcustomer.dao.ContactUsDao;
import com.cibnvideo.oms.tcmallcustomer.service.ContactUsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Description:
 * @Author: WangBin
 * @Date: 2018/7/10 21:08
 */
@Service
public class ContactUsServiceImpl implements ContactUsService{
    @Autowired
    private ContactUsDao contactUsDao;

    @Override
    public List<ContactUsBean> getByVenderId(Long venderId) {
        return contactUsDao.getByVenderId(venderId);
    }

    @Override
    public ContactUsBean getById(Integer id) {
        return contactUsDao.getById(id);
    }

    @Override
    public Integer update(ContactUsBean contactUsBean) {
        return contactUsDao.update(contactUsBean);
    }

    @Override
    public int add(ContactUsBean contactUsBean) {
        return contactUsDao.add(contactUsBean);
    }
}

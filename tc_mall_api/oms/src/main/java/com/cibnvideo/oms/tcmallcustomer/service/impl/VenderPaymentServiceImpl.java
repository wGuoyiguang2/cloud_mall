package com.cibnvideo.oms.tcmallcustomer.service.impl;

import com.cibnvideo.oms.tcmallcustomer.bean.VenderPayment;
import com.cibnvideo.oms.tcmallcustomer.dao.VenderPaymentDao;
import com.cibnvideo.oms.tcmallcustomer.service.VenderPaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class VenderPaymentServiceImpl implements VenderPaymentService {

    @Autowired
    VenderPaymentDao venderPaymentDao;
    @Override
    public List<VenderPayment> get(Long venderId) {
        return venderPaymentDao.get(venderId);
    }

    @Override
    public List<VenderPayment> list(Map<String, Object> map) {
        return venderPaymentDao.list(map);
    }

    @Override
    public int count(Map<String, Object> map) {
        return venderPaymentDao.count(map);
    }

    @Override
    public int save(VenderPayment venderPayment) {
        return venderPaymentDao.save(venderPayment);
    }

    @Override
    public int batchSave(List<VenderPayment> venderPayments) {
        return venderPaymentDao.batchSave(venderPayments);
    }

    @Override
    public int update(VenderPayment venderPayment) {
        return venderPaymentDao.update(venderPayment);
    }

    @Override
    public int remove(VenderPayment venderPayment) {
        return venderPaymentDao.remove(venderPayment);
    }
}

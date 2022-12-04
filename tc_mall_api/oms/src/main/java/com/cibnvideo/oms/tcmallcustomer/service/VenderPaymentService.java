package com.cibnvideo.oms.tcmallcustomer.service;

import com.cibnvideo.oms.tcmallcustomer.bean.VenderPayment;

import java.util.List;
import java.util.Map;

public interface VenderPaymentService {
    List<VenderPayment> get(Long venderId);

    List<VenderPayment> list(Map<String,Object> map);

    int count(Map<String,Object> map);

    int save(VenderPayment venderPayment);

    int batchSave(List<VenderPayment> venderPayments);

    int update(VenderPayment venderPayment);

    int remove(VenderPayment venderPayment);
}

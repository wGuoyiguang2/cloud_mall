package com.cibnvideo.oms.tcmallcustomer.dao;

import com.cibnvideo.oms.tcmallcustomer.bean.VenderPayment;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Map;

@Mapper
public interface VenderPaymentDao {

    List<VenderPayment> get(Long venderId);

    List<VenderPayment> list(Map<String,Object> map);

    int count(Map<String,Object> map);

    int save(VenderPayment venderPayment);

    int batchSave(List<VenderPayment> venderPayments);

    int update(VenderPayment venderPayment);

    int remove(VenderPayment venderPayment);
}

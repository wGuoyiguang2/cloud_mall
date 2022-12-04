package com.cibnvideo.oms.tcmallcustomer.service.impl;

import com.cibnvideo.oms.tcmallcustomer.bean.VenderInvoiceManagerVo;
import com.cibnvideo.oms.tcmallcustomer.bean.VenderOrderManagerVo;
import com.cibnvideo.oms.tcmallcustomer.bean.VenderSettlement;
import com.cibnvideo.oms.tcmallcustomer.dao.VenderSettlementDao;
import com.cibnvideo.oms.tcmallcustomer.service.VenderSettlementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@Service
public class VenderSettlementServiceImpl implements VenderSettlementService {

    @Autowired
    VenderSettlementDao venderSettlementDao;
    @Override
    public VenderSettlement get(Long venderId) {
        return venderSettlementDao.get(venderId);
    }

    @Override
    public List<VenderSettlement> list(Map<String, Object> map) {
        return venderSettlementDao.list(map);
    }

    @Override
    public int count(Map<String, Object> map) {
        return venderSettlementDao.count(map);
    }

    @Override
    public int save(VenderSettlement venderSettlement) {
        return venderSettlementDao.save(venderSettlement);
    }

    @Override
    public int update(VenderSettlement venderSettlement) {
        return venderSettlementDao.update(venderSettlement);
    }

    @Override
    public BigDecimal balanceGet(Integer venderId) {
        return venderSettlementDao.balanceGet(venderId);
    }

    @Override
    public BigDecimal pricePercentGet(Integer venderId) {
        return venderSettlementDao.pricePercentGet(venderId);
    }

    @Override
    public int balanceAdd(Integer venderId, BigDecimal value) {
        return venderSettlementDao.balanceAdd(venderId, value);
    }

    @Override
    public int balanceReduce(Integer venderId, BigDecimal value) {
        return venderSettlementDao.balanceReduce(venderId, value);
    }

    @Override
    public int remove(Long venderId) {
        return venderSettlementDao.remove(venderId);
    }

    @Override
    public List<VenderOrderManagerVo> listVenderOrderManagerVo(Map<String, Object> params) {
        return venderSettlementDao.listVenderOrderManagerVo(params);
    }

    @Override
    public int countVenderOrderManagerVo(Map<String, Object> params) {
        return venderSettlementDao.countVenderOrderManagerVo(params);
    }

    @Override
    public List<VenderInvoiceManagerVo> listVenderInvoiceManager(Map<String, Object> params) {
        return venderSettlementDao.listVenderInvoiceManager(params);
    }

    @Override
    public int countVenderInvoiceManager(Map<String, Object> params) {
        return venderSettlementDao.countVenderInvoiceManager(params);
    }

    @Override
    public List<Integer> listVenderId() {
        return venderSettlementDao.listVenderId();
    }
}

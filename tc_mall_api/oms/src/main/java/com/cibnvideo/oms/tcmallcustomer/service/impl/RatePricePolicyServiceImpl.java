package com.cibnvideo.oms.tcmallcustomer.service.impl;

import com.cibnvideo.oms.tcmallcustomer.bean.PricePolicy;
import com.cibnvideo.oms.tcmallcustomer.bean.PriceRate;
import com.cibnvideo.oms.tcmallcustomer.dao.RatePricePolicyDao;
import com.cibnvideo.oms.tcmallcustomer.service.RatePricePolicyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class RatePricePolicyServiceImpl implements RatePricePolicyService {

    @Autowired
    RatePricePolicyDao ratePricePolicyDao;
    @Override
    public PriceRate get(Integer id) {
        return ratePricePolicyDao.get(id);
    }

    @Override
    public List<PriceRate> list(Map<String, Object> map) {
        return ratePricePolicyDao.list(map);
    }

    @Override
    public int count(Map<String, Object> map) {
        return ratePricePolicyDao.count(map);
    }

    @Override
    public int save(PriceRate priceRate) {
        priceRate.setCtime(new Date());
        priceRate.setUtime(new Date());
        return ratePricePolicyDao.save(priceRate);
    }

    @Override
    public int remove(Integer id) {
        return ratePricePolicyDao.remove(id);
    }

    @Override
    public int batchRemove(Map<String, Object> map) {
        return ratePricePolicyDao.batchRemove(map);
    }

    @Override
    public PricePolicy getPricePolicyByRate(Integer venderId, BigDecimal rate) {
        return ratePricePolicyDao.getPricePolicyByRate(venderId, rate);
    }
}

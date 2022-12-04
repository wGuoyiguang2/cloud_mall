package com.cibnvideo.oms.tcmallcustomer.service.impl;

import com.cibnvideo.oms.tcmallcustomer.bean.AfterSaleConfBean;
import com.cibnvideo.oms.tcmallcustomer.dao.AfterSaleConfDao;
import com.cibnvideo.oms.tcmallcustomer.service.AfterSaleConfService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AfterSaleConfServiceImpl implements AfterSaleConfService {

    @Autowired
    AfterSaleConfDao afterSaleConfDao;
    @Override
    public List<AfterSaleConfBean> getAfterSaleConf(Integer venderId, Integer type) {
        return afterSaleConfDao.getAfterSaleConf(venderId, type);
    }
}

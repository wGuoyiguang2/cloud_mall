package com.hqtc.bms.service.impl;

import com.hqtc.bms.model.mapper.AfterSaleMapper;
import com.hqtc.bms.model.response.AfterSaleVo;
import com.hqtc.bms.service.AfterSaleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @Description:
 * @Author: WangBin
 * @Date: 2019/2/17 14:19
 */
@Service
public class AfterSaleServiceImpl implements AfterSaleService{
    @Autowired
    private AfterSaleMapper afterSaleMapper;
    @Override
    public int countAfterSaleManager(Map<String, Object> params) {
        return afterSaleMapper.countAfterSaleManager(params);
    }

    @Override
    public List<AfterSaleVo> afterSaleManagerList(Map<String, Object> params) {
        return afterSaleMapper.afterSaleManagerList(params);
    }
}

package com.cibnvideo.oms.tcmallcustomer.service.impl;

import com.cibnvideo.oms.tcmallcustomer.bean.*;
import com.cibnvideo.oms.tcmallcustomer.dao.VenderAccountSettlementDao;
import com.cibnvideo.oms.tcmallcustomer.service.VenderAccountSettlementService;
import com.cibnvideo.oms.tcmallcustomer.service.VenderSettlementService;
import org.apache.ibatis.annotations.Select;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * @Description:
 * @Author: WangBin
 * @Date: 2018/7/27 20:58
 */
@Service
public class VenderAccountSettlementServiceImpl implements VenderAccountSettlementService {
    @Autowired
    private VenderAccountSettlementDao venderAccountSettlementDao;
    @Autowired
    private VenderSettlementService venderSettlementService;
    @Override
    public AccountInfoVo getAccountInfo(Long venderId) {
        /*VenderSettlement venderSettlement = venderSettlementService.get(venderId);
        if(venderSettlement==null||venderSettlement.getSettlementType()!=1){
            return null;
        }*/
        return venderAccountSettlementDao.getAccountInfo(venderId);
    }

    @Override
    public List<AccountManagerVo> accountManagerList(Map<String, Object> params) {
        return venderAccountSettlementDao.accountManagerList(params);
    }

    @Override
    public int countAccountManagerList(Map<String, Object> params) {
        return venderAccountSettlementDao.countAccountManagerList(params);
    }

    @Override
    public List<AccountManagerVo> getAccountListByOrders(List<String> orderList) {
        return venderAccountSettlementDao.getAccountListByOrders(orderList);
    }

    @Override
    public int save(VenderSettlementAccount venderSettlementAccount) {
        return venderAccountSettlementDao.save(venderSettlementAccount);
    }

    @Override
    public int settleAccount(String startTime, String endTime, Long venderId) {
        return venderAccountSettlementDao.settleAccount(startTime,endTime,venderId);
    }

    @Override
    public List<VenderAccountVo> getVenderAccountList(Map<String, Object> map) {
        return venderAccountSettlementDao.getVenderAccountList(map);
    }

    @Override
    public int countVenderAccountList(Map<String, Object> map) {
        return venderAccountSettlementDao.countVenderAccountList(map);
    }

    @Override
    public BigDecimal sumFreightByVenderId(int venderId) {
        return venderAccountSettlementDao.sumFreightByVenderId(venderId);
    }
}

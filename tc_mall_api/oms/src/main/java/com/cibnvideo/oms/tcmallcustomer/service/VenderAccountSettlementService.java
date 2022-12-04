package com.cibnvideo.oms.tcmallcustomer.service;

import com.cibnvideo.oms.tcmallcustomer.bean.AccountInfoVo;
import com.cibnvideo.oms.tcmallcustomer.bean.AccountManagerVo;
import com.cibnvideo.oms.tcmallcustomer.bean.VenderAccountVo;
import com.cibnvideo.oms.tcmallcustomer.bean.VenderSettlementAccount;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * @Description:
 * @Author: WangBin
 * @Date: 2018/7/27 20:58
 */
public interface VenderAccountSettlementService {
    AccountInfoVo getAccountInfo(Long venderId);

    List<AccountManagerVo> accountManagerList(Map<String, Object> params);

    int countAccountManagerList(Map<String, Object> params);

    List<AccountManagerVo> getAccountListByOrders(List<String> orderList);

    int save(VenderSettlementAccount venderSettlementAccount);

    int settleAccount(String startTime, String endTime, Long venderId);

    List<VenderAccountVo> getVenderAccountList(Map<String, Object> map);

    int countVenderAccountList(Map<String, Object> map);

    BigDecimal sumFreightByVenderId(int venderId);
}

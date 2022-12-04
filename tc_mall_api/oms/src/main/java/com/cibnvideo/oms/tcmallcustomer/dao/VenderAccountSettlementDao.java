package com.cibnvideo.oms.tcmallcustomer.dao;

import com.cibnvideo.oms.tcmallcustomer.bean.AccountInfoVo;
import com.cibnvideo.oms.tcmallcustomer.bean.AccountManagerVo;
import com.cibnvideo.oms.tcmallcustomer.bean.VenderAccountVo;
import com.cibnvideo.oms.tcmallcustomer.bean.VenderSettlementAccount;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * @Description:
 * @Author: WangBin
 * @Date: 2018/7/27 21:00
 */
@Mapper
public interface VenderAccountSettlementDao {
    AccountInfoVo getAccountInfo(@Param("venderId") Long venderId);

    List<AccountManagerVo> accountManagerList(Map<String, Object> params);

    int countAccountManagerList(Map<String, Object> params);

    List<AccountManagerVo> getAccountListByOrders(@Param("orderList") List<String> orderList);

    int save(VenderSettlementAccount venderSettlementAccount);

    int settleAccount(@Param("startTime") String startTime, @Param("endTime") String endTime, @Param("venderId") Long venderId);

    List<VenderAccountVo> getVenderAccountList(Map<String, Object> map);

    int countVenderAccountList(Map<String, Object> map);

    BigDecimal sumFreightByVenderId(@Param("venderId")int venderId);
}

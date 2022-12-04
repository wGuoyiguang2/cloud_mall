package com.cibnvideo.oms.tcmallcustomer.dao;

import com.cibnvideo.oms.tcmallcustomer.bean.VenderInvoiceManagerVo;
import com.cibnvideo.oms.tcmallcustomer.bean.VenderOrderManagerVo;
import com.cibnvideo.oms.tcmallcustomer.bean.VenderSettlement;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@Mapper
public interface VenderSettlementDao {

    VenderSettlement get(Long venderId);

    List<VenderSettlement> list(Map<String,Object> map);

    int count(Map<String,Object> map);

    int save(VenderSettlement venderSettlement);

    int update(VenderSettlement venderSettlement);

    BigDecimal balanceGet(@Param("venderId") Integer venderId);

    BigDecimal pricePercentGet(@Param("venderId") Integer venderId);

    int balanceAdd(@Param("venderId") Integer venderId, @Param("value") BigDecimal value);

    int balanceReduce(@Param("venderId") Integer venderId, @Param("value") BigDecimal value);

    int remove(Long venderId);

    List<VenderOrderManagerVo> listVenderOrderManagerVo(Map<String, Object> params);

    int countVenderOrderManagerVo(Map<String, Object> params);

    List<VenderInvoiceManagerVo> listVenderInvoiceManager(Map<String, Object> params);

    int countVenderInvoiceManager(Map<String, Object> params);

    List<Integer> listVenderId();


}

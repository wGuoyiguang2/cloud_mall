package com.cibnvideo.oms.tcmallcustomer.service;

import com.cibnvideo.oms.tcmallcustomer.bean.FreeFreightBean;

import java.util.List;
import java.util.Map; /**
 * @Description:
 * @Author: WangBin
 * @Date: 2018/9/7 17:58
 */
public interface FreeFreightService {
    List<FreeFreightBean> listManagerFreeFreight(Map<String, Object> params);

    int countManagerFreeFreight(Map<String, Object> params);

    int addFreeFreight(Map<String, Object> params);

    FreeFreightBean getById(Integer id);

    int updateManagerFreeFreight(Map<String, Object> params);

    Double getByVenderId(Integer venderId);
}

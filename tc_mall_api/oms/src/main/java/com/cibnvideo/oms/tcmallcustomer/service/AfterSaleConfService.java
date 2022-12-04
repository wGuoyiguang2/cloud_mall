package com.cibnvideo.oms.tcmallcustomer.service;

import com.cibnvideo.oms.tcmallcustomer.bean.AfterSaleConfBean;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface AfterSaleConfService {
    List<AfterSaleConfBean> getAfterSaleConf(@Param("venderId") Integer venderId,
                                             @Param("type") Integer type);
}

package com.cibnvideo.oms.tcmallcustomer.dao;

import com.cibnvideo.oms.tcmallcustomer.bean.AfterSaleConfBean;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by makuan on 18-7-21.
 */
@Mapper
public interface AfterSaleConfDao {
    List<AfterSaleConfBean> getAfterSaleConf(@Param("venderId") Integer venderId,
                                             @Param("type") Integer type);
}

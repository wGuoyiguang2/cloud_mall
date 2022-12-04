package com.cibnvideo.oms.tcmallcustomer.service;

import com.cibnvideo.oms.tcmallcustomer.bean.ConfigManagerVo;

import java.util.List;
import java.util.Map; /**
 * @Description:
 * @Author: WangBin
 * @Date: 2018/8/8 14:59
 */
public interface ConfigService {
    List<ConfigManagerVo> listManagerConfig(Map<String, Object> params);

    int countManagerConfig(Map<String, Object> params);

    int addConfig(Map<String, Object> params);

    ConfigManagerVo getById(Integer id);

    int updateManagerConfig(Map<String, Object> params);

    int deleteById(Integer id);
}

package com.hqtc.bms.service;

import com.hqtc.bms.model.response.AfterSaleVo;

import java.util.List;
import java.util.Map; /**
 * @Description:
 * @Author: WangBin
 * @Date: 2019/2/17 14:19
 */
public interface AfterSaleService {
    int countAfterSaleManager(Map<String, Object> params);

    List<AfterSaleVo> afterSaleManagerList(Map<String, Object> params);
}

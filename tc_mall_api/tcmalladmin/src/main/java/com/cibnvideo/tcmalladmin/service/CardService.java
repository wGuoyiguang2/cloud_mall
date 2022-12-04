package com.cibnvideo.tcmalladmin.service;

import com.hqtc.common.response.ResultData;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse; /**
 * @Description:
 * @Author: WangBin
 * @Date: 2018/10/9 18:29
 */
public interface CardService {
    ResultData cardBatchExport(HttpServletRequest request, HttpServletResponse response, Integer userId, String intro, String batchNo) throws Exception;

    ResultData cardExport(HttpServletRequest request, HttpServletResponse response, Integer userId, String intro, String cardNos) throws Exception;
}

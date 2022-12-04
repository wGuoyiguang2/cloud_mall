package com.cibnvideo.tcmalladmin.service.impl;

import com.cibnvideo.tcmalladmin.bmsapi.CardApi;
import com.cibnvideo.tcmalladmin.model.bean.CardAdminRecord;
import com.cibnvideo.tcmalladmin.model.bean.CardVo;
import com.cibnvideo.tcmalladmin.service.CardService;
import com.hqtc.common.config.ErrorCode;
import com.hqtc.common.response.ResultData;
import com.hqtc.common.utils.ExcelPOIUtil;
import com.hqtc.common.utils.Tools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

/**
 * @Description:
 * @Author: WangBin
 * @Date: 2018/10/9 18:29
 */
@Service
public class CardServiceImpl implements CardService{
    @Autowired
    private CardApi cardApi;
    @Override
    public ResultData cardBatchExport(HttpServletRequest request, HttpServletResponse response, Integer userId, String intro, String batchNo) throws Exception{
        ResultData resultData= Tools.getThreadResultData();
        //获取要导出的数据
        Map<String,Object> map=new HashMap<>();
        map.put("batchNo",batchNo);
        ResultData<List<CardVo>> resultData1=cardApi.listCard(map);
        if(resultData1.getError()!= ErrorCode.SUCCESS){
            return resultData1;
        }
        //导出
        ExcelPOIUtil.export(String.valueOf(System.currentTimeMillis()),resultData1.getData(),request,response);
        //记录操作记录
        CardAdminRecord cardAdminRecord=new CardAdminRecord();
        List<String> cardNoList=new ArrayList<>();
        for(CardVo cardVo:resultData1.getData()){
            cardNoList.add(cardVo.getCardNo());
        }
        cardAdminRecord.setCardNoList(cardNoList);
        cardAdminRecord.setBatchNo(batchNo);
        cardAdminRecord.setIntro(intro);
        cardAdminRecord.setOperateType(6);
        cardAdminRecord.setOperator(String.valueOf(userId));
        ResultData resultData2=cardApi.addCardAdminOperateRecord(cardAdminRecord);
        if(resultData2.getError()!= ErrorCode.SUCCESS){
            return resultData2;
        }
        return resultData;
    }

    @Override
    public ResultData cardExport(HttpServletRequest request, HttpServletResponse response, Integer userId, String intro, String cardNos) throws Exception{
        ResultData resultData= Tools.getThreadResultData();
        //获取要导出的数据
        List<String> cardNoList= Arrays.asList(cardNos.split(","));
        ResultData<List<CardVo>> resultData1=cardApi.listCardByCardNos(cardNoList);
        if(resultData1.getError()!= ErrorCode.SUCCESS){
            return resultData1;
        }
        //导出
        ExcelPOIUtil.export(String.valueOf(System.currentTimeMillis()),resultData1.getData(),request,response);
        //记录操作记录
        CardAdminRecord cardAdminRecord=new CardAdminRecord();
        cardAdminRecord.setCardNoList(cardNoList);
        cardAdminRecord.setIntro(intro);
        cardAdminRecord.setOperateType(6);
        cardAdminRecord.setOperator(String.valueOf(userId));
        ResultData resultData2=cardApi.addCardAdminOperateRecord(cardAdminRecord);
        if(resultData2.getError()!= ErrorCode.SUCCESS){
            return resultData2;
        }
        return resultData;
    }
}

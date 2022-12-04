package com.hqtc.bms.task;

import com.hqtc.bms.model.params.InvoiceresultDto;
import com.hqtc.bms.model.params.InvoiceresultResponseParams;
import com.hqtc.bms.model.rpc.InvoiceResultRequestBean;
import com.hqtc.bms.service.InvoiceService;
import com.hqtc.common.utils.JsonTools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * description:电子发票定时任务
 * Created by laiqingchuang on 18-7-25 .
 */
@Component
public class InvoiceTask {
    @Autowired
    InvoiceService invoiceService;

    /**
     * 更新发票信息
     */
    //@Scheduled(cron="0/10 * *  * * ? ")   //每10秒执行一次
    public void updateTOrderInvoice() {
        //获取流水号集合
        List<String> serialNoList = invoiceService.getSerialNoList();

        if(serialNoList !=null && serialNoList.size() !=0) {
            //根据发票流水获取发票结果
            String str1 = invoiceService.getInvoiceResult(getInvoiceResultRequestBean(serialNoList));
            Object o1=JsonTools.getInstance().stringToObject(str1,InvoiceresultResponseParams.class);
            Map<String,List<InvoiceresultDto>> o2=JsonTools.getInstance().convertObjToMap(o1);
            List<InvoiceresultDto> invoiceList = dealInvoiceList(o2.get("list"));

            //更新发票信息
            if(invoiceList.size() >0){
                int result=invoiceService.updateOrderInvoice(invoiceList);
                System.out.println("定时任务updateTOrderInvoice---------result=" + result);
            }
        }
    }

    /**
     * 过滤不符合条件的发票
     */
    private List<InvoiceresultDto> dealInvoiceList(List<InvoiceresultDto> list) {
        List<InvoiceresultDto> dtos=new ArrayList<InvoiceresultDto>();
        if(list !=null && list.size() !=0){
            for(InvoiceresultDto dto: list){
                if(!dto.getC_fpdm().equals("") && !dto.getC_fphm().equals("")){
                    dtos.add(dto);
                }
            }
        }
        return dtos;
    }

    /**
     * 发票流水号参数
     */
    private InvoiceResultRequestBean getInvoiceResultRequestBean(List<String> serialNoList) {
        InvoiceResultRequestBean bean = new InvoiceResultRequestBean();
        bean.setFpqqlsh(serialNoList);
        return bean;
    }
}


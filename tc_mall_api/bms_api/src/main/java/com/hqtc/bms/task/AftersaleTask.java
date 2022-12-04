package com.hqtc.bms.task;

import com.hqtc.bms.model.params.OrderAftersaleBean;
import com.hqtc.bms.model.params.ServiceInfoDto;
import com.hqtc.bms.model.params.ServiceListPageResponseParams;
import com.hqtc.bms.service.AfterSaleJdService;
import com.hqtc.bms.service.AfterSaleTaskService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * description:售后定时任务
 * Created by laiqingchuang on 18-9-13 .
 */
@Component
public class AftersaleTask {

    Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    AfterSaleJdService afterSaleJdService;
    @Autowired
    AfterSaleTaskService afterSaleTaskService;
    @Value("${service.config.serviceSize}")
    private int serviceSize;

    @Scheduled(fixedRate = 1000*60*5)
    public void updateAftersale() {
        List<OrderAftersaleBean> beans= afterSaleTaskService.getUpdateAftersaleList();
        if(beans !=null && beans.size()>0){
            int num = updateServiceNos(beans);
            logger.info("售后更新服务单数量:"+num);
        }
    }

    /**
     * 更新服务单
     * @return
     */
    public int updateServiceNos(List<OrderAftersaleBean> beans) {
        int num=0;
        List<OrderAftersaleBean> orderAftersaleBeans = new ArrayList<>();
        for(OrderAftersaleBean bean:beans){
            Map<String, ServiceListPageResponseParams> serviceMap = afterSaleJdService.getServiceListPage(Long.valueOf(bean.getChildTradeNo()), 1, serviceSize);
            if(serviceMap !=null){
                ServiceListPageResponseParams serviceParam = serviceMap.get("biz_afterSale_serviceListPage_query_response");
                if (serviceParam != null && serviceParam.getResult() != null && serviceParam.getResult().getServiceInfoList() != null && serviceParam.getResult().getServiceInfoList().size() >= bean.getSeq()) {
                    List<ServiceInfoDto> serviceInfoList = serviceParam.getResult().getServiceInfoList();
                    sort(serviceInfoList);
                    OrderAftersaleBean orderAftersaleBean = new OrderAftersaleBean();
                    orderAftersaleBean.setChildTradeNo(bean.getChildTradeNo());
                    orderAftersaleBean.setSeq(bean.getSeq());
                    orderAftersaleBean.setServiceNo(serviceInfoList.get(bean.getSeq()-1).getAfsServiceId());
                    orderAftersaleBeans.add(orderAftersaleBean);
                }
            }
        }
        if(orderAftersaleBeans !=null && orderAftersaleBeans.size() !=0){
            num = afterSaleTaskService.updateServiceNos(orderAftersaleBeans);
        }
        return num;
    }


    /**
     * 服务单按照时间升序排列
     * @param list
     * @return
     */
    private void sort(List<ServiceInfoDto> list) {
        Collections.sort(list, new Comparator<ServiceInfoDto>() {
            @Override
            public int compare(ServiceInfoDto o1, ServiceInfoDto o2) {
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                try {
                    Date dt1 = format.parse(o1.getAfsApplyTime());
                    Date dt2 = format.parse(o2.getAfsApplyTime());
                    if (dt1.getTime() > dt2.getTime()) {
                        return 1;
                    } else if (dt1.getTime() < dt2.getTime()) {
                        return -1;
                    } else {
                        return 0;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return 0;
            }
        });
    }

}

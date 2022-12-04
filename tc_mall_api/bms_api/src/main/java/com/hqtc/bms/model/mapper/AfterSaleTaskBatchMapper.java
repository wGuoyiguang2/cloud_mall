package com.hqtc.bms.model.mapper;

import com.hqtc.bms.model.base.BaseDao;
import com.hqtc.bms.model.params.MessageInfo;
import com.hqtc.bms.model.params.OrderAftersaleBean;
import com.hqtc.bms.model.params.OrderProductBean;
import com.hqtc.bms.model.params.ServiceRequestParams;
import org.springframework.stereotype.Repository;
import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * description:售后物流(批处理)
 * Created by laiqingchuang on 18-7-27 .
 */
@Repository
public class AfterSaleTaskBatchMapper {

    @Resource(name = "baseDaoImpl")
    private BaseDao dao;

    /**
     * 更新订单状态
     */
    public int updateOrderState(List<OrderProductBean> list) {
        List<String> sqlList = new ArrayList<>();
        for(OrderProductBean bean: list){
            String sql="update t_order_product set order_state="+bean.getOrderState()+",utime=now() where child_trade_no="+bean.getChildTradeNo();
            sqlList.add(sql);
        }
        int[] ints = dao.batchExecute(sqlList);
        return ints.length;
    }

    /**
     * 更新取消服务单信息
     */
    public int updateCancelService(ServiceRequestParams requestParams) {
        List<Integer> serviceNoList=requestParams.getServiceIdList();
        String reason=requestParams.getApproveNotes();
        List<String> sqlList = new ArrayList<>();
        for(int serviceNo: serviceNoList){
            String sql="update t_order_aftersale set status=60,reason='"+reason+"',remarks='您的服务单已取消',utime=now() where service_no="+serviceNo;
            sqlList.add(sql);
        }
        int[] ints = dao.batchExecute(sqlList);
        return ints.length;
    }

    /**
     * 更新售后流水
     */
    public int updateAftersaleState(List<OrderAftersaleBean> list) {
        List<String> sqlList = new ArrayList<>();
        for(OrderAftersaleBean bean: list){
            String sql="update t_order_aftersale set status="+bean.getStatus()+",remarks='"+bean.getRemarks()+"',utime=now() where service_no="+bean.getServiceNo();
            sqlList.add(sql);
        }
        int[] ints = dao.batchExecute(sqlList);
        return ints.length;
    }

    /**
     * 添加售后流水
     */
    public int addOrderAftersale(OrderAftersaleBean bean,List<Integer> seqList) {
        List<String> sqlList = new ArrayList<>();
        for(int seq:seqList){
            String sql="insert into t_order_aftersale set venderid="+bean.getVenderid()+",user_id="+bean.getUserId()+",order_sn="+bean.getOrderSn()+",child_trade_no="+bean.getChildTradeNo()+",name='"+bean.getName()+"',"
                    +"phone="+bean.getPhone()+",product_id="+bean.getProductId()+",product_name='"+bean.getProductName()+"',price="+bean.getPrice()+",count=1,service_type="+bean.getServiceType()+","
                    +"reason='"+bean.getReason()+"',back_type="+bean.getBackType()+",return_type="+bean.getReturnType()+",back_addr='"+bean.getBackAddr()+"',return_addr='"+bean.getReturnAddr()+"',status="+bean.getStatus()+","
                    +"remarks='"+bean.getRemarks()+"',seq="+seq+",ctime=now(),utime=now()";
            sqlList.add(sql);
        }
        int[] ints = dao.batchExecute(sqlList);
        return ints.length;
    }

    /**
     * 更新服务单号
     */
    public int updateServiceNos(List<OrderAftersaleBean> list) {
        List<String> sqlList = new ArrayList<>();
        for(OrderAftersaleBean bean: list){
            String sql="update t_order_aftersale set service_no="+bean.getServiceNo()+",utime=now() where child_trade_no="+bean.getChildTradeNo()+" and seq="+bean.getSeq();
            sqlList.add(sql);
        }
        int[] ints = dao.batchExecute(sqlList);
        return ints.length;
    }

    /**
     * 更新售后申请失败信息
     * @param messageInfo
     */
    public int updateAftersaleFailinfo(MessageInfo messageInfo) {
        List<String> sqlList = new ArrayList<>();
        for(int seq: messageInfo.getSeqList()){
            String sql="update t_order_aftersale set status=100,remarks='售后申请失败',utime=now() where child_trade_no="+messageInfo.getChildTradeNo()+" and seq="+seq;
            sqlList.add(sql);
        }
        int[] ints = dao.batchExecute(sqlList);
        return ints.length;
    }
}

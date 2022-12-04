package com.hqtc.bms.model.mapper;

import com.hqtc.bms.model.base.BaseDao;
import com.hqtc.bms.model.params.InvoiceresultDto;
import com.hqtc.bms.model.rpc.InvoiceDetailRequestBean;
import org.springframework.stereotype.Repository;
import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * description:电子发票(批处理)
 * Created by laiqingchuang on 18-7-27 .
 */
@Repository
public class InvoiceBatchMapper {

    @Resource(name = "baseDaoImpl")
    private BaseDao dao;

    /**
     * 更新发票状态
     */
    public int updateOrderInvoice(List<InvoiceresultDto> list) {
        List<String> sqlList = new ArrayList<>();
        for(InvoiceresultDto bean: list){
            String sql="update t_order_invoice set invoice_code='"+bean.getC_fpdm()+"',invoice_no='"+bean.getC_fphm()+"',status="+bean.getC_status()+",utime=now() where serial_no="+bean.getC_fpqqlsh()+"";
            sqlList.add(sql);
        }
        int[] ints = dao.batchExecute(sqlList);
        return ints.length;
    }

    /**
     * 添加发票明细信息
     */
    public int saveInvoiceDetail(String serialNo, List<InvoiceDetailRequestBean> list) {
        List<String> sqlList = new ArrayList<>();
        for(InvoiceDetailRequestBean bean: list){
            String sql="insert into t_order_invoice_detail(serial_no,product_id,name,taxrate,price,count,ctime) values ("+serialNo+","+bean.getProductId()+",'"+bean.getGoodsname()+"',"+bean.getTaxrateInt()+","+bean.getPrice()+","+bean.getNum()+",now())";
            sqlList.add(sql);
        }
        int[] ints = dao.batchExecute(sqlList);
        return ints.length;
    }
}
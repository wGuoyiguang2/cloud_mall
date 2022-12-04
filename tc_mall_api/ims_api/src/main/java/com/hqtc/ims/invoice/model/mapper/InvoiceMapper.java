package com.hqtc.ims.invoice.model.mapper;

import com.hqtc.ims.invoice.model.bean.InvoiceBean;
import com.hqtc.ims.invoice.model.bean.InvoiceVo;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

/**
 * @Description:
 * @Author: WangBin
 * @Date: 2018/7/20 11:27
 */
@Repository
public interface InvoiceMapper {
    @Update("replace into t_invoice set user_id=#{userId},taxnum=#{taxnum},name=#{name},phone=#{phone},email=#{email},invoice_head=#{invoiceHead},ctime=NOW(),invoice_type=#{invoiceType}")
    public Integer addOrUpdateInvoice(InvoiceBean invoiceBean);
    @Select("select name,phone,email,invoice_head as invoiceHead,taxnum,invoice_type as invoiceType from t_invoice where user_id=#{userId}")
    InvoiceVo getByUserId(@Param("userId") Integer userId);
}

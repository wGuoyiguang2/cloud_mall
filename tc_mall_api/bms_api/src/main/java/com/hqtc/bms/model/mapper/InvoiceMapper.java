package com.hqtc.bms.model.mapper;

import com.hqtc.bms.model.params.InvoiceInfoVo;
import com.hqtc.bms.model.params.InvoiceManagerVo;
import com.hqtc.bms.model.params.InvoiceresultDto;
import com.hqtc.bms.model.params.OrderInvoiceBean;
import com.hqtc.bms.model.rpc.InvoiceDetailRequestBean;
import com.hqtc.bms.model.rpc.InvoiceRequestBean;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

/**
 * description:电子发票
 * Created by laiqingchuang on 18-7-18 .
 */
@Repository
public interface InvoiceMapper {

    /**
     * 获取订单主表信息参数
     * @param orderSn
     */
    @Select("select a.id,a.venderid,a.user_id userId,a.trade_no tradeNo,b.phone,b.county_id countyId,b.town_id townId," +
            "detail detailAddr from t_order a left join t_order_address b on a.id=b.order_id where order_sn=#{orderSn} ")
    InvoiceRequestBean getMainOrder(@Param("orderSn") String orderSn);

    /**
     * 获取电子发票明细参数
     * @param orderSn
     * @return
     */
    @Select("SELECT a.product_id productId,a.name goodsname,a.taxrate taxrateInt,a.taxrate / 100 taxrate,a.price,(sum(a.count)-ifnull((select " +
            "sum(ifnull(count,0)) from t_order_aftersale where order_sn=#{orderSn} and product_id=a.product_id and status not in(20,60)),0)) num " +
            "FROM t_order_product a WHERE a.order_sn = #{orderSn} and a.order_state = 5 GROUP BY a.product_id ,a.name,a.taxrate,a.price")
    List<InvoiceDetailRequestBean> getInvoiceDetail(@Param("orderSn") String orderSn);

    /**
     * 从发票明细表中获取发票明细参数
     * @param orderSn
     * @return
     */
    @Select("select product_id productId,name goodsname,taxrate taxrateInt,taxrate/100 taxrate,price,count num from t_order_invoice_detail " +
            "where serial_no=(select serial_no from t_order_invoice where order_sn=#{orderSn} order by ctime desc limit 1)")
    List<InvoiceDetailRequestBean> getInvoiceDetail2(@Param("orderSn") String orderSn);

    /**
     * 同步发票数据到数据库
     * @param bean
     * @return
     */
    @Insert("insert into t_order_invoice set venderid=#{bean.venderid},user_id=#{bean.userId},order_sn=#{bean.orderSn},trade_no=#{bean.tradeNo}," +
            "invoice_id=#{bean.invoiceId},serial_no=#{bean.serialNo},invoice_type=#{bean.invoiceType},invoice_price=#{bean.invoicePrice}," +
            "tax=#{bean.tax},phone=#{bean.phone},email=#{bean.email},taxnum=#{bean.taxnum},address=#{bean.address},invoice_head=#{bean.invoiceHead}," +
            "invoice_code=#{bean.invoiceCode},invoice_no=#{bean.invoiceNo},status=#{bean.status},ctime=NOW(),utime=NOW()")
    int saveInvoiceInfo(@Param("bean") OrderInvoiceBean bean);

    /**
     * 根据平台订单号获取蓝票信息
     */
    @Select("SELECT order_sn orderSn,invoice_id invoiceId,invoice_code invoiceCode,invoice_no invoiceNo,invoice_head invoiceHead," +
            "phone,email,taxnum FROM t_order_invoice where order_sn=#{orderSn} order by ctime desc limit 1")
    OrderInvoiceBean getInvoiceInfo(@Param("orderSn") String orderSn);
    @Select("<script> SELECT i.venderid as venderId,GROUP_CONCAT(CONCAT(d.`name`,'，数量：',d.count) SEPARATOR ';<![CDATA[<br>]]>') AS productName,i.invoice_id as invoiceId,o.order_sn AS orderNo,o.pay_order_sn AS payNo,i.invoice_head AS invoiceHead,i.email AS email,i.invoice_type AS invoiceType,DATE_FORMAT(i.ctime,'%Y-%m-%d %H:%i:%s') AS createTime " +
            " FROM t_order_invoice i left join t_order_invoice_detail d on i.serial_no=d.serial_no LEFT JOIN t_order o on o.order_sn=i.order_sn "+
            " <where> i. STATUS = 2 "+
            "<if test=\"venderid != null and venderid!=''\"> and i.venderid=#{venderid} </if>"+
            "<if test=\"productId != null and productId!=''\"> and d.product_id=#{productId} </if>"+
            "<if test=\"orderNo != null and orderNo!=''\"> and o.order_sn=#{orderNo} </if>"+
            "<if test=\"invoiceId != null and invoiceId!=''\"> and i.invoice_id=#{invoiceId} </if>"+
            "<if test=\"invoiceHead != null and invoiceHead!=''\"> and i.invoice_head=#{invoiceHead} </if>"+
            " </where> "+
            " GROUP BY i.serial_no order by i.ctime desc"+
            "<if test='offset != null and limit != null'> limit ${offset}, ${limit} </if>"+
            "</script>")
    List<InvoiceManagerVo> invoiceManagerList(Map<String, Object> params);//因为有红票和蓝票一说，并且都要显示，则需要以serial_no分组
    @Select("<script> select count(1) from (select count(1) from t_order_invoice i  left join t_order_invoice_detail d on i.serial_no=d.serial_no  LEFT JOIN t_order o ON o.order_sn = i.order_sn   "+
            " <where> i. STATUS = 2 "+
            "<if test=\"venderid != null and venderid!=''\"> and i.venderid=#{venderid} </if>"+
            "<if test=\"productId != null and productId!=''\"> and d.product_id=#{productId} </if>"+
            "<if test=\"orderNo != null and orderNo!=''\"> and o.order_sn=#{orderNo} </if>"+
            "<if test=\"invoiceId != null and invoiceId!=''\"> and i.invoice_id=#{invoiceId} </if>"+
            "<if test=\"invoiceHead != null and invoiceHead!=''\"> and i.invoice_head=#{invoiceHead} </if>"+
            " </where>" +
            " group by i.serial_no)a"+
            "</script>")
    int countInvoiceManagerList(Map<String, Object> params);//因为有红票和蓝票一说，并且都要显示，则需要以serial_no分组

    @Select("select (SELECT count(1) FROM t_order_invoice i WHERE venderid = #{venderId} AND STATUS = '2') as count,(select count(1) from t_order_invoice i where venderid=#{venderId} and status='2' and invoice_type='1' ) as opened,(select count(1) from t_order_invoice i where venderid=#{venderId} and status='2' and invoice_type='2' ) as canceled")
    InvoiceInfoVo getInvoiceManagerInfo(@Param("venderId") Long venderId);//因为有红票和蓝票一说，并且都要显示，则需要以serial_no分组
    @Select("select id,venderid,order_sn as orderSn,user_id as userId,trade_no as tradeNo,child_trade_no as childTradeNo,invoice_id as invoiceId,serial_no as serialNo,invoice_type as invoiceType,invoice_price as invoicePrice,tax,phone,email,address,invoice_head as invoiceHead,invoice_code as invoiceCode,invoice_no as invoiceNo,taxnum,status,ctime,utime from t_order_invoice where invoice_id=#{id}")
    OrderInvoiceBean getById(@Param("id") String id);
    /**
     * 获取流水号集合
     */
    @Select("select serial_no serialNo from t_order_invoice where status=20 and DATE_SUB(CURDATE(), INTERVAL 7 DAY) <= ctime order by ctime desc")
    List<String> getSerialNoList();

    /**
     * 校验是否已开发票
     */
    @Select("select order_sn orderSn,invoice_type invoiceType,invoice_no invoiceNo from t_order_invoice where order_sn=#{orderSn} order by ctime desc limit 0,1")
    OrderInvoiceBean isHaveInvoice(@Param("orderSn") String orderSn);

    /**
     * 检验订单是否已完成
     */
    @Select("select count(1) from t_order_product where order_sn=#{orderSn} and order_state<4")
    int getCount(@Param("orderSn") String orderSn);
}

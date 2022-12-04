package com.hqtc.bms.config;

/**
 * description:常用类
 * Created by laiqingchuang on 18-7-3 .
 */
public class Constants {
    //身份认证(测试)
    public static final String IDENTITY_DEV="93363DCC6064869708F1F3C72A0CE72A713A9D425CD50CDE";
    //身份认证(正式)
    public static final String IDENTITY="";
    //销方税号(测试)
    public static final String SALETAXNUM_DEV="339901999999142";
    //销方税号(正式)
    public static final String SALETAXNUM="";
    //添加发票http地址(测试)
    public static final String INVOICE_ADD_URL_DEV = "https://nnfpdev.jss.com.cn/shop/buyer/allow/cxfKp/cxfServerKpOrderSync.action";
    //添加发票http地址(正式)
    public static final String INVOICE_ADD_URL = "https://nnfp.jss.com.cn/shop/buyer/allow/cxfKp/cxfServerKpOrderSync.action";
    //开票结果查询http地址(测试)
    public static final String INVOICE_GETRESULT_URL_DEV ="https://nnfpdev.jss.com.cn/shop/buyer/allow/ecOd/queryElectricKp.action";
    //开票结果查询http地址(正式)
    public static final String INVOICE_GETRESULT_URL ="https://nnfp.jss.com.cn/shop/buyer/allow/ecOd/queryElectricKp.action";
    //根据订单号查询发票请求流水号接口(测试)
    public static final String INVOICE_GETFPQQLSH_URL_DEV ="https://nnfpdev.jss.com.cn/shop/buyer/allow/ecOd/queryElectricKp.action";
    //根据订单号查询发票请求流水号接口(正式)
    public static final String INVOICE_GETFPQQLSH_URL ="https://nnfp.jss.com.cn/shop/buyer/allow/ecOd/queryElectricKp.action";
    //销方地址（必填）
    public static final String Saleaddress="北京市朝阳区安定路39号,长新大厦13层 环球天成科技（北京）有限公司";
    //销方电话（必填）
    public static final String Salephone="0571-87022168";
    //销方银行账户（可为空）
    public static final String Saleaccount="东亚银行杭州分行 131001088303400";
    //开票员（必填）
    public static final String Clerk="来庆闯";
    //收款人（可为空）
    public static final String Payee="王斌";
    //复合人（可为空）
    public static final String Checker="马宽";
    //备注
    public static final String Message="请前往诺诺网(www.jss.com.cn)查询发票详情";
    //清单标志（默认为 0，可为空）
    public static final String Qdbz="0";
    //单价含税标志，0：不含税，1：含税（必填）
    public static final String Hsbz="1";
    //发票行性质 :0,正常行;1,折扣行;2,被折扣行（必填）
    public static final String Fphxz="0";
    //优惠政策标识:0,不使用;1,使用（可为空）
    public static final String Yhzcbs="0";
    //税率（必填）
    public static final String Taxrate="0.16";
    //空
    public static final String NULL="";
    //推送方式
    public static final String TSFS="2";

}

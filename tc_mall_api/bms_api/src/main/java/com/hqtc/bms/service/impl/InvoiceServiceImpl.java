package com.hqtc.bms.service.impl;

import com.hqtc.bms.config.Constants;
import com.hqtc.bms.config.DESDZFP;
import com.hqtc.bms.config.HttpClientUtil;
import com.hqtc.bms.config.IdCreator;
import com.hqtc.bms.model.mapper.InvoiceBatchMapper;
import com.hqtc.bms.model.mapper.InvoiceMapper;
import com.hqtc.bms.model.mapper.TOrderMapper;
import com.hqtc.bms.model.params.InvoiceInfoVo;
import com.hqtc.bms.model.params.InvoiceManagerVo;
import com.hqtc.bms.model.params.InvoiceresultDto;
import com.hqtc.bms.model.params.OrderInfoResponseParams;
import com.hqtc.bms.model.params.OrderInvoiceBean;
import com.hqtc.bms.model.rpc.*;
import com.hqtc.bms.service.ImsAddressService;
import com.hqtc.bms.service.InvoiceService;
import com.hqtc.common.utils.DateUtil;
import com.hqtc.common.utils.JsonTools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * description:发票Impl
 * Created by laiqingchuang on 18-7-3 .
 */
@Service
public class InvoiceServiceImpl implements InvoiceService {
    @Autowired
    private InvoiceMapper invoiceMapper;
    @Autowired
    private InvoiceBatchMapper invoiceBatchMapper;
    @Autowired
    ImsAddressService imsAddressService;

    /**
     * 统一发送请求
     * @return
     */
    private String post(String sendUrl, String order) {
        BaseRequestBean bean = new BaseRequestBean();
        bean.setOrder(DESDZFP.encrypt(order));
        String json = HttpClientUtil.post(sendUrl, bean, "UTF-8", true);
        return json;
    }

    /**
     * 开发票
     */
    @Override
    public String addInvoice(InvoiceRequestBean requestbean) {
        InvoiceBaseRequestBean params=new InvoiceBaseRequestBean();

        params.setIdentity(Constants.IDENTITY_DEV);                    //身份认证
        requestbean.setSaletaxnum(Constants.SALETAXNUM_DEV);           //销方税号（必填）
        if(requestbean.getKptype() !=null && requestbean.getKptype().equals("1"))
            requestbean.setOrderno(IdCreator.getId(20));        //发票单号
        requestbean.setSaleaddress(Constants.Saleaddress);             //销方地址（必填）
        requestbean.setSalephone(Constants.Salephone);                 //销方电话（必填）
        requestbean.setSaleaccount(Constants.Saleaccount);             //销方银行账户（可为空）
        requestbean.setClerk(Constants.Clerk);                         //开票员（必填）
        requestbean.setPayee(Constants.Payee);                         //收款人（可为空）
        requestbean.setChecker(Constants.Checker);                     //复合人（可为空）
        requestbean.setInvoicedate(DateUtil.getSimpleDateFormat());    //单据时间（必填）
        requestbean.setMessage(Constants.Message);                     //备注
        requestbean.setQdbz(Constants.Qdbz);                           //清单标志（默认为 0，可为空）
        requestbean.setQdxmmc(Constants.NULL);                         //清单项目名称
        //推送方式 :-1, 不推送 ;0,邮箱 ;1,手 机(默认);2,邮箱、手机
        if(requestbean.getEmail()!=null && !requestbean.getEmail().equals("")) {
            requestbean.setTsfs("2");
        }
        for(InvoiceDetailRequestBean b:requestbean.getDetail()){
             b.setHsbz(Constants.Hsbz);                                //单价含税标志，0：不含税，1：含税（必填）
             b.setFphxz(Constants.Fphxz);                              //发票行性质 :0,正常行;1,折扣行;2,被折扣行（必填）
             b.setYhzcbs(Constants.Yhzcbs);                            //优惠政策标识:0,不使用;1,使用（可为空）
             b.setZzstsgl(Constants.NULL);                             //增值税特殊管理,如: 50即征即退、免税、简易征收等，当 yhzcbs 为1时,此项必填
             b.setLslbs(Constants.NULL);                               //零税率标识:空,非零 1税率;1,免 税; 2,不征税; 3,普通零税率（可为空）
        }

        params.setOrder(requestbean);
        String order =JsonTools.getInstance().objectToString(params);
        String sendUrl=Constants.INVOICE_ADD_URL_DEV;
        return post(sendUrl,order);
    }

    /**
     * 开票结果查询
     */
    @Override
    public String getInvoiceResult(InvoiceResultRequestBean requestbean) {
        requestbean.setIdentity(Constants.IDENTITY_DEV);

        String order =JsonTools.getInstance().objectToString(requestbean);
        String sendUrl=Constants.INVOICE_GETRESULT_URL_DEV;
        return post(sendUrl,order);
    }

    /**
     * 根据订单号查询发票请求流水号接口
     */
    @Override
    public String getFpqqlshByOrdeno(InvoiceOrdernoRequestBean requestbean) {
        requestbean.setIdentity(Constants.IDENTITY_DEV);

        String order =JsonTools.getInstance().objectToString(requestbean);
        String sendUrl=Constants.INVOICE_GETFPQQLSH_URL_DEV;
        return post(sendUrl,order);
    }

    /**
     * 获取开发票所需要的参数
     */
    @Override
    public InvoiceRequestBean getInvoiceParam(String orderSn, String invoiceHead, String kptype) {
        InvoiceRequestBean param = new InvoiceRequestBean();
        param.setOrderno(orderSn);

        //获取发票主档信息
        InvoiceRequestBean mainBean=invoiceMapper.getMainOrder(orderSn);
        param.setVenderId(mainBean.getVenderId());
        param.setUserId(mainBean.getUserId());
        param.setTradeNo(mainBean.getTradeNo());
        param.setKptype(kptype);
        param.setBuyername(invoiceHead);
        OrderInfoResponseParams addreBean=imsAddressService.getAddressInfo(mainBean.getCountyId(),mainBean.getTownId()).getData();
        param.setAddress(addreBean.getProvinceName()+addreBean.getCityName()+addreBean.getCountyName()+addreBean.getTownName()+mainBean.getDetailAddr());
        if(kptype.equals("2")){ //冲红时添加
            OrderInvoiceBean invoice=invoiceMapper.getInvoiceInfo(orderSn);
            param.setOrderno(invoice.getInvoiceId());
            param.setBuyername(invoice.getInvoiceHead());
            param.setPhone(invoice.getPhone());
            param.setEmail(invoice.getEmail());
            param.setTaxnum(invoice.getTaxnum());
            param.setFpdm(revert(invoice.getInvoiceCode(),12));  //对应蓝票发票代码（红票必填,不满12 位请左补 0）
            param.setFphm(revert(invoice.getInvoiceNo(),8));     //对应蓝票发票号码（红票必填,不满8位请左补 0）
        }

        //获取电子发票明细参数a
        List<InvoiceDetailRequestBean> invoieDetail;
        if(kptype.equals("1")){
            invoieDetail=invoiceMapper.getInvoiceDetail(orderSn);
        }else{
            invoieDetail=invoiceMapper.getInvoiceDetail2(orderSn);
        }
        List<InvoiceDetailRequestBean> detailList = new ArrayList<InvoiceDetailRequestBean>();
        if(invoieDetail !=null && invoieDetail.size() !=0){
            for(InvoiceDetailRequestBean bean:invoieDetail){
                if(kptype.equals("2")){
                    bean.setNum("-"+bean.getNum());
                }
                bean.setSpbm("");
                //移除数量为0的商品
                if(!bean.getNum().equals("0")){
                    detailList.add(bean);
                }
            }
        }
        param.setDetail(detailList);
        return param;
    }

    /**
     * 同步发票数据到数据库
     */
    @Override
    @Transactional
    public int saveInvoiceInfo(OrderInvoiceBean invoiceMain, List<InvoiceDetailRequestBean> invoiceDetail) {
        int main=invoiceMapper.saveInvoiceInfo(invoiceMain);
        int detail=invoiceBatchMapper.saveInvoiceDetail(invoiceMain.getSerialNo(),invoiceDetail);
        return main+detail;
    }

    @Override
    public List<InvoiceManagerVo> invoiceManagerList(Map<String, Object> params) {
        return invoiceMapper.invoiceManagerList(params);
    }

    @Override
    public int countInvoiceManagerList(Map<String, Object> params) {
        return invoiceMapper.countInvoiceManagerList(params);
    }

    /**
     * 获取流水号集合
     */
    @Override
    public List<String> getSerialNoList() {
        return invoiceMapper.getSerialNoList();
    }

    /**
     * 更新发票表数据
     */
    @Override
    public int updateOrderInvoice(List<InvoiceresultDto> list) {
        int num = invoiceBatchMapper.updateOrderInvoice(list);
        return num;
    }
    @Override
    public InvoiceInfoVo getInvoiceInfo(Long venderId) {
        return invoiceMapper.getInvoiceManagerInfo(venderId);
    }

    @Override
    public OrderInvoiceBean getById(String id) {
        return invoiceMapper.getById(id);
    }

    /**
     * 校验是否已开发票
     * @param orderSn
     */
    @Override
    public OrderInvoiceBean isHaveInvoice(String orderSn) {
        return invoiceMapper.isHaveInvoice(orderSn);
    }

    /**
     * 检验订单是否已完成
     */
    @Override
    public int getCount(String orderSn) {
        return invoiceMapper.getCount(orderSn);
    }

    private String revert(String fpdm,int sumleng) {
        int leng=fpdm.length();
        if(leng <sumleng){
            fpdm=ling(sumleng-leng) + fpdm;
        }
        return fpdm;
    }

    private String ling(int num) {
        String str="";
        for (int i = 0; i <num ; i++) {
            str+=0;
        }
        return str;
    }

}
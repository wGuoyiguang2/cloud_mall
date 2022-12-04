package com.hqtc.bms.controller;

import com.hqtc.bms.config.Router;
import com.hqtc.bms.model.params.*;
import com.hqtc.bms.model.response.Result;
import com.hqtc.bms.model.rpc.InvoiceDetailRequestBean;
import com.hqtc.bms.model.rpc.InvoiceOrdernoRequestBean;
import com.hqtc.bms.model.rpc.InvoiceRequestBean;
import com.hqtc.bms.model.rpc.InvoiceResultRequestBean;
import com.hqtc.bms.service.InvoiceService;
import com.hqtc.common.config.ErrorCode;
import com.hqtc.common.response.ResultData;
import com.hqtc.common.utils.JsonTools;
import com.hqtc.common.utils.Tools;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.*;
import static com.hqtc.common.utils.Tools.getThreadResultData;

/**
 * description:发票
 * Created by laiqingchuang on 18-7-3 .
 */
@RestController
public class InvoiceController {
    private static Logger log  = LoggerFactory.getLogger(InvoiceController.class);
    @Autowired
    InvoiceService invoiceService;

    /**
     * 开发票
     */
    @RequestMapping(value=Router.V1_INVOICE_ADD, method = RequestMethod.POST)
    public ResultData addInvoice(@RequestBody InvoiceRequestBean requestbean){
        ResultData result = getThreadResultData();
        String str=invoiceService.addInvoice(requestbean);
        result.setData(JsonTools.getInstance().stringToObject(str,InvoiceaddResponseParams.class));
        return result;
    }

    /**
     * 开票结果查询
     */
    @RequestMapping(value=Router.V1_INVOICE_GETRESULT, method = RequestMethod.POST)
    public ResultData getInvoiceResult(@RequestBody InvoiceResultRequestBean requestbean){
        ResultData result = getThreadResultData();
        String str=invoiceService.getInvoiceResult(requestbean);
        result.setData(JsonTools.getInstance().stringToObject(str,InvoiceresultResponseParams.class));
        return result;
    }

    /**
     * 根据订单号查询发票请求流水号接口
     */
    @RequestMapping(value=Router.V1_INVOICE_GETFPQQLSH, method = RequestMethod.POST)
    public ResultData getFpqqlshByOrdeno(@RequestBody InvoiceOrdernoRequestBean requestbean){
        ResultData result = getThreadResultData();
        String str=invoiceService.getFpqqlshByOrdeno(requestbean);
        result.setData(JsonTools.getInstance().stringToObject(str,InvoiceresultResponseParams.class));
        return result;
    }

    /**
     * 电子发票(简化版)
     */
    @RequestMapping(value=Router.V1_INVOICE_SAVE, method = RequestMethod.POST)
    public ResultData addInvoice(@RequestParam String orderSn,
                                 @RequestParam String kptype,
                                 @RequestParam(required=false,defaultValue = "") String invoiceHead,
                                 @RequestParam(required=false,defaultValue = "") String phone,
                                 @RequestParam(required=false,defaultValue = "") String email,
                                 @RequestParam(required=false,defaultValue = "") String taxnum){
        ResultData result = getThreadResultData();

        //参数校验
        if(kptype.equals("1") && phone.equals("")){
            result.setError(ErrorCode.MISS_PARAM);
            result.setMsg("缺少参数");
            return result;
        }

        //检验订单是否已完成
        if(invoiceService.getCount(orderSn)>0){
            result.setData("该订单未完成,不能开发票");
            return result;
        }

        //校验是否已开发票
        OrderInvoiceBean iBean=invoiceService.isHaveInvoice(orderSn);
        if(kptype.equals("1") && iBean !=null && iBean.getInvoiceType().equals(1) && iBean.getInvoiceNo()!=null){
            result.setData("您已开过发票");
            return result;
        }
        if(kptype.equals("2") && iBean !=null && iBean.getInvoiceType().equals(2) && iBean.getInvoiceNo()!=null){
            result.setData("您已红冲过该发票");
            return result;
        }

        //获取开发票所需要的参数
        InvoiceRequestBean requestbean=invoiceService.getInvoiceParam(orderSn,invoiceHead,kptype);
        if(requestbean.getDetail()==null || requestbean.getDetail().size()==0){
            result.setData("该订单没有符合开发票的商品");
            return result;
        }

        //红冲时由后台获取
        if(kptype.equals("1")){
            requestbean.setPhone(phone);
            requestbean.setEmail(email);
            requestbean.setTaxnum(taxnum);
        }

        //企业发票不填写地址
        if(requestbean.getTaxnum() !=null && !requestbean.getTaxnum().equals("")){
            requestbean.setAddress("");
        }

        //开发票
        String str=invoiceService.addInvoice(requestbean);
        Object o = JsonTools.getInstance().stringToObject(str, InvoiceaddResponseParams.class);
        Map<String,String> map=JsonTools.getInstance().convertObjToMap(o);

        if(map.get("message").equals("同步成功")){
            //根据发票流水获取发票结果
            String str1=invoiceService.getInvoiceResult(getInvoiceResultRequestBean(map));
            Object o1=JsonTools.getInstance().stringToObject(str1,InvoiceresultResponseParams.class);
            Map<String,List<InvoiceresultDto>> o2=JsonTools.getInstance().convertObjToMap(o1);
            InvoiceresultDto invoiceresult=o2.get("list").get(0);

            //同步发票数据到数据库
            OrderInvoiceBean invoiceMain = getOrderInvoiceBean(orderSn, kptype, requestbean, invoiceresult);
            List<InvoiceDetailRequestBean> invoiceDetail=requestbean.getDetail();
            int num=invoiceService.saveInvoiceInfo(invoiceMain,invoiceDetail);
        }
        result.setData(map.get("message"));
        return result;
    }

    /**
     * 根据发票流水获取发票结果参数
     */
    private InvoiceResultRequestBean getInvoiceResultRequestBean(Map<String,String> map) {
        InvoiceResultRequestBean invoiceResultRequestBean =new InvoiceResultRequestBean();
        ArrayList<String> fpqqlsh = new ArrayList<>();

        fpqqlsh.add(map.get("fpqqlsh"));
        invoiceResultRequestBean.setFpqqlsh(fpqqlsh);
        return invoiceResultRequestBean;
    }

    /**
     * 同步发票数据到数据库参数
     */
    private OrderInvoiceBean getOrderInvoiceBean(String orderSn,String kptype,InvoiceRequestBean requestbean,InvoiceresultDto invoiceresult) {
        OrderInvoiceBean orderInvoiceBean = new OrderInvoiceBean();
        orderInvoiceBean.setVenderid(Integer.valueOf(requestbean.getVenderId()));
        orderInvoiceBean.setUserId(requestbean.getUserId());
        orderInvoiceBean.setOrderSn(orderSn);                          //平台订单号码
        orderInvoiceBean.setTradeNo(requestbean.getTradeNo());         //京东订单号码
        orderInvoiceBean.setInvoiceId(invoiceresult.getC_orderno());
        orderInvoiceBean.setSerialNo(invoiceresult.getC_fpqqlsh());    //流水号
        orderInvoiceBean.setInvoiceType(Integer.valueOf(kptype));
        orderInvoiceBean.setInvoicePrice(invoiceresult.getC_bhsje());  //发票不含税价格
        orderInvoiceBean.setTax(invoiceresult.getC_hjse());            //合计税额
        orderInvoiceBean.setPhone(requestbean.getPhone());
        orderInvoiceBean.setEmail(requestbean.getEmail());
        orderInvoiceBean.setTaxnum(requestbean.getTaxnum());
        orderInvoiceBean.setAddress(requestbean.getAddress());
        orderInvoiceBean.setInvoiceHead(invoiceresult.getC_buyername());
        orderInvoiceBean.setInvoiceCode(invoiceresult.getC_fpdm());    //发票代码
        orderInvoiceBean.setInvoiceNo(invoiceresult.getC_fphm());      //发票号码
        orderInvoiceBean.setStatus(Integer.valueOf(invoiceresult.getC_status()));
        return orderInvoiceBean;

    }

    /**
     * 发票后台管理列表
     * @param params
     * @return
     */
    @PostMapping(Router.BMS_INVOICE_MANAGER_LIST)
    public Result<InvoiceManagerVo> orderManagerList(@RequestParam Map<String,Object> params) {
        Result<InvoiceManagerVo> resultData = new Result<>();
        List<InvoiceManagerVo> list = invoiceService.invoiceManagerList(params);
        int count = invoiceService.countInvoiceManagerList(params);
        resultData.setRows(list);
        resultData.setTotal(count);
        return resultData;
    }


    /**
     * 通过客户id获取发票信息
     * @param venderId
     * @return
     */
    @PostMapping(Router.BMS_INVOICE_INFO_MANAGER_GET)
    public InvoiceInfoVo getInvoiceInfo(@RequestParam("venderid") Long venderId) {
        InvoiceInfoVo invoiceInfo=invoiceService.getInvoiceInfo(venderId);
        return invoiceInfo;
    }

    @PostMapping(Router.BMS_INVOICE_MANAGER_REMOVE)
    public ResultData removeInvoices(@RequestParam("invoiceIds") String invoiceIds){
        ResultData resultData= Tools.getThreadResultData();
        int count=0;
        if(StringUtils.isEmpty(invoiceIds)){
            resultData.setMsg("invoiceIds 为空，请选择要撤销的发票！");
            resultData.setError(ErrorCode.PARAM_ERROR);
            return resultData;
        }
        List<String> invoiceList=Arrays.asList(invoiceIds.split("; <br> "));
        if(invoiceList!=null){
            for (String id:invoiceList) {
                try {
                    //通过id查询发票信息
                    OrderInvoiceBean bean=invoiceService.getById(id);
                    if(bean==null||bean.getInvoiceType()==2){
                        //如果bean不存在或者发票是红票则进行下次循环。
                        continue;
                    }
                    resultData=this.addInvoice(bean.getOrderSn(),"2",null,null,null,null);
                    if(resultData.getError()==0){
                        count++;
                    }
                }catch(Exception e){
                    e.printStackTrace();
                    log.info("撤销发票操作失败！发票id:"+id);
                }
            }
        }
        resultData.setData(count);
        return resultData;
    }
}
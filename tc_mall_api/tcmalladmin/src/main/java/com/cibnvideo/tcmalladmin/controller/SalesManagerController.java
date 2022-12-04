package com.cibnvideo.tcmalladmin.controller;

import com.cibnvideo.common.controller.BaseController;
import com.cibnvideo.common.utils.Result;
import com.cibnvideo.tcmalladmin.bmsapi.SalesApi;
import com.cibnvideo.tcmalladmin.jdsyncapi.ProductApi;
import com.cibnvideo.tcmalladmin.model.bean.ProductInfo;
import com.cibnvideo.tcmalladmin.model.bean.SalesAmountVo;
import com.cibnvideo.tcmalladmin.model.bean.SalesManagerProductDetailVo;
import com.cibnvideo.tcmalladmin.model.bean.SalesManagerVo;
import com.cibnvideo.tcmalladmin.omsapi.ProductRemoveApi;
import com.hqtc.common.config.ErrorCode;
import com.hqtc.common.response.ResultData;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description: 销量
 * @Author: WangBin
 * @Date: 2018/8/8 18:53
 */
@Controller
public class SalesManagerController extends BaseController {

    @Autowired
    private ProductApi productApi;
    @Autowired
    private ProductRemoveApi productRemoveApi;
    @Autowired
    private SalesApi salesApi;

    @GetMapping("/v1/tcmalladmin/sales/list")
    @RequiresPermissions("mall:sales:list")
    public String salesHtml(Model model) {
        Long venderId = this.getVenderId();
        //查询销售总金额和盈利金额
        ResultData<SalesAmountVo> resultData = salesApi.getSalesAmount(venderId);
        SalesAmountVo bean = resultData.getData();
        if(bean==null) {
            bean=new SalesAmountVo();
        }
        model.addAttribute("bean", bean);
        return "tcmalladmin/salesmanager/sales";
    }

    @GetMapping("/v1/tcmalladmin/sales/list0")
    @RequiresPermissions("mall:sales:list")
    @ResponseBody
    public Result<SalesManagerVo> salesList(@RequestParam Map<String, Object> params) {
        Long venderId = this.getVenderId();
        if (venderId == null) {
            return null;
        }
        Result<SalesManagerVo> result = new Result<>();
        params.put("venderId", venderId);
        /*if("0".equals((String)params.get("productStatus"))){
            List<SalesManagerVo> salesManagerVos=new ArrayList<>();
            //获取下架商品sku
            ResultData<DataList<List<ProductRemove>>> removeResultData = productRemoveApi.list(params);
            if(removeResultData!=null){
                List<ProductRemove> productRemoves=removeResultData.getData().getData();
                if(productRemoves!=null){
                    for (ProductRemove pr:productRemoves) {
                        SalesManagerVo vo=new SalesManagerVo();
                        Integer sku=pr.getSku();
                        vo.setProductId(sku);
                        //通过商品id获取商品名称等信息 bms
                        ResultData<SalesManagerVo> resultDataSalesVo=salesApi.getSalesManagerDetail(sku);
                        if(resultDataSalesVo.getData()!=null){
                            vo.setProductPrice(resultDataSalesVo.getData().getProductPrice());
                            vo.setProductName(resultDataSalesVo.getData().getProductName());
                            vo.setPayPrice(resultDataSalesVo.getData().getPayPrice());
                            vo.setCount(resultDataSalesVo.getData().getCount());
                            vo.setAgreePrice(resultDataSalesVo.getData().getAgreePrice());
                        }
                        //通过商品id获取品牌，分类等信息 oms
                        ResultData<SalesManagerProductDetailVo> detail = productApi.salesManagerProductDetail(vo.getProductId());
                        if (detail.getData() != null) {
                            vo.setBrandName(detail.getData().getBrandName());
                            vo.setCategory(detail.getData().getCategory());
                        }
                    }
                }
            }
        }else if("1".equals((String)params.get("productStatus"))||StringUtils.isNotEmpty((String)params.get("brandName"))||StringUtils.isNotEmpty((String)params.get("category"))){
            //通过品牌信息、分类获取sku,品牌信息，分类名称
            ResultData<DataList<List<SalesManagerVo>>> resultData=productApi.salesManagerProductDetailList(params);
            DataList<List<SalesManagerVo>> dataResultList=resultData.getData();
            List<SalesManagerVo> salesManagerVos=new ArrayList<>();
            if(dataResultList!=null){
                salesManagerVos=dataResultList.getData();
            }
            if(salesManagerVos!=null){
                for (SalesManagerVo vo:salesManagerVos) {
                    Integer sku=vo.getProductId();
                    //通过商品id获取上下架信息 oms
                    Map<String, Object> map = new HashMap<>();
                    map.put("sku", sku);
                    ResultData<Integer> resultD = productRemoveApi.count(map);
                    if (resultD.getData() != null) {
                        if (resultD.getData() > 0) {
                            //下架
                            vo.setProductStatus(0);
                        } else {
                            vo.setProductStatus(1);
                        }
                    }
                    //通过商品id获取商品名称等信息 bms
                    ResultData<SalesManagerVo> resultDataSalesVo=salesApi.getSalesManagerDetail(sku);
                    SalesManagerVo vo1=resultDataSalesVo.getData();
                    if(vo1!=null){
                        vo.setAgreePrice(vo1.getAgreePrice());
                        vo.setCount(vo1.getCount());
                        vo.setPayPrice(vo1.getPayPrice());
                        vo.setProductName(vo1.getProductName());
                        vo.setProductPrice(vo1.getProductPrice());
                    }
                }
            }
        }else{*/
        ResultData<Result<SalesManagerVo>> resultData = salesApi.salesManagerList(params);
        if(resultData.getError()!= ErrorCode.SUCCESS){
            return result;
        }
        result = resultData.getData();
        if (result != null) {
            List<SalesManagerVo> list = result.getRows();
            if (list != null) {
                for (SalesManagerVo vo : list) {
                    //通过商品ID获取分类、品牌信息
                    ResultData<SalesManagerProductDetailVo> detail = productApi.salesManagerProductDetail(vo.getProductId());
                    if (detail.getData() != null) {
                        vo.setBrandName(detail.getData().getBrandName());
                        vo.setCategory(detail.getData().getCategory());
                    }
                    //通过sku获取上下架信息,京东sync和oms的商品有一个下架即为下架
                    Map<String, Object> map = new HashMap<>();
                    map.put("sku", vo.getProductId());
                    ResultData<Integer> resultD = productRemoveApi.count(map);
                    List<Long> skuList=new ArrayList<>();
                    int status1=0;
                    int status2=0;
                    skuList.add(vo.getProductId());
                    ResultData<List<ProductInfo>> resultData1=productApi.getProductInfoBySkus(skuList);
                    List<ProductInfo> productInfoList=resultData1.getData();
                    if(productInfoList!=null&&productInfoList.size()>0){
                        status1=productInfoList.get(0).getState();
                    }
                    if (resultD.getData() != null) {
                        if(resultD.getData()>0){
                            status2=0;
                        }else{
                            status2=1;
                        }
                    }
                    vo.setProductStatus(status1&status2);
                }
            }
        }
        //}
        return result;
    }
}

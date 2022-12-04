package com.cibnvideo.tcmalladmin.controller;

import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.metadata.Sheet;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.cibnvideo.common.utils.MyStringUtils;
import com.cibnvideo.common.utils.Result;
import com.cibnvideo.tcmalladmin.jdsyncapi.CategoryApi;
import com.cibnvideo.tcmalladmin.jdsyncapi.ProductApi;
import com.cibnvideo.tcmalladmin.model.bean.CategoryResultInfo;
import com.cibnvideo.tcmalladmin.model.bean.ProductInfo;
import com.cibnvideo.tcmalladmin.model.bean.ProductRemove;
import com.cibnvideo.tcmalladmin.model.bean.SellPriceResult;
import com.cibnvideo.tcmalladmin.omsapi.ProductRemoveApi;
import com.cibnvideo.tcmalladmin.omsapi.VenderSettlementApi;
import com.hqtc.common.config.ErrorCode;
import com.hqtc.common.response.DataList;
import com.hqtc.common.response.ResultData;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class ProductDetailSearch {
    Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    ProductApi productApi;

    @Autowired
    CategoryApi categoryApi;

    @Autowired
    VenderSettlementApi venderSettlementApi;

    @Autowired
    ProductRemoveApi productRemoveApi;

    /**
     * 获取商品信息列表
     * @param venderId
     * @param params
     * @param pricePolicy 是否有大客户定价策略，没有时为京东批发价
     * @return
     */
    public Result getProductList(Integer venderId, Map<String, Object> params, boolean pricePolicy){
        Result<ProductInfo> result = new Result<ProductInfo>();
        if(pricePolicy) {
            if (params.containsKey("notin")) {
                List<Long> notin = (List<Long>) params.get("notin");
                notin.addAll(getRemoveProducts(venderId));
            } else {
                params.put("notin", getRemoveProducts(venderId));
            }
        }
        ResultData<DataList<List<ProductInfo>>> resultData = productApi.detailSearchList(params);
        if(resultData.getError() == ErrorCode.SUCCESS){
            DataList<List<ProductInfo>> productInfo = resultData.getData();
            result.setTotal(productInfo.getTotalRows());
            List<ProductInfo> productInfoList = productInfo.getData();
            for(ProductInfo info:productInfoList){
                String categorys = getCategoryNameByIds(info.getCategory());
                if(!StringUtils.isEmpty(categorys)){
                    info.setCategory(categorys);
                }
                if(pricePolicy){
                    ResultData<SellPriceResult> priceResults = venderSettlementApi.getPrice(venderId.intValue(), info.getSku());
                    if(priceResults.getError() == ErrorCode.SUCCESS){
                        SellPriceResult r = priceResults.getData();
                        if(r!=null){
                            info.setPrice(r.getPrice());
                            info.setTradePrice(r.getTradePrice());
                        }
                    }
                } else {
                    //没有定价策略时，批发价等于零售价
                    info.setTradePrice(info.getPrice());
                }
            }
            result.setRows(productInfo.getData());
            return result;
        }else{
            result.setTotal(0);
            result.setRows(new ArrayList<ProductInfo>());
            return result;
        }
    }

    public String getCategoryNameByIds(String categoryIds){
        String result = null;
        if(!StringUtils.isEmpty(categoryIds)){
            String[] cats =categoryIds.split(";");
            Integer[] catIds = MyStringUtils.StringToInt(cats);
            String[] categoryName = new String[3];
            ResultData<List<CategoryResultInfo>> resultData1Cats = categoryApi.categoryListByCatIds(catIds);
            if(resultData1Cats.getError() == ErrorCode.SUCCESS){
                List<CategoryResultInfo> categoryResultInfoList = resultData1Cats.getData();
                int index = 0;
                for(CategoryResultInfo r:categoryResultInfoList){
                    categoryName[index] = r.getName();
                    ++index;
                }
            }
            result = StringUtils.join(categoryName, ";");
        }
        return result;
    }

    public void exportExcel(HttpServletResponse response, Map<String, Object> params, Integer venderId, boolean pricePolicy) {
        try(OutputStream out = response.getOutputStream()) {
            response.setHeader("content-Type", "application/vnd.ms-excel");
            response.setHeader("Content-Disposition", "attachment;filename="+URLEncoder.encode("商品列表.xlsx", "utf-8"));
            ExcelWriter writer = new ExcelWriter(out, ExcelTypeEnum.XLSX);
            writeData(writer, params, venderId, pricePolicy);
            writer.finish();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    List<Long> getRemoveProducts(Integer venderId){
        ResultData<List<Long>> resultData = productRemoveApi.listSkusByVenderId(venderId);
        if(resultData.getError() == ErrorCode.SUCCESS){
            return resultData.getData();
        }
        return null;
    }

    private void writeData(ExcelWriter excelWriter, Map<String, Object> params, Integer venderId, boolean pricePolicy) {
        Sheet sheet1 = new Sheet(1, 0,ProductInfo.class);
        Map columnWidth = new HashMap();
        //columnWidth.put(8,6500);
        sheet1.setColumnWidthMap(columnWidth);
        int pageSize = 200;
        int total = 1;
        for(int offset = 0;offset< total;) {
            params.put("offset", offset);
            params.put("limit", pageSize);
            Result<ProductInfo> result = getProductList(venderId, params, pricePolicy);
            if(result != null && result.getTotal() > 0) {
                total = result.getTotal();
                excelWriter.write(result.getRows(), sheet1);
            }
            offset = offset + pageSize;
            logger.info("export num = " + offset);
        }
    }
}

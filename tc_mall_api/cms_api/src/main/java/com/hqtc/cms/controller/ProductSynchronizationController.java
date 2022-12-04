package com.hqtc.cms.controller;

import com.hqtc.cms.config.Router;
import com.hqtc.cms.model.bean.*;
import com.hqtc.cms.model.service.JdSyncService;
import com.hqtc.cms.model.service.SearchService;
import com.hqtc.common.config.ErrorCode;
import com.hqtc.common.response.ResultData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import static com.hqtc.common.utils.Tools.getThreadResultData;

/**
 * description:全量同步商品信息接口
 * Created by laiqingchuang on 19-1-10 .
 */
@RestController
public class ProductSynchronizationController {
    
    @Value("${service.config.synchronizationPageSize}")
    private Integer defaultPageSize;
    @Value("${service.config.synchronizationMaxPageSize}")
    private Integer maxPageSize;
    @Autowired
    JdSyncService jdSyncService;
    @Autowired
    SearchService searchService;

    @RequestMapping(value = Router.ROUTE_PRODUCT_SYNCLIST, method = RequestMethod.GET)
    public ResultData synchronization(@RequestParam Integer venderId,
                                      @RequestParam(required = false) Integer pageNum,
                                      @RequestParam(required = false) Integer pageSize){
        ResultData resultData = getThreadResultData();
        if(pageSize !=null && pageSize>maxPageSize){
            resultData.setError(ErrorCode.FALI);
            resultData.setMsg("分页数据过多");
            return resultData;
        }
        ProductListBean data = new ProductListBean();
        pageSize = (pageSize == null || pageSize <= 0) ? defaultPageSize: pageSize;
        pageNum = (pageNum == null || pageNum <= 0) ? 1: pageNum;

        //获取所有商品sku和价格
        ProductListBean<List<PriceBean>> product = searchService.syncSearch(venderId, pageNum, pageSize).getData();
        if(product==null || product.getTotal()==0){
            resultData.setData(data);
            return resultData;
        }

        //获取商品明细
        String skus="";
        Map<Long, BigDecimal> map = new HashMap<>();
        for(PriceBean bean: product.getDataList()) {
            skus += bean.getSku() + ",";
            map.put(bean.getSku(),bean.getPrice());
        }
        List<ProductSynchronizationBean> detailList = jdSyncService.getProductDetailList(skus.substring(0,skus.length() - 1)).getData();
        if(detailList==null || detailList.size()==0){
            resultData.setData(data);
            return resultData;
        }

        //添加商品价格
        for(ProductSynchronizationBean bean:detailList){
            if(map.get(bean.getSku()) !=null){
                bean.setPrice(map.get(bean.getSku()));
            }
        }

        data.setTotal(product.getTotal());
        data.setDataList(detailList);
        resultData.setData(data);
        return resultData;
    }

}
package com.hqtc.bms.service.impl;

import com.google.gson.Gson;
import com.hqtc.bms.model.mapper.TOrderProductMapper;
import com.hqtc.bms.model.params.SalesAmountVo;
import com.hqtc.bms.model.params.SalesManagerVo;
import com.hqtc.bms.model.params.SkuSearchParamsBean;
import com.hqtc.bms.model.rpc.ProductPriceBean;
import com.hqtc.bms.model.rpc.ProductVenderPriceBean;
import com.hqtc.bms.model.rpc.ProductsOriginInfoBean;
import com.hqtc.bms.service.ProductService;
import com.hqtc.bms.service.rpc.RPCOmsService;
import com.hqtc.bms.service.rpc.RPCProductInfoService;
import com.hqtc.bms.service.rpc.RPCSearchService;
import com.hqtc.common.config.ErrorCode;
import com.hqtc.common.response.ResultData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;

/**
 * Created by wanghaoyang on 18-7-4.
 */
@Service("ProductServiceImpl")
public class ProductServiceImpl implements ProductService {

    private Logger logger = LoggerFactory.getLogger("ProductServiceImpl");

    @Autowired
    private RPCProductInfoService productInfoService;

    @Autowired
    private RPCOmsService rpcOmsService;

    @Autowired
    private TOrderProductMapper productMapper;

    @Autowired
    private RPCSearchService rpcSearchService;

    @Override
    public boolean checkProductIds(List<String> productIds){
        return true;
    }

    @Override
    public List<ProductsOriginInfoBean> getOriginProductsInfo(List<String> productIds){
        ResultData<List<ProductsOriginInfoBean>> resultData = productInfoService.getHomeRecommend(String.join(",", productIds));
        return resultData.getData();
    }

    @Override
    public List<ProductVenderPriceBean> getProductVenderPrice(int venderId, List<String> productIds){
        ResultData<List<ProductVenderPriceBean>> resultData = rpcOmsService.getBatchPrice((long)venderId, productIds);
        return resultData.getData();
    }

    @Override
    public Map<Long, ProductPriceBean> getProductPrice(int venderId, List<String> productIds){
        List<ProductsOriginInfoBean> productsOriginInfoBeans = this.getOriginProductsInfo(productIds);
        if(null == productsOriginInfoBeans || productsOriginInfoBeans.isEmpty()){
            return null;
        }
        List<ProductVenderPriceBean> productVenderPriceBeans = this.getProductVenderPrice(venderId, productIds);
        if(null == productVenderPriceBeans || productVenderPriceBeans.isEmpty()){
            return null;
        }
        Map<Long, ProductPriceBean> map = new HashMap<>(productIds.size());
        for(ProductsOriginInfoBean productsOriginInfoBean :productsOriginInfoBeans){
            if(!map.containsKey(productsOriginInfoBean.getSku())){
                ProductPriceBean productPriceBean = new ProductPriceBean();
//                productPriceBean.setAgree_price(productsOriginInfoBean.getPrice());
                productPriceBean.setName(productsOriginInfoBean.getName());
                map.put(productsOriginInfoBean.getSku(), productPriceBean);
            }
        }
        for(ProductVenderPriceBean productVenderPriceBean:productVenderPriceBeans){
            ProductPriceBean productPriceBean = map.get(productVenderPriceBean.getSkuId());
            productPriceBean.setPrice(productVenderPriceBean.getPrice());
            productPriceBean.setAgree_price(productVenderPriceBean.getTradePrice());
        }
        return map;
    }

    /**
     * 大客户收益=（支付金额_包含运费-退款金额_不含运费）-（总协议价-退款的协议价和）-大客户支付给京东的运费总和
     * @param venderId
     * @return
     */
    @Override
    public SalesAmountVo getSalesAmount(Long venderId) {
        SalesAmountVo salesAmountVo=productMapper.getSalesAmount(venderId);
        if(salesAmountVo!=null){
            //从oms查询大客户支付给京东的总邮费
            ResultData<BigDecimal> resultData=rpcOmsService.sumFreeFreighPrice(venderId.intValue());
            BigDecimal sumFreight=(resultData.getData()==null?new BigDecimal(0):resultData.getData());
            salesAmountVo.setGainsAmount(salesAmountVo.getGainsAmount().subtract(sumFreight));
            salesAmountVo.setSumFreight(sumFreight);
        }
        return salesAmountVo;
    }

    @Override
    public List<SalesManagerVo> salesManagerList(Map<String, Object> params) {
        return productMapper.salesManagerList(params);
    }

    @Override
    public int countSalesManager(Map<String, Object> params) {
        return productMapper.countSalesManager(params);
    }

    @Override
    public SalesManagerVo getSalesManagerVo(Long sku) {
        return productMapper.getSalesManagerVo(sku);
    }

    @Override
    public Map<Long, Integer> getProductState(int venderId , List<String> productIds){
        ResultData resultData = this.searchProductInfo(venderId, String.join(",", productIds));
        if(null == resultData || resultData.getError() != ErrorCode.SUCCESS){
            return null;
        }
        List<Map> products = (List<Map>)((Map)resultData.getData()).get("dataList");
        if(null == products || products.isEmpty()){
            return null;
        }
        Map<Long, Integer> map = new HashMap<>(productIds.size());
        for(Map info :products){
            map.put(Long.parseLong(info.get("sku").toString()), (int)info.get("state"));
        }
        return map;
    }

    public ResultData searchProductInfo(int venderId, String skus){
        return rpcSearchService.searchProduct(venderId, skus, 1, 50);
    }
}

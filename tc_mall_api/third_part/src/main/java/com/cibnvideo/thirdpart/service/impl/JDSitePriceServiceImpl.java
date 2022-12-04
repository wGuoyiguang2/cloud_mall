package com.cibnvideo.thirdpart.service.impl;

import com.cibnvideo.thirdpart.config.BaseConfig;
import com.cibnvideo.thirdpart.feign.OmsFeign;
import com.cibnvideo.thirdpart.feign.SearchFeign;
import com.cibnvideo.thirdpart.model.bean.*;
import com.cibnvideo.thirdpart.model.mapper.JDSitePriceMapper;
import com.cibnvideo.thirdpart.service.JDSitePriceService;
import com.hqtc.common.config.ErrorCode;
import com.hqtc.common.response.ResultData;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @Description:
 * @Author: WangBin
 * @Date: 2019/1/21 17:23
 */
@Service
public class JDSitePriceServiceImpl implements JDSitePriceService {
    private Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    private OmsFeign omsFeign;
    @Autowired
    private SearchFeign searchFeign;
    @Autowired
    private BaseConfig baseConfig;
    @Autowired
    private JDSitePriceMapper jdSitePriceMapper;

    /**
     * 将京东网站的商品价格同步到数据库
     *
     * @param jdSitePriceList 京东网站商品价格列表
     * @return
     * @throws Exception
     */
    @Override
    public List<JDSitePriceBean> saveJDSitePrice(List<SyncJDSitePriceResponse> jdSitePriceList){
        List<JDSitePriceBean> jdSitePriceBeanList = new ArrayList<>();
        for (SyncJDSitePriceResponse syncJDSitePriceResponse : jdSitePriceList) {
            String price = syncJDSitePriceResponse.getP();
            String sku = syncJDSitePriceResponse.getId();
            sku = sku.substring(sku.indexOf("_") + 1);
            JDSitePriceBean jdSitePriceBean = new JDSitePriceBean();
            jdSitePriceBean.setSku(Long.valueOf(sku));
            jdSitePriceBean.setPrice(new BigDecimal(price));
            jdSitePriceBeanList.add(jdSitePriceBean);
        }
        this.save(jdSitePriceBeanList);
        return jdSitePriceBeanList;
    }

    /**
     * 比较京东网站价格，对指定大客户的商品进行上下架操作
     * @param jdSitePriceBeanList
     */
    @Override
    public void syncProductStatus(List<JDSitePriceBean> jdSitePriceBeanList) {
        StringBuilder sb=new StringBuilder();
        for (JDSitePriceBean jdSitePriceBean:jdSitePriceBeanList) {
            sb.append(jdSitePriceBean.getSku()).append(",");
        }
        String skus=sb.toString();
        skus=skus.substring(0,skus.length()-1);
        String vendorIds = baseConfig.getVendorIds();
        List<String> vendorList = Arrays.asList(vendorIds.split(","));
        for (String vendor : vendorList) {
            try {
                if(StringUtils.isEmpty(vendor)){
                    continue;
                }
                //从消息队列获取jdSitePriceBeanList列表
                //上架商品列表
                List<ProductRemove> addProductList = new ArrayList<>();
                //下架商品列表
                List<ProductRemove> removeProductList = new ArrayList<>();
                //调用search接口批量获取大客户商品价格
                List<ProductBean> productList = this.searchProductPrice(skus, vendor,baseConfig.getPerCount());
                if(productList==null||productList.isEmpty()){
                    logger.error("从search批量获取大客户商品价格为空！");
                    continue;
                }
                if(productList.size()<jdSitePriceBeanList.size()){
                    logger.error("从search服务大客户商品不全！");
                }
                //比较大客户商品价格和京东网站价格，从而对大客户商品进行上下架操作
                Map<Long, BigDecimal> priceMap = productList.stream().collect(Collectors.toMap(ProductBean::getSku, priceBean -> priceBean.getPrice()));
                Map<Long, BigDecimal> jdSitePriceMap = jdSitePriceBeanList.stream().collect(Collectors.toMap(JDSitePriceBean::getSku, priceBean -> priceBean.getPrice()));
                for (Map.Entry<Long, BigDecimal> p : priceMap.entrySet()) {
                    Long skuId=p.getKey();
                    BigDecimal price=p.getValue();
                    if(price==null||BigDecimal.ZERO.compareTo(price)>0){
                        logger.warn("从搜索服务查询商品sku:"+skuId+"的价格为空或价格小于0！");
                        continue;
                    }
                    BigDecimal jdPrice = jdSitePriceMap.get(skuId);
                    if(jdPrice==null||BigDecimal.ZERO.compareTo(jdPrice)>0){
                        logger.warn("从第三方查询商品sku:"+skuId+"的价格为空或价格小于0！");
                        continue;
                    }
                    if (price.compareTo(jdPrice) > 0) {
                        //添加下架列表
                        ProductRemove productRemove = new ProductRemove();
                        productRemove.setVenderId(Integer.valueOf(vendor));
                        productRemove.setSku(skuId);
                        removeProductList.add(productRemove);
                    } else {
                        //添加上架列表
                        ProductRemove productRemove = new ProductRemove();
                        productRemove.setVenderId(Integer.valueOf(vendor));
                        productRemove.setSku(skuId);
                        addProductList.add(productRemove);
                    }
                }
                //调用批量下架接口
                if (removeProductList != null && !removeProductList.isEmpty()) {
                    ResultData removeResultData = omsFeign.removeProducts(removeProductList);
                    if (removeResultData.getError() != ErrorCode.SUCCESS) {
                        logger.error("对大客户" + vendor + "商品进行批量下架失败：" + removeResultData.getMsg());
                    }
                }
                //调用批量上架接口
                if (addProductList != null && !addProductList.isEmpty()) {
                    ResultData addResultData = omsFeign.addProducts(addProductList);
                    if (addResultData.getError() != ErrorCode.SUCCESS) {
                        logger.error("对大客户" + vendor + "商品进行批量上架失败:" + addResultData.getMsg());
                    }
                }
            } catch (Exception e) {
                logger.error("同步京东网站价格，对大客户" + vendor + "的商品进行上下架操作失败：" + e.getMessage());
            }
        }
    }

    /**
     * 从搜索服务获取大客户商品价格
     *
     * @param skus
     * @param vendor
     * @return
     * @throws Exception
     */
    private List<ProductBean> searchProductPrice(String skus, String vendor,Integer pageSize) throws Exception {
        ResultData<ProductListBean<List<ProductBean>>> priceResultData = searchFeign.getBatchPrice(skus, Integer.valueOf(vendor),pageSize);
        if (priceResultData.getError() != ErrorCode.SUCCESS) {
            throw new Exception("从search批量获取价格失败:" + priceResultData.getMsg());
        }
        ProductListBean<List<ProductBean>> data = priceResultData.getData();
        if (data == null || data.getDataList() == null || data.getDataList().isEmpty()) {
            throw new Exception("从search批量获取价格为空！");
        }
        List<ProductBean> productList = data.getDataList();
        return productList;
    }
    @Override
    public int save(List<JDSitePriceBean> jdSitePriceBeanList) {
        return jdSitePriceMapper.add(jdSitePriceBeanList);
    }

    @Override
    public List<JDSitePriceBean> listJDSitePrice(List<Long> skuList) {
        return jdSitePriceMapper.listJDSitePrice(skuList);
    }
}

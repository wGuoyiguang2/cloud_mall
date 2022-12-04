package com.cibnvideo.jdsync.controller;

import com.cibnvideo.jdsync.bean.*;
import com.cibnvideo.jdsync.config.Router;
import com.cibnvideo.jdsync.dao.JdProductDetailDao;
import com.cibnvideo.jdsync.dao.JdProductPriceDao;
import com.hqtc.common.response.DataList;
import com.hqtc.common.response.ResultData;
import com.hqtc.common.utils.Tools;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@RestController
public class JdProductDetailController {

    private final String REMOVED_SKUS = "removedSkus";

    @Autowired
    private JdProductDetailDao jdProductDetailDao;


    @Autowired
    private JdProductPriceDao jdProductPriceDao;

    @ApiOperation(value = "批量获取商品添加时间", notes = "根据sku列表批量获取商品添加时间")
    @PostMapping(Router.GOODS_PRODUCT_CTIME_GET)
    public ResultData<List<ProductCtime>> getProductCtime(@RequestBody List<Long> skus) {
        ResultData<List<ProductCtime>> resultData = Tools.getThreadResultData();
        List<ProductCtime> productCtimeList = jdProductDetailDao.getProductCtime(skus);
        resultData.setData(productCtimeList);
        return resultData;
    }

    @ApiOperation(value = "批量获取商品简单信息", notes = "根据sku列表批量获取商品简单信息")
    @PostMapping(Router.V1_JDSYNC_PRODUCT_DETAILINFO_BY_SKUS)
    public ResultData<List<ProductInfo>> getProductInfoBySkus(@RequestBody List<Long> skus) {
        ResultData<List<ProductInfo>> resultData = Tools.getThreadResultData();
        List<ProductInfo> productInfoList = jdProductDetailDao.getProductInfoBySkus(skus);
        resultData.setData(productInfoList);
        return resultData;
    }

    @ApiOperation(value = "根据筛选条件获取商品简单信息(admin接口)", notes = "根据筛选条件获取商品简单信息，供admin管理后台使用")
    @GetMapping(Router.V1_JDSYNC_PRODUCT_DETAILINFOLIST)
    public ResultData getProductDetailInfoList(@RequestParam Map<String, Object> params) {
        ResultData<DataList<List<ProductInfo>>> resultData = Tools.getThreadResultData();
        DataList<List<ProductInfo>> result = Tools.getThreadDataList();
        result.setTotalRows(jdProductDetailDao.count(params));
        result.setData(jdProductDetailDao.listinfo(params));
        resultData.setData(result);
        return resultData;
    }
    @ApiOperation(value = "根据筛选条件获取商品简单信息(api接口)", notes = "根据筛选条件获取商品简单信息，供api使用")
    @PostMapping(Router.V1_JDSYNC_PRODUCT_DETAILINFOSEARCH)
    public ResultData searchProductDetailInfoList(@RequestBody Map<String, Object> params) {
        ResultData<DataList<List<ProductInfo>>> resultData = Tools.getThreadResultData();
        DataList<List<ProductInfo>> result = Tools.getThreadDataList();
        result.setTotalRows(jdProductDetailDao.searchcount(params));
        result.setData(jdProductDetailDao.searchinfo(params));
        resultData.setData(result);
        return resultData;
    }

    @ApiOperation(value = "根据sku获取商品简单信息", notes = "根据sku获取商品简单信息")
    @GetMapping(Router.V1_JDSYNC_PRODUCT_DETAILINFO)
    public ResultData getProductDetailInfo(@RequestParam Long sku) {
        ResultData<ProductInfo> resultData = Tools.getThreadResultData();
        resultData.setData(jdProductDetailDao.getinfo(sku));
        return resultData;
    }

    @ApiOperation(value = "批量获取商品简单信息", notes = "根据sku列表批量获取商品简单信息")
    @GetMapping(Router.V1_JDSYNC_PRODUCT_DETAILINFOBYSKUS)
    @ResponseBody
    public ResultData listProductDetailInfo(@RequestParam String skus) {
        ResultData<List<ProductInfo>> resultData = Tools.getThreadResultData();
        String[] skuArray = skus.split(",");
        List<ProductInfo> productInfoList = jdProductDetailDao.listInfo(skuArray);
        resultData.setData(productInfoList);
        return resultData;
    }

    @ApiOperation(value = "根据筛选条件获取商品全部信息", notes = "根据筛选条件获取商品全部信息")
    @GetMapping(Router.V1_JDSYNC_PRODUCT_DETAILLIST)
    public ResultData getProductDetailList(@RequestParam Map<String, Object> params) {
        ResultData<DataList<List<ProductResult>>> resultData = Tools.getThreadResultData();
        DataList<List<ProductResult>> result = Tools.getThreadDataList();
        result.setTotalRows(jdProductDetailDao.count(params));
        result.setData(jdProductDetailDao.list(params));
        resultData.setData(result);
        return resultData;
    }

    @ApiOperation(value = "根据sku获取商品全部信息", notes = "根据sku获取商品全部信息")
    @GetMapping(Router.V1_JDSYNC_PRODUCT_DETAIL)
    public ResultData getProductDetail(@RequestParam Long sku) {
        ResultData<ProductResult> resultData = Tools.getThreadResultData();
        resultData.setData(jdProductDetailDao.get(sku));
        return resultData;
    }

    @ApiOperation(value = "根据sku列表批量获取商品价格", notes = "根据sku列表批量获取商品价格")
    @PostMapping(Router.V1_JDSYNC_PRODUCT_BATCH_PRICE)
    public ResultData getBatchPrice(@RequestBody List<Long> skus) {
        ResultData<List<SellPriceResult>> result = Tools.getThreadResultData();
        List<SellPriceResult> sellPriceResults = jdProductPriceDao.getPriceBatch(skus);
        result.setData(sellPriceResults);
        return result;

    }

    @ApiOperation(value = "根据sku获取商品价格", notes = "根据sku获取商品价格")
    @PostMapping(Router.V1_JDSYNC_PRODUCT_PRICE)
    public ResultData getPrice(@RequestBody Long sku) {
        ResultData<SellPriceResult> result = Tools.getThreadResultData();
        SellPriceResult sellPriceResult = jdProductPriceDao.get(sku);
        result.setData(sellPriceResult);
        return result;
    }

    @ApiOperation(value = "根据分类ID获取品牌", notes = "根据分类ID及移除的sku获取分类下品牌")
    @GetMapping(Router.V1_JDSYNC_PRODUCT_BRANDNAME_BY_CAT)
    public ResultData getBrandNamesByCat2(@RequestParam Map<String, Object> params) {
        ResultData<List<String>> result = Tools.getThreadResultData();
        List<String> skuList = new ArrayList<>();
        if (StringUtils.isNotEmpty((String) params.get(REMOVED_SKUS))) {
            String skus = (String) params.get(REMOVED_SKUS);
            String[] split = skus.split(",");
            skuList = Arrays.asList(split);
        }
        params.put(REMOVED_SKUS, skuList);
        result.setData(jdProductDetailDao.getBrandNamesByCat2(params));
        return result;
    }

    @ApiOperation(value = "根据sku列表获取品牌列表", notes = "根据sku列表获取相关品牌列表")
    @PostMapping(Router.V1_JDSYNC_PRODUCT_BRANDNAME_BY_SKUS)
    public ResultData getBrandNameBySkus(@RequestBody List<Long> skus) {
        ResultData<List<String>> result = Tools.getThreadResultData();
        List<String> brandNames = jdProductDetailDao.getBrandNamesBySkus(skus);
        result.setData(brandNames);
        return result;
    }

    @ApiOperation(value = "根据sku获取商品分类及品牌", notes = "根据sku获取商品分类及品牌")
    @GetMapping(Router.V1_JDSYNC_SALES_MANAGER__PRODUCT_DETAIL)
    public ResultData<SalesManagerProductDetailVo> getSalesManagerProductDetail(@RequestParam("sku") Long sku) {
        ResultData resultData = Tools.getThreadResultData();
        SalesManagerProductDetailVo vo = jdProductDetailDao.getSalesManagerProductDetail(sku);
        resultData.setData(vo);
        return resultData;
    }

    @PostMapping(Router.V1_JDSYNC_SALES_MANAGER_LIST)
    public ResultData<DataList<List<SalesManagerVo>>> listSalesManager(@RequestParam Map<String, Object> params) {
        ResultData resultData = Tools.getThreadResultData();
        DataList dataList = Tools.getThreadDataList();
        List<SalesManagerVo> list = jdProductDetailDao.listSalesManager(params);
        int total = jdProductDetailDao.countSalesManager(params);
        dataList.setData(list);
        dataList.setTotalRows(total);
        resultData.setData(dataList);
        return resultData;
    }

    @ApiOperation(value = "根据sku获取ES所需商品信息", notes = "根据sku获取ES所需商品信息")
    @GetMapping(Router.V1_JDSYNC_PRODUCT_ES_GET)
    public ResultData<ProductEsBean> listProductForEs(@RequestParam Long sku) {
        ResultData resultData = Tools.getThreadResultData();
        resultData.setData(jdProductDetailDao.getProductForEs(sku));
        return resultData;
    }

    @ApiOperation(value = "根据筛选条件获取ES所需商品信息", notes = "根据筛选条件获取ES所需商品信息")
    @PostMapping(Router.V1_JDSYNC_PRODUCT_ES_LIST)
    public ResultData<DataList<List<ProductEsBean>>> listProductForEs(@RequestBody Map<String, Object> params) {
        ResultData resultData = Tools.getThreadResultData();
        DataList dataList = Tools.getThreadDataList();
        List<ProductEsBean> list = jdProductDetailDao.listProductForEs(params);
        int total = jdProductDetailDao.countProductForEs(params);
        dataList.setData(list);
        dataList.setTotalRows(total);
        resultData.setData(dataList);
        return resultData;
    }

    @ApiOperation(value = "根据sku更新商品短视频", notes = "根据sku更新商品短视频")
    @GetMapping(Router.V1_JDSYNC_PRODUCT_VIDEO_ADD)
    public ResultData<Integer> addVideo(@RequestParam("sku") Long sku, @RequestParam("videoPath") String videoPath) {
        ResultData resultData = Tools.getThreadResultData();
        ProductVideo productVideo = jdProductDetailDao.getProductVideo(sku);
        if(productVideo != null){
            resultData.setData(jdProductDetailDao.updateProductVideo(sku, videoPath));
        }else {
            resultData.setData(jdProductDetailDao.saveProductVideo(sku, videoPath));
        }
        return resultData;
    }

    @ApiOperation(value = "获取商品详情列表", notes = "获取商品详情列表")
    @PostMapping(Router.V1_JDSYNC_PRODUCT_DETAIL_LIST)
    public ResultData productDetailList(@RequestParam Map<String, Object> params) {
        ResultData resultData = Tools.getThreadResultData();
        resultData.setData(jdProductDetailDao.productDetailList(params));
        return resultData;
    }

    @ApiOperation(value = "获取商品总数量", notes = "获取商品总数量")
    @GetMapping(Router.V1_JDSYNC_PRODUCT_COUNT)
    public ResultData<Integer> getCount(@RequestParam Map<String, Object> params){
        ResultData<Integer> resultData=Tools.getThreadResultData();
        int count = jdProductDetailDao.count(params);
        resultData.setData(count);
        return resultData;
    }
    @ApiOperation(value = "获取上架商品sku列表", notes = "获取上架商品sku列表")
    @GetMapping(Router.V1_JDSYNC_PRODUCT_SKULIST)
    public ResultData<String> getSkus(@RequestParam Map<String, Object> params){
        ResultData<String> resultData=Tools.getThreadResultData();
        String skus=jdProductDetailDao.getSkus(params);
        resultData.setData(skus);
        return resultData;
    }
}

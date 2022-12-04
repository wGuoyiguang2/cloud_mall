package com.hqtc.cms.model.service;

import com.hqtc.cms.config.Router;
import com.hqtc.cms.fallback.OmsFallbackFactory;
import com.hqtc.cms.model.bean.*;
import com.hqtc.cms.model.bean.search.ProductRemove;
import com.hqtc.common.config.FeignClientService;
import com.hqtc.common.response.DataList;
import com.hqtc.common.response.ResultData;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

/**
 * description:oms服务
 * Created by laiqingchuang on 18-7-16 .
 */
@FeignClient(name =FeignClientService.OMSAPI,fallbackFactory = OmsFallbackFactory.class)
public interface OmsService {

    /**
     * 获取商品集列表
     * @param venderId
     * @return
     */
    @RequestMapping(method = RequestMethod.GET, value = Router.V1_OMS_PRODUCTCOLLECTION_LIST)
    ResultData<Result<ProductCollectionBean>> getProductcollectionList(@RequestParam("venderId") Integer venderId);

    /**
     * 根据id获取单个商品集
     * @param id
     * @return
     */
    @RequestMapping(method = RequestMethod.GET, value = Router.V1_OMS_PRODUCTCOLLECTION_GET)
    ResultData<ProductCollectionBean> getProductcollectionById(@RequestParam("id") Integer id);

    /**
     * 根据商品集id获取商品列表
     * @param
     * @return
     */
    @RequestMapping(method = RequestMethod.GET, value = Router.V1_OMS_PRODUCTOFCOLLECTION_LIST)
    ResultData<Result<ProductOfCollectionBean>> getProductlistById(@RequestParam("collectionId") Integer collectionId);

    /**
     * 根据商品集id获取品牌列表
     * @param collectionId
     * @return
     */
    @RequestMapping(method = RequestMethod.GET,value = Router.V1_OMS_PRODUCTCOLLECTION_BRANDNAMES)
    ResultData<List<String>> getBrandNameById(@RequestParam("collectionId") Integer collectionId,
                                              @RequestParam("removeSkus") String removeSkus);

    @GetMapping(value = Router.V1_OMS_PRODUCT_REMOVE_LIST)
    ResultData<DataList<List<ProductRemove>>> getRemovedProducts(@RequestParam("venderid") Long venderId);

    /**
     * 根据venderid获取移除sku
     */
    @GetMapping(value = Router.V1_OMS_PRODUCT_REMOVESKUS)
    ResultData<List<Long>> getRemoveSkus(@PathVariable("venderId") Integer venderId);

    /**
     * 获取批量价格
     */
    @PostMapping(value=Router.V1_OMS_PRODUCT_COLLECTION_BATCHPRICE)
    ResultData<List<PriceBean>> getBatchPrice(@PathVariable("venderId") Integer venderId,@RequestBody List<Long> skus);

    /**
     * 获取价格
     */
    @PostMapping(value=Router.V1_OMS_PRODUCT_COLLECTION_PRICE)
    ResultData<PriceBean> getPrice(@PathVariable("venderId") Integer venderId,@RequestBody Long sku);

    /**
     * 获取分类图片
     */
    @PostMapping(value=Router.V1_OMS_CATEGORY_PICTURE_BY_VENDERID)
    ResultData<Map<Integer,CategoryPictureBean>> getCategoryPicture(@PathVariable("venderId") Integer venderId,@RequestBody List<Integer> catIds);

    /**
     * 热门搜索
     */
    @PostMapping(value=Router.V1_OMS_PRODUCT_HOTSEARCH)
    ResultData<List<String>> getHotSearch(@RequestParam("venderId") Integer venderId);

}

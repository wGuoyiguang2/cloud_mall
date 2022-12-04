package com.hqtc.cms.model.service;

import com.hqtc.cms.config.Router;
import com.hqtc.cms.fallback.JdSearchFallbackFactory;
import com.hqtc.cms.model.bean.PriceBean;
import com.hqtc.cms.model.bean.PriceStateBean;
import com.hqtc.cms.model.bean.ProductBean;
import com.hqtc.cms.model.bean.ProductListBean;
import com.hqtc.common.config.FeignClientService;
import com.hqtc.common.response.ResultData;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import java.util.List;
import java.util.Map;

/**
 * Created by makuan on 18-7-5.
 */
@FeignClient(name = FeignClientService.SEARCH,fallbackFactory = JdSearchFallbackFactory.class)
public interface SearchService {

    @RequestMapping(value = Router.ROUTE_SEARCH_PRODUCT_SEARCH, method = RequestMethod.POST)
    ResultData<ProductListBean<List<ProductBean>>> searchProduct(@RequestParam("venderId") Long venderId,
                                                                   @RequestParam("keyword") String keyword,
                                                                   @RequestParam("brandName") String brandName,
                                                                   @RequestParam("cat0") Integer cat0,
                                                                   @RequestParam("cat1") Integer cat1,
                                                                   @RequestParam("cat2") Integer cat2,
                                                                   @RequestParam("pageNum") Integer pageNum,
                                                                   @RequestParam("pageSize") Integer pageSize,
                                                                   @RequestParam("isNew") Integer isNew,
                                                                   @RequestParam("isSales") Integer isSales,
                                                                   @RequestParam("isPrice") Integer isPrice);

    /**
     * 获取分类catId集合
     */
    @RequestMapping(value = Router.ROUTE_SEARCH_PRODUCT_CATELIST, method = RequestMethod.POST)
    ResultData<List<Integer>> getCatlist(@RequestParam("venderId") Integer venderId,
                                         @RequestParam("catClass") String catClass,
                                         @RequestParam("parentId") Integer parentId);

    /**
     * 根据sku搜索接口
     */
    @RequestMapping(value = Router.ROUTE_SEARCH_PRODUCT_SKUS, method = RequestMethod.POST)
    ResultData<ProductListBean<List<ProductBean>>> idsQuery(@RequestParam("venderId") Integer venderId,
                                                      @RequestParam("skus") String skus,
                                                      @RequestParam("state") Integer state,
                                                      @RequestParam("brandName") String brandName,
                                                      @RequestParam("pageNum") Integer pageNum,
                                                      @RequestParam("pageSize") Integer pageSize);

    /**
     * 根据venderId搜索商品价格
     */
    @RequestMapping(value = Router.ROUTE_SEARCH_PRODUCT_SEARCH, method = RequestMethod.POST)
    ResultData<ProductListBean<List<PriceBean>>> syncSearch(@RequestParam("venderId") Integer venderId,
                                                            @RequestParam("pageNum") Integer pageNum,
                                                            @RequestParam("pageSize") Integer pageSize);

    /**
     * 根据分类获取品牌
     */
    @RequestMapping(value = Router.ROUTE_SEARCH_BRANDLIST, method = RequestMethod.POST)
    ResultData<List<String>> brandListByCat(@RequestParam("venderId") Integer venderId,
                                            @RequestParam("cat0") Integer cat0,
                                            @RequestParam("cat1") Integer cat1,
                                            @RequestParam("cat2") Integer cat2,
                                            @RequestParam("limit") Integer limit);
    /**
     * 搜索品牌列表
     */
    @RequestMapping(value = Router.ROUTE_SEARCH_BRANDLIST, method = RequestMethod.POST)
    ResultData<List<String>> brandListBySearch(@RequestParam("venderId") Integer venderId,
                                               @RequestParam("cat0") Integer cat0,
                                               @RequestParam("cat1") Integer cat1,
                                               @RequestParam("cat2") Integer cat2,
                                               @RequestParam("keyword") String keyword,
                                               @RequestParam("limit") Integer limit);

    /**
     * 根据商品集id获取品牌
     */
    @RequestMapping(value = Router.ROUTE_SEARCH_BRANDLIST, method = RequestMethod.POST)
    ResultData<List<String>> brandListByCollectionId(@RequestParam("venderId") Integer venderId,
                                                     @RequestParam("collectionId") Integer collectionId,
                                                     @RequestParam("limit") Integer limit);

    @RequestMapping(value = Router.ROUTE_SEARCH_PRODUCT_SEARCH, method = RequestMethod.POST)
    ResultData<Map<String,Object>> collectionProduct(@RequestParam("venderId") Integer venderId,
                                                     @RequestParam("collectionId") Integer collectionId,
                                                     @RequestParam("brandName") String brandName,
                                                     @RequestParam("pageNum") Integer pageNum,
                                                     @RequestParam("pageSize") Integer pageSize);

    @RequestMapping(value = Router.ROUTE_SEARCH_PRODUCT_SKUS, method = RequestMethod.POST)
    ResultData<ProductListBean<List<PriceStateBean>>> idsQuery(@RequestParam("venderId") Integer venderId,
                                                               @RequestParam("skus") String skus,
                                                               @RequestParam("pageNum") Integer pageNum,
                                                               @RequestParam("pageSize") Integer pageSize);
}

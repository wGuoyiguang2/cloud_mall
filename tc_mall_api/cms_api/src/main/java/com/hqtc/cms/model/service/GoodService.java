package com.hqtc.cms.model.service;

import com.hqtc.cms.fallback.GoodFallbackFactory;
import com.hqtc.cms.model.bean.*;
import com.hqtc.common.config.FeignClientService;
import com.hqtc.common.response.ResultData;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import com.hqtc.cms.config.Router;
import java.util.List;

/**
 * description:商品库接口调用
 * Created by laiqingchuang on 18-6-22 .
 */
@FeignClient(name =FeignClientService.JDSYNC,fallbackFactory = GoodFallbackFactory.class)
public interface GoodService {

    /**
     * 获取分类列表
     * @param
     * @return
     */
    @RequestMapping(method = RequestMethod.GET, value = Router.V1_JDSYNC_CATEGORY_LIST)
    ResultData<Result<GoodClassificationBean>> getClassificationList(@RequestParam("catId") Integer catId,
                                     @RequestParam("parentId") Integer parentId,
                                     @RequestParam("name") String name,
                                     @RequestParam("catClass") String catClass,
                                     @RequestParam("state") String state,
                                     @RequestParam("offset") Integer offset,
                                     @RequestParam("limit") Integer limit);

    /**
     * 根据分类获取商品列表
     * @param
     * @return
     */
    @RequestMapping(method = RequestMethod.GET, value = Router.V1_JDSYNC_PRODUCT_DETAILINFOLIST)
    ResultData<Result<GoodDetailinfoListBean>> getGoodDetailinfoList(@RequestParam("sku") Long sku,
                                             @RequestParam("name") String name,
                                             @RequestParam("brandName") String brandName,
                                             @RequestParam("state") String state,
                                             @RequestParam("category") String category,
                                             @RequestParam("cat0") Integer cat0,
                                             @RequestParam("cat1") Integer cat1,
                                             @RequestParam("cat2") Integer cat2,
                                             @RequestParam("offset") Integer offset,
                                             @RequestParam("limit") Integer limit,
                                             @RequestParam("sort") String sort,
                                             @RequestParam("order") String order);

    /**
     * 商品详情
     * @param
     * @return
     */
    @RequestMapping(method = RequestMethod.GET, value = Router.V1_JDSYNC_PRODUCT_DETAILINFO)
    ResultData<GoodDetailinfoBean> getGoodDetailinfo(@RequestParam("sku") Long sku);


    /**
     * 一级分类对应的所有二三级分类
     * @param
     * @return
     */
    @RequestMapping(method = RequestMethod.GET, value = Router.V1_JDSYNC_CATEGORY_LIST)
    ResultData<Result<GoodClassificationCatBean>> getGoodClassificationCatList(@RequestParam("parentId") Integer parentId,
                                                                               @RequestParam("catClass") Integer catClass,
                                                                               @RequestParam("state") String state);

    /**
     * 根据三级分类获取品牌
     * @param
     * @return
     */
    @RequestMapping(method = RequestMethod.GET, value = Router.V1_JDSYNC_PRODUCT_BRANKNAME)
    ResultData<List<String>> getBranknamebycat(@RequestParam("keyword") String keyword,
                                                @RequestParam("cat0") Integer cat0,
                                               @RequestParam("cat1") Integer cat1,
                                               @RequestParam("cat2") Integer cat2,
                                               @RequestParam("removedSkus") String removedSkus,
                                               @RequestParam("state") String state,
                                               @RequestParam("offset") Integer offset,
                                               @RequestParam("limit") Integer limit);

    /**
     * 获取指定商品pc端详情图片接口
     * @param
     * @return
     */
    @RequestMapping(method = RequestMethod.GET, value = Router.V1_JDSYNC_PICTURE_PC)
    ResultData<PicturePcBean> getPicturePc(@RequestParam("sku") Long sku);

    /**
     * 获取指定商品移动端详情图片接口
     * @param
     * @return
     */
    @RequestMapping(method = RequestMethod.GET, value = Router.V1_JDSYNC_PICTURE_MOBILE)
    ResultData<StyleMobileBean> getPictureMobile(@RequestParam("sku") Long sku);

    /**
     * 获取指定商品pc端样式接口
     * @param
     * @return
     */
    @RequestMapping(method = RequestMethod.GET, value = Router.V1_JDSYNC_STYLE_PC)
    ResultData<StylePcBean> getStylePc(@RequestParam("sku") Long sku);

    /**
     * 获取指定商品移动端样式接口
     * @param
     * @return
     */
    @RequestMapping(method = RequestMethod.GET, value = Router.V1_JDSYNC_STYLE_MOBILE)
    ResultData<StyleMobileBean> getStyleMobile(@RequestParam("sku") Long sku);

    /**
     * 根据sku列表批量获取商品信息
     * @param
     * @return
     */
    @RequestMapping(method = RequestMethod.GET, value = Router.V1_JDSYNC_PRODUCT_DETAILINFOBYSKUS)
    ResultData<List<GoodDetailinfoListBean>> getDetailinfobyskus(@RequestParam("skus") String skus);

    /**
     * 根据sku列表批量获取商品信息
     * @param
     * @return
     */
    @RequestMapping(method = RequestMethod.POST, value = Router.V1_JDSYNC_PRODUCT_DETAILINFOSEARCH)
    ResultData<Result<GoodDetailinfoListBean>> getProductListSearch(@RequestBody ProductSearchBean requestBean);

    /**
     * 根据catId获取分类列表
     * @param
     * @return
     */
    @RequestMapping(method = RequestMethod.POST, value ="/v1/jdsync/category/listbycatids")
    ResultData<List<GoodClassificationBean>> getAllCategoryByCatId(@RequestBody Integer[] catIds);

}
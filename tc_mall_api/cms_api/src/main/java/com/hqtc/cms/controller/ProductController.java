package com.hqtc.cms.controller;

import com.hqtc.cms.config.Router;
import com.hqtc.cms.model.bean.*;
import com.hqtc.cms.model.service.JdService;
import com.hqtc.cms.model.service.OmsService;
import com.hqtc.cms.model.service.SearchService;
import com.hqtc.common.config.ErrorCode;
import com.hqtc.common.response.ResultData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.util.*;
import static com.hqtc.common.utils.Tools.getThreadResultData;

/**
 * description:商品信息
 * Created by laiqingchuang on 18-6-22 .
 */
@RestController
public class ProductController {
    private Logger logger  = LoggerFactory.getLogger(getClass());

    @Value("${service.config.limit}")
    private Integer limit;
    @Value("${service.config.pageSize}")
    private Integer defaultPageSize;
    @Autowired
    OmsService omsService;
    @Autowired
    JdService jdService;
    @Autowired
    SearchService searchService;

    /**
     * 根据分类获取商品列表
     * @param
     * @return
     */
    @RequestMapping(value=Router.ROUTE_PRODUCT_LIST, method = RequestMethod.GET)
    @Cacheable(value = "cms", key = "'product_list:venderId:' +#venderId +':cat0:' +#cat0 +" +
            "':cat1:' +#cat1 + ':cat2:' +#cat2 + ':brandName:' +#brandName + ':isNew:' +#isNew +" +
            "':isSales:' +#isSales + ':isPrice:' +#isPrice + ':pageSize:' +#pageSize + ':pageNum:' +#pageNum",
            cacheManager = "CmsCacheManager")
    public ResultData getGoodDetailinfoList(@RequestParam Integer venderId,
                                            @RequestParam(defaultValue = "") String brandName,
                                            @RequestParam(required=false) Integer cat0,
                                            @RequestParam(required=false) Integer cat1,
                                            @RequestParam(required=false) Integer cat2,
                                            @RequestParam(required=false) Integer pageNum,
                                            @RequestParam(required=false) Integer pageSize,
                                            @RequestParam(required=false) Integer isNew,
                                            @RequestParam(required=false) Integer isSales,
                                            @RequestParam(required=false) Integer isPrice){
        ResultData result = getThreadResultData();
        Map<String, Object> map = new LinkedHashMap<>();
        pageSize = (pageSize == null || pageSize <= 0) ? defaultPageSize: pageSize;
        pageNum = (pageNum == null || pageNum <= 0) ? 1: pageNum;

        //调用search服务得到符合条件商品
        ResultData<ProductListBean<List<ProductBean>>> product = searchService.searchProduct(Long.valueOf(String.valueOf(venderId)), null, brandName, cat0, cat1, cat2, pageNum, pageSize, isNew, isSales,isPrice);
        if(product.getError()== ErrorCode.SERVER_EXCEPTION){
            logger.error(product.getMsg());
        }
        if(product.getData()==null){
            map.put("totalRows",0);
            map.put("dataList",new ArrayList<>());
            map.put("brandList",new ArrayList<>());
        }else{
            map.put("totalRows",product.getData().getTotal());
            map.put("dataList",product.getData().getDataList());
            map.put("brandList",searchService.brandListByCat(venderId,cat0,cat1,cat2,limit).getData());
        }
        result.setData(map);
        return result;
    }

    /**
     * 单独根据分类获取品牌列表
     * @param
     * @return
     */
    @RequestMapping(value=Router.ROUTE_PRODUCT_BRANDLIST, method = RequestMethod.GET)
    @Cacheable(value = "cms", key = "'brand_list:venderId:' +#venderId + ':cat0:' +#cat0 +" +
            "':cat1:' +#cat1 + ':cat2:' +#cat2", cacheManager = "CmsCacheManager")
    public ResultData getbrandList(@RequestParam("venderId") Integer venderId,
                                   @RequestParam(required=false) Integer cat0,
                                   @RequestParam(required=false) Integer cat1,
                                   @RequestParam(required=false) Integer cat2){
        ResultData<List<String>> result = searchService.brandListByCat(venderId, cat0, cat1, cat2, limit);
        if(result.getError()== ErrorCode.SERVER_EXCEPTION){
            logger.error(result.getMsg());
        }
        return result;
    }

    /**
     * 热门搜索
     */
    @RequestMapping(value=Router.ROUTE_PRODUCT_HOTSEARCH, method = RequestMethod.GET)
    @Cacheable(value = "cms", key = "'hot_search:venderId:' +#venderId", cacheManager = "CmsCacheManager")
    public ResultData getHotSearch(@RequestParam("venderId") Integer venderId){
        ResultData<List<String>> result = omsService.getHotSearch(venderId);
        if(result.getError() ==ErrorCode.SERVER_EXCEPTION){
            logger.error(result.getMsg());
        }
        return result;
    }

    /**
     * 查询同类商品
     * @param
     * @return
     */
    @RequestMapping(value=Router.ROUTE_PRODUCT_SIMILARGOODS, method = RequestMethod.GET)
    public ResultData getSimilarGoods(SimilarRequestBean requestBean){
        ResultData result = getThreadResultData();
        requestBean.setSkuId(requestBean.getSku());
        Map<String,SimilarGoodsResponseBean> similarGoods = jdService.getSimilarGoods(requestBean);
        if(similarGoods==null || (similarGoods!=null && similarGoods.get("jd_biz_product_getSimilarSku_response").getResult()==null)){
            result.setData(new ArrayList<>());
            return result;
        }
        result.setData(similarGoods.get("jd_biz_product_getSimilarSku_response").getResult());
        return result;
    }

    /**
     * 查询商品延保
     */
    @RequestMapping(value=Router.ROUTE_PRODUCT_WARRANTY, method = RequestMethod.GET)
    public ResultData getWarrantyList(WarrantyRequestBean requestBean){
        ResultData result = getThreadResultData();
        Map<String, WarrantyResponseBean> warranty = jdService.getWarrantyList(requestBean);
        if(warranty==null || (warranty!=null && warranty.get("biz_product_yanbao_sku_query_response").getResult()==null)){
            result.setData(new HashMap<>());
            return result;
        }
        result.setData(warranty.get("biz_product_yanbao_sku_query_response").getResult());
        return result;
    }

}
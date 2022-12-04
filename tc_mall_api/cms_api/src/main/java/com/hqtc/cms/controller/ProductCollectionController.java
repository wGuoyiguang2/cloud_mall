package com.hqtc.cms.controller;

import com.hqtc.cms.config.Router;
import com.hqtc.cms.model.bean.ProductCollectionBean;
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
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import static com.hqtc.common.utils.Tools.getThreadResultData;

/**
 * description:商品集
 * Created by laiqingchuang on 19-1-23 .
 */
@RestController
public class ProductCollectionController {
    private Logger logger  = LoggerFactory.getLogger(getClass());
    @Value("${service.config.limit}")
    private Integer limit;
    @Value("${service.config.pageSize}")
    private Integer defaultPageSize;
    @Autowired
    SearchService searchService;
    @Autowired
    OmsService omsService;

    /**
     * 根据商品集合id获取商品列表
     * @param
     * @return
     */
    @RequestMapping(value=Router.ROUTE_PRODUCTOFCOLLECTION_LIST, method = RequestMethod.GET)
    @Cacheable(value = "cms", key = "'set_list:venderId:' +#venderId + ':id:' +#id + ':brandName:' +#brandName +" +
            "':pageSize:' +#pageSize + ':pageNum:' +#pageNum", cacheManager = "CmsCacheManager")
    public ResultData getProductofcollectionList(@RequestParam Integer venderId,
                                                 @RequestParam Integer id,
                                                 @RequestParam(required=false,defaultValue = "") String brandName,
                                                 @RequestParam(value="pageSize",required = false) Integer pageSize,
                                                 @RequestParam(value="pageNum",required = false) Integer pageNum){
        ResultData result = getThreadResultData();
        Map<String, Object> map = new LinkedHashMap<>();
        pageSize = (pageSize == null || pageSize <= 0) ? defaultPageSize: pageSize;
        pageNum = (pageNum == null || pageNum <= 0) ? 1: pageNum;

        brandName= brandName.equals("全部") || "".equals(brandName) ? null : brandName;
        ResultData<Map<String, Object>> product = searchService.collectionProduct(venderId, id, brandName, pageNum, pageSize);
        if(product.getError() ==ErrorCode.SERVER_EXCEPTION){
            logger.error(product.getMsg());
        }
        if(product.getData() ==null){
            map.put("totalRows",0);
            map.put("dataList",new ArrayList<>());
            map.put("productcollection",null);
            map.put("brandList",new ArrayList<>());
        }else{
            map.put("totalRows",product.getData().get("total"));
            map.put("dataList",product.getData().get("dataList"));
            map.put("productcollection",this.getcollection(id));
            map.put("brandList",this.getBrandList(venderId,id));
        }
        result.setData(map);
        return result;
    }

    private ProductCollectionBean getcollection(Integer id) {
        ResultData<ProductCollectionBean> collection = omsService.getProductcollectionById(id);
        if(collection.getError() ==ErrorCode.SERVER_EXCEPTION){
            logger.error(collection.getMsg());
        }
        return collection.getData();
    }

    private List<String> getBrandList(Integer venderId, Integer id) {
        List<String> newList = new ArrayList<>();
        ResultData<List<String>> listResultData = searchService.brandListByCollectionId(venderId, id, limit);
        if(listResultData.getError() ==ErrorCode.SERVER_EXCEPTION){
            logger.error(listResultData.getMsg());
        }
        List<String> data = listResultData.getData();
        if(data ==null || data.size()==0){
            return newList;
        }
        newList.add("全部");
        for(String brand:data){
            newList.add(brand);
        }
        return newList;
    }

}
package com.hqtc.cms.controller;

import com.hqtc.cms.config.Router;
import com.hqtc.cms.model.bean.CategoryPictureBean;
import com.hqtc.cms.model.bean.GoodClassificationBean;
import com.hqtc.cms.model.bean.GoodClassificationCatBean;
import com.hqtc.cms.model.service.GoodService;
import com.hqtc.cms.model.service.OmsService;
import com.hqtc.cms.model.service.SearchService;
import com.hqtc.common.config.ErrorCode;
import com.hqtc.common.response.ResultData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.util.*;
import static com.hqtc.common.utils.Tools.getThreadResultData;

/**
 * description:商品分类
 * Created by laiqingchuang on 19-1-23 .
 */
@RestController
public class ProductCatController {
    private Logger logger  = LoggerFactory.getLogger(getClass());

    @Autowired
    GoodService goodService;
    @Autowired
    OmsService omsService;
    @Autowired
    SearchService searchService;

    /**
     * 获取分类列表
     * @param
     * @return
     */
    @RequestMapping(value=Router.ROUTE_PRODUCT_CATE_LIST, method = RequestMethod.GET)
    @Cacheable(value = "cms", key = "'cate_list:venderId:' +#venderId + ':parentId:' +#parentId +" +
            "':catId:' +#catId + ':catClass:' +#catClass",cacheManager = "CmsCacheManager")
    public ResultData getClassificationList(@RequestParam("venderId") Integer venderId ,
                                            @RequestParam(required=false) Integer catId,
                                            @RequestParam(required=false) Integer parentId,
                                            @RequestParam String catClass){

        ResultData result = getThreadResultData();
        Map<String, Object> map = new HashMap<>();
        List<GoodClassificationBean> list = listSort(this.getCategory(venderId, catClass, parentId));
        if(list==null || list.size()==0){
            map.put("totalRows",0);
            map.put("data",new ArrayList<>());
            result.setData(map);
            return result;
        }
        List<Integer> catIds = new ArrayList<>();
        for(GoodClassificationBean bean: list){
            catIds.add(bean.getCatId());
        }
        ResultData<Map<Integer, CategoryPictureBean>> categoryPicture = omsService.getCategoryPicture(venderId, catIds);
        if(categoryPicture.getError()== ErrorCode.SERVER_EXCEPTION){
            logger.error(categoryPicture.getMsg());
        }
        Map<Integer, CategoryPictureBean> picture = categoryPicture.getData();
        if(list.size() !=0 && !picture.isEmpty()){
            for(GoodClassificationBean bean: list){
                CategoryPictureBean pictureBean=picture.get(bean.getCatId());
                if(pictureBean !=null){
                    bean.setIcon(pictureBean.getIcon());
                    bean.setPicture(pictureBean.getPicture());
                    bean.setBackground(pictureBean.getBackground());
                }
            }
        }
        map.put("totalRows",list.size());
        map.put("data",list);
        result.setData(map);
        return result;
    }

    /**
     * 一级分类对应的所有二三级分类
     * @param
     * @return
     */
    @RequestMapping(value=Router.ROUTE_PRODUCT_CATE1_LIST, method = RequestMethod.GET)
    @Cacheable(value = "cms", key = "'cate1_list:venderId:' +#venderId + ':catId:' +#catId",
            cacheManager = "CmsCacheManager")
    public ResultData getClassificationList(@RequestParam("venderId") Integer venderId,
                                            @RequestParam Integer catId,
                                            @RequestParam(required=false) String state){
        ResultData result = getThreadResultData();
        List<GoodClassificationBean> list = listSort(this.getCategory(venderId, "1", catId));
        List<GoodClassificationCatBean> twoList = new ArrayList<GoodClassificationCatBean>();
        if(list !=null && list.size() !=0){
            for(GoodClassificationBean bean: list){
                GoodClassificationCatBean catBean = new GoodClassificationCatBean();
                catBean.setCatId(bean.getCatId());
                catBean.setParentId(bean.getParentId());
                catBean.setName(bean.getName());
                catBean.setCatClass(bean.getCatClass());
                catBean.setState(bean.getState());
                twoList.add(catBean);
            }
        }
        if(twoList !=null && twoList.size() !=0){
            for(GoodClassificationCatBean bean :twoList){
                List<GoodClassificationBean> threeList=listSort(this.getCategory(venderId, "2", bean.getCatId()));
                if(threeList !=null && threeList.size() !=0){
                    List<Integer> catIds = new ArrayList<>();
                    for(GoodClassificationBean three: threeList){
                        catIds.add(three.getCatId());
                    }
                    ResultData<Map<Integer, CategoryPictureBean>> categoryPicture = omsService.getCategoryPicture(venderId, catIds);
                    if(categoryPicture.getError()== ErrorCode.SERVER_EXCEPTION){
                        logger.error(categoryPicture.getMsg());
                    }
                    Map<Integer, CategoryPictureBean> picture =categoryPicture.getData();
                    if(threeList.size() !=0 && !picture.isEmpty()){
                        for(GoodClassificationBean three: threeList){
                            CategoryPictureBean pictureBean=picture.get(three.getCatId());
                            if(pictureBean !=null){
                                three.setIcon(pictureBean.getIcon());
                                three.setPicture(pictureBean.getPicture());
                                three.setBackground(pictureBean.getBackground());
                            }
                        }
                    }
                }
                bean.setList(threeList);
            }
        }

        result.setData(twoList);
        return result;
    }

    private List<GoodClassificationBean> getCategory(Integer venderId, String catClass, Integer parentId) {
        ResultData<List<Integer>> result= searchService.getCatlist(venderId,catClass,parentId);
        if(result.getError()== ErrorCode.SERVER_EXCEPTION){
            logger.error(result.getMsg());
        }
        List<Integer> list=result.getData();
        if(list !=null && list.size()!=0){
            Integer[] ints = new Integer[list.size()];
            list.toArray(ints);
            return goodService.getAllCategoryByCatId(list.toArray(ints)).getData();
        }
        return new ArrayList<>();
    }

    private List<GoodClassificationBean> listSort(List<GoodClassificationBean> list) {
        if(list !=null && list.size() !=0){
            Collections.sort(list, new Comparator<GoodClassificationBean>() {
                public int compare(GoodClassificationBean u1, GoodClassificationBean u2) {
                    return new Double(u1.getCatId()).compareTo(new Double(u2.getCatId())); //升序
                }
            });
        }
        return list;
    }

}
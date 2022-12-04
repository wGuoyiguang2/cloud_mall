package com.cibnvideo.oms.tcmallcustomer.controller;

import com.cibnvideo.oms.config.Router;
import com.cibnvideo.oms.tcmallcustomer.bean.*;
import com.cibnvideo.oms.tcmallcustomer.service.CategoryPictureService;
import com.hqtc.common.response.DataList;
import com.hqtc.common.response.ResultData;
import com.hqtc.common.utils.Tools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RestController
public class CategoryPictureController {

    @Autowired
    private CategoryPictureService categoryPictureService;

    @RequestMapping(value = Router.V1_OMS_CATEGORY_PICTURE_LIST, method = RequestMethod.POST)
    ResultData list(@RequestBody Map<String, Object> params){
        ResultData resultData = Tools.getThreadResultData();
        List<CategoryPicture> categoryPictureList = categoryPictureService.list(params);
        int count = categoryPictureService.count(params);
        DataList<List<CategoryPicture>> result = Tools.getThreadDataList();
        result.setData(categoryPictureList);
        result.setTotalRows(count);
        resultData.setData(result);
        return resultData;
    }

    @RequestMapping(value = Router.V1_OMS_CATEGORY_PICTURE_COUNT, method = RequestMethod.GET)
    ResultData count(@RequestParam Map<String, Object> params){
        ResultData<Integer> resultData = Tools.getThreadResultData();
        int count = categoryPictureService.count(params);
        resultData.setData(count);
        return resultData;
    }

    @RequestMapping(value = Router.V1_OMS_CATEGORY_PICTURE_GET, method = RequestMethod.GET)
    ResultData get(@RequestParam Integer id){
        ResultData<CategoryPicture> resultData = Tools.getThreadResultData();
        resultData.setData(categoryPictureService.get(id));
        return resultData;
    }


    @RequestMapping(value = Router.V1_OMS_CATEGORY_PICTURE_ADD, method = RequestMethod.POST)
    ResultData add(@RequestBody CategoryPicture categoryPicture){
        ResultData resultData = Tools.getThreadResultData();
        resultData.setData(categoryPictureService.save(categoryPicture));
        return resultData;
    }

    @RequestMapping(value = Router.V1_OMS_CATEGORY_PICTURE_UPDATE, method = RequestMethod.POST)
    ResultData update(@RequestBody CategoryPicture categoryPicture){
        ResultData<Integer> resultData = Tools.getThreadResultData();
        resultData.setData(categoryPictureService.update(categoryPicture));
        return resultData;
    }

    @RequestMapping(value = Router.V1_OMS_CATEGORY_PICTURE_REMOVE, method = RequestMethod.GET)
    ResultData remove(@RequestParam Integer id){
        ResultData<Integer> resultData = Tools.getThreadResultData();
        resultData.setData(categoryPictureService.remove(id));
        return resultData;
    }

    @RequestMapping(value = Router.V1_OMS_CATEGORY_PICTURE_BY_VENDERID, method = RequestMethod.POST)
    ResultData getCategoryPicture(@PathVariable("venderId") Integer venderId, @RequestBody List<Integer> catIds){
        ResultData<Map<Integer, Object>> resultData = Tools.getThreadResultData();
        HashMap<Integer, Object> maps = new HashMap<Integer, Object>();
        if(catIds.size() > 0){
            List<CategoryPicture> categoryPictureList = categoryPictureService.getByCatIds(venderId, catIds);
            for(CategoryPicture c:categoryPictureList){
                maps.put(c.getCatId(), c);
            }
        }
        resultData.setData(maps);
        return resultData;
    }
}

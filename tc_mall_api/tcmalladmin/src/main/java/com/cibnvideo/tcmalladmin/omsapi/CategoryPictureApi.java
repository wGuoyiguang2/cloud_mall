package com.cibnvideo.tcmalladmin.omsapi;

import com.cibnvideo.config.Router;
import com.cibnvideo.tcmalladmin.model.bean.CategoryPicture;
import com.hqtc.common.config.FeignClientService;
import com.hqtc.common.response.DataList;
import com.hqtc.common.response.ResultData;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

@FeignClient(FeignClientService.OMSAPI)
public interface CategoryPictureApi {
    @RequestMapping(value = Router.V1_OMS_CATEGORY_PICTURE_LIST, method = RequestMethod.POST)
    ResultData<DataList<List<CategoryPicture>>> list(@RequestBody Map<String, Object> params);

    @RequestMapping(value = Router.V1_OMS_CATEGORY_PICTURE_GET, method = RequestMethod.GET)
    ResultData<CategoryPicture> get(@RequestParam("id") Integer id);

    @RequestMapping(value = Router.V1_OMS_CATEGORY_PICTURE_COUNT, method = RequestMethod.GET)
    ResultData<Integer> count(@RequestParam("params") Map<String, Object> params);

    @RequestMapping(value = Router.V1_OMS_CATEGORY_PICTURE_ADD, method = RequestMethod.POST)
    ResultData<Integer> add(@RequestBody CategoryPicture categoryPicture);

    @RequestMapping(value = Router.V1_OMS_CATEGORY_PICTURE_UPDATE, method = RequestMethod.POST)
    ResultData<Integer> update(@RequestBody CategoryPicture categoryPicture);

    @RequestMapping(value = Router.V1_OMS_CATEGORY_PICTURE_REMOVE, method = RequestMethod.GET)
    ResultData<Integer> remove(@RequestParam("id") Integer id);

    @RequestMapping(value = Router.V1_OMS_CATEGORY_PICTURE_BATCHREMOVE, method = RequestMethod.POST)
    ResultData<Integer> batchRemove(@RequestBody Map<String, Object> params);
}

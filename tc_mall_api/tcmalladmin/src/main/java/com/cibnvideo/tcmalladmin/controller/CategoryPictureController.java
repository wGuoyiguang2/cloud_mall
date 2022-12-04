package com.cibnvideo.tcmalladmin.controller;

import com.cibnvideo.common.controller.BaseController;
import com.cibnvideo.common.utils.R;
import com.cibnvideo.common.utils.Result;
import com.cibnvideo.tcmalladmin.jdsyncapi.CategoryApi;
import com.cibnvideo.tcmalladmin.model.bean.CategoryPicture;
import com.cibnvideo.tcmalladmin.model.bean.CategoryResultInfo;
import com.cibnvideo.tcmalladmin.omsapi.CategoryPictureApi;
import com.hqtc.common.config.ErrorCode;
import com.hqtc.common.response.DataList;
import com.hqtc.common.response.ResultData;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@Controller
public class CategoryPictureController extends BaseController {

    @Autowired
    CategoryPictureApi categoryPictureApi;

    @Autowired
    CategoryApi categoryApi;

    @RequiresPermissions("mall:categorypicture:list")
    @GetMapping("/v1/tcmalladmin/categorypicture")
    public String list(){
        return "tcmalladmin/categorypicturemanager/categorypictures";
    }

    @RequiresPermissions("mall:categorypicture:list")
    @GetMapping("/v1/tcmalladmin/categorypicture/list")
    @ResponseBody()
    Result<CategoryPicture> list(@RequestParam Map<String, Object> params) {
        Long venderId = getVenderId();
        Result<CategoryPicture> result = new Result<CategoryPicture>();
        result.setTotal(0);
        if(params.containsKey("name")){
            String name = (String)params.get("name");
            if(!StringUtils.isEmpty(name)){
                Map<String, Object> paramCat = new HashMap<String, Object>();
                paramCat.put("name", name);
                ResultData<DataList<List<CategoryResultInfo>>> resultListResultData = categoryApi.categoryList(paramCat);
                if(resultListResultData.getError() == ErrorCode.SUCCESS){
                    DataList<List<CategoryResultInfo>> dataList = resultListResultData.getData();
                    if(dataList != null && dataList.getTotalRows() > 0){
                        List<CategoryResultInfo> categoryResultInfoList = dataList.getData();
                        List<Integer> catIds = new ArrayList<Integer>();
                        for(CategoryResultInfo info:categoryResultInfoList){
                            catIds.add(info.getCatId());
                        }
                        if(catIds.size() == 0){
                            return result;
                        }else {
                            params.put("isin", catIds);
                        }
                    }else {
                        return result;
                    }
                }else {
                    return result;
                }
            }
        }
        params.put("venderId", venderId.intValue());
        ResultData<DataList<List<CategoryPicture>>> resultData = categoryPictureApi.list(params);
        if(resultData.getError() == ErrorCode.SUCCESS){
            DataList<List<CategoryPicture>> dataList = resultData.getData();
            if(dataList != null){
                List<CategoryPicture> categoryPictureList = dataList.getData();
                for(CategoryPicture p:categoryPictureList){
                    ResultData<CategoryResultInfo> categoryResultInfo = categoryApi.categoryGet(p.getCatId());
                    if(categoryResultInfo.getError() == ErrorCode.SUCCESS){
                        CategoryResultInfo resultInfo = categoryResultInfo.getData();
                        if(resultInfo != null){
                            p.setCatName(resultInfo.getName());
                        }
                    }
                }
                result.setRows(categoryPictureList);
                result.setTotal(dataList.getTotalRows());
            }
        }
        return result;
    }

    @RequiresPermissions("mall:categorypicture:list")
    @GetMapping("/v1/tcmalladmin/categorypicture/background")
    public String previewBackground(Model model, @RequestParam("id") Integer id){
        ResultData<CategoryPicture> resultData = categoryPictureApi.get(id);
        if(resultData.getError() == ErrorCode.SUCCESS){
            CategoryPicture categoryPicture = resultData.getData();
            if(categoryPicture != null){
                model.addAttribute("imagePath", categoryPicture.getBackground());
            }else {
                model.addAttribute("imagePath", "");
            }

        }else {
            model.addAttribute("imagePath", "");
        }
        return "tcmalladmin/categorypicturemanager/previewPicture";
    }

    @RequiresPermissions("mall:categorypicture:list")
    @GetMapping("/v1/tcmalladmin/categorypicture/picture")
    public String previewPicture(Model model, @RequestParam("id") Integer id){
        ResultData<CategoryPicture> resultData = categoryPictureApi.get(id);
        if(resultData.getError() == ErrorCode.SUCCESS){
            CategoryPicture categoryPicture = resultData.getData();
            if(categoryPicture != null){
                model.addAttribute("imagePath", categoryPicture.getPicture());
            }else {
                model.addAttribute("imagePath", "");
            }

        }else {
            model.addAttribute("imagePath", "");
        }
        return "tcmalladmin/categorypicturemanager/previewPicture";
    }

    @RequiresPermissions("mall:categorypicture:list")
    @GetMapping("/v1/tcmalladmin/categorypicture/icon")
    public String previewIcon(Model model, @RequestParam("id") Integer id){
        ResultData<CategoryPicture> resultData = categoryPictureApi.get(id);
        if(resultData.getError() == ErrorCode.SUCCESS){
            CategoryPicture categoryPicture = resultData.getData();
            if(categoryPicture != null){
                model.addAttribute("imagePath", categoryPicture.getIcon());
            }else {
                model.addAttribute("imagePath", "");
            }

        }else {
            model.addAttribute("imagePath", "");
        }
        return "tcmalladmin/categorypicturemanager/previewPicture";
    }

    @RequiresPermissions("mall:categorypicture:add")
    @GetMapping("/v1/tcmalladmin/categorypicture/add")
    public String add(Model model){
        HashMap<String, Object> paramsCat = new HashMap<String, Object>();
        paramsCat.put("catClass", 0);
        ResultData<DataList<List<CategoryResultInfo>>> resultDataCat0 = categoryApi.categoryList(paramsCat);
        if(resultDataCat0.getError() == ErrorCode.SUCCESS){
            DataList<List<CategoryResultInfo>> resultList = resultDataCat0.getData();
            model.addAttribute("cat0list", resultList.getData());
        }
        return "tcmalladmin/categorypicturemanager/add";
    }

    @RequiresPermissions("mall:categorypicture:add")
    @PostMapping("/v1/tcmalladmin/categorypicture/save")
    @ResponseBody
    public R save(CategoryPicture categoryPicture) {
        Integer venderId = this.getVenderId().intValue();
        categoryPicture.setVenderId(venderId);
        categoryPicture.setCtime(new Date());
        categoryPicture.setUtime(new Date());
        Integer shared = categoryPicture.getShared();
        HashMap<String, Object> params = new HashMap<String, Object>();
        params.put("catId", categoryPicture.getCatId());
        params.put("catType", categoryPicture.getCatType());
        params.put("shared", shared);
        if(shared == 0){
            params.put("venderId", venderId);
        }
        ResultData<Integer> resultDataCount = categoryPictureApi.count(params);
        if(resultDataCount.getError() == ErrorCode.SUCCESS){
            Integer r = resultDataCount.getData();
            if(r != null && r > 0){
                return R.error("此分类图已存在");
            }
        }else {
            return R.error(resultDataCount.getMsg());
        }
        ResultData<Integer> resultData = categoryPictureApi.add(categoryPicture);
        if(resultData.getError() == ErrorCode.SUCCESS){
            if(resultData.getData() != null && resultData.getData() > 0){
                return R.ok();
            }else {
                return R.error("保存失败");
            }
        }else {
            return R.error(resultData.getMsg());
        }
    }

    @RequiresPermissions("mall:categorypicture:edit")
    @GetMapping("/v1/tcmalladmin/categorypicture/edit")
    public String edit(Model model, @RequestParam("id") Integer id){
        Long venderId = getVenderId();
        int catId = -1;
        int catType = -1;
        ResultData<CategoryPicture> categoryPictureResultData = categoryPictureApi.get(id);
        if(categoryPictureResultData.getError() == ErrorCode.SUCCESS){
            CategoryPicture categoryPicture = categoryPictureResultData.getData();
            if(categoryPicture != null){
                catId = categoryPicture.getCatId();
                catType = categoryPicture.getCatType();
                model.addAttribute("categorypicture", categoryPicture);
                if(catType == 0){
                    model.addAttribute("cat0", catId);
                    HashMap<String, Object> paramsCat = new HashMap<String, Object>();
                    paramsCat.put("catClass", 0);
                    paramsCat.put("catId", catId);
                    ResultData<DataList<List<CategoryResultInfo>>> resultDataCat0 = categoryApi.categoryList(paramsCat);
                    if(resultDataCat0.getError() == ErrorCode.SUCCESS){
                        DataList<List<CategoryResultInfo>> resultList = resultDataCat0.getData();
                        model.addAttribute("cat0list", resultList.getData());
                    }
                }else if(catType == 1){
                    model.addAttribute("cat1", catId);
                    HashMap<String, Object> paramsCat = new HashMap<String, Object>();
                    paramsCat.put("catClass", 1);
                    paramsCat.put("catId", catId);
                    ResultData<DataList<List<CategoryResultInfo>>> resultDataCat1 = categoryApi.categoryList(paramsCat);
                    if(resultDataCat1.getError() == ErrorCode.SUCCESS){
                        DataList<List<CategoryResultInfo>> resultList1 = resultDataCat1.getData();
                        model.addAttribute("cat1list", resultList1.getData());//添加二级分类列表
                        if(resultList1.getData() != null){
                            CategoryResultInfo info = resultList1.getData().get(0);
                            paramsCat.put("catClass", 0);
                            paramsCat.put("catId", info.getParentId());
                            ResultData<DataList<List<CategoryResultInfo>>> resultDataCat0 = categoryApi.categoryList(paramsCat);
                            if(resultDataCat0.getError() == ErrorCode.SUCCESS){
                                DataList<List<CategoryResultInfo>> resultList0 = resultDataCat0.getData();
                                model.addAttribute("cat0list", resultList0.getData());//添加一级分类列表
                            }
                        }
                    }
                }else if(catType == 2){
                    model.addAttribute("cat2", catId);
                    HashMap<String, Object> paramsCat = new HashMap<String, Object>();
                    paramsCat.put("catClass", 2);
                    paramsCat.put("catId", catId);
                    ResultData<DataList<List<CategoryResultInfo>>> resultDataCat2 = categoryApi.categoryList(paramsCat);
                    if(resultDataCat2.getError() == ErrorCode.SUCCESS){
                        DataList<List<CategoryResultInfo>> resultList2 = resultDataCat2.getData();
                        model.addAttribute("cat2list", resultList2.getData());//添加三级分类
                        if(resultList2.getData() != null){
                            CategoryResultInfo info2 = resultList2.getData().get(0);
                            paramsCat.put("catClass", 1);
                            paramsCat.put("catId", info2.getParentId());
                            ResultData<DataList<List<CategoryResultInfo>>> resultDataCat1 = categoryApi.categoryList(paramsCat);
                            if(resultDataCat1.getError() == ErrorCode.SUCCESS){
                                DataList<List<CategoryResultInfo>> resultList1 = resultDataCat1.getData();
                                model.addAttribute("cat1list", resultList1.getData());//添加二级分类列表
                                if(resultList1 != null){
                                    CategoryResultInfo info1 = resultList1.getData().get(0);
                                    paramsCat.put("catClass", 0);
                                    paramsCat.put("catId", info1.getParentId());
                                    ResultData<DataList<List<CategoryResultInfo>>> resultDataCat0 = categoryApi.categoryList(paramsCat);
                                    if(resultDataCat0.getError() == ErrorCode.SUCCESS){
                                        DataList<List<CategoryResultInfo>> resultList0 = resultDataCat0.getData();
                                        model.addAttribute("cat0list", resultList0.getData());//添加一级分类列表
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        return "tcmalladmin/categorypicturemanager/edit";
    }

    @RequiresPermissions("mall:categorypicture:edit")
    @PostMapping("/v1/tcmalladmin/categorypicture/update")
    @ResponseBody
    public R update(CategoryPicture categoryPicture) {
        Integer venderId = this.getVenderId().intValue();
        categoryPicture.setVenderId(venderId);
        categoryPicture.setUtime(new Date());
        ResultData<Integer> resultData = categoryPictureApi.update(categoryPicture);
        if(resultData.getError() == ErrorCode.SUCCESS){
            Integer r = resultData.getData();
            if(r != null && r > 0){
                return R.ok();
            }else {
                return R.error("更新失败");
            }
        }else {
            return R.error(resultData.getMsg());
        }
    }

    @RequiresPermissions("mall:categorypicture:remove")
    @PostMapping("/v1/tcmalladmin/categorypicture/remove")
    @ResponseBody
    public R remove(Integer id) {
        ResultData<Integer> resultData = categoryPictureApi.remove(id);
        if(resultData.getError() == ErrorCode.SUCCESS){
            Integer r = resultData.getData();
            if(r != null && r > 0){
                return R.ok();
            }else {
                return R.error("删除失败");
            }
        }else {
            return R.error(resultData.getMsg());
        }
    }
}

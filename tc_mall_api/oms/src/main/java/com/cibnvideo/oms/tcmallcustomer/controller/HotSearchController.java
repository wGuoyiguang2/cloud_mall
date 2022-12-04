package com.cibnvideo.oms.tcmallcustomer.controller;

import com.cibnvideo.oms.bean.Result;
import com.cibnvideo.oms.config.Router;
import com.cibnvideo.oms.tcmallcustomer.bean.HotSearchBean;
import com.cibnvideo.oms.tcmallcustomer.service.HotSearchService;
import com.hqtc.common.config.ErrorCode;
import com.hqtc.common.response.ResultData;
import com.hqtc.common.utils.Tools;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * @Description:
 * @Author: WangBin
 * @Date: 2018/8/13 14:30
 */
@RestController
public class HotSearchController {

    private Logger logger = LoggerFactory.getLogger("CardController");
    @Value("${spring.config.hotSearchLimit}")
    private Integer limit;
    @Autowired
    private HotSearchService hotSearchService;

    @PostMapping(Router.V1_OMS_HOTSEARCH_LIST_BY_VENDERID)
    ResultData<List<String>> listByVenderId(@RequestParam("venderId") Long venderId){
        ResultData resultData= Tools.getThreadResultData();
        List<String> list=hotSearchService.listByVenderId(venderId,limit);
        resultData.setData(list);
        return resultData;
    }
    @PostMapping(Router.V1_OMS_HOTSEARCH_MANAGER_LIST)
    ResultData<Result<HotSearchBean>> listManagerHotSearch(@RequestParam Map<String, Object> params){
        ResultData resultData= Tools.getThreadResultData();
        Result result=new Result();
        List<HotSearchBean> list=hotSearchService.listManagerHotSearch(params);
        int total=hotSearchService.countManagerHotSearch(params);
        result.setRows(list);
        result.setTotal(total);
        resultData.setData(result);
        return resultData;
    }

    @PostMapping(Router.V1_OMS_HOTSEARCH_ADD)
    ResultData addHotSearch(@RequestParam Map<String, Object> params){
        ResultData resultData= Tools.getThreadResultData();
        int count=0;
        try{
            count=hotSearchService.addHotSearch(params);
        } catch (DuplicateKeyException e){
            resultData.setMsg("该关键字已存在！");
            resultData.setError(ErrorCode.WRITE_DATA_ERROR);
            return resultData;
        } catch (Exception e){
            logger.error("添加热搜异常："+e.getMessage());
            resultData.setMsg("添加失败");
            resultData.setError(ErrorCode.WRITE_DATA_ERROR);
        }
        if(count>0){
            resultData.setMsg("添加成功！");
        }else{
            resultData.setMsg("添加失败！");
            resultData.setError(ErrorCode.WRITE_DATA_ERROR);
        }
        return resultData;
    }

    @GetMapping(Router.V1_OMS_HOTSEARCH_GET)
    ResultData<HotSearchBean> getById(@RequestParam("id") Integer id){
        ResultData resultData= Tools.getThreadResultData();
        HotSearchBean hotSearchBean=hotSearchService.getById(id);
        resultData.setData(hotSearchBean);
        return resultData;
    }

    @PostMapping(Router.V1_OMS_HOTSEARCH_UPDATE)
    ResultData updateManagerHotSearch(@RequestParam Map<String, Object> params){
        ResultData resultData= Tools.getThreadResultData();
        int count=0;
        try{
            count=hotSearchService.updateManagerHotSearch(params);
        } catch (DuplicateKeyException e){
            resultData.setMsg("该关键字已存在！");
            resultData.setError(ErrorCode.WRITE_DATA_ERROR);
            return resultData;
        } catch (Exception e){
            logger.error("更新热搜异常："+e.getMessage());
            resultData.setMsg("更新失败");
            resultData.setError(ErrorCode.WRITE_DATA_ERROR);
        }
        if(count>0){
            resultData.setMsg("更新成功！");
        }else{
            resultData.setError(ErrorCode.WRITE_DATA_ERROR);
            resultData.setMsg("更新失败！");
        }
        return  resultData;
    }

    @PostMapping(Router.V1_OMS_HOTSEARCH_DELETE)
    ResultData deleteById(@RequestParam("id") Integer id){
        ResultData resultData= Tools.getThreadResultData();
        int count=hotSearchService.deleteById(id);
        if(count>0){
            resultData.setMsg("删除成功！");
        }else{
            resultData.setError(ErrorCode.WRITE_DATA_ERROR);
            resultData.setMsg("删除失败！");
        }
        return  resultData;
    }
}

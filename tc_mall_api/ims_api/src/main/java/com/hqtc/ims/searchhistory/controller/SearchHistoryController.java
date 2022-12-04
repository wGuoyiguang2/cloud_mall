package com.hqtc.ims.searchhistory.controller;

import com.hqtc.common.config.ErrorCode;
import com.hqtc.common.response.ResultData;
import com.hqtc.common.utils.Tools;
import com.hqtc.ims.common.constant.PathConstants;
import com.hqtc.ims.searchhistory.model.bean.SearchHistoryBean;
import com.hqtc.ims.searchhistory.service.SearchHistoryService;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description:
 * @Author: WangBin
 * @Date: 2018/7/6 10:17
 */
@RestController
public class SearchHistoryController {
    private Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private SearchHistoryService searchHistoryService;

    @Value("${service.config.searchHistoryLimit}")
    private Integer limit;

    /**
     * 添加搜索历史记录
     * @param searchHistoryBean
     * @return
     */
    @PostMapping(PathConstants.SEARCH_HISTORY_CREATE)
    public ResultData createSearchHistory(@RequestBody SearchHistoryBean searchHistoryBean){
        ResultData resultData= Tools.getThreadResultData();
        Integer result=searchHistoryService.createSearchHistory(searchHistoryBean);
        if(result>0) {
            resultData.setMsg("添加搜索历史记录成功！");
            resultData.setError(ErrorCode.SUCCESS);
        }else{
            resultData.setMsg("添加搜索历史记录失败！");
            resultData.setError(ErrorCode.WRITE_DATA_ERROR);
        }
        resultData.setData(result);
        return resultData;
    }

    /**
     * 获取用户搜索历史记录
     * @param userId
     * @param mac
     * @return
     */
    @GetMapping(PathConstants.SEARCH_HISTORY_LIST)
    public ResultData listKeywordsByUserIdOrMac(@RequestAttribute("userId") Integer userId, @RequestParam(value="mac1",required = false) String mac){
        ResultData resultData= Tools.getThreadResultData();
        List<String> keywordList = new ArrayList<>();
        if (userId != 0){
            keywordList = searchHistoryService.listKeywordsByUserId(userId, limit);
        } else if (StringUtils.isNotBlank(mac)){
            keywordList = searchHistoryService.listKeywordsByMac(mac, limit);
        }
        resultData.setData(keywordList);
        return resultData;
    }

    /**
     * 删除用户搜索历史记录
     * @param userId
     * @param mac
     * @return
     */
    @GetMapping(PathConstants.SEARCH_HISTORY_DELETE)
    public ResultData deleteKeywordsByUserIdOrMac(@RequestAttribute("userId") Integer userId, @RequestParam(value="mac1",required = false) String mac){
        ResultData resultData= Tools.getThreadResultData();
        try {
            if (userId != 0) {
                searchHistoryService.deleteKeywordsByUserId(userId);
            } else if (StringUtils.isNotBlank(mac)) {
                searchHistoryService.deleteKeywordsByMac(mac);
            }
        }catch (Exception e){
            log.error("清空搜索历史记录异常："+e.getMessage());
            resultData.setError(ErrorCode.WRITE_DATA_ERROR);
            resultData.setMsg("清空记录失败！");
        }
        return resultData;
    }
}

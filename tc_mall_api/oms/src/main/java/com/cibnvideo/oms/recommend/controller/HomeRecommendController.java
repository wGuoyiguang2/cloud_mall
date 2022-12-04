package com.cibnvideo.oms.recommend.controller;

import com.alibaba.fastjson.JSON;
import com.cibnvideo.oms.config.Router;
import com.cibnvideo.oms.recommend.model.bean.LayoutRecommendBean;
import com.cibnvideo.oms.recommend.model.bean.RecommendBean;
import com.cibnvideo.oms.recommend.model.bean.TemplateBean;
import com.cibnvideo.oms.recommend.service.RecommendService;
import com.cibnvideo.oms.recommend.service.TemplateService;
import com.cibnvideo.oms.recommend.service.LayoutService;
import com.hqtc.common.response.ResultData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.hqtc.common.utils.Tools.getThreadResultData;

/**
 * Created by makuan on 18-6-28.
 */
@RestController
public class HomeRecommendController {

    @Autowired
    LayoutService layoutService;

    @Autowired
    RecommendService recommendService;

    @Autowired
    TemplateService templateService;

    @RequestMapping(value = Router.ROUTE_GET_HOME_RECOMMEND, method = RequestMethod.GET)
    public ResultData getHomeRecommend(@RequestParam Map<String, Object> params){
        ResultData resultData = getThreadResultData();
        HashMap<String, Object> recommendParam = new HashMap<String, Object>(1);
        List<LayoutRecommendBean> layoutRecommendBeanList = layoutService.getLayoutRecommendList(params);
        if (layoutRecommendBeanList != null && layoutRecommendBeanList.size() > 0){
            for (LayoutRecommendBean layoutRecommend : layoutRecommendBeanList){
                TemplateBean templateBean = templateService.get(layoutRecommend.getTemplateId());
                if(templateBean != null){
                    Map<String, String> map = (Map) JSON.parse(templateBean.getLayout());
                    layoutRecommend.setLayoutSize(map.getOrDefault("layoutsize", ""));
                    recommendParam.put("layoutId", layoutRecommend.getId());
                    recommendParam.put("status", 1);
                    List<RecommendBean> recommendBeanList = recommendService.list(recommendParam);
                    if (recommendBeanList != null && recommendBeanList.size() > 0){
                        for (RecommendBean recommendBean : recommendBeanList){
                            Map recommendMap = (Map) JSON.parse((String)recommendBean.getActionParam());
                            recommendBean.setActionParam(recommendMap);
                        }
                    }
                    layoutRecommend.setDataList(recommendBeanList);
                }
            }
        }
        resultData.setData(layoutRecommendBeanList);
        return resultData;
    }

}

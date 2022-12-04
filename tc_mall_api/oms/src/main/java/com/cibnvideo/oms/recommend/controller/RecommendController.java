package com.cibnvideo.oms.recommend.controller;

import com.cibnvideo.oms.config.Router;
import com.cibnvideo.oms.recommend.model.bean.LayoutBean;
import com.cibnvideo.oms.recommend.model.bean.RecommendBean;
import com.cibnvideo.oms.recommend.service.LayoutService;
import com.cibnvideo.oms.recommend.service.RecommendService;
import com.hqtc.common.config.ErrorCode;
import com.hqtc.common.response.DataList;
import com.hqtc.common.response.ResultData;
import com.hqtc.common.utils.Tools;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class RecommendController {
    Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private RecommendService recommendService;

    @Autowired
    private LayoutService layoutService;

    @RequestMapping(value = Router.V1_OMS_RECOMMEND_LIST, method = RequestMethod.GET)
    ResultData list(@RequestParam Map<String, Object> params) {
        ResultData resultData = Tools.getThreadResultData();
        List<RecommendBean> recommendBeanList = recommendService.list(params);
        int count = recommendService.count(params);
        DataList<List<RecommendBean>> result = Tools.getThreadDataList();
        result.setData(recommendBeanList);
        result.setTotalRows(count);
        resultData.setData(result);
        return resultData;
    }

    @RequestMapping(value = Router.V1_OMS_RECOMMEND_GET, method = RequestMethod.GET)
    ResultData get(@RequestParam Integer id) {
        ResultData<RecommendBean> resultData = Tools.getThreadResultData();
        resultData.setData(recommendService.get(id));
        return resultData;
    }

    @RequestMapping(value = Router.V1_OMS_RECOMMEND_GET_BY_POSITION, method = RequestMethod.GET)
    ResultData getbyposition(@RequestParam("venderId") Integer venderId, @RequestParam("layoutId") Integer layoutId, @RequestParam("position") Integer position) {
        ResultData<RecommendBean> resultData = Tools.getThreadResultData();
        resultData.setData(recommendService.getbyposition(venderId, layoutId, position));
        return resultData;
    }

    @RequestMapping(value = Router.V1_OMS_RECOMMEND_ADD, method = RequestMethod.POST)
    @Transactional
    ResultData add(@RequestBody RecommendBean recommendBean) {
        ResultData<Integer> resultData = Tools.getThreadResultData();
        Integer layoutId = recommendBean.getLayoutId();
        if (!StringUtils.equals("OPEN_COLLECTION", recommendBean.getAction())) {
            Map<String, Object> params = new HashMap<String, Object>() {
                {
                    put("parentId", layoutId);
                    put("autoSync", 1);
                }
            };

            List<LayoutBean> layoutList = layoutService.list(params);
            for (LayoutBean layout : layoutList) {
                try {
                    RecommendBean recommendBean1 = (RecommendBean) BeanUtils.cloneBean(recommendBean);
                    recommendBean1.setId(null);
                    recommendBean1.setVenderId(layout.getVenderId());
                    recommendBean1.setLayoutId(layout.getId());
                    recommendService.save(recommendBean1);
                } catch (Exception e) {
                    logger.error(e.toString());
                }

            }
        }
        if(!recommendCheck(recommendBean)) {
            resultData.setError(ErrorCode.PARAM_ERROR);
            resultData.setMsg("父布局推荐位已存在，无法修改");
            return resultData;
        }
        resultData.setData(recommendService.save(recommendBean));
        return resultData;
    }

    @RequestMapping(value = Router.V1_OMS_RECOMMEND_REMOVE, method = RequestMethod.GET)
    ResultData remove(@RequestParam Integer id) {
        ResultData<Integer> resultData = Tools.getThreadResultData();
        resultData.setData(recommendService.remove(id));
        return resultData;
    }

    @RequestMapping(value = Router.V1_OMS_RECOMMEND_UPDATE, method = RequestMethod.POST)
    @Transactional
    ResultData update(@RequestBody RecommendBean recommendBean) throws Exception {
        ResultData<Integer> resultData = Tools.getThreadResultData();
        Integer layoutId = recommendBean.getLayoutId();
        if (!StringUtils.equals("OPEN_COLLECTION", recommendBean.getAction())) {
            Map<String, Object> params = new HashMap<>();
            params.put("parentId", layoutId);
            params.put("autoSync", 1);
            List<LayoutBean> layoutList = layoutService.list(params);
            for (LayoutBean layout : layoutList) {
                RecommendBean recommendBean1 = recommendService.getbyposition(layout.getVenderId(), layout.getId(), recommendBean.getPosition());
                RecommendBean recommendBean2 = (RecommendBean) BeanUtils.cloneBean(recommendBean);
                recommendBean2.setId(recommendBean1.getId());
                recommendBean2.setLayoutId(layout.getId());
                recommendService.update(recommendBean2);
            }
        }
        if(!recommendCheck(recommendBean)) {
            resultData.setError(ErrorCode.PARAM_ERROR);
            resultData.setMsg("父布局推荐位已存在，无法修改");
            return resultData;
        }
        resultData.setData(recommendService.update(recommendBean));
        return resultData;
    }

    private boolean recommendCheck(RecommendBean recommendBean) {
        LayoutBean layoutBean = layoutService.get(recommendBean.getLayoutId());
        Integer parentId = layoutBean.getParentId();
        if (parentId != 0) {
            LayoutBean parentLayout = layoutService.get(parentId);
            if (parentLayout != null) {
                RecommendBean parentRecommendBean = recommendService.getbyposition(parentLayout.getVenderId(), parentLayout.getId(), recommendBean.getPosition());
                if (parentRecommendBean != null && !StringUtils.equals("OPEN_COLLECTION", parentRecommendBean.getAction())) {
                    return false;
                }
            }
        }
        return true;
    }
}

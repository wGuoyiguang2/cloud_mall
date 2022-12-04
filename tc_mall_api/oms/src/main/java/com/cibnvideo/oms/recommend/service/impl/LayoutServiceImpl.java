package com.cibnvideo.oms.recommend.service.impl;

import com.cibnvideo.oms.exception.LayoutCopyException;
import com.cibnvideo.oms.exception.LayoutRemoveException;
import com.cibnvideo.oms.recommend.dao.LayoutDao;
import com.cibnvideo.oms.recommend.model.bean.LayoutBean;
import com.cibnvideo.oms.recommend.model.bean.LayoutRecommendBean;
import com.cibnvideo.oms.recommend.model.bean.RecommendBean;
import com.cibnvideo.oms.recommend.service.LayoutService;
import com.cibnvideo.oms.recommend.service.RecommendService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class LayoutServiceImpl implements LayoutService {

    @Autowired
    LayoutDao layoutDao;

    @Autowired
    RecommendService recommendService;

    @Override
    public List<LayoutBean> list(Map<String, Object> params) {
        return layoutDao.list(params);
    }

    @Override
    public List<LayoutRecommendBean> getLayoutRecommendList(Map<String, Object> params) {
        return layoutDao.getLayoutRecommendList(params);
    }

    @Override
    public LayoutBean get(int id) {
        return layoutDao.get(id);
    }

    @Override
    public int count(Map<String, Object> params) {
        return layoutDao.count(params);
    }

    @Override
    public int save(LayoutBean layoutBean) {
        return layoutDao.save(layoutBean);
    }

    @Override
    @Transactional
    public int update(LayoutBean layoutBean) {
        Integer layoutId = layoutBean.getId();
        Integer venderId = layoutBean.getVenderId();
        Integer parentId = layoutBean.getParentId();
        Integer autoSync = layoutBean.getAutoSync();
        if (parentId != 0 && autoSync == 1) {
            Map<String, Object> params = new HashMap<>();
            params.put("layoutId", parentId);
            List<RecommendBean> recommendBeanList = recommendService.list(params);
            if (recommendBeanList != null) {
                for (RecommendBean recommendBean : recommendBeanList) {
                    if (!StringUtils.equals("OPEN_COLLECTION", recommendBean.getAction())) {
                        RecommendBean recommendBean1 = recommendService.getbyposition(venderId, layoutId, recommendBean.getPosition());
                        recommendBean.setId(recommendBean1.getId());
                        recommendBean.setLayoutId(layoutId);
                        recommendBean.setVenderId(venderId);
                        recommendService.update(recommendBean);
                    }
                }
            }
        }

        return layoutDao.update(layoutBean);
    }

    @Override
    @Transactional
    public int remove(Integer id) throws Exception{
        Map<String, Object> params = new HashMap<String, Object>() {
            {
                put("parentId", id);
            }
        };
        List<LayoutBean> layoutBeanList = list(params);
        if(layoutBeanList != null && layoutBeanList.size() > 0) {
            StringBuilder stringBuilder = new StringBuilder();
            for(LayoutBean layoutBean:layoutBeanList) {
                stringBuilder.append("大客户ID:" + layoutBean.getVenderId() + " 布局ID:" + layoutBean.getId() + " 布局名称:" + layoutBean.getTitle());
            }
            throw new LayoutRemoveException("此布局被其他布局复制,无法删除: " + stringBuilder);
        }
        recommendService.removeByLayoutId(id);
        return layoutDao.remove(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int copyByLayoutId(Integer venderId, Integer parentId) throws Exception {
        LayoutBean layoutBean = get(parentId);
        if (layoutBean != null) {
            layoutBean.setId(null);
            layoutBean.setParentId(parentId);
            layoutBean.setVenderId(venderId);
            layoutBean.setShare(0);
            layoutBean.setAutoSync(1);
            layoutBean.setCtime(new Date());
            layoutBean.setUtime(new Date());
            int saveResult = save(layoutBean);
            if (saveResult == 1) {
                int saveRecommendResult = recommendService.copyByLayoutId(venderId, parentId, layoutBean.getId());
                if (saveRecommendResult == 0) {
                    throw new LayoutCopyException("推荐位复制失败");
                } else {
                    return 1;
                }
            } else {
                throw new LayoutCopyException("布局复制失败");
            }
        }
        return 0;
    }
}

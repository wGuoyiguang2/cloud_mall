package com.cibnvideo.oms.recommend.service.impl;

import com.cibnvideo.oms.recommend.dao.RecommendDao;
import com.cibnvideo.oms.recommend.model.bean.RecommendBean;
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
public class RecommendServiceImpl implements RecommendService {

    @Autowired
    RecommendDao recommendDao;
    @Override
    public List<RecommendBean> list(Map<String, Object> params) {
        return recommendDao.list(params);
    }

    @Override
    public RecommendBean get(int id) {
        return recommendDao.get(id);
    }

    @Override
    public RecommendBean getbyposition(Integer venderId, Integer layoutId, Integer position) {
        return recommendDao.getbyposition(venderId, layoutId, position);
    }

    @Override
    public int count(Map<String, Object> params) {
        return recommendDao.count(params);
    }

    @Override
    public int save(RecommendBean recommendBean) {
        return recommendDao.save(recommendBean);
    }

    @Override
    public int update(RecommendBean recommendBean) {
        return recommendDao.update(recommendBean);
    }

    @Override
    public int remove(Integer id) {
        return recommendDao.remove(id);
    }

    @Override
    public int removeByLayoutId(Integer layoutId) {
        return recommendDao.removeByLayoutId(layoutId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int copyByLayoutId(Integer venderId, Integer parentLayoutId, Integer layoutId) throws Exception{
        Map<String, Object> params = new HashMap<>();
        params.put("layoutId", parentLayoutId);
        List<RecommendBean> recommendList = recommendDao.list(params);
        for(RecommendBean recommendBean:recommendList) {
            //不复制商品集
            if(!StringUtils.equals("OPEN_COLLECTION", recommendBean.getAction())) {
                recommendBean.setId(null);
                recommendBean.setLayoutId(layoutId);
                recommendBean.setVenderId(venderId);
                recommendBean.setCtime(new Date());
                recommendBean.setUtime(new Date());
                int result = recommendDao.save(recommendBean);
                if(result == 0) {
                    throw new Exception("推荐位保存失败");
                }
            }


        }
        return 1;
    }
}

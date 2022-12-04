package com.hqtc.cms.controller;

import com.google.gson.Gson;
import com.hqtc.cms.config.Router;
import com.hqtc.cms.model.bean.*;
import com.hqtc.cms.model.service.RecommendService;
import com.hqtc.cms.model.service.SearchService;
import com.hqtc.common.response.ResultData;
import com.hqtc.common.utils.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import static com.hqtc.common.utils.Tools.getThreadResultData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by makuan on 18-6-27.
 */
@RestController
public class RecommendController {
    Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    RecommendService recommendService;

    @Autowired
    SearchService searchService;

    @Value("${service.config.pageSize}")
    private int defaultPageSize;

    @Value("${service.config.layoutStatus}")
    private int layoutStatus;

    @Value("${service.config.defaultDate}")
    private int defaultDate;

    @Value("${service.config.recommendSort}")
    private String sort;

    @Value("${service.config.recommendOrder}")
    private String order;

    @RequestMapping(value = Router.ROUTE_GET_HOME_RECOMMEND, method = RequestMethod.GET)
    @Cacheable(value = "cms", key = "'home_recommend:venderId:' +#venderId + ':pageSize:'+#pageSize + ':pageNum:' +#pageNum",
                cacheManager = "CmsCacheManager")
    public ResultData getHomeRecommend(@RequestParam("venderId") int venderId,
                                       Integer pageNum, Integer pageSize){
        pageNum = (pageNum == null || pageNum <= 0) ? 0: pageNum -1;
        pageSize = (pageSize == null || pageSize <= 0) ? defaultPageSize: pageSize;
        ResultData<List<LayoutRecommendBean>> resultData = recommendService.getHomeRecommend(venderId, layoutStatus, pageNum * pageSize, pageSize, sort, order);
        return resultData;
    }

    @RequestMapping(value = Router.ROUTE_GET_HOME_HISTORY_RECOMMEND, method = RequestMethod.GET)
    @Cacheable(value = "cms", key = "'history_recommend:venderId:' +#venderId + ':pageSize:'+#pageSize + ':startTime:' +#startTime",
            cacheManager = "CmsCacheManager")
    public ResultData getHistoryRecommend(@RequestParam("venderId") int venderId,
                                          @RequestParam(value = "startTime",defaultValue = "") String startTime,
                                          Integer pageSize) throws ParseException {
        pageSize = (pageSize == null || pageSize <= 0) ? defaultDate: pageSize;
        ResultData result = getThreadResultData();
        List<RecommendHistoryResonseBean> historyList = new ArrayList<>();
        if(startTime.equals("")){
            startTime=DateUtil.getSimpleDate();
        }else {
            Date date = DateUtil.getDateFormatStr(startTime);
            startTime = DateUtil.getSimpleDate(date, -1);
        }
        List<RecommendHistoryBean> recomendList = recommendService.getHistoryRecommend(getRecommendRequestBean(venderId,startTime,pageSize)).getData();
        if(recomendList==null || recomendList.size()==0){
            result.setData(historyList);
            return result;
        }

        //获取skus和商品列表
        String skus="";
        for(RecommendHistoryBean bean: recomendList){
            try{
                RecommendHistoryBean recommendBean = new Gson().fromJson(bean.getActionParam(), RecommendHistoryBean.class);
                skus+=recommendBean.getSku()+",";
                bean.setSku(recommendBean.getSku());
            } catch (Exception e) {
                logger.warn(e.getMessage());
            }
        }
        if(skus.equals("")){
            result.setData(historyList);
            return result;
        }

        ProductListBean<List<ProductBean>> productData = searchService.idsQuery(venderId, skus.substring(0, skus.length() - 1), null, null, null, recomendList.size()).getData();
        List<ProductBean> beanList=new ArrayList<>();
        if(productData !=null){
            beanList = productData.getDataList();
        }
        List<String> time = new ArrayList<>();
        if(recomendList !=null && recomendList.size()!=0){
            for(RecommendHistoryBean recommend: recomendList){
                recommend.setPrice(null);
                if(beanList !=null && beanList.size() !=0){
                    for(ProductBean bean: beanList ){
                        if(recommend.getSku() !=null && recommend.getSku().equals(bean.getSku())){
                            recommend.setName(bean.getName());
                            recommend.setBrandName(bean.getBrandName());
                            recommend.setImagePath(recommend.getImage());
                            recommend.setJdPrice(bean.getJdPrice());
                            recommend.setPrice(bean.getPrice());
                            if(!time.contains(DateUtil.getSimpleDate(recommend.getUtime()))){
                                time.add(DateUtil.getSimpleDate(recommend.getUtime()));
                            }
                        }
                    }
                }
            }
        }

        //往期推荐按时间分组
        if(time !=null && time.size()!=0){
            for(String t:time){
                List<RecommendHistoryBean> list = new ArrayList<RecommendHistoryBean>();
                RecommendHistoryResonseBean bean = new RecommendHistoryResonseBean();
                for(RecommendHistoryBean recommend: recomendList){
                    if(recommend.getSku() !=null && t.equals(DateUtil.getSimpleDate(recommend.getUtime()))){
                        list.add(recommend);
                    }
                }
                bean.setTime(t);
                bean.setList(list);
                historyList.add(bean);
            }
        }
        result.setData(historyList);
        return result;
    }

    private RecommendRequestBean getRecommendRequestBean(int venderId, String startTime, Integer limit) {
        RecommendRequestBean requestBean = new RecommendRequestBean();
        requestBean.setVenderId(venderId);
        requestBean.setStartTime(startTime);
        requestBean.setLimit(limit);
        return requestBean;
    }

}

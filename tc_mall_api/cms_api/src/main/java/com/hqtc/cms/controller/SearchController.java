package com.hqtc.cms.controller;

import com.hqtc.cms.config.Router;
import com.hqtc.cms.model.bean.ProductBean;
import com.hqtc.cms.model.bean.ProductListBean;
import com.hqtc.cms.model.bean.search.SearchParamsBean;
import com.hqtc.cms.model.bean.search.SyncSearchHistoryBean;
import com.hqtc.cms.model.service.SearchService;
import com.hqtc.cms.task.AsyncSearchTask;
import com.hqtc.common.config.ErrorCode;
import com.hqtc.common.response.ResultData;
import com.hqtc.common.utils.DateUtil;
import com.hqtc.common.utils.Tools;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.*;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import static com.hqtc.common.utils.Tools.getThreadResultData;

/**
 * Created by makuan on 18-7-5.
 */
@RestController
public class SearchController {
    private Logger log= LoggerFactory.getLogger(this.getClass());
    @Autowired
    SearchService searchService;
    @Autowired
    AsyncSearchTask asyncSearchTask;
    @Value("${service.config.jd-domain}")
    private String jdDomain;
    @Value("${service.config.pageSize}")
    private int pageSize;
    @Value("${service.config.searchQrcode}")
    private String searchQrcode;
    @Value("${service.config.limit}")
    private Integer limit;

    @RequestMapping(value = Router.ROUTE_SEARCH_PRODUCT, method = RequestMethod.POST)
    public ResultData searchProduct(@RequestParam(value="keyword",required = false) String keyword,
                                    @RequestParam(value="cat0",required = false) Integer cat0,
                                    @RequestParam(value="cat1",required = false) Integer cat1,
                                    @RequestParam(value="cat2",required = false) Integer cat2,
                                    @RequestParam(value="brands",required = false) String brands,
                                    @RequestParam(value="pageSize",required = false) Integer pageSize,
                                    @RequestParam(value="pageNum",required = false) Integer pageIndex,
                                    @RequestAttribute("userId") Integer userId,
                                    @RequestParam(value = "venderId") Long venderId,
                                    @RequestParam(value = "mac1") String mac,
                                    @RequestParam(value = "isNew",required = false) Integer isNew,
                                    @RequestParam(value="isSales",required = false) Integer isSales,
                                    @RequestParam(value="isPrice",required = false) Integer isPrice) throws Exception {
        if(StringUtils.isNotEmpty(keyword)) {
            keyword = URLDecoder.decode(keyword, "UTF-8");
        }
        if(StringUtils.isNotEmpty(brands)){
            brands = URLDecoder.decode(brands, "UTF-8");
        }
        ResultData resultData = getThreadResultData();
        SearchParamsBean searchParamsBean=new SearchParamsBean();
        searchParamsBean.setBrands(brands);
        searchParamsBean.setKeyword(keyword);
        searchParamsBean.setPageIndex(pageIndex);
        searchParamsBean.setPageSize(pageSize);
        //异步保存搜索历史记录
        try {
            SyncSearchHistoryBean searchHistoryBean = new SyncSearchHistoryBean();
            searchHistoryBean.setKeyword(searchParamsBean.getKeyword());
            searchHistoryBean.setMac(mac);
            searchHistoryBean.setUserId(userId);
            searchHistoryBean.setCtime(DateUtil.getSimpleDateFormat());
            if (StringUtils.isNotEmpty(searchParamsBean.getKeyword())) {
                asyncSearchTask.syncSearchHistory(searchHistoryBean);
            }
        }catch (Exception e){
            e.printStackTrace();
            log.info("异步保存搜索历史记录失败！");
        }
        //调用search搜索接口查询 品牌列表和商品列表
        ResultData<ProductListBean<List<ProductBean>>> mapResultData = searchService.searchProduct(venderId,keyword,brands,cat0,cat1,cat2,pageIndex,pageSize,isNew,isSales,isPrice);
        if(mapResultData.getError() !=0){
            resultData.setMsg("SEARCH服务异常！");
            resultData.setError(ErrorCode.SERVER_EXCEPTION);
            return resultData;
        }
        ProductListBean<List<ProductBean>> data = mapResultData.getData();
        if(data==null){
            resultData.setMsg("未找到相关商品！");
            resultData.setError(ErrorCode.SUCCESS);
            resultData.setData(null);
            return resultData;
        }
        Map<String,Object> map=new HashMap<>();
        map.put("totalRows",data.getTotal());
        map.put("brandList",searchService.brandListBySearch(Integer.valueOf(String.valueOf(venderId)),cat0,cat1,cat2,keyword,limit).getData());
        map.put("dataList",data.getDataList());
        resultData.setData(map);
        return resultData;
    }

    @GetMapping(Router.ROUTER_GET_SEARCH_QRCODE)
    @Cacheable(value = "cms", key = "'search_qrcode'", cacheManager = "CmsCacheManager")
    public ResultData<String> getQrCodeHtml(@RequestParam("venderId") Integer venderId,
                                            @RequestParam("mac1") String mac){
        ResultData<String> resultData= Tools.getThreadResultData();
        String url = searchQrcode + "?vender=" + venderId + "&mac=" + mac;
        resultData.setData(url);
        return resultData;
    }
}

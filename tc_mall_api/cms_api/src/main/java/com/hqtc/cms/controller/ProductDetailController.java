package com.hqtc.cms.controller;

import com.hqtc.cms.config.Router;
import com.hqtc.cms.model.bean.*;
import com.hqtc.cms.model.service.GoodService;
import com.hqtc.cms.model.service.OmsService;
import com.hqtc.cms.model.service.SearchService;
import com.hqtc.common.config.ErrorCode;
import com.hqtc.common.response.ResultData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;
import static com.hqtc.common.utils.Tools.getThreadResultData;

/**
 * description:商品详情
 * Created by laiqingchuang on 19-1-23 .
 */
@RestController
public class ProductDetailController {
    private Logger logger  = LoggerFactory.getLogger(getClass());
    @Autowired
    GoodService goodService;
    @Autowired
    OmsService omsService;
    @Autowired
    SearchService searchService;

    /**
     * 商品详情(pc端)
     * @param
     * @return
     */
    @RequestMapping(value=Router.ROUTE_PRODUCT_DETAILPC, method = RequestMethod.GET)
    @Cacheable(value = "cms", key = "'product_detail_pc:venderId:' +#venderId +':sku:' +#sku",
            cacheManager = "CmsCacheManager")
    public ResultData getGoodDetailinfoPc(@RequestParam Integer venderId,
                                          @RequestParam Long sku){
        ResultData result = getThreadResultData();
        ResultData<GoodDetailinfoBean> goodDetailinfo = goodService.getGoodDetailinfo(sku);
        if(goodDetailinfo.getError() ==ErrorCode.SERVER_EXCEPTION){
            logger.error(result.getMsg());
        }
        GoodDetailinfoBean detailinfo= goodDetailinfo.getData();
        if(detailinfo==null){
            return result;
        }
        detailinfo=this.addPriceState(venderId,detailinfo);
        PicturePcBean picturePc= goodService.getPicturePc(sku).getData();
        StylePcBean stylePc= goodService.getStylePc(sku).getData();
        if(stylePc==null){
            stylePc=new StylePcBean();
        }
        stylePc.setSku(detailinfo.getSku());            //sku id
        stylePc.setName(detailinfo.getName());          //名称
        stylePc.setBrandName(detailinfo.getBrandName());//品牌名称
        stylePc.setCategory(detailinfo.getCategory());  //三级分类信息
        stylePc.setImagePath(detailinfo.getImagePath());//图片地址
        stylePc.setVideoPath(detailinfo.getVideoPath());//视频地址
        stylePc.setJdPrice(detailinfo.getJdPrice());    //京东价
        stylePc.setPrice(detailinfo.getPrice());        //零售价
        stylePc.setState(detailinfo.getState());        //是否可用 0:下架 1:上架
        stylePc.setCat0(detailinfo.getCat0());          //一级分类id
        stylePc.setCat1(detailinfo.getCat1());
        stylePc.setCat2(detailinfo.getCat2());
        stylePc.setWeight(detailinfo.getWeight());      //重量
        stylePc.setProductArea(detailinfo.getProductArea()); //条形号
        stylePc.setUpc(detailinfo.getUpc());            //条形号
        stylePc.setSaleUnit(detailinfo.getSaleUnit());  //销售单位
        stylePc.setWareQD(detailinfo.getWareQD());      //商品清单
        stylePc.setIntroduction(detailinfo.getIntroduction());//pc端商品简介
        stylePc.setAppintroduce(detailinfo.getAppintroduce());//app端商品简介
        stylePc.setParam(detailinfo.getParam());        //商品参数
        if(picturePc !=null){
            stylePc.setPropCode(picturePc.getPropCode());   //规格参数
            stylePc.setService(picturePc.getService());     //服务
            stylePc.setwReadMe(picturePc.getwReadMe());     //readme
            stylePc.setShouhou(picturePc.getShouhou());     //售后
            stylePc.setWdis(picturePc.getWdis());           //商品详情
            stylePc.setWareQD(picturePc.getWareQD());       //商品清单
        }
        result.setData(stylePc);
        return result;
    }

    /**
     * 商品详情(移动端)
     * @param
     * @return
     */
    @RequestMapping(value=Router.ROUTE_PRODUCT_DETAILMOBILE, method = RequestMethod.GET)
    @Cacheable(value = "cms", key = "'product_detail_mobile:venderId:' +#venderId +':sku:' +#sku",
            cacheManager = "CmsCacheManager")
    public ResultData getGoodDetailinfoMobile(@RequestParam Integer venderId,
                                              @RequestParam Long sku){
        ResultData result = getThreadResultData();
        ResultData<GoodDetailinfoBean> goodDetailinfo = goodService.getGoodDetailinfo(sku);
        if(goodDetailinfo.getError() ==ErrorCode.SERVER_EXCEPTION){
            logger.error(result.getMsg());
        }
        GoodDetailinfoBean detailinfo= goodDetailinfo.getData();
        if(detailinfo==null){
            return result;
        }
        detailinfo=this.addPriceState(venderId,detailinfo);
        StyleMobileBean pictureMobile= goodService.getPictureMobile(sku).getData();
        StyleMobileBean styleMobile= goodService.getStyleMobile(sku).getData();
        if(styleMobile==null){
            styleMobile=new StyleMobileBean();
        }
        styleMobile.setSku(detailinfo.getSku());            //sku id
        styleMobile.setName(detailinfo.getName());          //名称
        styleMobile.setBrandName(detailinfo.getBrandName());//品牌名称
        styleMobile.setCategory(detailinfo.getCategory());  //三级分类信息
        styleMobile.setImagePath(detailinfo.getImagePath());//图片地址
        styleMobile.setVideoPath(detailinfo.getVideoPath());//视频地址
        styleMobile.setJdPrice(detailinfo.getJdPrice());    //京东价
        styleMobile.setPrice(detailinfo.getPrice());        //零售价
        styleMobile.setState(detailinfo.getState());        //是否可用 0:下架 1:上架
        styleMobile.setCat0(detailinfo.getCat0());          //一级分类id
        styleMobile.setCat1(detailinfo.getCat1());
        styleMobile.setCat2(detailinfo.getCat2());
        styleMobile.setWeight(detailinfo.getWeight());      //重量
        styleMobile.setProductArea(detailinfo.getProductArea()); //条形号
        styleMobile.setUpc(detailinfo.getUpc());            //条形号
        styleMobile.setSaleUnit(detailinfo.getSaleUnit());  //销售单位
        styleMobile.setWareQD(detailinfo.getWareQD());      //商品清单
        styleMobile.setIntroduction(detailinfo.getIntroduction());//pc端商品简介
        styleMobile.setAppintroduce(detailinfo.getAppintroduce());//app端商品简介
        styleMobile.setParam(detailinfo.getParam());        //商品参数
        if(pictureMobile !=null){
            styleMobile.setResult(pictureMobile.getResult());   //移动商品介绍
        }
        result.setData(styleMobile);
        return result;
    }

    private GoodDetailinfoBean addPriceState(Integer venderId,GoodDetailinfoBean detailinfo) {
        ResultData<ProductListBean<List<PriceStateBean>>> product = searchService.idsQuery(venderId, String.valueOf(String.valueOf(detailinfo.getSku())),1,1);
        if(product.getError() ==ErrorCode.SERVER_EXCEPTION){
            logger.error(product.getMsg());
        }
        ProductListBean<List<PriceStateBean>> data=product.getData();
        if(data !=null && data.getTotal() !=0){
            detailinfo.setState(data.getDataList().get(0).getState());
            detailinfo.setPrice(data.getDataList().get(0).getPrice());
        }
        return detailinfo;
    }
}

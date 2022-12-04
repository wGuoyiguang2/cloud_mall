package com.cibnvideo.jdsynctask.jdserviceapi;


import com.cibnvideo.jdsynctask.config.Router;
import com.cibnvideo.jdsynctask.fallback.jdserviceapi.GoodsApiFallbackFactory;
import com.cibnvideo.jdsynctask.model.*;
import com.hqtc.common.config.FeignClientService;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(value = FeignClientService.JDSERVICE, fallbackFactory = GoodsApiFallbackFactory.class)
public interface GoodsApi {
    @RequestMapping(value = Router.GOODS_PRODUCT_POOL_GET_PAGENUM, method = RequestMethod.POST)
    PageNum getPageNum();

    @RequestMapping(value = Router.GOODS_SKUS_BY_PAGENUM, method = RequestMethod.POST)
    Sku getSkus(PageNumRequestParams param);

    @RequestMapping(value = Router.GOODS_TAX_PRICE_GET, method = RequestMethod.POST)
    SellPrice getSellPrice(SkuRequestParams param);

    @RequestMapping(value = Router.GOODS_PRODUCT_DETAIL_GET, method = RequestMethod.POST)
    Product getProductDetail(SkuShowRequestParams param);

    @RequestMapping(value = Router.GOODS_PC_STYLE_GET, method = RequestMethod.POST)
    StyleOfPc getStyleOfPc(SkuRequestParams param);

    @RequestMapping(value = Router.GOODS_MOBILE_STYLE_GET, method = RequestMethod.POST)
    StyleOfMobile getStyleOfMobile(SkuRequestParams param);

    @RequestMapping(value = Router.GOODS_PC_PRODUCT_DETAIL_IMG, method = RequestMethod.POST)
    PictureOfPc getPictureOfPc(SkuRequestParams param);

    @RequestMapping(value = Router.GOODS_H5_PRODUCT_DETAIL_IMG, method = RequestMethod.POST)
    PictureOfMobile getPictureOfMobile(SkuIdRequestParams param);

    @RequestMapping(value = Router.GOODS_CATEGORY_PAGE_GET, method = RequestMethod.POST)
    Category getCategorys(CategoryPageRequestParams param);

    @RequestMapping(value = Router.GOODS_ADDRESS_ONE_LEVEL_GET, method = RequestMethod.POST)
    AddressProvinces getAddressProvinces();

    @RequestMapping(value = Router.GOODS_ADDRESS_TWO_LEVEL_GET, method = RequestMethod.POST)
    AddressCitys getAddressCityss(AddressRequestParams param);

    @RequestMapping(value = Router.GOODS_ADDRESS_THREE_LEVEL_GET, method = RequestMethod.POST)
    AddressCountys getAddressCountys(AddressRequestParams param);

    @RequestMapping(value = Router.GOODS_ADDRESS_FOUR_LEVEL_GET, method = RequestMethod.POST)
    AddressTowns getAddressTowns(AddressRequestParams param);

    @RequestMapping(value = Router.GOODS_MESSAGE_DELETE, method = RequestMethod.POST)
    DeleteMessageResponseParams deleteMessage(DeleteMessageRequestParams requestParams);

    @RequestMapping(value = Router.GOODS_MESSAGE_GET, method = RequestMethod.POST)
    GetMessageResponseParams getMessage(GetMessageRequestParams requestParams);
}



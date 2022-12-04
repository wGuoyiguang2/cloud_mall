package com.cibnvideo.jd.goods.service;

import com.cibnvideo.jd.common.service.BaseService;
import com.cibnvideo.jd.goods.params.address.*;
import com.cibnvideo.jd.goods.params.message.DeleteMessageRequestParams;
import com.cibnvideo.jd.goods.params.message.DeleteMessageResponseParams;
import com.cibnvideo.jd.goods.params.message.GetMessageRequestParams;
import com.cibnvideo.jd.goods.params.message.GetMessageResponseParams;
import com.cibnvideo.jd.goods.params.price.PriceRequestParams;
import com.cibnvideo.jd.goods.params.price.PriceResponseParams;
import com.cibnvideo.jd.goods.params.price.PriceTaxRequestParams;
import com.cibnvideo.jd.goods.params.price.PriceTaxResponseParams;
import com.cibnvideo.jd.goods.params.product.arealimit.AreaLimitRequestParams;
import com.cibnvideo.jd.goods.params.product.arealimit.AreaLimitResponseParams;
import com.cibnvideo.jd.goods.params.product.calendar.CalendarRequestParams;
import com.cibnvideo.jd.goods.params.product.calendar.CalendarResponseParams;
import com.cibnvideo.jd.goods.params.product.category.CategoryPageRequestParams;
import com.cibnvideo.jd.goods.params.product.category.CategoryPageResponseParams;
import com.cibnvideo.jd.goods.params.product.category.CategoryRequestParams;
import com.cibnvideo.jd.goods.params.product.category.CategoryResponseParams;
import com.cibnvideo.jd.goods.params.product.cod.IsCodRequestParams;
import com.cibnvideo.jd.goods.params.product.cod.IsCodResponseParams;
import com.cibnvideo.jd.goods.params.product.comment.ProductCommentsRequestParams;
import com.cibnvideo.jd.goods.params.product.comment.ProductCommentsResponseParams;
import com.cibnvideo.jd.goods.params.product.freight.FreightRequestParams;
import com.cibnvideo.jd.goods.params.product.freight.FreightResponseParams;
import com.cibnvideo.jd.goods.params.product.gift.GiftRequestParams;
import com.cibnvideo.jd.goods.params.product.gift.GiftResponseParams;
import com.cibnvideo.jd.goods.params.product.img.*;
import com.cibnvideo.jd.goods.params.product.pagenum.PageNumResponseParams;
import com.cibnvideo.jd.goods.params.product.productdetail.ProductDetailRequestParams;
import com.cibnvideo.jd.goods.params.product.sellcheck.SellCheckRequestParams;
import com.cibnvideo.jd.goods.params.product.sellcheck.SellCheckResponseParams;
import com.cibnvideo.jd.goods.params.product.sku.SkuByPageRequestParams;
import com.cibnvideo.jd.goods.params.product.sku.SkuByPageResponseParams;
import com.cibnvideo.jd.goods.params.product.sku.SkuRequestParams;
import com.cibnvideo.jd.goods.params.product.sku.SkuResponseParams;
import com.cibnvideo.jd.goods.params.product.state.ProductStateRequestParams;
import com.cibnvideo.jd.goods.params.product.state.ProductStateResponseParams;
import com.cibnvideo.jd.goods.params.product.style.MobileStyleRequestParams;
import com.cibnvideo.jd.goods.params.product.style.MobileStyleResponseParams;
import com.cibnvideo.jd.goods.params.product.style.PCStyleRequestParams;
import com.cibnvideo.jd.goods.params.product.style.PCStyleResponseParams;
import com.cibnvideo.jd.goods.params.search.SearchRequestParams;
import com.cibnvideo.jd.goods.params.similar.SimilarGoodsResponseParams;
import com.cibnvideo.jd.goods.params.similar.SimilarRequestParams;
import com.cibnvideo.jd.goods.params.stock.StockOrderRequestParams;
import com.cibnvideo.jd.goods.params.stock.StockOrderResponseParams;
import com.cibnvideo.jd.goods.params.stock.StockRequestParams;
import com.cibnvideo.jd.goods.params.stock.StockResponseParams;
import com.cibnvideo.jd.goods.params.yanbao.YanbaoRequestParams;
import com.cibnvideo.jd.goods.params.yanbao.YanbaoResponseParams;

/**
 * @Description: 商品接口
 * @Author: WangBin
 * @Date: 2018/6/22 18:35
 */
public interface GoodsService extends BaseService {
    PriceResponseParams getPrices(PriceRequestParams requestParams);

    PriceTaxResponseParams getTaxPrices(PriceTaxRequestParams requestParams);

    StockResponseParams getStocks(StockRequestParams requestParams);

    StockOrderResponseParams getOrderStocks(StockOrderRequestParams requestParams);

    AddressOneLevelResponseParams getOneLevelAddress();

    AddressTwoLevelResponseParams getTwoLevelAddress(AddressRequestParams requestParams);

    AddressThreeLevelResponseParams getThreeLevelAddress(AddressRequestParams requestParams);

    AddressFourLevelResponseParams getFourLevelAddress(AddressRequestParams requestParams);

    PageNumResponseParams getPageNum();

    SkuResponseParams getSkusByPageNum(SkuRequestParams requestParams);

    Object getProductDetail(ProductDetailRequestParams requestParams);

    PCStyleResponseParams getPCStyle(PCStyleRequestParams requestParams);

    MobileStyleResponseParams getMobileStyle(MobileStyleRequestParams requestParams);

    PCIMGResponseParams getPCProductDetailIMG(PCIMGRequestParams requestParams);

    H5IMGResponseParams getH5ProductDetailIMG(H5IMGRequestParams requestParams);

    SkuByPageResponseParams getSkusByPage(SkuByPageRequestParams requestParams);

    ProductStateResponseParams getProductState(ProductStateRequestParams requestParams);

    ProductImgResponseParams getProductImg(ProductImgRequestParams requestParams);

    ProductCommentsResponseParams getProductComments(ProductCommentsRequestParams requestParams);

    AreaLimitResponseParams getAreaLimit(AreaLimitRequestParams requestParams);

    IsCodResponseParams getIsCod(IsCodRequestParams requestParams);

    GiftResponseParams getGift(GiftRequestParams requestParams);

    FreightResponseParams getFreight(FreightRequestParams requestParams);

    SellCheckResponseParams getSellCheck(SellCheckRequestParams requestParams);

    CalendarResponseParams getCalendar(CalendarRequestParams requestParams);

    CategoryResponseParams getCategory(CategoryRequestParams requestParams);

    CategoryPageResponseParams getCategoryPage(CategoryPageRequestParams requestParams);

    GetMessageResponseParams getMessage(GetMessageRequestParams requestParams);

    DeleteMessageResponseParams deleteMessage(DeleteMessageRequestParams requestParams);

    SimilarGoodsResponseParams getSimilarGoods(SimilarRequestParams requestParams);

    YanbaoResponseParams getYanbaoList(YanbaoRequestParams requestParams);

    String getSearchResult(SearchRequestParams requestParams);
}

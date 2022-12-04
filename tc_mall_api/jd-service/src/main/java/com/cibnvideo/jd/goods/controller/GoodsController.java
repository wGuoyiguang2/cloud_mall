package com.cibnvideo.jd.goods.controller;

import com.cibnvideo.jd.common.constants.PathConstants;
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
import com.cibnvideo.jd.goods.service.GoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description: 商品controller
 * @Author: WangBin
 * @Date: 2018/6/22 18:35
 */
@RestController
public class GoodsController {

    @Autowired
    private GoodsService goodsService;

    /**
     * @param requestParams
     * @return
     */
    @PostMapping(path = PathConstants.GOODS_MESSAGE_DELETE)
    public DeleteMessageResponseParams deleteMessage(@RequestBody DeleteMessageRequestParams requestParams) {
        return goodsService.deleteMessage(requestParams);
    }

    @PostMapping(path = PathConstants.GOODS_MESSAGE_GET)
    public GetMessageResponseParams getMessage(@RequestBody GetMessageRequestParams requestParams) {
        return goodsService.getMessage(requestParams);
    }

    @PostMapping(path = PathConstants.GOODS_CATEGORY_PAGE_GET)
    public CategoryPageResponseParams getCategoryPage(@RequestBody CategoryPageRequestParams requestParams) {
        return goodsService.getCategoryPage(requestParams);
    }

    @PostMapping(path = PathConstants.GOODS_CATEGORY_GET)
    public CategoryResponseParams getCategory(@RequestBody CategoryRequestParams requestParams) {
        return goodsService.getCategory(requestParams);
    }

    //获取日历 TODO
    @PostMapping(path = PathConstants.GOODS_CALENDAR_GET)
    public CalendarResponseParams getCalendar(@RequestBody CalendarRequestParams requestParams) {
        return goodsService.getCalendar(requestParams);
    }

    @PostMapping(path = PathConstants.GOODS_SELL_CHECK_GET)
    public SellCheckResponseParams getSellCheck(@RequestBody SellCheckRequestParams requestParams) {
        return goodsService.getSellCheck(requestParams);
    }

    /**
     * 运费查询接口
     *
     * @param requestParams
     * @return
     */
    @PostMapping(path = PathConstants.GOODS_FREIGHT_GET)
    public FreightResponseParams getFreight(@RequestBody FreightRequestParams requestParams) {
        return goodsService.getFreight(requestParams);
    }

    @PostMapping(path = PathConstants.GOODS_GIFT_GET)
    public GiftResponseParams getGift(@RequestBody GiftRequestParams requestParams) {
        return goodsService.getGift(requestParams);
    }

    @PostMapping(path = PathConstants.GOODS_IS_COD_GET)
    public IsCodResponseParams getIsCod(@RequestBody IsCodRequestParams requestParams) {
        return goodsService.getIsCod(requestParams);
    }

    @PostMapping(path = PathConstants.GOODS_AREA_LIMIT_GET)
    public AreaLimitResponseParams getAreaLimit(@RequestBody AreaLimitRequestParams requestParams) {
        return goodsService.getAreaLimit(requestParams);
    }

    @PostMapping(path = PathConstants.GOODS_COMMENTS_GET)
    public ProductCommentsResponseParams getProductComments(@RequestBody ProductCommentsRequestParams requestParams) {
        return goodsService.getProductComments(requestParams);
    }

    @PostMapping(path = PathConstants.GOODS_IMG_BY_SKU)
    public ProductImgResponseParams getProductImg(@RequestBody ProductImgRequestParams requestParams) {
        return goodsService.getProductImg(requestParams);
    }

    @PostMapping(path = PathConstants.GOODS_STATE_GET)
    public ProductStateResponseParams getProductState(@RequestBody ProductStateRequestParams requestParams) {
        return goodsService.getProductState(requestParams);
    }

    @PostMapping(path = PathConstants.GOODS_SKUS_BY_PAGE)
    public SkuByPageResponseParams getSkusByPage(@RequestBody SkuByPageRequestParams requestParams) {
        return goodsService.getSkusByPage(requestParams);
    }

    @PostMapping(path = PathConstants.GOODS_H5_PRODUCT_DETAIL_IMG)
    public H5IMGResponseParams getH5ProductDetailIMG(@RequestBody H5IMGRequestParams requestParams) {
        return goodsService.getH5ProductDetailIMG(requestParams);
    }

    @PostMapping(path = PathConstants.GOODS_PC_PRODUCT_DETAIL_IMG)
    public PCIMGResponseParams getPCProductDetailIMG(@RequestBody PCIMGRequestParams requestParams) {
        return goodsService.getPCProductDetailIMG(requestParams);
    }

    @PostMapping(path = PathConstants.GOODS_MOBILE_STYLE_GET)
    public MobileStyleResponseParams getMobileStyle(@RequestBody MobileStyleRequestParams requestParams) {
        return goodsService.getMobileStyle(requestParams);
    }

    @PostMapping(path = PathConstants.GOODS_PC_STYLE_GET)
    public PCStyleResponseParams getPCStyle(@RequestBody PCStyleRequestParams requestParams) {
        return goodsService.getPCStyle(requestParams);
    }

    @PostMapping(path = PathConstants.GOODS_PRODUCT_DETAIL_GET)
    public Object getProductDetail(@RequestBody ProductDetailRequestParams requestParams) {
        return goodsService.getProductDetail(requestParams);
    }

    @PostMapping(path = PathConstants.GOODS_PRODUCT_POOL_GET_PAGENUM)
    public PageNumResponseParams getPageNum() {
        return goodsService.getPageNum();
    }

    @PostMapping(path = PathConstants.GOODS_SKUS_BY_PAGENUM)
    public SkuResponseParams getSkusByPageNum(@RequestBody SkuRequestParams requestParams) {
        return goodsService.getSkusByPageNum(requestParams);
    }

    @PostMapping(path = PathConstants.GOODS_PRICE_GET)
    public PriceResponseParams getPrices(@RequestBody PriceRequestParams requestParams) {
        return goodsService.getPrices(requestParams);
    }

    @PostMapping(path = PathConstants.GOODS_TAX_PRICE_GET)
    public PriceTaxResponseParams getTaxPrices(@RequestBody PriceTaxRequestParams requestParams) {
        return goodsService.getTaxPrices(requestParams);
    }

    @PostMapping(path = PathConstants.GOODS_STOCK_GET)
    public StockResponseParams getStocks(@RequestBody StockRequestParams requestParams) {
        return goodsService.getStocks(requestParams);
    }

    @PostMapping(path = PathConstants.GOODS_ORDER_STOCK_GET)
    public StockOrderResponseParams getOrderStocks(@RequestBody StockOrderRequestParams requestParams) {
        return goodsService.getOrderStocks(requestParams);
    }

    @PostMapping(path = PathConstants.GOODS_ADDRESS_ONE_LEVEL_GET)
    public AddressOneLevelResponseParams getOneLevelAddress() {
        return goodsService.getOneLevelAddress();
    }

    @PostMapping(path = PathConstants.GOODS_ADDRESS_TWO_LEVEL_GET)
    public AddressTwoLevelResponseParams getTwoLevelAddress(@RequestBody AddressRequestParams addressRequestParams) {
        return goodsService.getTwoLevelAddress(addressRequestParams);
    }

    @PostMapping(path = PathConstants.GOODS_ADDRESS_THREE_LEVEL_GET)
    public AddressThreeLevelResponseParams getThreeLevelAddress(@RequestBody AddressRequestParams addressRequestParams) {
        return goodsService.getThreeLevelAddress(addressRequestParams);
    }

    @PostMapping(path = PathConstants.GOODS_ADDRESS_FOUR_LEVEL_GET)
    public AddressFourLevelResponseParams getFourLevelAddress(@RequestBody AddressRequestParams addressRequestParams) {
        return goodsService.getFourLevelAddress(addressRequestParams);
    }

    @PostMapping(path = PathConstants.GOODS_SIMILAR_GET)
    public SimilarGoodsResponseParams getSimilarGoods(@RequestBody SimilarRequestParams requestParams) {
        return goodsService.getSimilarGoods(requestParams);
    }

    @PostMapping(path = PathConstants.GOODS_YANBAO_GET)
    public YanbaoResponseParams getYanbaoList(@RequestBody YanbaoRequestParams requestParams) {
        return goodsService.getYanbaoList(requestParams);
    }

    /**
     * 调用京东搜索接口
     *
     * @param requestParams
     * @return
     */
    @PostMapping(path = PathConstants.V1_PRODUCT_SEARCH)
    public String getSearchResult(@RequestBody SearchRequestParams requestParams) {
        return goodsService.getSearchResult(requestParams);
    }
}

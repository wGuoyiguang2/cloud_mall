package com.cibnvideo.jd.goods.service.impl;

import com.cibnvideo.jd.common.constants.JdMethodConstants;
import com.cibnvideo.jd.common.service.impl.BaseServiceImpl;
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
import com.cibnvideo.jd.goods.params.product.productdetail.ProductDetailResponseNoBookParams;
import com.cibnvideo.jd.goods.params.product.productdetail.ProductDetailResponseParams;
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
import com.google.gson.Gson;
import org.springframework.stereotype.Service;

/**
 * @Description: 商品service impl
 * @Author: WangBin
 * @Date: 2018/6/22 18:35
 */
@Service
public class GoodsServiceImpl extends BaseServiceImpl implements GoodsService {
    private Gson gson = new Gson();

    /**
     * 查询商品价格
     *
     * @param requestParams
     * @return
     */
    @Override
    public PriceResponseParams getPrices(PriceRequestParams requestParams) {
        String json = this.request(JdMethodConstants.getPriceMethod(), requestParams);
        return gson.fromJson(json, PriceResponseParams.class);
    }

    @Override
    public PriceTaxResponseParams getTaxPrices(PriceTaxRequestParams requestParams) {
        String json = this.request(JdMethodConstants.getPriceTaxMethod(), requestParams);
        return gson.fromJson(json, PriceTaxResponseParams.class);
    }

    /**
     * 查询库存信息
     *
     * @param requestParams
     * @return
     */
    @Override
    public StockResponseParams getStocks(StockRequestParams requestParams) {
        String json = this.request(JdMethodConstants.getStockMethod(), requestParams);
        return gson.fromJson(json, StockResponseParams.class);
    }

    /**
     * 查询库存信息
     *
     * @param requestParams
     * @return
     */
    @Override
    public StockOrderResponseParams getOrderStocks(StockOrderRequestParams requestParams) {
        String json = this.request(JdMethodConstants.getStockOrderMethod(), requestParams);
        return gson.fromJson(json, StockOrderResponseParams.class);
    }

    @Override
    public AddressOneLevelResponseParams getOneLevelAddress() {
        AddressRequestParams requestParams = new AddressRequestParams();
        String json = this.request(JdMethodConstants.getAddressLevel1Method(), requestParams);
        return gson.fromJson(json, AddressOneLevelResponseParams.class);
    }

    @Override
    public AddressTwoLevelResponseParams getTwoLevelAddress(AddressRequestParams requestParams) {
        String json = this.request(JdMethodConstants.getAddressLevel2Method(), requestParams);
        return gson.fromJson(json, AddressTwoLevelResponseParams.class);
    }

    @Override
    public AddressThreeLevelResponseParams getThreeLevelAddress(AddressRequestParams requestParams) {
        String json = this.request(JdMethodConstants.getAddressLevel3Method(), requestParams);
        return gson.fromJson(json, AddressThreeLevelResponseParams.class);
    }

    @Override
    public AddressFourLevelResponseParams getFourLevelAddress(AddressRequestParams requestParams) {
        String json = this.request(JdMethodConstants.getAddressLevel4Method(), requestParams);
        return gson.fromJson(json, AddressFourLevelResponseParams.class);
    }

    @Override
    public PageNumResponseParams getPageNum() {
        String json = this.request(JdMethodConstants.getProductPoolPageNum(), null);
        return gson.fromJson(json, PageNumResponseParams.class);
    }

    @Override
    public SkuResponseParams getSkusByPageNum(SkuRequestParams requestParams) {
        String json = this.request(JdMethodConstants.getSkusByPageNum(), requestParams);
        return gson.fromJson(json, SkuResponseParams.class);
    }

    @Override
    public Object getProductDetail(ProductDetailRequestParams requestParams) {
        String json = this.request(JdMethodConstants.getProductDetail(), requestParams);
        //sku长度为非8位时是非图书音响类目类型
        if (requestParams.getSku().toString().length() != 8) {
            return gson.fromJson(json, ProductDetailResponseNoBookParams.class);
        }
        return gson.fromJson(json, ProductDetailResponseParams.class);
    }

    @Override
    public PCStyleResponseParams getPCStyle(PCStyleRequestParams requestParams) {
        String json = this.request(JdMethodConstants.getPcStyle(), requestParams);
        return gson.fromJson(json, PCStyleResponseParams.class);
    }

    @Override
    public MobileStyleResponseParams getMobileStyle(MobileStyleRequestParams requestParams) {
        String json = this.request(JdMethodConstants.getMobileStyle(), requestParams);
        return gson.fromJson(json, MobileStyleResponseParams.class);
    }

    @Override
    public PCIMGResponseParams getPCProductDetailIMG(PCIMGRequestParams requestParams) {
        String json = this.request(JdMethodConstants.getPcProductDetailImg(), requestParams);
        return gson.fromJson(json, PCIMGResponseParams.class);
    }

    @Override
    public H5IMGResponseParams getH5ProductDetailIMG(H5IMGRequestParams requestParams) {
        String json = this.request(JdMethodConstants.getH5ProductDetailImg(), requestParams);
        return gson.fromJson(json, H5IMGResponseParams.class);
    }

    @Override
    public SkuByPageResponseParams getSkusByPage(SkuByPageRequestParams requestParams) {
        String json = this.request(JdMethodConstants.getSkusByPage(), requestParams);
        return gson.fromJson(json, SkuByPageResponseParams.class);
    }

    @Override
    public ProductStateResponseParams getProductState(ProductStateRequestParams requestParams) {
        String json = this.request(JdMethodConstants.getProductState(), requestParams);
        return gson.fromJson(json, ProductStateResponseParams.class);
    }

    @Override
    public ProductImgResponseParams getProductImg(ProductImgRequestParams requestParams) {
        String json = this.request(JdMethodConstants.getProductImg(), requestParams);
        return gson.fromJson(json, ProductImgResponseParams.class);
    }

    @Override
    public ProductCommentsResponseParams getProductComments(ProductCommentsRequestParams requestParams) {
        String json = this.request(JdMethodConstants.getProductComments(), requestParams);
        return gson.fromJson(json, ProductCommentsResponseParams.class);
    }

    @Override
    public AreaLimitResponseParams getAreaLimit(AreaLimitRequestParams requestParams) {
        String json = this.request(JdMethodConstants.getCheckAreaLimit(), requestParams);
        return gson.fromJson(json, AreaLimitResponseParams.class);
    }

    @Override
    public IsCodResponseParams getIsCod(IsCodRequestParams requestParams) {
        String json = this.request(JdMethodConstants.getIsCod(), requestParams);
        return gson.fromJson(json, IsCodResponseParams.class);
    }

    @Override
    public GiftResponseParams getGift(GiftRequestParams requestParams) {
        String json = this.request(JdMethodConstants.getSkuGift(), requestParams);
        return gson.fromJson(json, GiftResponseParams.class);
    }

    @Override
    public FreightResponseParams getFreight(FreightRequestParams requestParams) {
        String json = this.request(JdMethodConstants.getFREIGHT(), requestParams);
        return gson.fromJson(json, FreightResponseParams.class);
    }

    @Override
    public SellCheckResponseParams getSellCheck(SellCheckRequestParams requestParams) {
        String json = this.request(JdMethodConstants.getSellCheck(), requestParams);
        return gson.fromJson(json, SellCheckResponseParams.class);
    }

    @Override
    public CalendarResponseParams getCalendar(CalendarRequestParams requestParams) {
        String json = this.request(JdMethodConstants.getCALENDAR(), requestParams);
        return gson.fromJson(json, CalendarResponseParams.class);
    }

    @Override
    public CategoryResponseParams getCategory(CategoryRequestParams requestParams) {
        String json = this.request(JdMethodConstants.getCATEGORY(), requestParams);
        return gson.fromJson(json, CategoryResponseParams.class);
    }

    @Override
    public CategoryPageResponseParams getCategoryPage(CategoryPageRequestParams requestParams) {
        String json = this.request(JdMethodConstants.getCategoryPage(), requestParams);
        return gson.fromJson(json, CategoryPageResponseParams.class);
    }

    @Override
    public GetMessageResponseParams getMessage(GetMessageRequestParams requestParams) {
        String json = this.request(JdMethodConstants.getMessageGet(), requestParams);
        return gson.fromJson(json, GetMessageResponseParams.class);
    }

    @Override
    public DeleteMessageResponseParams deleteMessage(DeleteMessageRequestParams requestParams) {
        String json = this.request(JdMethodConstants.getMessageDelete(), requestParams);
        return gson.fromJson(json, DeleteMessageResponseParams.class);
    }

    @Override
    public SimilarGoodsResponseParams getSimilarGoods(SimilarRequestParams requestParams) {
        String json = this.request(JdMethodConstants.getSimilarGoods(), requestParams);
        return gson.fromJson(json, SimilarGoodsResponseParams.class);
    }

    @Override
    public YanbaoResponseParams getYanbaoList(YanbaoRequestParams requestParams) {
        String json = this.request(JdMethodConstants.getYanbaoGoods(), requestParams);
        return gson.fromJson(json, YanbaoResponseParams.class);
    }

    /**
     * 调用京东搜索接口
     *
     * @param requestParams
     * @return
     */
    @Override
    public String getSearchResult(SearchRequestParams requestParams) {
        return this.request(JdMethodConstants.getSEARCH(), requestParams);
    }
}

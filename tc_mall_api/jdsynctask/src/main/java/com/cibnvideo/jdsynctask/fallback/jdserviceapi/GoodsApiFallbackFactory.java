package com.cibnvideo.jdsynctask.fallback.jdserviceapi;


import com.cibnvideo.jdsynctask.jdserviceapi.GoodsApi;
import com.cibnvideo.jdsynctask.model.*;
import feign.hystrix.FallbackFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;


@Component
public class GoodsApiFallbackFactory implements FallbackFactory<GoodsApi> {
    private Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public GoodsApi create(Throwable throwable) {
        return new GoodsApi() {
            @Override
            public PageNum getPageNum() {
                return null;
            }

            @Override
            public Sku getSkus(PageNumRequestParams param) {
                return null;
            }

            @Override
            public SellPrice getSellPrice(SkuRequestParams param) {
                return null;
            }

            @Override
            public Product getProductDetail(SkuShowRequestParams param) {
                return null;
            }

            @Override
            public StyleOfPc getStyleOfPc(SkuRequestParams param) {
                return null;
            }

            @Override
            public StyleOfMobile getStyleOfMobile(SkuRequestParams param) {
                return null;
            }

            @Override
            public PictureOfPc getPictureOfPc(SkuRequestParams param) {
                return null;
            }

            @Override
            public PictureOfMobile getPictureOfMobile(SkuIdRequestParams param) {
                return null;
            }

            @Override
            public Category getCategorys(CategoryPageRequestParams param) {
                return null;
            }

            @Override
            public AddressProvinces getAddressProvinces() {
                return null;
            }

            @Override
            public AddressCitys getAddressCityss(AddressRequestParams param) {
                return null;
            }

            @Override
            public AddressCountys getAddressCountys(AddressRequestParams param) {
                return null;
            }

            @Override
            public AddressTowns getAddressTowns(AddressRequestParams param) {
                return null;
            }

            @Override
            public DeleteMessageResponseParams deleteMessage(DeleteMessageRequestParams requestParams) {
                return null;
            }

            @Override
            public GetMessageResponseParams getMessage(GetMessageRequestParams requestParams) {
                return null;
            }
        };
    }
}

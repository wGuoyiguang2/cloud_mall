package com.cibnvideo.jdsynctask.service.impl;

import com.cibnvideo.jdsynctask.jdserviceapi.GoodsApi;
import com.cibnvideo.jdsynctask.model.*;
import com.cibnvideo.jdsynctask.service.JdGoodsService;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class JdGoodsServiceImpl implements JdGoodsService {
    Logger logger = LoggerFactory.getLogger(getClass());

    private final String JD_ERRORCODE_OK = "0000";

    @Autowired
    GoodsApi goodsApi;

    @Override
    public List<PageNumInfo> getPageNum() {
        PageNum pageNum = goodsApi.getPageNum();
        if (pageNum != null) {
            PageNumResponse pageNumResponse = pageNum.getBiz_product_PageNum_query_response();
            if (pageNumResponse != null) {
                String resultCodePageNum = pageNumResponse.getResultCode();
                if (StringUtils.equals(resultCodePageNum, JD_ERRORCODE_OK)) {
                    return pageNumResponse.getResult();
                } else {
                    logger.warn("getPageNum failed ==> resultCode = " + resultCodePageNum + " resultMessage = " + pageNumResponse.getResultMessage());
                }
            } else {
                logger.warn("getPageNum pageNumResponse is null");
            }
        }
        return null;
    }

    @Override
    public List<String> getSkus(String pageNum) {
        PageNumRequestParams pageNumRequestParams = new PageNumRequestParams();
        pageNumRequestParams.setPageNum(pageNum);
        Sku skuInfo = goodsApi.getSkus(pageNumRequestParams);
        if (skuInfo != null) {
            SkuResponse skuResponse = skuInfo.getBiz_product_sku_query_response();
            if (skuResponse != null) {
                String resultCodeSkuResponse = skuResponse.getResultCode();
                if (StringUtils.equals(resultCodeSkuResponse, JD_ERRORCODE_OK)) {
                    String skuIds = skuResponse.getResult();
                    if (StringUtils.isNotEmpty(skuIds)) {
                        String[] skuIdList = skuIds.split(",");
                        if (skuIdList != null && skuIdList.length > 0) {
                            return Arrays.asList(skuIdList);
                        }
                    } else {
                        logger.warn("getSkuIds is empty");
                    }
                } else {
                    logger.warn("getSkus failed ==> resultCode = " + resultCodeSkuResponse + " resultMessage = " + skuResponse.getResultCode());
                }
            } else {
                logger.warn("getBiz_product_sku_query_response is null");
            }
        } else {
            logger.error("goodsApi getSkus is null");
        }
        return null;
    }

    @Override
    public Map<Long, SellPriceResult> getSellPrice(List<Long> skuList) {
        SkuRequestParams skuRequestParams = new SkuRequestParams();
        skuRequestParams.setSku(StringUtils.join(skuList.toArray(), ","));
        SellPrice sellPrice = goodsApi.getSellPrice(skuRequestParams);
        if (sellPrice != null) {
            SellPriceResponse sellPriceResponse = sellPrice.getJd_kpl_open_getsellprice_query_response();
            if (sellPriceResponse != null) {
                String sellPriceResultCode = sellPriceResponse.getResultCode();
                if (StringUtils.equals(sellPriceResultCode, JD_ERRORCODE_OK)) {
                    List<SellPriceResult> sellPriceResults = sellPriceResponse.getResult();
                    if (sellPriceResults != null && sellPriceResults.size() > 0) {
                        return sellPriceResults.stream().collect(Collectors.toMap(SellPriceResult::getSkuId, Function.identity()));
                    }
                } else {
                    logger.warn("getSellPrice failed ==> resultCode = " + sellPriceResultCode);
                }

            } else {
                logger.error("sellPriceResponse is null");
            }
        } else {
            logger.error("sellprice is null");
        }
        return null;
    }

    @Override
    public ProductResult getProductDetail(Long sku) {
        SkuShowRequestParams skuShowRequestParams = new SkuShowRequestParams();
        skuShowRequestParams.setSku(sku.toString());
        skuShowRequestParams.setShow(true);
        Product product = goodsApi.getProductDetail(skuShowRequestParams);
        if (product != null) {
            ProductResponse productResponse = product.getBiz_product_detail_query_response();
            if (productResponse != null) {
                String resultCodeProductResponse = productResponse.getResultCode();
                if (StringUtils.equals(resultCodeProductResponse, JD_ERRORCODE_OK)) {
                    return productResponse.getResult();
                } else {
                    logger.warn("getProductDetail failed ==> resultCode = " + resultCodeProductResponse);
                }
            } else {
                logger.warn("productResponse is null, sku = " + sku);
            }
        } else {
            logger.error("goodsApi getProductDetail is null");
        }
        return null;
    }

    @Override
    public StyleOfPcResult getStyleOfPc(Long sku) {
        SkuRequestParams skuRequestParams = new SkuRequestParams();
        skuRequestParams.setSku(sku.toString());
        StyleOfPc styleOfPc = goodsApi.getStyleOfPc(skuRequestParams);
        if (styleOfPc != null) {
            StyleOfPcResponse styleOfPcResponse = styleOfPc.getJd_kpl_open_item_getwarestyleandjsbywareid_response();
            if (styleOfPcResponse != null) {
                Integer resultCode = styleOfPcResponse.getCode();
                if (resultCode != null && resultCode == 0) {
                    return styleOfPcResponse.getDetail();
                } else {
                    logger.error("getStyleOfPc resultCode = " + resultCode);
                }
            } else {
                logger.warn("getStyleOfPc styleOfPcResponse is null");
            }
        } else {
            logger.warn("getStyleOfPc styleOfPc is null");
        }
        return null;
    }

    @Override
    public StyleOfMobileResult getStyleOfMobile(Long sku) {
        SkuRequestParams skuRequestParams = new SkuRequestParams();
        skuRequestParams.setSku(sku.toString());
        StyleOfMobile styleOfMobile = goodsApi.getStyleOfMobile(skuRequestParams);
        if (styleOfMobile != null) {
            StyleOfMobileResponse styleOfMobileResponse = styleOfMobile.getJd_kpl_open_item_getmobilewarestyleandjsbywareid_response();
            if (styleOfMobileResponse != null) {
                Integer resultCode = styleOfMobileResponse.getCode();
                if (resultCode != null && resultCode == 0) {
                    return styleOfMobileResponse.getDetail();
                } else {
                    logger.error("getStyleOfMobile resultCode = " + resultCode);
                }
            } else {
                logger.warn("getStyleOfMobile styleOfMobileResponse is null");
            }
        } else {
            logger.warn("getStyleOfMobile styleOfMobile is null");
        }

        return null;
    }

    @Override
    public PictureOfPcResponse getPictureOfPc(Long sku) {
        SkuRequestParams skuRequestParams = new SkuRequestParams();
        skuRequestParams.setSku(sku.toString());
        PictureOfPc pictureOfPc = goodsApi.getPictureOfPc(skuRequestParams);
        if (pictureOfPc != null) {
            PictureOfPcResponse pictureOfPcResponse = pictureOfPc.getJd_kepler_item_querybigfieldconvertsku_response();
            if (pictureOfPcResponse != null) {
                String resultCode = pictureOfPcResponse.getCode();
                if (StringUtils.equals(resultCode, "0")) {
                    return pictureOfPcResponse;
                } else {
                    logger.error("getPictureOfPc resultCode = " + resultCode);
                }
            } else {
                logger.error("getPictureOfPc pictureOfPcResponse is null");
            }
        } else {
            logger.warn("getPictureOfPc pictureOfPc is null");
        }
        return null;
    }

    @Override
    public PictureOfMobileResponse getPictureOfMobile(Long sku) {
        SkuIdRequestParams skuIdRequestParams = new SkuIdRequestParams();
        skuIdRequestParams.setSkuId(sku);
        PictureOfMobile pictureOfMobile = goodsApi.getPictureOfMobile(skuIdRequestParams);
        if (pictureOfMobile != null) {
            PictureOfMobileResponse pictureOfMobileResponse = pictureOfMobile.getJingdong_new_ware_mobilebigfield_get_response();
            if (pictureOfMobileResponse != null) {
                String resultCode = pictureOfMobileResponse.getCode();
                if (StringUtils.equals(resultCode, "0")) {
                    return pictureOfMobileResponse;
                } else {
                    logger.error("getPictureOfMobile resultCode = " + resultCode);
                }
            } else {
                logger.error("getPictureOfMobile pictureOfMobileResponse is null");
            }
        } else {
            logger.warn("getPictureOfMobile pictureOfMobile is null");
        }
        return null;
    }

    @Override
    public CategoryResult getCategorys(CategoryPageRequestParams param) {
        Category category = goodsApi.getCategorys(param);
        if (category != null) {
            CategoryResponse categoryResponse = category.getJd_biz_product_getcategorys_response();
            if (categoryResponse != null) {
                return categoryResponse.getResult();
            } else {
                logger.warn("getCategorys categoryResponse is null");
            }
        } else {
            logger.warn("getCategorys categorys is null");
        }
        return null;
    }

    @Override
    public Map<String, Integer> getAddressProvinces() {
        AddressProvinces addressProvinces = goodsApi.getAddressProvinces();
        if (addressProvinces != null) {
            AddressResponse addressResponse = addressProvinces.getBiz_address_allProvinces_query_response();
            if (addressResponse != null) {
                String resultCode = addressResponse.getResultCode();
                if (StringUtils.equals(resultCode, JD_ERRORCODE_OK)) {
                    return addressResponse.getResult();
                } else {
                    logger.error("getAddressProvinces failed, resultCode = " + resultCode + " resultMessage = " + addressResponse.getResultMessage());
                }
            } else {
                logger.error("getAddressProvinces addressResponse is null");
            }
        } else {
            logger.warn("getAddressProvinces addressProvinces is null");
        }
        return null;
    }

    @Override
    public Map<String, Integer> getAddressCityss(Integer provinceId) {
        AddressRequestParams addressRequestParams = new AddressRequestParams();
        addressRequestParams.setId(provinceId);
        AddressCitys addressCitys = goodsApi.getAddressCityss(addressRequestParams);
        if (addressCitys != null) {
            AddressResponse addressResponse = addressCitys.getBiz_address_citysByProvinceId_query_response();
            if (addressResponse != null) {
                String resultCode = addressResponse.getResultCode();
                if (StringUtils.equals(resultCode, JD_ERRORCODE_OK)) {
                    return addressResponse.getResult();
                } else {
                    logger.error("getAddressCityss failed, resultCode = " + resultCode + " resultMessage = " + addressResponse.getResultMessage());
                }
            } else {
                logger.error("getAddressCityss addressResponse is null");
            }
        } else {
            logger.warn("getAddressCityss addressCitys is null");
        }
        return null;
    }

    @Override
    public Map<String, Integer> getAddressCountys(Integer cityId) {
        AddressRequestParams addressRequestParams = new AddressRequestParams();
        addressRequestParams.setId(cityId);
        AddressCountys addressCountys = goodsApi.getAddressCountys(addressRequestParams);
        if (addressCountys != null) {
            AddressResponse addressResponse = addressCountys.getBiz_address_countysByCityId_query_response();
            if (addressResponse != null) {
                String resultCode = addressResponse.getResultCode();
                if (StringUtils.equals(resultCode, JD_ERRORCODE_OK)) {
                    return addressResponse.getResult();
                } else {
                    logger.warn("getAddressCountys failed, resultCode = " + resultCode + " resultMessage = " + addressResponse.getResultMessage());
                }
            }
        } else {
            logger.warn("getAddressCountys addressCountys is null");
        }
        return null;
    }

    @Override
    public Map<String, Integer> getAddressTowns(Integer countyId) {
        AddressRequestParams addressRequestParams = new AddressRequestParams();
        addressRequestParams.setId(countyId);
        AddressTowns addressTowns = goodsApi.getAddressTowns(addressRequestParams);
        if (addressTowns != null) {
            AddressResponse addressResponse = addressTowns.getBiz_address_townsByCountyId_query_response();
            if (addressResponse != null) {
                String resultCode = addressResponse.getResultCode();
                if (StringUtils.equals(resultCode, JD_ERRORCODE_OK)) {
                    return addressResponse.getResult();
                } else {
                    logger.warn("getAddressTowns failed, resultCode = " + resultCode + " resultMessage = " + addressResponse.getResultMessage());
                }
            } else {
                logger.warn("getAddressTowns addressResponse is null");
            }
        } else {
            logger.warn("getAddressTowns addressTowns is null");
        }
        return null;
    }

    @Override
    public boolean deleteMessage(String id) {
        DeleteMessageRequestParams deleteMessageRequestParams = new DeleteMessageRequestParams();
        deleteMessageRequestParams.setId(id);
        DeleteMessageResponseParams deleteMessageResponseParams = goodsApi.deleteMessage(deleteMessageRequestParams);
        if (deleteMessageResponseParams != null) {
            DeleteMessageResponse deleteMessageResponse = deleteMessageResponseParams.getBiz_message_del_response();
            if (deleteMessageResponse != null) {
                return deleteMessageResponse.getResult();
            }
        }
        return false;
    }

    @Override
    public List<GetMessageResponseResult> getMessage(String types) {
        GetMessageRequestParams getMessageRequestParams = new GetMessageRequestParams();
        getMessageRequestParams.setType(types);
        GetMessageResponseParams getMessageResponseParams = goodsApi.getMessage(getMessageRequestParams);
        if (getMessageResponseParams != null) {
            GetMessageResponse getMessageResponse = getMessageResponseParams.getBiz_message_get_response();
            if (getMessageResponse != null) {
                String resultCode = getMessageResponse.getResultCode();
                if (StringUtils.equals(resultCode, JD_ERRORCODE_OK)) {
                    return getMessageResponse.getResult();
                } else {
                    logger.error("getMessage failed, resultCode = " + resultCode + " resultMessage = " + getMessageResponse.getResultMessage());
                }
            } else {
                logger.warn("goodsApi getBiz_message_get_response is null");
            }
        } else {
            logger.warn("getMessage getMessageResponseParams is null");
        }
        return null;
    }
}

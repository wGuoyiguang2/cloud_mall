package com.cibnvideo.jd.goods.params.product.productdetail;

import com.cibnvideo.jd.common.params.BaseResponseParams;

/**
 * @Description:
 * @Author: WangBin
 * @Date: 2018/6/29 14:08
 */
public class ProductDetailResponseParams {
    private ProductDetailResponseVo biz_product_detail_query_response;

    public ProductDetailResponseVo getBiz_product_detail_query_response() {
        return biz_product_detail_query_response;
    }

    public void setBiz_product_detail_query_response(ProductDetailResponseVo biz_product_detail_query_response) {
        this.biz_product_detail_query_response = biz_product_detail_query_response;
    }
    class ProductDetailResponseVo extends BaseResponseParams {
        private ProductDetailVo result;

        public ProductDetailVo getResult() {
            return result;
        }

        public void setResult(ProductDetailVo result) {
            this.result = result;
        }

        class ProductDetailVo{
            private Long sku;//	Long	商品编号
            private String skuType;//	String	book
            private String Author;//	String	著者
            private String Editer;//	String	编者
            private String Transfer;//	String	译者
            private String Drawer;//	String	绘者
            private String Proofreader;//	String	校对
            private Integer ISBN;//	Integer	ISBN
            private String Publishers;//	String	出版社
            private String Sheet;//	String	开本
            private Integer Pages;//	Integer	页数
            private String Package;//	String	包装
            private String PublishTime;//	String	出版时间
            private String BatchNo;//	String	版次
            private String PrIntegerTime;//	String	印刷时间
            private String PrIntegerNo;//	String	印次
            private Integer PackNum;//Integer	套装数量
            private String Language;//	String	正文语言
            private String Papers;//	String	用纸
            private String Brand;//	String	品牌
            private String comments;//	String	媒体评论
            private String image;//	String	插图
            private String contentDesc;//	String	内容摘要
            private String relatedProducts;//	String	产品描述
            private String editerDesc;//	String	编辑推荐
            private String catalogue;//	String	目录
            private String bookAbstract;//	String	精彩摘要
            private String authorDesc;//	String	作者简介
            private String Integerroduction;//	String	前言

            public Long getSku() {
                return sku;
            }

            public void setSku(Long sku) {
                this.sku = sku;
            }

            public String getSkuType() {
                return skuType;
            }

            public void setSkuType(String skuType) {
                this.skuType = skuType;
            }

            public String getAuthor() {
                return Author;
            }

            public void setAuthor(String author) {
                Author = author;
            }

            public String getEditer() {
                return Editer;
            }

            public void setEditer(String editer) {
                Editer = editer;
            }

            public String getTransfer() {
                return Transfer;
            }

            public void setTransfer(String transfer) {
                Transfer = transfer;
            }

            public String getDrawer() {
                return Drawer;
            }

            public void setDrawer(String drawer) {
                Drawer = drawer;
            }

            public String getProofreader() {
                return Proofreader;
            }

            public void setProofreader(String proofreader) {
                Proofreader = proofreader;
            }

            public Integer getISBN() {
                return ISBN;
            }

            public void setISBN(Integer ISBN) {
                this.ISBN = ISBN;
            }

            public String getPublishers() {
                return Publishers;
            }

            public void setPublishers(String publishers) {
                Publishers = publishers;
            }

            public String getSheet() {
                return Sheet;
            }

            public void setSheet(String sheet) {
                Sheet = sheet;
            }

            public Integer getPages() {
                return Pages;
            }

            public void setPages(Integer pages) {
                Pages = pages;
            }

            public String getPackage() {
                return Package;
            }

            public void setPackage(String aPackage) {
                Package = aPackage;
            }

            public String getPublishTime() {
                return PublishTime;
            }

            public void setPublishTime(String publishTime) {
                PublishTime = publishTime;
            }

            public String getBatchNo() {
                return BatchNo;
            }

            public void setBatchNo(String batchNo) {
                BatchNo = batchNo;
            }

            public String getPrIntegerTime() {
                return PrIntegerTime;
            }

            public void setPrIntegerTime(String prIntegerTime) {
                PrIntegerTime = prIntegerTime;
            }

            public String getPrIntegerNo() {
                return PrIntegerNo;
            }

            public void setPrIntegerNo(String prIntegerNo) {
                PrIntegerNo = prIntegerNo;
            }

            public Integer getPackNum() {
                return PackNum;
            }

            public void setPackNum(Integer packNum) {
                PackNum = packNum;
            }

            public String getLanguage() {
                return Language;
            }

            public void setLanguage(String language) {
                Language = language;
            }

            public String getPapers() {
                return Papers;
            }

            public void setPapers(String papers) {
                Papers = papers;
            }

            public String getBrand() {
                return Brand;
            }

            public void setBrand(String brand) {
                Brand = brand;
            }

            public String getComments() {
                return comments;
            }

            public void setComments(String comments) {
                this.comments = comments;
            }

            public String getImage() {
                return image;
            }

            public void setImage(String image) {
                this.image = image;
            }

            public String getContentDesc() {
                return contentDesc;
            }

            public void setContentDesc(String contentDesc) {
                this.contentDesc = contentDesc;
            }

            public String getRelatedProducts() {
                return relatedProducts;
            }

            public void setRelatedProducts(String relatedProducts) {
                this.relatedProducts = relatedProducts;
            }

            public String getEditerDesc() {
                return editerDesc;
            }

            public void setEditerDesc(String editerDesc) {
                this.editerDesc = editerDesc;
            }

            public String getCatalogue() {
                return catalogue;
            }

            public void setCatalogue(String catalogue) {
                this.catalogue = catalogue;
            }

            public String getBookAbstract() {
                return bookAbstract;
            }

            public void setBookAbstract(String bookAbstract) {
                this.bookAbstract = bookAbstract;
            }

            public String getAuthorDesc() {
                return authorDesc;
            }

            public void setAuthorDesc(String authorDesc) {
                this.authorDesc = authorDesc;
            }

            public String getIntegerroduction() {
                return Integerroduction;
            }

            public void setIntegerroduction(String integerroduction) {
                Integerroduction = integerroduction;
            }
        }
    }
}

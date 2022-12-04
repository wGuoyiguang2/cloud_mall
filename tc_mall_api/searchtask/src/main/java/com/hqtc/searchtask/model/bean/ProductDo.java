package com.hqtc.searchtask.model.bean;

import org.apache.commons.codec.binary.StringUtils;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

public class ProductDo implements Serializable {
    private Long sku;
    private String name = "";
    private int cat0;
    private String cat0name;
    private int cat1;
    private String cat1name;
    private int cat2;
    private String cat2name;
    private String catename;
    private String cate;
    private String brandname;
    private String collectionids;
    private int sales;
    private String imagepath = "";
    private int state;
    private BigDecimal price;
    private BigDecimal agreeprice;
    private BigDecimal jdprice;
    private String ctime;
    private String utime;

    public Long getSku() {
        return sku;
    }

    public void setSku(Long sku) {
        this.sku = sku;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCat0() {
        return cat0;
    }

    public void setCat0(int cat0) {
        this.cat0 = cat0;
    }

    public String getCat0name() {
        return cat0name;
    }

    public void setCat0name(String cat0name) {
        this.cat0name = cat0name;
    }

    public int getCat1() {
        return cat1;
    }

    public void setCat1(int cat1) {
        this.cat1 = cat1;
    }

    public String getCat1name() {
        return cat1name;
    }

    public void setCat1name(String cat1name) {
        this.cat1name = cat1name;
    }

    public int getCat2() {
        return cat2;
    }

    public void setCat2(int cat2) {
        this.cat2 = cat2;
    }

    public String getCat2name() {
        return cat2name;
    }

    public void setCat2name(String cat2name) {
        this.cat2name = cat2name;
    }

    public String getCatename() {
        return catename;
    }

    public void setCatename(String catename) {
        this.catename = catename;
    }

    public String getCate() {
        return cate;
    }

    public void setCate(String cate) {
        this.cate = cate;
    }

    public String getBrandname() {
        return brandname;
    }

    public void setBrandname(String brandname) {
        this.brandname = brandname;
    }

    public String getCollectionids() {
        return collectionids;
    }

    public void setCollectionids(String collectionids) {
        this.collectionids = collectionids;
    }

    public int getSales() {
        return sales;
    }

    public void setSales(int sales) {
        this.sales = sales;
    }

    public String getImagepath() {
        return imagepath;
    }

    public void setImagepath(String imagepath) {
        this.imagepath = imagepath;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public BigDecimal getAgreeprice() {
        return agreeprice;
    }

    public void setAgreeprice(BigDecimal agreeprice) {
        this.agreeprice = agreeprice;
    }

    public BigDecimal getJdprice() {
        return jdprice;
    }

    public void setJdprice(BigDecimal jdprice) {
        this.jdprice = jdprice;
    }

    public String getCtime() {
        return ctime;
    }

    public void setCtime(String ctime) {
        this.ctime = ctime;
    }

    public String getUtime() {
        return utime;
    }

    public void setUtime(String utime) {
        this.utime = utime;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        } else if (obj instanceof ProductDo) {
            ProductDo productObj = (ProductDo) obj;
            if (StringUtils.equals(this.getName(), productObj.getName()) &&
                    StringUtils.equals(this.getCat0name(), productObj.getCat0name()) &&
                    StringUtils.equals(this.getCat1name(), productObj.getCat1name()) &&
                    StringUtils.equals(this.getCat2name(), productObj.getCat2name()) &&
                    StringUtils.equals(this.getCatename(), productObj.getCatename()) &&
                    StringUtils.equals(this.getCate(), productObj.getCate()) &&
                    StringUtils.equals(this.getBrandname(), productObj.getBrandname()) &&
                    StringUtils.equals(this.getCollectionids(), productObj.getCollectionids()) &&
                    StringUtils.equals(this.getImagepath(), productObj.getImagepath()) &&
                    cat0 == productObj.getCat0() &&
                    cat1 == productObj.getCat1() &&
                    cat2 == productObj.getCat2() &&
                    state == productObj.getState() &&
                    sales == productObj.getSales() &&
                    (this.sku == null ? productObj.getSku() == null : this.sku.equals(productObj.getSku())) &&
                    (this.price == null ? productObj.getPrice() == null : this.price.compareTo(productObj.getPrice()) == 0) &&
                    (this.agreeprice == null ? productObj.getAgreeprice() == null : this.agreeprice.compareTo(productObj.getAgreeprice()) == 0) &&
                    (this.jdprice == null ? productObj.getJdprice() == null : this.jdprice.compareTo(productObj.getJdprice()) == 0)) {
                return true;
            }

        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(sku, name, cat0, cat0name, cat1, cat1name, cat2, cat2name, catename, cate, brandname, collectionids, sales, imagepath, state, price, agreeprice, jdprice);
    }
}

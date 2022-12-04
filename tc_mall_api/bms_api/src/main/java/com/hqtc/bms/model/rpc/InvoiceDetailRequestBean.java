package com.hqtc.bms.model.rpc;

import java.io.Serializable;

/**
 * description:电子发票明细
 * Created by laiqingchuang on 18-7-3 .
 */
public class InvoiceDetailRequestBean implements Serializable {
    private String goodsname;    //商品名称
    private String num;          //数量
    private String price;        //单价
    private String hsbz;         //单价含税标志,0:不含税,1:含税
    private String taxrate;      //税率
    private String spec;         //规格型号
    private String unit;         //单位
    private String spbm;         //商品编码
    private String zsbm;         //自行编码
    private String fphxz;        //发票行性质:0,正常行;1,折扣行;2,被折扣行
    private String yhzcbs;       //优惠政策标识:0,不使用;1,使用
    private String zzstsgl;      //增值税特殊管理,如:即征即退、免税、简易征收 等
    private String lslbs;        //零税率标识:空,非零 税率 ;1, 免税 ;2,不征税;3,普通零税率
    private String kce;          //扣除额,小数点后两位
    private String taxfreeamt;   //不含税金额
    private String tax;          //税额
    private String taxamt;       //含税金额
    private Long productId;      //商品id
    private Integer taxrateInt;  //商品税率

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Integer getTaxrateInt() {
        return taxrateInt;
    }

    public void setTaxrateInt(Integer taxrateInt) {
        this.taxrateInt = taxrateInt;
    }

    public String getGoodsname() {
        return goodsname;
    }

    public void setGoodsname(String goodsname) {
        this.goodsname = goodsname;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getHsbz() {
        return hsbz;
    }

    public void setHsbz(String hsbz) {
        this.hsbz = hsbz;
    }

    public String getTaxrate() {
        return taxrate;
    }

    public void setTaxrate(String taxrate) {
        this.taxrate = taxrate;
    }

    public String getSpec() {
        return spec;
    }

    public void setSpec(String spec) {
        this.spec = spec;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getSpbm() {
        return spbm;
    }

    public void setSpbm(String spbm) {
        this.spbm = spbm;
    }

    public String getZsbm() {
        return zsbm;
    }

    public void setZsbm(String zsbm) {
        this.zsbm = zsbm;
    }

    public String getFphxz() {
        return fphxz;
    }

    public void setFphxz(String fphxz) {
        this.fphxz = fphxz;
    }

    public String getYhzcbs() {
        return yhzcbs;
    }

    public void setYhzcbs(String yhzcbs) {
        this.yhzcbs = yhzcbs;
    }

    public String getZzstsgl() {
        return zzstsgl;
    }

    public void setZzstsgl(String zzstsgl) {
        this.zzstsgl = zzstsgl;
    }

    public String getLslbs() {
        return lslbs;
    }

    public void setLslbs(String lslbs) {
        this.lslbs = lslbs;
    }

    public String getKce() {
        return kce;
    }

    public void setKce(String kce) {
        this.kce = kce;
    }

    public String getTaxfreeamt() {
        return taxfreeamt;
    }

    public void setTaxfreeamt(String taxfreeamt) {
        this.taxfreeamt = taxfreeamt;
    }

    public String getTax() {
        return tax;
    }

    public void setTax(String tax) {
        this.tax = tax;
    }

    public String getTaxamt() {
        return taxamt;
    }

    public void setTaxamt(String taxamt) {
        this.taxamt = taxamt;
    }
}
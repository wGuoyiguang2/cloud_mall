package com.cibnvideo.oms.tcmallcustomer.service;

public interface SendMessageService {
    boolean venderCreate(int venderId);//新大客户创建

    boolean venderRemove(int venderId);//大客户删除

    boolean venderSettlementModify(int venderId);//大客户批发系数修改

    boolean priceProductChange(int venderId, long sku);//大客户单品价格修改

    boolean productRemove(int venderId, long sku);//大客户商品删除

    boolean productRemoveRevert(int venderId, long sku);//大客户商品删除

    boolean priceProductCollectionChange(int venderId, int collectionId);//商品集定价策略修改

    boolean priceProductCategoryChange(int venderId, int categoryId, int catType);//商品分类定价策略修改

    boolean priceProductAllChange(int venderId);//全站定价系数修改
}

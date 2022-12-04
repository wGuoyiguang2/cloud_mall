package com.cibnvideo.oms.tcmallcustomer.service.impl;

import com.cibnvideo.oms.tcmallcustomer.bean.MessageInfo;
import com.cibnvideo.oms.tcmallcustomer.dao.SendMessageDao;
import com.cibnvideo.oms.tcmallcustomer.service.SendMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SendMessageServiceImpl implements SendMessageService {

    @Autowired
    SendMessageDao sendMessageDao;

    @Override
    public boolean venderCreate(int venderId) {
        MessageInfo messageInfo = new MessageInfo();
        messageInfo.setType(0);
        messageInfo.setVenderId(venderId);
        return sendMessageDao.sendMessage(messageInfo);
    }

    @Override
    public boolean venderRemove(int venderId) {
        MessageInfo messageInfo = new MessageInfo();
        messageInfo.setType(1);
        messageInfo.setVenderId(venderId);
        return sendMessageDao.sendMessage(messageInfo);
    }

    @Override
    public boolean venderSettlementModify(int venderId) {
        MessageInfo messageInfo = new MessageInfo();
        messageInfo.setType(2);
        messageInfo.setVenderId(venderId);
        return sendMessageDao.sendMessage(messageInfo);
    }

    @Override
    public boolean priceProductChange(int venderId, long sku) {
        MessageInfo messageInfo = new MessageInfo();
        messageInfo.setType(3);
        messageInfo.setVenderId(venderId);
        messageInfo.setSku(sku);
        return sendMessageDao.sendMessage(messageInfo);
    }

    @Override
    public boolean priceProductCollectionChange(int venderId, int collectionId) {
        MessageInfo messageInfo = new MessageInfo();
        messageInfo.setType(4);
        messageInfo.setVenderId(venderId);
        messageInfo.setCollectionId(collectionId);
        return sendMessageDao.sendMessage(messageInfo);
    }

    @Override
    public boolean priceProductCategoryChange(int venderId, int categoryId, int catType) {
        MessageInfo messageInfo = new MessageInfo();
        messageInfo.setType(5);
        messageInfo.setVenderId(venderId);
        messageInfo.setCatId(categoryId);
        messageInfo.setCatType(catType);
        return sendMessageDao.sendMessage(messageInfo);
    }

    @Override
    public boolean priceProductAllChange(int venderId) {
        MessageInfo messageInfo = new MessageInfo();
        messageInfo.setType(6);
        messageInfo.setVenderId(venderId);
        return sendMessageDao.sendMessage(messageInfo);
    }

    @Override
    public boolean productRemove(int venderId, long sku) {
        MessageInfo messageInfo = new MessageInfo();
        messageInfo.setType(7);
        messageInfo.setVenderId(venderId);
        messageInfo.setSku(sku);
        return sendMessageDao.sendMessage(messageInfo);
    }

    @Override
    public boolean productRemoveRevert(int venderId, long sku) {
        MessageInfo messageInfo = new MessageInfo();
        messageInfo.setType(8);
        messageInfo.setVenderId(venderId);
        messageInfo.setSku(sku);
        return sendMessageDao.sendMessage(messageInfo);
    }
}

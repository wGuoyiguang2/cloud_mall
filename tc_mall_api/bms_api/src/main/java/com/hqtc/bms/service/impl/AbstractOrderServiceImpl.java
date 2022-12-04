package com.hqtc.bms.service.impl;

import com.hqtc.bms.model.database.TOrderBean;
import com.hqtc.bms.model.database.TOrderProductBean;
import com.hqtc.bms.service.OrderService;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by wanghaoyang on 18-7-4.
 */
public abstract class AbstractOrderServiceImpl implements OrderService{

    @Override
    public String createOrderSn(){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        String currenTime = simpleDateFormat.format(new Date());
//        int randomInt = (int)(Math.random()*89999)+10000;
        int randomInt2 = (int)(Math.random()*899999)+100000;
//        return new StringBuffer().append(currenTime).append(randomInt).append(randomInt2).toString();
        return new StringBuffer().append(currenTime).append(randomInt2).toString();
    }
}

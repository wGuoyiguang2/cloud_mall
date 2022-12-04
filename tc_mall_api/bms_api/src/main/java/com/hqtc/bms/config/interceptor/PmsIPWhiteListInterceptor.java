package com.hqtc.bms.config.interceptor;

import com.hqtc.common.config.IPWhiteListInterceptor;

/**
 * Pms回调tcmallbms的ip白名单拦截器
 * Created by wanghaoyang on 19-1-22.
 */
public class PmsIPWhiteListInterceptor extends IPWhiteListInterceptor {

    public PmsIPWhiteListInterceptor(String ipWhiteList){
        super(ipWhiteList);
    }
}

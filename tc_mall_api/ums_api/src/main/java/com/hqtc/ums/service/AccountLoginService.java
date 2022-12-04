package com.hqtc.ums.service;

import com.hqtc.ums.model.databasebean.TLoginAccountBean;

/**
 * Created by wanghaoyang on 19-1-9.
 */
public interface AccountLoginService {

    TLoginAccountBean getVenderByNameAndPassWord(String userName, String passWord);
}

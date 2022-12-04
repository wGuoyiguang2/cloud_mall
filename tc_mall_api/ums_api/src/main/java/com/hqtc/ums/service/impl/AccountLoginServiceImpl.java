package com.hqtc.ums.service.impl;

import com.hqtc.ums.model.databasebean.TLoginAccountBean;
import com.hqtc.ums.model.mapper.TLoginAccountMapper;
import com.hqtc.ums.service.AccountLoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by wanghaoyang on 19-1-9.
 */
@Service("AccountLoginServiceImpl")
public class AccountLoginServiceImpl implements AccountLoginService {

    @Autowired
    private TLoginAccountMapper tLoginAccountMapper;

    @Override
    public TLoginAccountBean getVenderByNameAndPassWord(String userName, String passWord){
        return tLoginAccountMapper.getVenderByNameAndPassWord(userName, passWord);
    }
}

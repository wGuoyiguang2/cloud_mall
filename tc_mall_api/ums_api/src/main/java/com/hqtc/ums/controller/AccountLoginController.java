package com.hqtc.ums.controller;

import com.hqtc.common.config.ErrorCode;
import com.hqtc.common.response.ResultData;
import com.hqtc.common.utils.Tools;
import com.hqtc.ums.config.Router;
import com.hqtc.ums.model.databasebean.TLoginAccountBean;
import com.hqtc.ums.service.AccountLoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by wanghaoyang on 19-1-9.
 */
@RestController
public class AccountLoginController {

    @Autowired
    private AccountLoginService accountLoginService;

    @RequestMapping(value = Router.ROUTER_GET_VENDER_INFO, method = RequestMethod.GET)
    public ResultData<TLoginAccountBean> getVenderInfo(@RequestParam("userName")String userName,
                                                       @RequestParam("passWord")String passWord){
        ResultData<TLoginAccountBean> resultData = Tools.getThreadResultData();
        TLoginAccountBean tLoginAccountBean = accountLoginService.getVenderByNameAndPassWord(userName, Tools.md5(passWord));
        if(null == tLoginAccountBean){
            resultData.setError(ErrorCode.USER_NOT_EXIST);
            resultData.setMsg("用户名或密码错误");
        }else {
            resultData.setData(tLoginAccountBean);
        }
        return resultData;
    }
}

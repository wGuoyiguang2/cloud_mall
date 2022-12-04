package com.cibnvideo.jd.common.service.impl;

import com.cibnvideo.jd.common.config.BaseConfig;
import com.cibnvideo.jd.common.constants.JdMethodConstants;
import com.cibnvideo.jd.common.params.BaseRequestParams;
import com.cibnvideo.jd.common.service.BaseService;
import com.cibnvideo.jd.common.utils.HttpClientUtil;
import com.cibnvideo.jd.token.service.TokenService;
import com.google.gson.Gson;
import com.hqtc.common.utils.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Description:
 * @Author: WangBin
 * @Date: 2019/1/16 18:28
 */
@Service
public class BaseServiceImpl implements BaseService {
    private Gson gson = new Gson();
    @Autowired
    private BaseConfig baseConfig;
    @Autowired
    private TokenService tokenService;

    /**
     * 获取请求结果
     *
     * @param method
     * @param requestParams
     * @return
     */
    protected String request(String method, Object requestParams) {
        BaseRequestParams params = this.createRequestParams(method, requestParams);
        return HttpClientUtil.post(baseConfig.getApiUrl(), params, "UTF-8", true);
    }

    /**
     * 封装请求参数params
     *
     * @param method
     * @param requestParams
     * @return
     */
    private BaseRequestParams createRequestParams(String method, Object requestParams) {
        BaseRequestParams params = new BaseRequestParams();
        params.setMethod(method);
        params.setV(JdMethodConstants.getVERSION());
        params.setApp_key(baseConfig.getAppKey());
        params.setAccess_token(tokenService.getToken());
        params.setParam_json(gson.toJson(requestParams));
        params.setFormat("json");
        params.setTimestamp(DateUtil.getSimpleDateFormat());
        return params;
    }
}

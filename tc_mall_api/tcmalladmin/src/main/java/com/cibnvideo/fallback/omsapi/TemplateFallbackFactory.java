package com.cibnvideo.fallback.omsapi;

import com.cibnvideo.tcmalladmin.model.bean.TemplateBean;
import com.cibnvideo.tcmalladmin.omsapi.TemplateApi;
import com.hqtc.common.config.ErrorCode;
import com.hqtc.common.response.DataList;
import com.hqtc.common.response.ResultData;
import feign.hystrix.FallbackFactory;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

import static com.hqtc.common.utils.Tools.getThreadResultData;

/**
 * @Author: likai
 * @description description
 * @Date: 18-9-11 下午3:26
 */
@Component
public class TemplateFallbackFactory implements FallbackFactory<TemplateApi> {
    @Override
    public TemplateApi create(Throwable throwable) {
        return new TemplateApi() {
            @Override
            public ResultData<DataList<List<TemplateBean>>> list(Map<String, Object> params) {
                return errorResponse("templateApi list facback");
            }

            @Override
            public ResultData<Integer> count(Map<String, Object> params) {
                return errorResponse("templateApi count facback");
            }

            @Override
            public ResultData<TemplateBean> get(Integer id) {
                return errorResponse("templateApi get facback");
            }

            @Override
            public ResultData<Integer> add(TemplateBean templateBean) {
                return errorResponse("templateApi add facback");
            }

            @Override
            public ResultData<Integer> remove(Integer id) {
                return errorResponse("templateApi remove facback");
            }

            @Override
            public ResultData<Integer> update(TemplateBean templateBean) {
                return errorResponse("templateApi update facback");
            }

            private ResultData errorResponse(String msg){
                ResultData result = getThreadResultData();
                result.setMsg(msg);
                result.setError(ErrorCode.SERVER_EXCEPTION);
                return result;
            }
        };
    }
}

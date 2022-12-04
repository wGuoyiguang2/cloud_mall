package com.cibnvideo.fallback.omsapi;

import com.cibnvideo.tcmalladmin.model.bean.HelpCenterInfoVo;
import com.cibnvideo.tcmalladmin.omsapi.HelpCenterApi;
import com.hqtc.common.config.ErrorCode;
import com.hqtc.common.response.DataList;
import com.hqtc.common.response.ResultData;
import feign.hystrix.FallbackFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

import static com.hqtc.common.utils.Tools.getThreadResultData;


@Component
public class HelpCenterFallbackFactory implements FallbackFactory<HelpCenterApi> {
    private Logger logger  = LoggerFactory.getLogger(getClass());
    @Override
    public HelpCenterApi create(Throwable throwable) {
        return new HelpCenterApi(){

            @Override
            public DataList<List<HelpCenterInfoVo>> helpCenterList(Map<String, Object> params) {
                logger.debug("接口helpCenterList failed");
                return null;
            }

            @Override
            public ResultData<HelpCenterInfoVo> getQAById(Integer id) {
                return errorResponse("interface getQAById failed");
            }

            @Override
            public ResultData<Integer> updateQA(Map<String, Object> params) {
                return errorResponse("interface updateQA failed");
            }

            @Override
            public ResultData<Integer> updateType(String typeName, Integer id) {
                return errorResponse("interface updateType failed");
            }

            @Override
            public ResultData<Integer> deleteQAAndType(Integer id) {
                return errorResponse("interface deleteQAAndType failed");
            }

            @Override
            public ResultData<Integer> addQA(Map<String, Object> params) {
                return errorResponse("oms interface addQA failed");
            }

            @Override
            public ResultData<Integer> addType(Map<String, Object> params) {
                return errorResponse("oms interface addType failed");
            }

            @Override
            public ResultData<List<HelpCenterInfoVo>> listAllType(Long venderId) {
                return errorResponse("oms interface listAllType failed");
            }

            @Override
            public ResultData<HelpCenterInfoVo> getTypeById(Integer typeId) {
                return errorResponse("oms interface getTypeById failed");
            }

            @Override
            public ResultData<Integer> deleteType(Integer typeId) {
                return errorResponse("oms interface deleteType failed");
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

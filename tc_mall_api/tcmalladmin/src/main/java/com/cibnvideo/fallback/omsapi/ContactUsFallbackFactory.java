package com.cibnvideo.fallback.omsapi;

import com.cibnvideo.tcmalladmin.model.bean.ContactUsBean;
import com.cibnvideo.tcmalladmin.omsapi.ContactUsApi;
import com.hqtc.common.config.ErrorCode;
import com.hqtc.common.response.ResultData;
import feign.hystrix.FallbackFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.List;

import static com.hqtc.common.utils.Tools.getThreadResultData;


@Component
public class ContactUsFallbackFactory implements FallbackFactory<ContactUsApi> {
    private Logger logger  = LoggerFactory.getLogger(getClass());
    @Override
    public ContactUsApi create(Throwable throwable) {
        return new ContactUsApi(){

            @Override
            public ResultData<List<ContactUsBean>> getContactWithUsByVenderId(Long venderId){
                return errorResponse("oms interface getContactWithUsByVenderId failed");
            }

            @Override
            public ResultData<ContactUsBean> getContactUsById(Integer id) {
                return errorResponse("oms 接口getContactUsById failed");
            }

            @Override
            public ResultData<Integer> updateContactUs(ContactUsBean contactUsBean) {
                return errorResponse("oms 接口updateContactus failed");
            }

            @Override
            public ResultData addContactUs(ContactUsBean contactUsBean) {
                return errorResponse("oms addContactUs failed");
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

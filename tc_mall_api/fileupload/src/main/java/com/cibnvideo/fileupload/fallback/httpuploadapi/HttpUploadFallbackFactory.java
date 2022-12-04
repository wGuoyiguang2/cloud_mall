package com.cibnvideo.fileupload.fallback.httpuploadapi;

import com.cibnvideo.fileupload.httpuploadapi.UploadApi;
import com.cibnvideo.fileupload.model.bean.HttpUploadBean;
import com.hqtc.common.config.ErrorCode;
import com.hqtc.common.response.ResultData;
import feign.hystrix.FallbackFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import static com.hqtc.common.utils.Tools.getThreadResultData;


@Component
public class HttpUploadFallbackFactory implements FallbackFactory<UploadApi> {
    private Logger logger  = LoggerFactory.getLogger(getClass());
    @Override
    public UploadApi create(Throwable throwable) {
        return new UploadApi(){
            @Override
            public HttpUploadBean uploadFile(MultipartFile file, String site, String dir, int chunks, String name) {
                HttpUploadBean httpUploadBean = new HttpUploadBean();
                httpUploadBean.setCode(10);
                httpUploadBean.setMsg("httpupload feignclient failed");
                return httpUploadBean;
            }
        };
    }
}

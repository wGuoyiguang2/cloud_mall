package com.cibnvideo.tcmalladmin.fileuplaodapi;

import com.cibnvideo.config.Router;
import com.cibnvideo.tcmalladmin.model.bean.FileUploadBean;
import com.hqtc.common.config.FeignClientService;
import com.hqtc.common.response.ResultData;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.HttpMessageConverters;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.cloud.netflix.feign.support.SpringEncoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;
import feign.codec.Encoder;
import feign.form.spring.SpringFormEncoder;


@FeignClient(value = FeignClientService.FILEUPLOAD, configuration = Fileuploadapi.MultipartSupportConfig.class)
public interface Fileuploadapi {

    @PostMapping(value=Router.V1_FILEUPLOAD_UPLOAD, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    ResultData<FileUploadBean> uploadImg(@RequestPart("file") MultipartFile file, @RequestParam("venderId") Integer venderId);


    @Configuration
    class MultipartSupportConfig {
        @Autowired
        private ObjectFactory<HttpMessageConverters> messageConverters;
        @Bean
        public Encoder feignFormEncoder() {
            return new SpringFormEncoder(new SpringEncoder(messageConverters));
        }
    }
}

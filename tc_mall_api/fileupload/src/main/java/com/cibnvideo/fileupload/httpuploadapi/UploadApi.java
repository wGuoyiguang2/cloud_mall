package com.cibnvideo.fileupload.httpuploadapi;

import com.cibnvideo.fileupload.config.Router;
import com.cibnvideo.fileupload.fallback.httpuploadapi.HttpUploadFallbackFactory;
import com.cibnvideo.fileupload.model.bean.HttpUploadBean;
import feign.codec.Encoder;
import feign.form.spring.SpringFormEncoder;
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

@FeignClient(name = "httpupload", url = "${cibnvideo.httpupload}", fallbackFactory = HttpUploadFallbackFactory.class)
public interface UploadApi {
    @PostMapping(value=Router.V1_HTTPUPLOAD_UPLOAD, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    HttpUploadBean uploadFile(@RequestPart("file") MultipartFile file, @RequestParam("site") String site, @RequestParam("dir") String dir, @RequestParam("chunks") int chunks, @RequestParam("name") String name);


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

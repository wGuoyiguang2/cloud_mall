package com.cibnvideo.fileupload.controller;

import com.cibnvideo.fileupload.config.Router;
import com.cibnvideo.fileupload.httpuploadapi.UploadApi;
import com.cibnvideo.fileupload.model.bean.FileUploadBean;
import com.cibnvideo.fileupload.model.bean.HttpUploadBean;
import com.cibnvideo.fileupload.utils.Md5CaculateUtil;
import com.hqtc.common.config.ErrorCode;
import com.hqtc.common.response.ResultData;
import com.hqtc.common.utils.Tools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import com.cibnvideo.fileupload.utils.FileUtil;

import java.io.File;
import java.util.Date;

@RestController
public class FileUploadController {

    @Value("${cibnvideo.site}")
    String site = "tcmallfileupload";

    @Value("${cibnvideo.domain}")
    String domain = "http://images.can-tv.cn/02/";

    @Autowired
    UploadApi uploadApi;

    @PostMapping(value=Router.V1_FILEUPLOAD_UPLOAD, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResultData uploadImg(@RequestParam("file") MultipartFile file, @RequestParam("venderId") Integer venderId, HttpServletRequest request) {
        ResultData<FileUploadBean> resultData = Tools.getThreadResultData();
        FileUploadBean fileUploadBean = new FileUploadBean();
        String fileName = new Date().getTime() + "_" + file.getOriginalFilename();
        String dir = venderId.toString();
        HttpUploadBean httpUploadBean = uploadApi.uploadFile(file, site, dir, 1, fileName);
        if(httpUploadBean.getCode() == 1){
            fileUploadBean.setUrl(domain + site + "/" + httpUploadBean.getPath());
            fileUploadBean.setMd5(httpUploadBean.getMd5());
            resultData.setData(fileUploadBean);
        }else {
            resultData.setError(ErrorCode.SERVER_EXCEPTION);
            resultData.setMsg(httpUploadBean.getMsg());
        }
        return resultData;
    }
}

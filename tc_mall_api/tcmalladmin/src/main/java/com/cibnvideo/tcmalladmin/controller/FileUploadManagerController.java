package com.cibnvideo.tcmalladmin.controller;

import com.cibnvideo.common.controller.BaseController;
import com.cibnvideo.tcmalladmin.fileuplaodapi.Fileuploadapi;
import com.cibnvideo.tcmalladmin.model.bean.FileUploadBean;
import com.hqtc.common.response.ResultData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Map;

@RestController
public class FileUploadManagerController extends BaseController {

    @Autowired
    Fileuploadapi fileuploadapi;

    @PostMapping(value="/v1/tcmalladmin/fileupload/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public Map<String, Object> uploadImg(@RequestPart("file") MultipartFile file) {
        Integer venderId = this.getVenderId().intValue();
        Map<String, Object> json = new HashMap<String, Object>();
        ResultData<FileUploadBean> resultData = fileuploadapi.uploadImg(file, venderId);
        json.put("fileMd5", resultData.getData().getMd5());
        json.put("message", "图片上传成功");
        json.put("status", true);
        json.put("filePath", resultData.getData().getUrl());
        return json;
    }
}

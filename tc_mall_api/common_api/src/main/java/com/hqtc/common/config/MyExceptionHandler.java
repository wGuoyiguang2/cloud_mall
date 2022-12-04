package com.hqtc.common.config;

import com.google.gson.Gson;
import com.hqtc.common.response.ResultData;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;


public class MyExceptionHandler {

    @ExceptionHandler(value=Exception.class)
    public String globalExceptionHandler(HttpServletRequest req, Exception e){
        ResultData resultData = new ResultData();
        resultData.setError(ErrorCode.SERVER_EXCEPTION);
        resultData.setMsg("服务端发生异常" + e.toString());
        Gson gson = new Gson();
        return gson.toJson(resultData);
    }

    @ExceptionHandler(value = MissingServletRequestParameterException.class)
    public String methonArgumentExceptionHandler(HttpServletRequest req, MissingServletRequestParameterException e){
        ResultData resultData = new ResultData();
        resultData.setError(ErrorCode.MISS_PARAM);
        resultData.setMsg("缺少参数 " + e.toString());
        Gson gson = new Gson();
        return gson.toJson(resultData);
    }

    @ExceptionHandler(value = DuplicateKeyException.class)
    public String duplicateKeyExceptionHandler(HttpServletRequest req, DuplicateKeyException e){
        ResultData resultData = new ResultData();
        resultData.setError(ErrorCode.WRITE_DATA_ERROR);
        resultData.setMsg("数据已经存在");
        Gson gson = new Gson();
        return gson.toJson(resultData);
    }
}

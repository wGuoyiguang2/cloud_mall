package com.cibnvideo.jdsynctask.config;

import com.hqtc.common.config.ErrorCode;
import com.hqtc.common.response.ResultData;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@ControllerAdvice
public class MyExceptionHandler {

    @ExceptionHandler(value=Exception.class)
    public ResultData globalExceptionHandler(HttpServletRequest req, Exception e){
        ResultData resultData = new ResultData();
        resultData.setError(ErrorCode.SERVER_EXCEPTION);
        resultData.setMsg("服务端发生异常" + e.toString());
        return resultData;
    }

    @ExceptionHandler(value = MissingServletRequestParameterException.class)
    public ResultData methonArgumentExceptionHandler(HttpServletRequest req, MissingServletRequestParameterException e){
        ResultData resultData = new ResultData();
        resultData.setError(ErrorCode.MISS_PARAM);
        resultData.setMsg("缺少参数 " + e.toString());
        return resultData;
    }

    @ExceptionHandler(value = DuplicateKeyException.class)
    public ResultData duplicateKeyExceptionHandler(HttpServletRequest req, DuplicateKeyException e){
        ResultData resultData = new ResultData();
        resultData.setError(ErrorCode.WRITE_DATA_ERROR);
        resultData.setMsg("数据已经存在");
        return resultData;
    }
}

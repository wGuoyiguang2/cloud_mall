package com.cibnvideo.oms.config;

import com.cibnvideo.oms.exception.LayoutCopyException;
import com.cibnvideo.oms.exception.LayoutRemoveException;
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
        resultData.setMsg("OMS: 服务端发生异常" + e.toString());
        e.printStackTrace();
        return resultData;
    }

    @ExceptionHandler(value = MissingServletRequestParameterException.class)
    public ResultData methonArgumentExceptionHandler(HttpServletRequest req, MissingServletRequestParameterException e){
        ResultData resultData = new ResultData();
        resultData.setError(ErrorCode.MISS_PARAM);
        resultData.setMsg("OMS: 缺少参数 " + e.toString());
        return resultData;
    }

    @ExceptionHandler(value = DuplicateKeyException.class)
    public ResultData duplicateKeyExceptionHandler(HttpServletRequest req, DuplicateKeyException e){
        ResultData resultData = new ResultData();
        resultData.setError(ErrorCode.DUPLICATE_DATA);
        resultData.setMsg("OMS: 数据已经存在");
        return resultData;
    }

    @ExceptionHandler(value = LayoutRemoveException.class)
    public ResultData layoutRemoveExceptionHandler(HttpServletRequest req, LayoutRemoveException e){
        ResultData resultData = new ResultData();
        resultData.setError(ErrorCode.SERVER_EXCEPTION);
        resultData.setMsg(e.getMessage());
        return resultData;
    }

    @ExceptionHandler(value = LayoutCopyException.class)
    public ResultData layoutCopyExceptionHandler(HttpServletRequest req, LayoutCopyException e){
        ResultData resultData = new ResultData();
        resultData.setError(ErrorCode.SERVER_EXCEPTION);
        resultData.setMsg(e.getMessage());
        return resultData;
    }

}

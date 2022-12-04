package com.hqtc.bms.config;

import com.hqtc.common.config.ErrorCode;
import com.hqtc.common.response.ResultData;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 捕获并处理一些全局异常
 * Created by wanghaoyang on 18-5-24.
 * 如果是代码编写者主动抛出的自定义异常,建议在调用处捕获处理
 * 此处捕获那些意料之外的或者不是我们主动抛出得的异常
 */

@ControllerAdvice(value = {"com.hqtc.bms.controller"})
@ResponseBody
public class BmsGlobalException {

    private static ThreadLocal<ResultData> local = ThreadLocal.withInitial(() -> new ResultData());

    @ExceptionHandler(value = BindException.class)
    public ResultData globalBindingExceptionHandler(BindException e){
        ResultData resultData = local.get();
        resultData.setError(ErrorCode.PARAM_ERROR);
        ObjectError error = e.getAllErrors().get(0);
        resultData.setMsg(error.getObjectName() + ":" + error.getDefaultMessage());
        return resultData;
    }

    /**
     * 用于处理通用异常
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResultData bindException(MethodArgumentNotValidException e) {
        BindingResult bindingResult = e.getBindingResult();

        String errorMessage = "校验失败:";

        for (FieldError fieldError : bindingResult.getFieldErrors()) {
            errorMessage += fieldError.getDefaultMessage() + ", ";
        }
        ResultData resultData = local.get();
        resultData.setError(ErrorCode.PARAM_ERROR);
        resultData.setMsg(errorMessage);
        return resultData;
    }
}


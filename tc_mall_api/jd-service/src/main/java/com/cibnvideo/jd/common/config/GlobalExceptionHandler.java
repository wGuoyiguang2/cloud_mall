package com.cibnvideo.jd.common.config;

import com.hqtc.common.config.ErrorCode;
import com.hqtc.common.response.ResultData;
import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import static com.hqtc.common.utils.Tools.getThreadResultData;


@ControllerAdvice
@ResponseBody
public class GlobalExceptionHandler {
    Logger logger= LoggerFactory.getLogger(this.getClass());
    @ExceptionHandler(value=Exception.class)
    public ResultData globalExceptionHandler(HttpServletRequest req, Exception e){
        ResultData resultData = getThreadResultData();
        resultData.setError(ErrorCode.SERVER_EXCEPTION);
        logger.error("服务端发生异常："+e.getMessage());
        resultData.setMsg("服务端发生异常");
        return resultData;
    }

    @ExceptionHandler(value = MissingServletRequestParameterException.class)
    public ResultData methodArgumentExceptionHandler(HttpServletRequest req, MissingServletRequestParameterException e){
        ResultData resultData = getThreadResultData();
        resultData.setError(ErrorCode.MISS_PARAM);
        resultData.setMsg("缺少必要的参数!");
        return resultData;
    }
}

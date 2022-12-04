package com.hqtc.bms.controller;

import com.hqtc.bms.config.Router;
import com.hqtc.bms.service.CommonService;
import com.hqtc.bms.service.MessageService;
import com.hqtc.common.config.ErrorCode;
import com.hqtc.common.response.ResultData;
import com.hqtc.common.utils.Tools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * Created by wanghaoyang on 18-8-10.
 */
@RestController
public class MessageController {

    @Autowired
    private MessageService messageService;

    @Autowired
    private CommonService commonService;

    @RequestMapping(value = Router.ROUTER_JD_MESSAGE, method = RequestMethod.GET)
    public ResultData getMessage(@RequestParam(value = "type")String type){
        ResultData resultData = Tools.getThreadResultData();
        if(!commonService.isPositiveNumericByComma(type)){
            resultData.setError(ErrorCode.PARAM_ERROR);
            resultData.setMsg("参数错误,格式应为被逗号分割的数字");
            return resultData;
        }
        return messageService.getMessage(type);
    }

    @RequestMapping(value = Router.ROUTER_JD_MESSAGE, method = RequestMethod.DELETE)
    public ResultData delMessage(@RequestParam("messageId")String messageId){
        return messageService.delMessage(messageId);
    }
}

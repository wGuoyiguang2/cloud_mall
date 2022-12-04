package com.cibnvideo.jobservice.controller;


import com.cibnvideo.common.config.ErrorCode;
import com.cibnvideo.common.response.ResultData;
import com.cibnvideo.common.utils.Result;
import com.cibnvideo.common.utils.Tools;
import com.cibnvideo.jobservice.config.Router;
import com.cibnvideo.jobservice.model.JobResponse;
import com.cibnvideo.jobservice.model.TaskDO;
import com.cibnvideo.jobservice.service.JobService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;


@RestController
public class JobController {
    Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private JobService jobService;

    @GetMapping(Router.V1_JOB_GET)
    ResultData get(@RequestParam("id") Long id) {
        ResultData resultData = Tools.getThreadResultData();
        resultData.setData(jobService.get(id));
        return resultData;
    }

    @GetMapping(Router.V1_JOB_LIST)
    public ResultData list(@RequestParam Map<String, Object> params) {
        ResultData resultData = Tools.getThreadResultData();
        Result result = Tools.getThreadResult(jobService.list(params), jobService.count(params));
        resultData.setData(result);
        return resultData;
    }

    /**
     * 保存
     */
    @PostMapping(Router.V1_JOB_ADD)
    public ResultData save(@RequestBody TaskDO taskScheduleJob) {
        ResultData resultData = Tools.getThreadResultData();
        resultData.setData(jobService.save(taskScheduleJob));
        return resultData;
    }

    /**
     * 修改
     */
    @PostMapping(Router.V1_JOB_UPDATE)
    public ResultData update(@RequestBody TaskDO taskScheduleJob) {
        ResultData resultData = Tools.getThreadResultData();
        resultData.setData(jobService.update(taskScheduleJob));
        return resultData;
    }

    /**
     * 删除
     */
    @PostMapping(Router.V1_JOB_REMOVE)
    public ResultData remove(@RequestBody Long id) {
        ResultData resultData = Tools.getThreadResultData();
        resultData.setData(jobService.remove(id));
        return resultData;
    }

    @PostMapping(Router.V1_JOB_BATCH_REMOVE)
    public ResultData remove(@RequestParam("ids[]") Long[] ids) {
        ResultData resultData = Tools.getThreadResultData();
        resultData.setData(jobService.batchRemove(ids));
        return resultData;
    }

    @GetMapping(Router.V1_JOB_CHANGE_STATUS)
    public ResultData changeJobStatus(@RequestParam("id") Long id,@RequestParam("cmd") String cmd) {
        ResultData resultData = Tools.getThreadResultData();
        try {
            resultData.setData(jobService.changeStatus(id, cmd));
        } catch (Exception e) {
            resultData.setError(ErrorCode.SERVER_EXCEPTION);
            resultData.setMsg(e.toString());
        }
        return resultData;
    }

    @GetMapping(Router.V1_JOB_RUN)
    ResultData run(@RequestParam("id") Long id) {
        ResultData resultData = Tools.getThreadResultData();
        resultData.setData(jobService.run(id));
        return resultData;
    }

    @GetMapping(Router.V1_JOB_RESPONSE)
    public Integer jobResponse(JobResponse jobResponse) {
        return jobService.updateEndTime(jobResponse.getJobId(), jobResponse.getStatus(), jobResponse.getMessage());
    }


}

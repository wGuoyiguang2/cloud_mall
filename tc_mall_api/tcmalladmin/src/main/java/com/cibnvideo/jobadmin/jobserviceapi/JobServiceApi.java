package com.cibnvideo.jobadmin.jobserviceapi;

import com.cibnvideo.common.utils.Result;
import com.cibnvideo.config.Router;
import com.cibnvideo.jobadmin.model.TaskDO;
import com.hqtc.common.config.FeignClientService;
import com.hqtc.common.response.ResultData;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

/**
 * @Author: likai
 * @description description
 * @Date: 19-1-7 下午3:33
 */
@FeignClient(value = FeignClientService.JOBSERVICE)
public interface JobServiceApi {
    @RequestMapping(value = Router.V1_JOB_GET, method = RequestMethod.GET)
    ResultData<TaskDO> get(@RequestParam("id") Long id);

    @RequestMapping(value = Router.V1_JOB_LIST, method = RequestMethod.GET)
    ResultData<Result<TaskDO>> list(@RequestParam Map<String, Object> params);

    @RequestMapping(value = Router.V1_JOB_COUNT, method = RequestMethod.GET)
    ResultData<Integer> count(@RequestParam Map<String, Object> params);

    @RequestMapping(value = Router.V1_JOB_ADD, method = RequestMethod.POST)
    ResultData<Integer> add(@RequestBody TaskDO taskDO);

    @RequestMapping(value = Router.V1_JOB_REMOVE, method = RequestMethod.POST)
    ResultData<Integer> remove(@RequestBody Long id);

    @RequestMapping(value = Router.V1_JOB_BATCH_REMOVE, method = RequestMethod.POST)
    ResultData<Integer> batchRemove(@RequestParam("ids[]") Long[] ids);

    @RequestMapping(value = Router.V1_JOB_UPDATE, method = RequestMethod.POST)
    ResultData<Integer> update(@RequestBody TaskDO taskDO);

    @RequestMapping(value = Router.V1_JOB_CHANGE_STATUS, method = RequestMethod.GET)
    ResultData<Integer> changeStatus(@RequestParam("id") Long id, @RequestParam("cmd") String cmd);

    @RequestMapping(value = Router.V1_JOB_RUN, method = RequestMethod.GET)
    ResultData<Integer> run(@RequestParam("id") Long id);
}

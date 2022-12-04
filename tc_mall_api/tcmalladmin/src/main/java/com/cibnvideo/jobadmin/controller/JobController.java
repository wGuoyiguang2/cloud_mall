package com.cibnvideo.jobadmin.controller;


import com.cibnvideo.common.utils.R;
import com.cibnvideo.common.utils.Result;
import com.cibnvideo.jobadmin.model.TaskDO;
import com.cibnvideo.jobadmin.service.JobService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Map;


@Controller
public class JobController {

    @Autowired
    private JobService taskScheduleJobService;

    @GetMapping("/task/job")
    String taskScheduleJob(Model model) {
        return "task/job";
    }

    @ResponseBody
    @GetMapping("/task/job/list")
    public Result<TaskDO> list(@RequestParam Map<String, Object> params) {
        Result<TaskDO> result = taskScheduleJobService.list(params);
        return result;
    }

    @GetMapping("/task/job/add")
    String add() {
        return "task/add";
    }

    @GetMapping("/task/job/edit/{id}")
    String edit(@PathVariable("id") Long id, Model model) {
        TaskDO job = taskScheduleJobService.get(id);
        model.addAttribute("job", job);
        return "task/edit";
    }

    /**
     * 信息
     */
    @GetMapping("/task/job/info/{id}")
    public R info(@PathVariable("id") Long id) {
        TaskDO taskScheduleJob = taskScheduleJobService.get(id);
        return R.ok().put("taskScheduleJob", taskScheduleJob);
    }

    /**
     * 保存
     */
    @ResponseBody
    @PostMapping("/task/job/save")
    public R save(TaskDO taskScheduleJob) {
        if (taskScheduleJobService.save(taskScheduleJob) > 0) {
            return R.ok();
        }
        return R.error();
    }

    /**
     * 修改
     */
    @ResponseBody
    @PostMapping("/task/job/update")
    public R update(TaskDO taskScheduleJob) {
        if (taskScheduleJobService.update(taskScheduleJob) > 0) {
            return R.ok();
        }
        return R.error();
    }

    /**
     * 删除
     */
    @PostMapping("/task/job/remove")
    @ResponseBody
    public R remove(Long id) {
        if (taskScheduleJobService.remove(id) > 0) {
            return R.ok();
        }
        return R.error();
    }

    /**
     * 删除
     */
    @PostMapping("/task/job/batchRemove")
    @ResponseBody
    public R remove(@RequestParam("ids[]") Long[] ids) {
        if (taskScheduleJobService.batchRemove(ids) > 0) {
            return R.ok();
        }
        return R.error();
    }

    @GetMapping("/task/job/changeJobStatus")
    @ResponseBody
    public R changeJobStatus(Long id, String cmd) {
        String label = "停止";
        if ("start".equals(cmd)) {
            label = "启动";
        } else {
            label = "停止";
        }
        try {
            taskScheduleJobService.changeStatus(id, cmd);

            return R.ok("任务" + label + "成功");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return R.ok("任务" + label + "失败");
    }

    @GetMapping("/task/job/run")
    @ResponseBody
    public R run(Long id) {
        TaskDO taskDO = taskScheduleJobService.get(id);
        if(taskDO == null) {
            return R.error("任务不存在");
        }
        if(!StringUtils.equals("1", taskDO.getJobStatus())) {
            return R.error("任务未启动");
        }
        if(taskScheduleJobService.run(id) > 0) {
            return R.ok("执行成功");
        }else {
            return R.error("执行失败");
        }
    }
}

package com.bd.controller;


import org.quartz.CronScheduleBuilder;
import org.quartz.CronTrigger;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.TriggerKey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import myjob.common.exception.MyJobException;
import myjob.common.exception.Result;
import myjob.core.task.schedule.JobExecutor;

@RequestMapping("/schedule")
@RestController
@Api(value = "任务调度", tags = {"任务调度"})
public class ScheduleController {
    @Autowired
    private Scheduler scheduler;
    
//    @RequestMapping(value = "/t",method = RequestMethod.GET)
//    public Result getList() throws MyJobException {
//        //@RequestParam String cron, @RequestParam String data
//        String jobName = scheduleService.scheduleJob(SendEmailJob.class, "*/5 * * * * ?", new Date().toString());
//        return Result.success();
//    }

    @RequestMapping(value = "/t", method = RequestMethod.GET)
    public Result t() throws MyJobException, SchedulerException {
        //@RequestParam String cron, @RequestParam String data
        CronScheduleBuilder cronScheduleBuilder = CronScheduleBuilder.cronSchedule("0/1 * * * * ?");
        TriggerKey triggerKey = TriggerKey.triggerKey("main", "mainGroup");
        CronTrigger triggerOld = null;
        try {
            triggerOld = (CronTrigger) scheduler.getTrigger(triggerKey);
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
        if (triggerOld == null) {
            //将job加入到jobDetail
            JobDetail jobDetail = JobBuilder.newJob(JobExecutor.class).withIdentity("main", "mainGroup").storeDurably().build();
            Trigger trigger = TriggerBuilder.newTrigger().withIdentity("main", "mainGroup").withSchedule(cronScheduleBuilder).build();
            scheduler.scheduleJob(jobDetail, trigger);
            return Result.success();
        } else {
            System.out.println("当前任务已经存在-----------------!");
            return Result.fail("当前任务已经存在");
        }
    }
    /**
     * 暂停任务
     */

}

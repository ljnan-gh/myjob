package com.bd.service.impl;

import com.bd.service.InitService;
import com.bd.service.ScheduleService;

import lombok.extern.slf4j.Slf4j;
import myjob.common.util.DateUtil;

import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

import javax.annotation.PostConstruct;

@Slf4j
@Service
public class ScheduleServiceImpl implements ScheduleService {

	@Autowired
	private InitService initService;
    @Autowired
    private Scheduler scheduler;
    private String defaultGroup = "default_group";

    @Override
    public String scheduleJob(Class<? extends Job> jobBeanClass, String cron, String data) {
        // 创建需要执行的任务
//        String jobName = UUID.fastUUID().toString();
        String jobName = String.valueOf(System.currentTimeMillis());
        JobDetail jobDetail = JobBuilder.newJob(jobBeanClass)
                .withIdentity(jobName, defaultGroup)
                .usingJobData("data", data)
                .build();
        //创建触发器，指定任务执行时间
        CronTrigger cronTrigger = TriggerBuilder.newTrigger()
                .withIdentity(jobName, defaultGroup).withSchedule(CronScheduleBuilder.cronSchedule(cron))
                .build();
        //使用调度器进行任务调度
        try {
            scheduler.scheduleJob(jobDetail, cronTrigger);
        } catch (SchedulerException e) {
            e.printStackTrace();
            log.info("创建定时任务失败！");
        }
        return jobName;
    }

    @Override
    public String scheduleFixTimeJob(Class<? extends Job> jobBeanClass, Date startTime, String data) {
        //日期转CRON表达式
        String startCron = String.format("%d %d %d %d %d ? %d",
                DateUtil.second(startTime), DateUtil.minute(startTime),
                DateUtil.hour(startTime),
                DateUtil.dayOfMonth(startTime),
                DateUtil.month(startTime) + 1, DateUtil.year(startTime));
        return scheduleJob(jobBeanClass, startCron, data);
    }

    @Override
    public Boolean cancelScheduleJob(String jobName) {
        boolean success = false;
        try {
            // 暂停触发器
            scheduler.pauseTrigger(new TriggerKey(jobName, defaultGroup));
            // 移除触发器中的任务
            scheduler.unscheduleJob(new TriggerKey(jobName, defaultGroup));
            // 删除任务
            scheduler.deleteJob(new JobKey(jobName, defaultGroup));
            success = true;
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
        return success;
    }
    /*
     * 清空任务表
     */
    public Scheduler getScheduler() {
		return scheduler;
	}

    /**
     * 清空quartz表
     */
	@PostConstruct
	public void clearAllQuartz() {
		initService.clearQuartz();	
	}
    
}

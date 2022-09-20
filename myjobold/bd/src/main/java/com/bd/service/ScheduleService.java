package com.bd.service;

import org.quartz.Job;
import org.quartz.Scheduler;

import java.util.Date;


public interface ScheduleService {
    /**
     * 通过CRON表达式调度任务
     */
    String scheduleJob(Class<? extends Job> jobBeanClass, String cron, String data);

    /**
     * 调度指定时间的任务
     */
    String scheduleFixTimeJob(Class<? extends Job> jobBeanClass, Date startTime, String data);

    /**
     * 取消定时任务
     */
    Boolean cancelScheduleJob(String jobName);
    
    /**
     *获取唯一Schedule 
     */
    public Scheduler getScheduler();
    
}

package com.bd.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.quartz.Scheduler;

import com.bd.annotation.RegisterTask;
import com.bd.entity.AppJob;

import lombok.extern.slf4j.Slf4j;
import myjob.common.util.ExceptionInfoPrintUtil;
import myjob.common.util.SpringUtil;
import myjob.core.task.schedule.JobInfo;
import myjob.core.task.schedule.ScheduleUtils;

/** 
 * @author ljnan
 *2022-01-06
 */
@Slf4j
public class InitConfigService {
	
	private static InitConfigService initConfigService = null;
	
	
    private InitService initService;
	private AppJobService appJobService;
	
	private InitConfigService() {
		// 整个生存过程中只有一个实例
	}

	/**
	 * 代码中已经实现的任务
	 * 
	 * @param jobPaths
	 */
	public void init(List<String> jobPaths, Map<String, RegisterTask> jobInfo) {
		// 获取对象
		if (initService == null)
			initService = SpringUtil.getBean(com.bd.service.InitService.class);
		if (appJobService == null)
			appJobService = SpringUtil.getBean(com.bd.service.AppJobService.class);
		// 清理quartz框架表
		initService.clearQuartz();		
		//加载所有任务
		createScheduleJob(initService.synchronizationTask(jobPaths, jobInfo));// 所有任务
	}
	
	/**
	 * 创建定时任务
	 * @param jobs
	 */
	private void createScheduleJob(List<AppJob> jobs) {
		Scheduler scheduler = SpringUtil.getBean(com.bd.service.ScheduleService.class).getScheduler();
		SpringUtil.getBean(myjob.core.cache.redis.RedisConfig.class); 
		for (AppJob job : jobs) {
			try {
				ScheduleUtils.createScheduleJob(scheduler, buildJobInfo(job));
			}catch(Exception e) {
				log.error(ExceptionInfoPrintUtil.getExceptionInfo(e));
			}
		}
	}
	/*
	 * 补全任务信息
	 */
	private JobInfo buildJobInfo(AppJob job) {
		JobInfo jobInfo = new JobInfo();
		jobInfo.setCronExpression(job.getCronExpression());
		jobInfo.setJobName(job.getJobName());
		jobInfo.setJobId(ScheduleUtils.JObIDHEAD+ job.getJobId());
		jobInfo.setJobPath(job.getJobPath());
		if(job.getLastExecutionTime() != null) {
			jobInfo.setLastExecutionTime(new Date(job.getLastExecutionTime()));
		}else {
			jobInfo.setLastExecutionTime(null);
		}		
    	return jobInfo;
    }
	public static InitConfigService getInitConfigService() {
		if(initConfigService == null) {
			initConfigService = new InitConfigService();
		}
		return initConfigService;
	}
}

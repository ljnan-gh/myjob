package com.bd.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bd.annotation.RegisterTask;
import com.bd.entity.AppJob;
import com.bd.mapper.InitMapper;
import com.bd.service.AppJobService;
import com.bd.service.InitService;

import lombok.extern.slf4j.Slf4j;
import myjob.common.util.IDBuilder;

@Service
@Slf4j
public class InitServiceImpl implements InitService{
	
	@Autowired
	private InitMapper initMapper;
	@Autowired
	private AppJobService appJobService;
	
	public List<AppJob> get(){
		return initMapper.get();		
	}

	@Override
	public void clearQuartz() {
		initMapper.clearBlobTriggers();
		initMapper.clearBolbTriggersCopy();
		initMapper.clearCalendars();
		initMapper.clearCronTriggers();
		initMapper.clearFiredTriggers();
		initMapper.clearJobDetails();
		initMapper.clearLocks();
		initMapper.clearPausedTriggerGrps();
		initMapper.clearScheulerState();
		initMapper.clearSimpleTriggers();
		initMapper.clearSimpropTriggers();
		initMapper.clearTriggers();
		
	}

	@Override
	public List<AppJob> synchronizationTask(List<String> jobPaths, Map<String, RegisterTask> jobInfo) {
		// 矫正任务表app_job,有时候会出现改动路径，或者删除任务情况，故每次启动项目要检查任务准确性
		List<AppJob> jobs = appJobService.loadJobs();// 所有任务
		List<Long> delJobIds = new ArrayList<Long>();// 待删除的任务
		for (AppJob appJob : jobs) {
			String jobPath = appJob.getJobPath();
			if (jobPaths.contains(jobPath)) {
				jobPaths.remove(jobPath);// 比较完成如果没有异常，则可以移除
			} else {
				delJobIds.add(appJob.getJobId());
				// 代码中没有任何具体实现的任务，从数据库中移除
				log.error("该任务已经被移除，任务id======>{},路径======>{}", appJob.getJobId(), jobPath);
			}
		}		
		// 创建新的任务
		createJob(jobPaths, jobInfo);
		// 批量删除废弃的任务
		appJobService.deleteByWrapper(delJobIds);
		return appJobService.loadJobs();	
	};
	/**
	 * 通过反射创建新的任务app_job
	 */
	private void createJob(List<String> jobPaths,Map<String, RegisterTask> jobInfo) {
		for(String jobPath: jobPaths) {
			try {
				AppJob appJob = new AppJob();
				RegisterTask rt = jobInfo.get(jobPath);
				if(rt == null) {
					appJob.setJobName(jobPath);//默认全类名
					appJob.setCronExpression("3/5 * * * * ? *");
					appJob.setJobStatus(0);
				}else {
					appJob.setJobName(rt.taskName());
					appJob.setCronExpression(rt.cronStr());
					appJob.setDescription(rt.description());
					appJob.setJobStatus(rt.status());
				}
				appJob.setJobPath(jobPath);
				appJob.setJobId(IDBuilder.getGuid());
				appJob.setCreateTime(System.currentTimeMillis());
				
				appJobService.AddNewJob(appJob);
			}catch(Exception e) {
				e.printStackTrace();
				log.error("创建新任务{}，失败",jobPath);
			}
		}
		
	}
	
}

package com.bd.controller;


import java.util.List;

import lombok.extern.slf4j.Slf4j;
import org.quartz.Scheduler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.bd.entity.AppJob;
import com.bd.service.impl.AppJobServiceImpl;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RequestMapping("/appJob/")
@RestController
@Api(value = "任务相关", tags = {"任务相关"})
@Slf4j
public class AppJobController {
	@Autowired
	private AppJobServiceImpl appJobServiceImpl;
	@Autowired
	private Scheduler scheduler;
	
	@ApiOperation("获取所有任务")
	@RequestMapping(value = "getAllJob", method = RequestMethod.GET)
	public List<AppJob> getAllJob() {
		return appJobServiceImpl.loadJobs();
	}
	
	/**
	 * 暂停任务
	 * @return
	 */
	@RequestMapping(value = "pauseJob", method = RequestMethod.POST)
	@ApiOperation("暂停任务")
	public void pauseJob(Long jobId) {
		appJobServiceImpl.pauseJob(scheduler,jobId);
	}
	/**
	 * 恢复任务执行
	 * @param jobId
	 */
	@ApiOperation("恢复任务执行")
	@RequestMapping(value = "resumeJob", method = RequestMethod.POST)
	public void resumeJob(Long jobId) {
		appJobServiceImpl.resumeJob(scheduler,jobId);
	}
	/**
	 * 立即执行
	 * @param jobId
	 */
	@ApiOperation("立即执行")
	@RequestMapping(value = "runJob", method = RequestMethod.POST)
	public void runJob(Long jobId) {
		appJobServiceImpl.run(scheduler,jobId);
	}
	
	/**
	 * 暂停所有任务
	 * @param jobId
	 */
	@ApiOperation("暂停所有任务")
	@RequestMapping(value = "pauseAllJob", method = RequestMethod.POST)
	public void pauseAllJob() {
		appJobServiceImpl.pauseAllJob(scheduler);
	}
	
	/**
	 * 开始所有任务
	 * @param jobId
	 */
	@ApiOperation("开始所有任务")
	@RequestMapping(value = "startAllJob", method = RequestMethod.POST)
	public void startAllJob() {
		appJobServiceImpl.startAllJob(scheduler);
	}
}


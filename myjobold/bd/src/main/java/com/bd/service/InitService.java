package com.bd.service;

import java.util.List;
import java.util.Map;

import com.bd.annotation.RegisterTask;
import com.bd.entity.AppJob;


public interface InitService {
	/**
	 * 获取所有日志
	 * @return
	 */
	public List<AppJob> get();
	/**
	 * 清理任务quartz表
	 */
	public void clearQuartz();
	/**
	 * 同步任务
	 */
	List<AppJob> synchronizationTask(List<String> jobPaths, Map<String, RegisterTask> jobInfo);
}

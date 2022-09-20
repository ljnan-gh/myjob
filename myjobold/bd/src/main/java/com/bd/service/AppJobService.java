package com.bd.service;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.IService;
import com.bd.entity.AppJob;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author LJnan
 * @since 2022-01-07
 */
public interface AppJobService extends IService<AppJob> {
	/**
	 * 加载所有任务
	 */
	public List<AppJob> loadJobs();
	/**
	 * 插入新的任务
	 */
	public int AddNewJob(AppJob appJob);
	/**
	 * 删除任务
	 */
	public int delJob(List<Long> jobIds);
	/**
	 * 通过wrapper删除
	 */
	public void deleteByWrapper(List<Long> jobIds);
}

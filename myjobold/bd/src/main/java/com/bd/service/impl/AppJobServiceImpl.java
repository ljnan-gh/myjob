package com.bd.service.impl;

import java.util.List;

import org.quartz.Scheduler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bd.entity.AppJob;
import com.bd.mapper.AppJobMapper;
import com.bd.service.AppJobService;

import myjob.core.task.schedule.ScheduleUtils;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author LJnan
 * @since 2022-01-07
 */
@Service
public class AppJobServiceImpl extends ServiceImpl<AppJobMapper, AppJob> implements AppJobService {

	@Autowired
	private AppJobMapper appJobMapper;
	@Override
	public List<AppJob> loadJobs() {		
		return appJobMapper.loadJobs();
	}
	@Override
	public int AddNewJob(AppJob appJob) {		
		return appJobMapper.insert(appJob);
	}
	@Override
	public int delJob(List<Long> jobIds) {		
		
		return appJobMapper.deleteBatchIds(jobIds);
	}
	@Override
	public void deleteByWrapper(List<Long> jobIds) {		
		QueryWrapper wrapper = new QueryWrapper();
		if(jobIds.size()<=0) {
			return;//没有数据不用执行杀出
		}
		wrapper.in("job_id", jobIds);
		int rows = appJobMapper.delete(wrapper);
		System.out.println("删除" + rows + "行");
	}
	
	/**
	 * 暂停任务
	 */
	public void pauseJob(Scheduler scheduler, Long jobId) {
		ScheduleUtils.pauseJob(scheduler, jobId);
    }
	/**
	 * 恢复任务执行
	 */
	public void resumeJob(Scheduler scheduler, Long jobId) {
		ScheduleUtils.resumeJob(scheduler, jobId);
    }
	/**
	 * 任务立即执行
	 */
	public void run(Scheduler scheduler, Long jobId) {
		ScheduleUtils.run(scheduler, jobId);
    }
	
	/**
	 * 任务全部暂定
	 */
	public void pauseAllJob(Scheduler scheduler) {
		ScheduleUtils.pauseAllJob(scheduler);
	}
	public void startAllJob(Scheduler scheduler) {
		ScheduleUtils.startAllJob(scheduler);
	}
}

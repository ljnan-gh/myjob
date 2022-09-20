package myjob.core.task.schedule.entity;

import myjob.common.util.SpringUtil;
import myjob.core.task.schedule.service.TaskService;

public interface BaseJob {
	//任务直接入口，所有的任务必须继承此接口
	 public void execute();
	/**
	 * 任务执行状态
	 * @param status0:失败 1:成功
	 */
	public default void update(int status,long lastExecutionTime,long jobId) {
		TaskService TaskService = SpringUtil.getBean(myjob.core.task.schedule.service.TaskService.class);
		TaskService.updateLastExcuteTime(lastExecutionTime, status, jobId);
	}
}

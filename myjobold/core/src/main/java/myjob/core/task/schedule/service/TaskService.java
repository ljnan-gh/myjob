package myjob.core.task.schedule.service;

import java.util.List;

public interface TaskService {
	public List<String> gets();
	public int updateLastExcuteTime(Long lastExecutionTime,int lastExecutionStatus,Long jobId);
}

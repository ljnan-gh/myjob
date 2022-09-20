package myjob.core.task.schedule.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import myjob.core.task.schedule.mapper.TaskMapper;
import myjob.core.task.schedule.service.TaskService;

@Service
public class TaskServiceImpl implements TaskService{
	@Autowired
	private TaskMapper taskMapper;
	@Override
	public List<String> gets() {
		return taskMapper.gets();
	}
	@Override
	public int updateLastExcuteTime(Long lastExecutionTime, int lastExecutionStatus, Long jobId) {		
		return taskMapper.updateLastExcuteTime(lastExecutionTime, lastExecutionStatus, jobId);
	}
	
	
}

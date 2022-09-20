package myjob.core.task.schedule;

import java.util.Date;

import lombok.Data;

@Data
public class JobInfo {
	/**
	 * 任务名称
	 */
	private String jobName;
	/**
	 * 任务id
	 */
	private String JobId;
	/**
	 * 任务执行时间表达式
	 */
	private String cronExpression;
	/**
	 * 上一次开始执行时间
	 */
	private Date lastExecutionTime;
	/**
	 * 类路径
	 */
	private String jobPath;
}

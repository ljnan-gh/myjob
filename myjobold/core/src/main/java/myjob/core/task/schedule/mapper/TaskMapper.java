package myjob.core.task.schedule.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

public interface TaskMapper {
	@Select("select job_path from app_job")
	public List<String> gets();
	
	/**
	 * 跟新任务信息（任务执行状态，最后执行时间）
	 */
	@Update("update app_job set last_execution_time = #{lastExecutionTime},last_execution_status = #{lastExecutionStatus} where job_id = #{jobId};")
	public int updateLastExcuteTime(@Param("lastExecutionTime")Long lastExecutionTime,
			@Param("lastExecutionStatus")int lastExecutionStatus,@Param("jobId")Long jobId);
}

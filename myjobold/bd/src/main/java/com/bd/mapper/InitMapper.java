package com.bd.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Select;

import com.bd.entity.AppJob;


//@Repository
public interface InitMapper{
	
	//加载所有任务
	@Select("select * from app_job")
	public List<AppJob> get();
	
	//清空quartz定时表
	@Delete("delete from qrtz_blob_triggers")
	public void clearBlobTriggers();
	@Delete("delete from qrtz_blob_triggers_copy")
	public void clearBolbTriggersCopy();
	@Delete("delete from qrtz_calendars")
	public void clearCalendars();
	@Delete("delete from qrtz_cron_triggers")
	public void clearCronTriggers();
	@Delete("delete from qrtz_fired_triggers")
	public void clearFiredTriggers();
	@Delete("delete from qrtz_job_details")
	public void clearJobDetails();
	@Delete("delete from qrtz_locks")
	public void clearLocks();
	@Delete("delete from qrtz_paused_trigger_grps")
	public void clearPausedTriggerGrps();
	@Delete("delete from qrtz_scheduler_state")
	public void clearScheulerState();
	@Delete("delete from qrtz_simple_triggers")
	public void clearSimpleTriggers();
	@Delete("delete from qrtz_simprop_triggers")
	public void clearSimpropTriggers();
	@Delete("delete from qrtz_triggers")
	public void clearTriggers();
	
}

package myjob.core.task.schedule;

import java.util.Date;

import org.quartz.CronScheduleBuilder;
import org.quartz.JobBuilder;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ScheduleUtils {
	
	 public static final String JObIDHEAD = "TASK_";
    /**
     * 创建定时任务
     * @param scheduler
     */
    public static void createScheduleJob(Scheduler scheduler,JobInfo job) {
        String cronExpression = job.getCronExpression();
        CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(cronExpression);
        try{
        	//设置使用时需要提供的数据
        	JobDataMap jobDataMap = new JobDataMap();
        	jobDataMap.put(job.getJobId(),job.getJobPath());
            JobDetail jobDetail = JobBuilder.newJob(JobExecutor.class).withIdentity(job.getJobId()).storeDurably().setJobData(jobDataMap).build();
            Trigger trigger = TriggerBuilder.newTrigger().withIdentity(job.getJobId()).withSchedule(scheduleBuilder).
            		startAt(job.getLastExecutionTime()== null ? new Date():job.getLastExecutionTime()).build();
            scheduler.scheduleJob(jobDetail, trigger);
        }catch (SchedulerException e){
            log.info("创建定时任务失败,任务id{}",job.getJobId());
            throw new RuntimeException("创建定时任务失败",e);
        }
    }
    
    public static void pauseJob(Scheduler scheduler, Long jobId) {
        try {
            scheduler.pauseJob(getJobKey(jobId));
        } catch (org.quartz.SchedulerException e) {
            throw new RuntimeException("暂停定时任务失败", e);
        }
    }
    
    /**
     * 暂定任务
     * @param jobId
     * @return
     */
    public static JobKey getJobKey(Long jobId) {
        return JobKey.jobKey(JObIDHEAD + jobId);
    }
    /**
     * 启动任务
     * @param scheduler
     * @param flowId
     */
    public static void resumeJob(org.quartz.Scheduler scheduler, Long jobId) {
        try
        {
            scheduler.resumeJob(getJobKey(jobId));
        }
        catch (org.quartz.SchedulerException e) {
            throw new RuntimeException("启动定时任务失败", e);
        }
    }
    public static void deleteScheduleJob(org.quartz.Scheduler scheduler, Long jobId) {
        try  {
            scheduler.deleteJob(getJobKey(jobId));
        } catch (org.quartz.SchedulerException e) {
            throw new RuntimeException("删除定时任务失败", e);
        }
    }
    /**
     * 立即执行
     * @param scheduler
     */
    public static void run(org.quartz.Scheduler scheduler,Long jobId) {
        try
        {
            scheduler.triggerJob(getJobKey(jobId));
        }
        catch (org.quartz.SchedulerException e) {
            e.printStackTrace();
            throw new RuntimeException("立即执行定时任务失败", e);
        }
    }
    /**
     * 修改执行时间
     * @throws SchedulerException 
     */
    public static void updateTime(Scheduler scheduler,Long jobId) throws SchedulerException {
    	JobKey jk = getJobKey(jobId);
    	JobDetail jobDetail = scheduler.getJobDetail(jk);
    	Trigger trigger = TriggerBuilder.newTrigger().withSchedule(CronScheduleBuilder.cronSchedule("3/4 * * * * ? *")).build();
    	//删除任务，不删除会报错，报任务已经存在
    	scheduler.deleteJob(jk);
    	scheduler.scheduleJob(jobDetail,trigger);
    }
    /**
	 * 任务全部暂定
	 */
	public static void pauseAllJob(Scheduler scheduler) {
		try {
			if(!scheduler.isShutdown())
				scheduler.shutdown();
		} catch (SchedulerException e) {
			log.info("暂停全部任务失败！！！");
		}
	}

	/*
	 * 任务全部开始
	 */
	public static void startAllJob(Scheduler scheduler) {
		try {
			scheduler.start();
		} catch (SchedulerException e) {
			log.info("启动全部任务失败！！！");
		}
	}

	/*
	 * 任务全部运行，只执行一次
	 */
	public static void runAllJob(Scheduler scheduler) {
//		try {
//			scheduler.start();
//		} catch (SchedulerException e) {
//			log.info("启动全部任务失败！！！");
//		}
	}
}

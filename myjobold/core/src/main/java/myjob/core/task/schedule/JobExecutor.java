package myjob.core.task.schedule;

import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;

import lombok.extern.slf4j.Slf4j;
import myjob.common.util.ExceptionInfoPrintUtil;
import myjob.common.util.ExecutorFactory;
import myjob.common.util.ReflectUtil;
import myjob.core.lock.redis.RedisUtils;
import myjob.core.task.schedule.entity.BaseJob;

/**
 * 任务执行器
 * @author ljnan
 *2022-01-10
 */
@Slf4j
public class JobExecutor extends QuartzJobBean {
	@Autowired
	private RedisUtils redisUtils;
	private ExecutorService service = ExecutorFactory.getInstace().getService();
	@Override
	protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {
//		log.info("主进程{}",Thread.currentThread().getName() );
		// 获取任务名称
		JobDetail jobDetail = jobExecutionContext.getJobDetail();
		if(!redisUtils.tryLock(jobDetail.getKey().getName())) {
//			log.info("{}任务正在执行中...",jobName);
			return;
		}
		// 获取任务类路径
		try {
			//下次执行时间
			Date nextFireTime = jobExecutionContext.getNextFireTime();
			long nextFireTimeOfLong = nextFireTime.getTime();
			ScheduleRunner task = new ScheduleRunner(jobDetail,nextFireTimeOfLong);
			Future<?> future = this.service.submit(task);
			future.get();
			redisUtils.unLock(jobDetail.getKey().getName());
			task = null;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
    
    /**
     * ScheduleRunner执行类
     * @author ljnan
     *2022-01-10
     */
    public class ScheduleRunner implements Runnable{    	
    	String jobName;
    	Object params;
    	String jobPath;
    	long nextFireTime;//下一次任务执行时间
    	long jobId;//任务id
    	private boolean result;//暂时只是作为是否成功的标志，后边如果有需要可将其改成实体类，返回执行需要的结果。
    	public ScheduleRunner(JobDetail jobDetail,long nextFireTime) {
    		String jobName = jobDetail.getKey().getName();
    		this.jobId = Long.parseLong(jobName.replace(ScheduleUtils.JObIDHEAD,""));
    		this.nextFireTime = nextFireTime;
    		jobPath = (String)jobDetail.getJobDataMap().get(jobName);
    		
    	}

		@Override
		public void run() {
			BaseJob bj = null;
			try {
				bj = (BaseJob) ReflectUtil.getClassByUrl(jobPath).newInstance();
			} catch (InstantiationException | IllegalAccessException e) {
				log.error(ExceptionInfoPrintUtil.getExceptionInfo(e));
			}
			try {
				if (bj == null) {
					log.error("路径配置错误{}", jobPath);
					return;
				}
				bj.execute();
				bj.update(1,nextFireTime,jobId);	
				result = true;
			} catch (Exception e) {
				result = false;
				bj.update(0,nextFireTime,jobId);
				log.error(ExceptionInfoPrintUtil.getExceptionInfo(e));
			}
			log.info("{}任务结束------>执行{}，时间是{}", jobPath, result == true ? "成功" : "失败", new Date());
		}		
    	public boolean getResult() {
    		return result;
    	}
    }
}
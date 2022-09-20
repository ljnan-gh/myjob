package com.bd.job;

import java.util.Date;

import org.springframework.stereotype.Component;

import com.bd.annotation.RegisterTask;

import lombok.extern.slf4j.Slf4j;
import myjob.core.task.schedule.entity.BaseJob;

@Component
@Slf4j
public class Test1 implements BaseJob{
	
	@RegisterTask(taskName = "Test1",cronStr = "3/4 * * * * ? *",description = "测试Test1的任务")
	@Override
	public void execute() {
		try {
			log.info("{}任务开始执行{}","Test1",new Date());			
			Thread.sleep(9999);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}

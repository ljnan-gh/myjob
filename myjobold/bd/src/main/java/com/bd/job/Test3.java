package com.bd.job;

import java.util.Date;

import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;
import myjob.core.task.schedule.entity.BaseJob;

@Component
@Slf4j
public class Test3 implements BaseJob{

	@Override
	public void execute() {
		log.info("{}任务开始执行{}","Test3",new Date());		
	}
}

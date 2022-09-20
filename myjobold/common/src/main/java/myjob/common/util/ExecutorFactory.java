package myjob.common.util;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.springframework.beans.factory.annotation.Value;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ExecutorFactory{
    private ExecutorService service = null;

    @Value("${app.jobThreadNumber}")
    Integer threadNumber = Integer.valueOf(3);

    private static ExecutorFactory instace = new ExecutorFactory();

	public static ExecutorFactory getInstace() {
		return instace;
	}

	public ExecutorService getService() {
		if (this.service == null) {
			log.info("create thread pool (" + this.threadNumber + ")");
			this.service = Executors.newFixedThreadPool(this.threadNumber.intValue());
		}
		return this.service;
	}
}
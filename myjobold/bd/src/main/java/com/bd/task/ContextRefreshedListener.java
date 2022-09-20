package com.bd.task;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.map.HashedMap;
import org.springframework.aop.support.AopUtils;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import org.springframework.util.ReflectionUtils;

import com.bd.annotation.RegisterTask;
import com.bd.service.InitConfigService;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import myjob.core.task.schedule.entity.BaseJob;

@Slf4j
@Component
public class ContextRefreshedListener implements ApplicationListener<ContextRefreshedEvent> {
    @SneakyThrows
    public void onApplicationEvent(ContextRefreshedEvent event) {
        // 根容器为Spring容器
        if (event.getApplicationContext().getParent() == null) {
        	Map<String, Object> beans = event.getApplicationContext().getBeansWithAnnotation(Component.class);
        	//所有任务
            Map<String, BaseJob> baseJobs = event.getApplicationContext().getBeansOfType(BaseJob.class);              
            getClassMethodByAnnotation(beans,toPath(baseJobs.values()));                      
        } else {
            log.info("=====ContextRefreshedEvent===== 获取Spring根容器失败 ");
        }
    }

    public List<String> toPath(Collection<BaseJob> baseJobs) throws Exception {    	
    	List<String>jobPaths = new ArrayList<String>();
        for(BaseJob bj: baseJobs) {
        	jobPaths.add(bj.getClass().getName());
        }
        return jobPaths;
    }

	public void getClassMethodByAnnotation(Map<String, Object> beans, List<String> jodPaths)
			throws Exception {
		Map<String, RegisterTask> result = new HashedMap();
		for (Object bean : beans.values()) {
			Class clz = bean.getClass();
			clz = Class.forName(clz.getName(), true, clz.getClassLoader());			
			Class clazz = AopUtils.getTargetClass(bean);
			String name = clazz.getName();
			if (jodPaths.contains(name)) {
				Method[] methods = ReflectionUtils.getAllDeclaredMethods(clazz);
				for (Method method : methods) {
					if (method.getName().equals("execute")) {
						RegisterTask registerTask = method.getAnnotation(RegisterTask.class);
						result.put(name, registerTask);
						break;
					}
				}
			}
		}
		InitConfigService i = InitConfigService.getInitConfigService();
		//i.init(jodPaths,result);
	}
}

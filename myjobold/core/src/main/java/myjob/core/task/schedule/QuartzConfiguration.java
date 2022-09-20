package myjob.core.task.schedule;

import lombok.extern.slf4j.Slf4j;
import org.quartz.*;
import org.quartz.impl.JobDetailImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.config.YamlPropertiesFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.io.IOException;
import java.util.Properties;

/**
 * quartz配置信息
 */
@Configuration
@Slf4j
public class QuartzConfiguration {
    @Autowired
    private JobFactory jobFactory;
    @Resource(name = "mainDataSource")
    private DataSource mainDataSource;

    @Bean(name = "schedulerFactoryBean")
    public SchedulerFactoryBean schedulerFactoryBean() {
        SchedulerFactoryBean schedulerFactoryBean = new SchedulerFactoryBean();
        schedulerFactoryBean.setJobFactory(jobFactory);
        schedulerFactoryBean.setDataSource(mainDataSource);
        schedulerFactoryBean.setSchedulerName("scheduler");
        //延长启动
        schedulerFactoryBean.setStartupDelay(1);
        //启动时跟新已存在的Job，这样就不用每次修改targetObject后删除qrtz_job_details表对应的纪录了
        schedulerFactoryBean.setOverwriteExistingJobs(true);
        //设置加载的配置文件
        try {
            schedulerFactoryBean.setQuartzProperties(quartzProperties());
        } catch (Exception e) {
            log.info("加载配置文件失败!");
        }
        return schedulerFactoryBean;
    }

    @Bean(name = "scheduler")
    public Scheduler scheduler() {
        return schedulerFactoryBean().getScheduler();
    }

    /**
     * 暂停任务
     */
    private void stopJob(@Qualifier("schedulerFactoryBean") SchedulerFactoryBean factory) {
        Scheduler scheduler = factory.getScheduler();
        JobDetail a = new JobDetailImpl();
    }

    @Bean
    public Properties quartzProperties() throws IOException {
//        PropertiesFactoryBean propertiesFactoryBean = new PropertiesFactoryBean();
        YamlPropertiesFactoryBean yamlPropertiesFactoryBean = new YamlPropertiesFactoryBean();
        yamlPropertiesFactoryBean.setResources(new ClassPathResource("/conf/quartz.yml"));
        // 在quartz.properties中的属性被读取并注入后再初始化对象
        yamlPropertiesFactoryBean.afterPropertiesSet();
        return yamlPropertiesFactoryBean.getObject();
    }
}

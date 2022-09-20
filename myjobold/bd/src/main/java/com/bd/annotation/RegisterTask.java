package com.bd.annotation;

import java.lang.annotation.*;

/**
 * 注册任务
 */
@Target(ElementType.METHOD)
@Retention(value = RetentionPolicy.RUNTIME)
@Documented
public @interface RegisterTask {
    String value() default "";

    /**
     * 任务名称
     *
     * @return
     */
    String taskName() default "";

    /**
     * cron表达式，任务执行时间设置
     *
     * @return
     */
    String cronStr() default "3/5 * * * * ? *";
    /**
     * 任务默认状态
     * 0开始1暂停2其他(2情况下准备在前端自启动)
     */
    int status() default 0;
    
    /**
     * 任务说明，标注该任务具体功能
     * @return
     */
    String description() default "";
    
}

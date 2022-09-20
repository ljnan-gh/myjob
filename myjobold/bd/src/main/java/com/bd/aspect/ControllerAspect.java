package com.bd.aspect;

import java.lang.reflect.Method;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bd.annotation.ControllerCheck;

import lombok.extern.slf4j.Slf4j;
import myjob.common.exception.MyJobException;
import myjob.core.db.mysql.ChangeDataSourceService;
import myjob.core.db.mysql.DynamicDataSourceContextHolder;

@Slf4j
@Aspect
@Component
public class ControllerAspect {

    @Autowired
    private ChangeDataSourceService changeDataSourceService;

    @Pointcut("@annotation(com.bd.annotation.ControllerCheck)")
    public void annotationPointcut() {
    }

    @Before("annotationPointcut()")
    public void beforePointcut(JoinPoint joinPoint) {
    }

    @Around("annotationPointcut()")
    public Object doAround(ProceedingJoinPoint joinPoint) throws Throwable {
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        Method method = methodSignature.getMethod();
        ControllerCheck controllerChack = method.getAnnotation(ControllerCheck.class);
        //检查数据库设置,如果没有填写就是默认数据库
        String currDatasourceId;
        log.info("执行开始,当前系统内数据源个数是：{}", DynamicDataSourceContextHolder.getNum());
        if ((currDatasourceId = controllerChack.datasourceId().trim()).equals("")) {
            DynamicDataSourceContextHolder.removeDataSource();
        } else {
            //判断是否有这个数据库
            if (!changeDataSourceService.changeDB(currDatasourceId)) {
                throw new MyJobException("error_datasourceId");
            }
        }
        log.info("数据源初始化完毕,当前系统内数据源个数是：{}", DynamicDataSourceContextHolder.getNum());
        return joinPoint.proceed();
    }


    /**
     * 在切入点return内容之后切入内容（可以用来对处理返回值做一些加工处理）
     *
     * @param joinPoint
     */
    @AfterReturning(returning = "obj", pointcut = "annotationPointcut()")
    public void doAfterReturning(JoinPoint joinPoint, Object obj) {
        log.info("执行完毕,当前系统内数据源个数是：{}", DynamicDataSourceContextHolder.getNum());
    }

    /**
     * 捕获异常
     *
     * @param ex
     * @throws Exception
     */
    @AfterThrowing(throwing = "ex", pointcut = "annotationPointcut()")
    public void doAfterThrowing(Throwable ex) throws Exception {

    }

}

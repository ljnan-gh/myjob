package com.bd;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class},
        scanBasePackages = {"com.bd","myjob.common","myjob.core"})
@EnableSwagger2
@MapperScan(value = {"com.bd.mapper","myjob.core.task.schedule.mapper"})
public class App {
    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }
    /**
     * 测试对象池
     */
    public static void test() {
//    	ConnectionFactory orderFactory = new ConnectionFactory();
//    	GenericObjectPoolConfig config  = new GenericObjectPoolConfig();
//    	config.setMaxTotal(5);
//    	config.setMaxWaitMillis(1000);
//    	ConnectionPool connectionPool= new ConnectionPool(orderFactory,config);
//    	for(int i = 0; i < 7;i++) {
//    		try {
//				Connection con = connectionPool.borrowObject();
//				System.out.println("borrow a connection:" + con + " active connection:" + connectionPool.getNumActive());
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
//    	}
    }
}

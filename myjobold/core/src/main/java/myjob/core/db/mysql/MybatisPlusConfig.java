package myjob.core.db.mysql;

import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import com.baomidou.mybatisplus.extension.plugins.PerformanceInterceptor;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
public class MybatisPlusConfig {
    /**
     * 分页插件
     */
    @Bean
    public PaginationInterceptor paginationInterceptor() {
        return new PaginationInterceptor();
    }
    
    @Bean
    @Profile({"dbinfo","dev"})
    public PerformanceInterceptor performanceInterceptor() {
    	PerformanceInterceptor performanceInterceptor = new PerformanceInterceptor();
    	//单位ms,超过此处设置的时间后，则sql就不执行了
    	performanceInterceptor.setMaxTime(10000);//此处时间太短导致sql不执行
    	performanceInterceptor.setFormat(true);//
    	return performanceInterceptor;
    }
}

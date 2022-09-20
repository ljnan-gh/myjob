package myjob.core.cache.redis;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
public class RedisPoolConfig {
    //最大空闲连接数
    @Value("${redis.pool.maxIdle:400}")
    private int maxIdle;
    //最小空闲连接数
    @Value("${redis.pool.minIdle:8}")
    private int minIdle = 8;
    //当池内没有可用的连接时最大等待时间
    @Value("${redis.pool.maxWaitMillis:1000}")
    private int maxWaitMillis = -1;
    //最大连接数
    @Value("${redis.pool.maxTotal:6000}")
    private int maxTotal;

    @Bean("redis-Pool-Config")
    public RedisPoolConfig redisPoolConfig() {
        return this;
    }
}

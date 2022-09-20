package myjob.core.cache.redis;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 哨兵模式redis配置
 */
@Configuration
@Data
public class RedisSentinelConfig {

    /**
     * nodes
     */
    @Value("${redis.sentinel.nodes:}")
    private String nodes;
    /**
     * 密码
     */
    @Value("${redis.sentinel.password:}")
    private String password;
    /**
     * 超时时间
     */
    @Value("${redis.sentinel.timeout:0}")
    private int timeout;
    /**
     * 数据库,默认是0
     */
    @Value("${redis.sentinel.database:1}")
    private int database;
    /**
     * master
     */
    @Value("${redis.sentinel.master:}")
    private String master;

    @Bean("redis-Sentinel-Config")
    public RedisSentinelConfig redisSentinelConfig() {
        return this;
    }
}

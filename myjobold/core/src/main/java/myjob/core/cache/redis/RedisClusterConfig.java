package myjob.core.cache.redis;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * redis集群配置
 */
@Configuration
@Data
public class RedisClusterConfig {

    /**
     * nodes
     */
    @Value("${redis.cluster.nodes:}")
    private String nodes;
    /**
     * 密码
     */
    @Value("${redis.cluster.password:}")
    private String password;
    /**
     * 超时时间
     */
    @Value("${redis.cluster.timeout:2000}")
    private int timeout;
    /**
     * 数据库,默认是0
     */
    @Value("${redis.cluster.database:1}")
    private int database;
    /**
     * maxRedirects
     */
    @Value("${redis.cluster.maxRedirects:}")
    private String maxRedirects;

    @Bean("redis-Cluster-Config")
    public RedisClusterConfig redisClusterConfig() {
        return this;
    }
}

package myjob.core.cache.redis;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/***
 * Redis单点配置
 */
@Configuration
@Data
public class RedisStandaloneConfig {

    @Value("${redis.standalone.host}")
    private String hostName;
    @Value("${redis.standalone.port}")
    private int port;
    @Value("${redis.standalone.password}")
    private String password;

    @Bean("redis-Standalone-Config")
    public RedisStandaloneConfig redisStandaloneConfig() {
        return this;
    }
}

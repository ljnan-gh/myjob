package myjob.core.cache.redis;

import java.util.HashSet;
import java.util.Set;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.connection.RedisClusterConfiguration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisPassword;
import org.springframework.data.redis.connection.RedisSentinelConfiguration;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisClientConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;
import myjob.common.util.EncryptorUtil;
import redis.clients.jedis.JedisPoolConfig;

@Component
@Slf4j
public class RedisConfig {

    @Resource(name = "redis-Standalone-Config", description = "单点模式配置信息")
    private RedisStandaloneConfig redisStandaloneConfig;

    @Resource(name = "redis-Sentinel-Config", description = "哨兵模式配置信息")
    private RedisSentinelConfig redisSentinelConfig;

    @Resource(name = "redis-Cluster-Config", description = "集群模式配置信息")
    private RedisClusterConfig redisClusterConfig;

    @Resource(name = "redis-Pool-Config", description = "连接池配置信息")
    private RedisPoolConfig redisPoolConfig;

    @Autowired
    private EncryptorUtil encryptorUtil;

    @Bean("redisTemplate")
    public RedisTemplate<Object, Object> redisTemplate(@Qualifier("redisConnectionFactory") RedisConnectionFactory factory,
                                                       @Qualifier("stringRedisSerializer") StringRedisSerializer stringRedisSerializer) {
        RedisTemplate<String, Object> template = new RedisTemplate();
        template.setConnectionFactory(factory);
        return setRedisTemplateConfig(template, stringRedisSerializer);
    }

    @Bean("redisTemplate2")
    public RedisTemplate<String, Object> redisTemplate2(@Qualifier("redisConnectionFactory") RedisConnectionFactory factory,
                                                        @Qualifier("stringRedisSerializer") StringRedisSerializer stringRedisSerializer) {
        RedisTemplate<String, Object> template = new RedisTemplate();
        template.setConnectionFactory(factory);
        return setRedisTemplateConfig(template, stringRedisSerializer);
    }
    @Bean("redisTemplate3")
    public RedisTemplate<String, String> redisTemplate3(@Qualifier("redisConnectionFactory") RedisConnectionFactory factory,
                                                        @Qualifier("stringRedisSerializer") StringRedisSerializer stringRedisSerializer) {
        RedisTemplate<String, String> template = new RedisTemplate();
        template.setConnectionFactory(factory);
        return setRedisTemplateConfig(template, stringRedisSerializer);
    }
    private RedisTemplate setRedisTemplateConfig(RedisTemplate template, StringRedisSerializer stringRedisSerializer) {
        //Json序列化配置
        Jackson2JsonRedisSerializer jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer(Object.class);
        ObjectMapper om = new ObjectMapper();
        om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        om.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
        jackson2JsonRedisSerializer.setObjectMapper(om);
        //Key采用string序列化方式
        template.setKeySerializer(stringRedisSerializer);
        //hash的key也采用String序列化方式
        template.setHashKeySerializer(stringRedisSerializer);
        //value的key也采用jackson序列化方式
        template.setValueSerializer(jackson2JsonRedisSerializer);
        //hash的value也采用jackson序列化方式
        template.setHashValueSerializer(jackson2JsonRedisSerializer);
        template.afterPropertiesSet();
        return template;
    }

    @Bean("poolConfig")
    public JedisPoolConfig jedisPoolConfig() {
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        jedisPoolConfig.setMaxIdle(redisPoolConfig.getMaxIdle());
        jedisPoolConfig.setMaxTotal(redisPoolConfig.getMaxTotal());
        jedisPoolConfig.setMaxWaitMillis(redisPoolConfig.getMaxWaitMillis());
        jedisPoolConfig.setBlockWhenExhausted(true);
        jedisPoolConfig.setTestOnBorrow(true);
        return jedisPoolConfig;
    }

    /**
     * jedis连接工厂,选择连接模式，配置文件中配置什么模式会自动识别，如果全部配置了，那么就会按照集群、哨兵、单点顺序优先选择，同时有且只有一种模式生效
     */
    @Bean("redisConnectionFactory")
    public RedisConnectionFactory redisConnectionFactory(@Qualifier("poolConfig") JedisPoolConfig jedisPoolConfig) {
        //获得默认的连接池构造器
        JedisClientConfiguration.JedisPoolingClientConfigurationBuilder jpcb = (JedisClientConfiguration.JedisPoolingClientConfigurationBuilder) JedisClientConfiguration.builder();
        //指定jedisPoolConfig来修改默认的连接池构造器
        jpcb.poolConfig(jedisPoolConfig);
        JedisClientConfiguration jedisClientConfiguration = jpcb.build();
        //是否是集群配置
        if (redisClusterConfig.getNodes() != null && !"".equals(redisClusterConfig.getNodes().trim())) {
            redisClusterConfig.setPassword(encryptorUtil.decryptStr(redisClusterConfig.getPassword()));
            log.info("Redis采用集群配置方式启动");
            return new JedisConnectionFactory(redisClusterConfiguration(), jedisClientConfiguration);
        }
        //是否配置哨兵模式
        if (redisSentinelConfig.getNodes() != null && !"".equals(redisSentinelConfig.getNodes().trim())) {
            redisSentinelConfig.setPassword(encryptorUtil.decryptStr(redisSentinelConfig.getPassword()));
            log.info("Redis采用哨兵配置方式启动");
            return new JedisConnectionFactory(redisSentinelConfiguration(), jedisClientConfiguration);
        }
        redisStandaloneConfig.setPassword(encryptorUtil.decryptStr(redisStandaloneConfig.getPassword()));
        log.info("Redis采用单点配置方式启动");
        return new JedisConnectionFactory(redisStandaloneConfiguration(), jedisClientConfiguration);
        //单机配置 + 客户端配置 = jedis连接工厂redisStandaloneConfiguration,jedisClientConfiguration
    }

    /**
     * redis序列化
     */
    @Bean("stringRedisSerializer")
    public StringRedisSerializer stringRedisSerializer() {
        StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();
        return stringRedisSerializer;
    }

    /********************************************************************************/
    /********************************模式配置*****************************************/
    /********************************************************************************/
    /**
     * 集群模式配置
     *
     * @return
     */
    private RedisClusterConfiguration redisClusterConfiguration() {
        String[] nodes = redisClusterConfig.getNodes().split(",");
        Set<String> setNodes = new HashSet<>();
        for (String node : nodes) {
            setNodes.add(node.trim());
        }
        RedisClusterConfiguration configuration = new RedisClusterConfiguration(setNodes);
        return configuration;
    }

    /**
     * 哨兵模式配置
     */
    private RedisSentinelConfiguration redisSentinelConfiguration() {
        String[] nodes = redisSentinelConfig.getNodes().split(",");
        Set<String> setNodes = new HashSet<>();
        for (String n : nodes) {
            setNodes.add(n.trim());
        }
        RedisSentinelConfiguration configuration = new RedisSentinelConfiguration(redisSentinelConfig.getMaster(), setNodes);
        return configuration;
    }

    /**
     * 单点模式配置
     */
    private RedisStandaloneConfiguration redisStandaloneConfiguration() {
        RedisStandaloneConfiguration redisStandaloneConfiguration = new RedisStandaloneConfiguration();
        redisStandaloneConfiguration.setHostName(redisStandaloneConfig.getHostName());
        //设置默认使用的数据库
        redisStandaloneConfiguration.setDatabase(0);
        //设置密码
        redisStandaloneConfiguration.setPassword(RedisPassword.of(redisStandaloneConfig.getPassword()));
        //设置redis服务端口号
        redisStandaloneConfiguration.setPort(redisStandaloneConfig.getPort());
        return redisStandaloneConfiguration;
    }   
    
}

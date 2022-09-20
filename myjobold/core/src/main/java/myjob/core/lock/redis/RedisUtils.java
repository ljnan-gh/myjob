package myjob.core.lock.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import redis.clients.jedis.Jedis;

import java.util.concurrent.TimeUnit;

@Component
public class RedisUtils {

    //    @Resource(name="redisTemplate")
//    private RedisTemplate<Object,Object> redisTemplate;
    @Autowired
    private StringRedisTemplate redisTemplate;
    private final String LOCKFLAG = "LOCK";
    /**
     * redis分布式锁
     *
     * @param key
     * @param value
     * @param timeout
     * @return 如果已经存在就会放回false
     */
    public boolean tryLock(String key, String value, int timeout) {
    	key = key + LOCKFLAG;
    	value = value + LOCKFLAG;
        if (timeout == 0) timeout = 60 * 3;
        boolean isSuccess = redisTemplate.opsForValue().setIfAbsent(key, value);
        //设置过期时间防止死锁
        if (isSuccess) redisTemplate.expire(key, timeout, TimeUnit.SECONDS);
        return isSuccess;
    }
    /**
     * redis分布式锁
     * @param key
     * @return
     */
    public boolean tryLock(String key) {
    	return tryLock(key,key,0);
    }
    
    /**
     * 获取分布式锁
     * @param key
     * @param timeout
     * @return
     */
	public boolean tryLock(String key,int timeout) {
		return tryLock(key,key,timeout);
	}
    /**
     * 分布式锁释放
     *
     * @param key
     * @param value
     */
    public void unLock(String key, String value) {
    	key = key + LOCKFLAG;
    	value = value + LOCKFLAG;
        String currentValue = redisTemplate.opsForValue().get(key);
        if (currentValue.equals(value))
            redisTemplate.opsForValue().getOperations().delete(key);
    }
    /**
     * 分布式锁释放
     * @param key
     */
    public void unLock(String key) {
    	unLock(key,key);
    }
}

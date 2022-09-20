package myjob.core.cache.redis;

import javax.annotation.Resource;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
/**
 * redis统一管理类
 * @author ljnan
 *2022-01-10
 */
@Service
public class RedisManage {
	@Resource(name = "redisTemplate")
    private RedisTemplate<Object, Object> redisTemplate;
	@Resource(name = "redisTemplate2")
    private RedisTemplate<String, Object> redisTemplate2;
	@Resource(name = "redisTemplate3")
    private RedisTemplate<String, String> redisTemplate3;
	
	/**
	 * 添加缓存信息
	 * @param key 存储时用的key
	 * @param resourceKey 存在哪个缓存区域里面
	 * @param data 缓存数据
	 */
	public void saveRedis(String key,RedisKey resourceKey,Object data) {
		switch (resourceKey) {
		case redisTemplate:
			redisTemplate.boundValueOps(key).set(data);
			break;
		case redisTemplate2:
			redisTemplate2.boundValueOps(key).set(data);
			break;
		case redisTemplate3:
			redisTemplate3.boundValueOps(key).set(String.valueOf(data));
			break;
		}    		
	} 

	public Object getRedisByKey(String key, RedisKey resourceKey) {
		Object object = null;// 存储取出的结果
		switch (resourceKey) {
		case redisTemplate:
			object = redisTemplate.boundValueOps(key).get();
			break;
		case redisTemplate2:
			object = redisTemplate2.boundValueOps(key).get();
			break;
		case redisTemplate3:
			object = redisTemplate3.boundValueOps(key).get();
			break;
		}
		return object;
	}

}

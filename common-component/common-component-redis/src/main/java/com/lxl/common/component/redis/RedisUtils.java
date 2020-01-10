/**
 * 
 */
package com.lxl.common.component.redis;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

/**
 * @author Administrator
 *
 */
@Component
public class RedisUtils {

	private static final Logger logger = LoggerFactory.getLogger(RedisUtils.class);

	@Autowired
	private RedisTemplate<String, Object> redisTemplate;

	/**
	 * String 类型set
	 * 
	 * @param key
	 * @param value
	 */
	public void set(String key, Object value) {
		try {
			redisTemplate.opsForValue().set(key, value);
		} catch (Exception e) {
			logger.error("redis set error: key={}, value={}", key, value, e);
			throw new RuntimeException("redis set error");
		}
	}

	/**
	 * String 类型set
	 * 
	 * @param key
	 * @param value
	 * @param timeout
	 */
	public void set(String key, Object value, long timeout) {
		try {
			redisTemplate.opsForValue().set(key, value, timeout, TimeUnit.SECONDS);
		} catch (Exception e) {
			logger.error("redis set error: key={}, value={}, timeout={}", key, value, timeout, e);
			throw new RuntimeException("redis set error");
		}
	}

	/**
	 * String 类型get
	 * 
	 * @param key
	 * @return
	 */
	public Object get(String key) {
		try {
			return key == null ? null : redisTemplate.opsForValue().get(key);
		} catch (Exception e) {
			logger.error("redis get error: key={}", key, e);
			throw new RuntimeException("redis set error");
		}
	}

	/**
	 * Sting 类型设置过期时间
	 * 
	 * @param key
	 * @param timeout
	 */
	public void expire(String key, long timeout) {
		if (timeout <= 0) {
			throw new RuntimeException("redis expire timeout is less than zero!");
		}
		try {
			redisTemplate.expire(key, timeout, TimeUnit.SECONDS);
		} catch (Exception e) {
			logger.error("redis expire error: key={}", key, e);
			throw new RuntimeException("redis set error");
		}
	}

	/**
	 * String 类型获取过期时间
	 * 
	 * @param key
	 * @return
	 */
	public long getExpire(String key) {
		try {
			return redisTemplate.getExpire(key, TimeUnit.SECONDS);
		} catch (Exception e) {
			logger.error("redis getExpire error: key={}", key, e);
			throw new RuntimeException("redis getExpire error");
		}
	}

	/**
	 * Stirng 类型递增
	 * 
	 * @param key
	 * @param delta
	 * @return
	 */
	public long incr(String key, long delta) {
		if (delta < 0) {
			throw new RuntimeException("递增因子必须大于0");
		}
		try {
			return redisTemplate.opsForValue().increment(key, delta);
		} catch (Exception e) {
			logger.error("redis incr error: key={}, delta={}", key, delta, e);
			throw new RuntimeException("redis incr error");
		}
	}

	/**
	 * Stirng 类型递减
	 * 
	 * @param key
	 * @param delta
	 * @return
	 */
	public long decr(String key, long delta) {
		if (delta < 0) {
			throw new RuntimeException("递减因子必须大于0");
		}
		try {
			return redisTemplate.opsForValue().increment(key, -delta);
		} catch (Exception e) {
			logger.error("redis decr error: key={}, delta={}", key, delta, e);
			throw new RuntimeException("redis decr error");
		}
	}

	/**
	 * String 类型判断是否有key
	 * 
	 * @param key
	 */
	public boolean hasKey(String key) {
		try {
			return redisTemplate.hasKey(key);
		} catch (Exception e) {
			logger.error("redis hasKey error: key={}", key, e);
			throw new RuntimeException("redis hasKey error");
		}
	}

	/**
	 * 批量删除String类型的key
	 * 
	 * @param key
	 */
	@SuppressWarnings("unchecked")
	public void del(String... key) {
		if (key != null && key.length > 0) {
			try {
				if (key.length == 1) {
					redisTemplate.delete(key[0]);
				} else {
					redisTemplate.delete(CollectionUtils.arrayToList(key));
				}
			} catch (Exception e) {
				logger.error("redis del error: key={}", CollectionUtils.arrayToList(key), e);
				throw new RuntimeException("redis del error");
			}
		}
	}

	/**
	 * hash get 某项
	 * 
	 * @param key
	 * @param item
	 * @return
	 */
	public Object hget(String key, String item) {
		return redisTemplate.opsForHash().get(key, item);
	}

	/**
	 * hash set map
	 * 
	 * @param key
	 * @param map
	 */
	public void hmset(String key, Map<String, Object> map) {
		try {
			redisTemplate.opsForHash().putAll(key, map);
		} catch (Exception e) {
			logger.error("redis hmset error: key={}, map={}", key, map, e);
			throw new RuntimeException("redis hmset error");
		}
	}

	/**
	 * hash set map
	 * 
	 * @param key
	 * @param map
	 * @param timeout
	 */
	public void hmset(String key, Map<String, Object> map, long timeout) {
		try {
			redisTemplate.opsForHash().putAll(key, map);
			if (timeout > 0) {
				expire(key, timeout);
			}
		} catch (Exception e) {
			logger.error("redis hmset error: key={}, map={}, timeout={}", key, map, timeout, e);
			throw new RuntimeException("redis hmset error");
		}
	}

	/**
	 * hash set 某项
	 * 
	 * @param key
	 * @param item
	 * @param value
	 */
	public void hset(String key, String item, Object value) {
		try {
			redisTemplate.opsForHash().put(key, item, value);
		} catch (Exception e) {
			logger.error("redis hset error: key={}, item={}, value={}", key, item, value, e);
			throw new RuntimeException("redis hmset error");
		}
	}

	/**
	 * hash set某项并设置hash过期时间
	 * 
	 * @param key
	 * @param item
	 * @param value
	 * @param timeout
	 */
	public void hset(String key, String item, Object value, long timeout) {
		try {
			redisTemplate.opsForHash().put(key, item, value);
			if (timeout > 0) {
				expire(key, timeout);
			}
		} catch (Exception e) {
			logger.error("redis hset error: key={}, item={}, value={}, timeout={}", key, item, value, timeout, e);
			throw new RuntimeException("redis hmset error");
		}
	}

	/**
	 * hash 删除某些项
	 * 
	 * @param key
	 * @param item
	 */
	public void hdel(String key, Object... item) {
		try {
			redisTemplate.opsForHash().delete(key, item);
		} catch (Exception e) {
			logger.error("redis hset error: key={}, item={}", key, CollectionUtils.arrayToList(item), e);
			throw new RuntimeException("redis hmset error");
		}
	}

	/**
	 * hash 判断是否存在某项
	 * 
	 * @param key
	 * @param item
	 */
	public void hHasKey(String key, String item) {
		try {
			redisTemplate.opsForHash().hasKey(key, item);
		} catch (Exception e) {
			logger.error("redis hset error: key={}, item={}", key, item, e);
			throw new RuntimeException("redis hHasKey error");
		}
	}

	/**
	 * hash 递增
	 * 
	 * @param key
	 * @param item
	 * @param by
	 * @return
	 */
	public double hincr(String key, String item, double by) {
		try {
			return redisTemplate.opsForHash().increment(key, item, by);
		} catch (Exception e) {
			logger.error("redis hset error: key={}, item={}, by={}", key, item, by, e);
			throw new RuntimeException("redis hincr error");
		}
	}

	/**
	 * hash 递减
	 * 
	 * @param key
	 * @param item
	 * @param by
	 * @return
	 */
	public double hdecr(String key, String item, double by) {
		try {
			return redisTemplate.opsForHash().increment(key, item, -by);
		} catch (Exception e) {
			logger.error("redis hset error: key={}, item={}, by={}", key, item, by, e);
			throw new RuntimeException("redis hdecr error");
		}
	}

	/**
	 * hash 获取所有项
	 * 
	 * @param key
	 * @return
	 */
	public Map<Object, Object> hmget(String key) {
		return redisTemplate.opsForHash().entries(key);
	}

	/**
	 * set 获取
	 * 
	 * @param key
	 * @return
	 */
	public Set<Object> sGet(String key) {
		try {
			return redisTemplate.opsForSet().members(key);
		} catch (Exception e) {
			logger.error("redis hset error: key={}", key, e);
			throw new RuntimeException("redis sGet error");
		}
	}

	/**
	 * set 判断是否存在
	 * 
	 * @param key
	 * @param value
	 */
	public void sHasKey(String key, Object value) {
		try {
			redisTemplate.opsForSet().isMember(key, value);
		} catch (Exception e) {
			logger.error("redis sHasKey error: key={}, value={}", key, value, e);
			throw new RuntimeException("redis sHasKey error");
		}
	}

	/**
	 * set set
	 * 
	 * @param key
	 * @param values
	 * @return
	 */
	public long sSet(String key, Object... values) {
		try {
			return redisTemplate.opsForSet().add(key, values);
		} catch (Exception e) {
			logger.error("redis sSet error: key={}, value={}", key, CollectionUtils.arrayToList(values), e);
			throw new RuntimeException("redis sSet error");
		}
	}

	/**
	 * set set
	 * 
	 * @param key
	 * @param timeout
	 * @param values
	 * @return
	 */
	public long sSetAndTime(String key, long timeout, Object... values) {
		try {
			Long count = redisTemplate.opsForSet().add(key, values);
			if (timeout > 0) {
				expire(key, timeout);
			}
			return count;
		} catch (Exception e) {
			logger.error("redis sSetAndTime error: key={}, values={}, timeout={}", key, CollectionUtils.arrayToList(values), timeout, e);
			throw new RuntimeException("redis sSetAndTime error");
		}
	}

	/**
	 * set 的大小
	 * 
	 * @param key
	 * @return
	 */
	public long sGetSetSize(String key) {
		try {
			return redisTemplate.opsForSet().size(key);
		} catch (Exception e) {
			logger.error("redis sGetSetSize error: key={}", key, e);
			throw new RuntimeException("redis sGetSetSize error");
		}
	}

	/**
	 * set 删除
	 * 
	 * @param key
	 * @param values
	 * @return
	 */
	public long setRemove(String key, Object... values) {
		try {
			Long count = redisTemplate.opsForSet().remove(key, values);
			return count;
		} catch (Exception e) {
			logger.error("redis setRemove error: key={}, values={}", key, CollectionUtils.arrayToList(values), e);
			throw new RuntimeException("redis setRemove error");
		}
	}

	/**
	 * list get
	 * 
	 * @param key
	 * @param start
	 * @param end
	 * @return
	 */
	public List<Object> lGet(String key, long start, long end) {
		try {
			return redisTemplate.opsForList().range(key, start, end);
		} catch (Exception e) {
			logger.error("redis lGet error: key={}, start={}, end={}", key, start, end, e);
			throw new RuntimeException("redis lGet error");
		}
	}

	/**
	 * list 获取大小
	 * 
	 * @param key
	 * @return
	 */
	public long lGetListSize(String key) {
		try {
			return redisTemplate.opsForList().size(key);
		} catch (Exception e) {
			logger.error("redis lGetListSize error: key={}", key, e);
			throw new RuntimeException("redis lGetListSize error");
		}
	}

	/**
	 * list 获取指定下标
	 * 
	 * @param key
	 * @param index
	 * @return
	 */
	public Object lGetIndex(String key, long index) {
		try {
			return redisTemplate.opsForList().index(key, index);
		} catch (Exception e) {
			logger.error("redis lGetIndex error: key={}, index={}", key, index, e);
			throw new RuntimeException("redis lGetIndex error");
		}
	}

	/**
	 * list 获取对应的下标值
	 * 
	 * @param key
	 * @param value
	 */
	public Long lSet(String key, Object value) {
		try {
			return redisTemplate.opsForList().rightPush(key, value);
		} catch (Exception e) {
			logger.error("redis lSet error: key={}, value={}", key, value, e);
			throw new RuntimeException("redis lSet error");
		}
	}

	/**
	 * list set
	 * 
	 * @param key
	 * @param value
	 * @param timeout
	 */
	public void lSet(String key, Object value, long timeout) {
		try {
			redisTemplate.opsForList().rightPush(key, value);
			if (timeout > 0)
				expire(key, timeout);
		} catch (Exception e) {
			logger.error("redis lSet error: key={}, value={}, timeout={}", key, value, timeout, e);
			throw new RuntimeException("redis lSet error");
		}
	}

	/**
	 * list set
	 * 
	 * @param key
	 * @param value
	 */
	public void lSet(String key, List<Object> value) {
		try {
			redisTemplate.opsForList().rightPushAll(key, value);
		} catch (Exception e) {
			logger.error("redis lSet error: key={}, value={}", key, value, e);
			throw new RuntimeException("redis lSet error");
		}
	}

	/**
	 * list set
	 * 
	 * @param key
	 * @param value
	 * @param timeout
	 */
	public void lSet(String key, List<Object> value, long timeout) {
		try {
			redisTemplate.opsForList().rightPushAll(key, value);
			if (timeout > 0)
				expire(key, timeout);
		} catch (Exception e) {
			logger.error("redis lSet error: key={}, value={}, timeout={}", key, value, timeout, e);
			throw new RuntimeException("redis lSet error");
		}
	}

	/**
	 * list 根据下标更新
	 * 
	 * @param key
	 * @param index
	 * @param value
	 */
	public void lUpdateIndex(String key, long index, Object value) {
		try {
			redisTemplate.opsForList().set(key, index, value);
		} catch (Exception e) {
			logger.error("redis lUpdateIndex error: key={}, value={}, index={}", key, value, index, e);
			throw new RuntimeException("redis lUpdateIndex error");
		}
	}

	/**
	 * list 删除
	 * 
	 * @param key
	 * @param count
	 * @param value
	 * @return
	 */
	public long lRemove(String key, long count, Object value) {
		try {
			Long remove = redisTemplate.opsForList().remove(key, count, value);
			return remove;
		} catch (Exception e) {
			logger.error("redis lRemove error: key={}, value={}, count={}", key, value, count, e);
			throw new RuntimeException("redis lRemove error");
		}
	}
}

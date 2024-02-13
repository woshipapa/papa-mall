package com.papa.portal.util;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.Collection;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Slf4j
@Component
public class RedisOpsExtUtil {

    @Autowired
    @Qualifier("redisCluster")
    private RedisTemplate<String, Object> redisTemplate;

    public void set(String key, Object value) {
        redisTemplate.opsForValue().set(key, value);
    }

    public void set(String key, Object value, long timeout, TimeUnit unit) {
        redisTemplate.opsForValue().set(key, value, timeout, unit);
    }

    public <V> void putListAllRight(String key, Collection<V> values) {
        if (CollectionUtils.isEmpty(values)) {
            log.warn("Collection for key {} is empty or null, nothing to push to Redis", key);
        } else {
            redisTemplate.opsForList().rightPushAll(key, values.toArray());
        }
    }

    public <T> List<T> getListAll(String key, Class<T> clazz) {
        return (List<T>) redisTemplate.opsForList().range(key, 0, -1);
    }

    public <T> T get(String key, Class<T> clazz) {
        return (T) redisTemplate.opsForValue().get(key);
    }

    public String getString(String key) {
        Object result = redisTemplate.opsForValue().get(key);
        return result != null ? result.toString() : null;
    }

    public Long decr(String key, long delta) {
        return redisTemplate.opsForValue().decrement(key, delta);
    }

    public Long incr(String key, long delta) {
        return redisTemplate.opsForValue().increment(key, delta);
    }

    public boolean expire(String key, long timeout, TimeUnit unit) {
        return Boolean.TRUE.equals(redisTemplate.expire(key, timeout, unit));
    }

    public boolean delete(String key) {
        return Boolean.TRUE.equals(redisTemplate.delete(key));
    }

    public boolean hasKey(String key) {
        return Boolean.TRUE.equals(redisTemplate.hasKey(key));
    }

    public void publish(String channel, Object message) {
        redisTemplate.convertAndSend(channel, message);
    }

}

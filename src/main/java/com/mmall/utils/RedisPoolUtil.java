package com.mmall.utils;


import com.mmall.common.RedisPool;
import lombok.extern.slf4j.Slf4j;
import redis.clients.jedis.Jedis;

@Slf4j
public class RedisPoolUtil {

    public static String set(String key, String value) {
        Jedis jedis = null;
        String result = null;

        try {
            jedis = RedisPool.getJedis();
            result = jedis.set(key, value);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("set key:{}  valut:{} error {}", key, value, e);
            RedisPool.returnBrokenResources(jedis);
            return result;
        }
        RedisPool.returnBrokenResources(jedis);
        return result;
    }

    //设置key的有效期，单位是秒
    public static Long expire(String key, int exTime) {
        Jedis jedis = null;
        Long result = null;

        try {
            jedis = RedisPool.getJedis();
            result = jedis.expire(key, exTime);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("expire key:{}  exTime:{} error {}", key, exTime, e);
            RedisPool.returnBrokenResources(jedis);
            return result;
        }
        RedisPool.returnBrokenResources(jedis);
        return result;
    }



    public static String setEx(String key, String value, int exTime) {
        Jedis jedis = null;
        String result = null;

        try {
            jedis = RedisPool.getJedis();
            result = jedis.setex(key, exTime, value);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("setEx key:{}  valut:{} error {}", key, value, e);
            RedisPool.returnBrokenResources(jedis);
            return result;
        }
        RedisPool.returnBrokenResources(jedis);
        return result;
    }

    public static String get(String key) {
        Jedis jedis = null;
        String result = null;

        try {
            jedis = RedisPool.getJedis();
            result = jedis.get(key);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("get key:{}  error {}", key, e);
            RedisPool.returnBrokenResources(jedis);
            return result;
        }
        RedisPool.returnBrokenResources(jedis);
        return result;
    }

    public static Long del(String key) {
        Jedis jedis = null;
        Long result = null;

        try {
            jedis = RedisPool.getJedis();
            result = jedis.del(key);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("del key:{}  error {}", key, e);
            RedisPool.returnBrokenResources(jedis);
            return result;
        }
        RedisPool.returnBrokenResources(jedis);
        return result;
    }
}

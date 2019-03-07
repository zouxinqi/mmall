package com.mmall.common;

import com.mmall.utils.PropertiesUtil;
import com.mmall.utils.RedisPoolUtil;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class RedisPool {
    //jedis链接池
    private static JedisPool pool;
    //最大连接数
    private static Integer maxTotal = Integer.parseInt(PropertiesUtil.getProperty("redis.max.tota","20"));
    //最多空闲实例个数
    private static Integer maxIdle = Integer.parseInt(PropertiesUtil.getProperty("redis.max.idle","10"));
    private static Integer minIdle = Integer.parseInt(PropertiesUtil.getProperty("redis.min.tota","2"));
    //在borrow一个jedis实例时，是否需要验证操作
    private static Boolean testOnBorrow = Boolean.parseBoolean(PropertiesUtil.getProperty("redis.test.borro","true"));
    //在return一个jedis实例时，是否需要验证操作
    private static Boolean testOnRetrun = Boolean.parseBoolean(PropertiesUtil.getProperty("redis.max.tota","true"));

    private static String redisIp = PropertiesUtil.getProperty("redis1.ip");
    private static Integer redisPort = Integer.parseInt(PropertiesUtil.getProperty("redis1.port"));

    private static void initPool(){
        JedisPoolConfig config = new JedisPoolConfig();
        config.setMaxTotal(maxTotal);
        config.setMaxIdle(maxIdle);
        config.setMinIdle(minIdle);

        config.setTestOnBorrow(testOnBorrow);
        config.setTestOnReturn(testOnRetrun);
        //连接耗尽的是否是否阻塞
        config.setBlockWhenExhausted(true);


        pool = new JedisPool(config,redisIp,redisPort,1000+2);
    }

    static {
        initPool();
    }

    public static Jedis getJedis(){
       return pool.getResource();
    }

    public static void returnBrokenResources(Jedis jedis){
        pool.returnBrokenResource(jedis);
    }

    public static void returnResource(Jedis jedis){
        pool.returnResource(jedis);
    }

    public static void main(String[] args) {
        Jedis jedis = pool.getResource();

        RedisPoolUtil.set("test","testvalue");

        String value = RedisPoolUtil.get("test");

        RedisPoolUtil.setEx("exTest","exValue",60*10);

        RedisPoolUtil.del("test");


        System.out.println("args = [" + args + "]");
    }
}

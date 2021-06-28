package com.dengshuo.spikeaction.distributed.redis;

import org.redisson.api.RLock;
import org.redisson.api.RMapCache;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.concurrent.TimeUnit;

/**
 * @Author deng shuo
 * @Date 6/16/21 21:15
 * @Version 1.0
 *
 * Redis 分布式锁工具类
 */
public class RedissonLockUtil {

    //@Autowired
    private static RedissonClient redissonClient;

    /**
     * 构造函数传入
      * @param redissonClient
     */
    public void setRedissonClient(RedissonClient redissonClient) {
        this.redissonClient = redissonClient;
    }

    /**
     * lock
     * @param lockKey
     * @return
     */
    public static RLock lock(String lockKey){
        RLock rlock = redissonClient.getLock(lockKey);
        rlock.lock();
        return rlock;
    }
    public static RLock lock(String lockKey, int timeout) {
        RLock lock = redissonClient.getLock(lockKey);
        lock.lock(timeout, TimeUnit.SECONDS);
        return lock;
    }

    /**
     * unlock
     * @param lockKey
     */
    public static void unlock(String lockKey) {
        RLock lock = redissonClient.getLock(lockKey);
        lock.unlock();
    }
    public static void unlock(RLock lock) {
        lock.unlock();
    }

    /**
     * tryLock
     * @param lockKey
     * @param waitTime
     * @param leaseTime
     * @return
     */
    public static boolean tryLock(String lockKey, int waitTime, int leaseTime) {
        RLock lock = redissonClient.getLock(lockKey);
        try {
            return lock.tryLock(waitTime, leaseTime, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            return false;
        }
    }

    public static boolean tryLock(String lockKey, TimeUnit unit,int waitTime,int lockTime){

        RLock lock = redissonClient.getLock(lockKey);

        try{
            return lock.tryLock(waitTime,lockTime,unit);

        } catch (InterruptedException e) {
            e.printStackTrace();
            return false;
        }

    }

    // 微信抢红包函数
    /**
     * 初始红包数量
     * @param key
     * @param count
     */
    public void initCount(String key,int count) {
        RMapCache<String, Integer> mapCache = redissonClient.getMapCache("spike");
        mapCache.putIfAbsent(key,count,3,TimeUnit.DAYS);
    }
    /**
     * 递增
     * @param key
     * @param delta 要增加几(大于0)
     * @return
     */
    public int incr(String key, int delta) {
        RMapCache<String, Integer> mapCache = redissonClient.getMapCache("spike");
        if (delta < 0) {
            throw new RuntimeException("递增因子必须大于0");
        }
        return  mapCache.addAndGet(key, 1);//加1并获取计算后的值
    }

    /**
     * 递减
     * @param key 键
     * @param delta 要减少几(小于0)
     * @return
     */
    public int decr(String key, int delta) {
        RMapCache<String, Integer> mapCache = redissonClient.getMapCache("spike");
        if (delta < 0) {
            throw new RuntimeException("递减因子必须大于0");
        }
        return mapCache.addAndGet(key, -delta);//加1并获取计算后的值
    }
}

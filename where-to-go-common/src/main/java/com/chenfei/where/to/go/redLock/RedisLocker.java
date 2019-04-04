package com.chenfei.where.to.go.redLock;
/*
 * Created by chenfei on 2019/3/29 15:52
 */

import com.chenfei.where.to.go.exception.UnableToAquireLockException;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

@Component
public class RedisLocker implements DistributedLocker {

    private final static String LOCKER_PREFIX = "lock:";

    @Resource
    private RedissonClient redissonClient;

    @Override
    public RLock getLock(String lockKey) throws UnableToAquireLockException, Exception{
        RLock lock = redissonClient.getLock(lockKey);
        return lock;
    }

    @Override
    public RLock lock(String lockKey, int leaseTime) throws UnableToAquireLockException, Exception{
        RLock lock = redissonClient.getLock(lockKey);
        lock.lock(leaseTime, TimeUnit.SECONDS);
        return lock;
    }

    @Override
    public RLock lock(String lockKey, TimeUnit unit ,int timeout) throws UnableToAquireLockException, Exception{
        RLock lock = redissonClient.getLock(lockKey);
        lock.lock(timeout, unit);
        return lock;
    }

    @Override
    public boolean tryLock(RLock lock,TimeUnit unit, int waitTime, int leaseTime) throws UnableToAquireLockException, Exception{
        try {
            return lock.tryLock(waitTime, leaseTime, unit);
        } catch (InterruptedException e) {
            return false;
        }
    }

    @Override
    public void unlock(String lockKey) throws UnableToAquireLockException, Exception{
        RLock lock = redissonClient.getLock(lockKey);
        lock.unlock();
    }

    @Override
    public void unlock(RLock lock) throws UnableToAquireLockException, Exception{
        lock.unlock();
    }
}

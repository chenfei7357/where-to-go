package com.chenfei.where.to.go.redLock;
/*
 * Created by chenfei on 2019/3/29 15:55
 */

import com.chenfei.where.to.go.exception.UnableToAquireLockException;
import org.redisson.api.RLock;

import java.util.concurrent.TimeUnit;

public interface DistributedLocker {

    RLock getLock(String lockKey)throws UnableToAquireLockException, Exception;

    RLock lock(String lockKey, int timeout)throws UnableToAquireLockException, Exception;

    RLock lock(String lockKey, TimeUnit unit, int timeout)throws UnableToAquireLockException, Exception;

    boolean tryLock(RLock lock, TimeUnit unit, int waitTime, int leaseTime)throws UnableToAquireLockException, Exception;

    void unlock(String lockKey)throws UnableToAquireLockException, Exception;

    void unlock(RLock lock)throws UnableToAquireLockException, Exception;

}

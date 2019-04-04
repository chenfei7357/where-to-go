package com.chenfei.where.to.go.aop;
/*
 * Created by chenfei on 2019/4/4 14:29
 */

import com.chenfei.where.to.go.annotation.RedLock;
import com.chenfei.where.to.go.exception.UnableToAquireLockException;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.lang.reflect.Method;
import java.util.concurrent.TimeUnit;

@Aspect
@Component
@Slf4j
public class redLockAop {

    private ThreadLocal<RLock> currentThreadLock = new ThreadLocal<>();

    private ThreadLocal<Boolean> currentThreadLockRes = new ThreadLocal<>();

    @Resource
    private RedissonClient redissonClient;

    /**
     * 切入点
     */
    @Pointcut("@annotation(com.chenfei.where.to.go.annotation.RedLock)")
    public  void pointCut(){

    }

    @Around(value = "pointCut()")
    public Object around(ProceedingJoinPoint point) throws Throwable {
        MethodSignature sign = (MethodSignature) point.getSignature();
        Method method = sign.getMethod();
        RedLock redLock = method.getAnnotation(RedLock.class);
        long leaseTime = redLock.leaseTime();
        long waitTime = redLock.waitTime();
        String lockName = redLock.lockName();
        RLock lock = redissonClient.getLock(lockName);
        try {
            boolean flag =lock.tryLock(waitTime, leaseTime, TimeUnit.SECONDS);
            currentThreadLock.set(lock);
            currentThreadLockRes.set(flag);
            if(!flag){
                throw new UnableToAquireLockException();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }catch (UnableToAquireLockException ue){
            throw ue;
        }
        return point.proceed();
    }


    @AfterReturning(value = "pointCut()")
    public void afterReturning() {
        if (currentThreadLockRes.get()
          && currentThreadLock.get().isHeldByCurrentThread()) {
            log.info("afterReturning释放锁");
            currentThreadLock.get().unlock();
        }
    }

    @AfterThrowing(value = "pointCut()")
    public void afterThrowing () {
        if(currentThreadLock.get().isHeldByCurrentThread()){
            log.info("afterThrowing释放锁");
            currentThreadLock.get().unlock();
        }
    }

}

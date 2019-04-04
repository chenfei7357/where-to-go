package com.chenfei.where.to.go.annotation;
/*
 * Created by chenfei on 2019/4/4 14:36
 */


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(value = {ElementType.METHOD})
@Retention(value = RetentionPolicy.RUNTIME)
public @interface RedLock {

    /*
     * 锁的名称
     * @return
     */
    String lockName();
    /**
     * 尝试加锁，最多等待时间(单位秒)
     * @return
     */
    long waitTime();
    /**
     *上锁以后xxx秒自动解锁（单位秒）
     * @return
     */
    long leaseTime();
}

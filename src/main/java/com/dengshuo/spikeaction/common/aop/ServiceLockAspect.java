package com.dengshuo.spikeaction.common.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.context.annotation.Scope;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @Author deng shuo
 * @Date 6/14/21 21:28
 * @Version 1.0
 *
 * 自定义锁的Aspect
 */
@Component
@Scope
@Aspect
@Order(1)
public class ServiceLockAspect {

    private static Lock lock = new ReentrantLock(true);

    // declared all the  JoinPoint
    @Pointcut("@annotation(com.dengshuo.spikeaction.common.aop.ServiceLock)")
    public void ServiceLock(){

    }

    // Advice
    @Around("ServiceLock()")
    public Object around(ProceedingJoinPoint proceedingJoinPoint){
        lock.lock();
        Object obj = null;
        try{
            obj = proceedingJoinPoint.proceed();
        } catch (Throwable throwable) {
            throwable.printStackTrace();
            throw new RuntimeException();
        }finally {
            lock.unlock();
        }
        return obj;

    }


}

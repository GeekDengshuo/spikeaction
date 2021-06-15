package com.dengshuo.spikeaction.common.aop;

import java.lang.annotation.*;

/**
 * @Author deng shuo
 * @Date 6/14/21 21:20
 * @Version 1.0
 */
@Target({ElementType.PARAMETER,ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ServiceLock {
    String description() default "service lock";
}

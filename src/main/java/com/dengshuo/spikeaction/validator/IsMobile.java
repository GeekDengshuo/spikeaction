package com.dengshuo.spikeaction.validator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

import static java.lang.annotation.ElementType.*;

/**
 * 自定义注解
 * 实现手机号码验证
 *
 * @Author deng shuo
 * @Date 5/23/21 14:43
 * @Version 1.0
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER, TYPE_USE})
@Constraint(validatedBy = { IsMobileValidator.class })
public @interface IsMobile {

    boolean required() default true;

    String message() default "{validator.手机号码有误}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}

package com.dengshuo.spikeaction.validator;

import com.dengshuo.spikeaction.utils.ValidationUtils;
import org.apache.commons.lang3.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * @Author deng shuo
 * @Date 5/23/21 14:54
 * @Version 1.0
 */
public class IsMobileValidator implements ConstraintValidator<IsMobile,String> {

    private boolean required = false;

    @Override
    public void initialize(IsMobile constraintAnnotation) {
        required = constraintAnnotation.required();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {

        if(required){
            return ValidationUtils.isMobile(value);
        }else{
            if(StringUtils.isEmpty(value)){
                return true;
            }else{
                return ValidationUtils.isMobile(value);
            }
        }
    }
}

package com.dengshuo.spikeaction.utils;

import org.apache.commons.lang3.StringUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 常用的校验工具
 * @Author deng shuo
 * @Date 5/23/21 14:05
 * @Version 1.0
 */
public class ValidationUtils {


    private static final String MOBILE_REGX =
            "^((13[0-9])|(14[5-9])|(15([0-3]|[5-9]))|(16[6-7])|(17[1-8])|(18[0-9])|(19[1|3])|(19[5|6])|(19[8|9]))\\d{8}$";
    private static final Pattern  mobile_pattern = Pattern.compile(MOBILE_REGX);


    public static boolean isMobile(String mobile){
        if(StringUtils.isEmpty(mobile)){
            return false;
        }
        Matcher matcher = mobile_pattern.matcher(mobile);
        return matcher.matches();
    }
}

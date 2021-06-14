package com.dengshuo.spikeaction.utils;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.text.RandomStringGenerator;
import org.springframework.stereotype.Component;


/**
 * @Author deng shuo
 * @Date 5/22/21 23:04
 * @Version 1.0
 * MD5 加密的工具类
 * 用于用户登陆密码加密
 */
@Component
public class MD5Utils {

    public static String md5(String src){
        return DigestUtils.md5Hex(src);
    }


    public static final int SALT_LENGTH = 20;
    public static RandomStringGenerator RandomStringGenerator =
            new RandomStringGenerator.Builder().withinRange('0','z').build();
    public static final String randomSalt = RandomStringGenerator.generate(SALT_LENGTH);


    // salt
    public static final String salt = "1a2b3c4d";


    /**
     * 调用接口,根据输入密码和数据中的salt,生成加密密码
     * @param inputPass
     * @param saltDB
     * @return
     */
    public static String inputPass2DBPass(String inputPass,String saltDB){
        String formPass = inputPass2outputPass(inputPass);
        String DBPass = formPass2DBPass(formPass,saltDB);
        return DBPass;
    }

    /**
     * first encoder : front to backend
     * 这个里面的salt的与前端通用的
     * @param inputPass
     * @return md5 encoder
     */
    public static String inputPass2outputPass(String inputPass){
        String slatInputPass = ""+salt.charAt(0)+salt.charAt(2)+
                                inputPass+salt.charAt(5)+ salt.charAt(4);

        return md5(slatInputPass);
    }

    /**
     * 二次加密，这里面的salt是随机生成的
     * 这里面的salt存储在DB数据库中
     * @param formPass
     * @param salt
     * @return
     */
    public static String formPass2DBPass(String formPass,String salt){
        String str = "" + salt.charAt(0)+salt.charAt(2)+
                formPass+salt.charAt(5)+ salt.charAt(4);
        return md5(str);
    }



}

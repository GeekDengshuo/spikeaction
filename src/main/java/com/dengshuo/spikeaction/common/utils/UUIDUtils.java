package com.dengshuo.spikeaction.common.utils;

import java.util.UUID;

/**
 * @Author deng shuo
 * @Date 5/23/21 16:46
 * @Version 1.0
 */
public class UUIDUtils {

    public static String uuid(){
        return UUID.randomUUID().toString().replace("-","");
    }
}

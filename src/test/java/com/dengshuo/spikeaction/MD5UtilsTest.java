package com.dengshuo.spikeaction;

import com.dengshuo.spikeaction.common.utils.MD5Utils;
import org.junit.jupiter.api.Test;

/**
 * @Author deng shuo
 * @Date 5/23/21 08:30
 * @Version 1.0
 */

public class MD5UtilsTest {


    @Test
    public void Md5UtilsEncodeTest(){
        System.out.println("Md5UtilsEncodeTest run --------------->");
        String testPassword = "123456";
        String salt = "1a2b3c4d";

        String inputPass2outputPass = MD5Utils.inputPass2outputPass(testPassword);

        System.out.println(inputPass2outputPass);
        // d3b1294a61a07da9b49b6e22b2cbd7f9

        String formPass2DBPass = MD5Utils.formPass2DBPass(inputPass2outputPass,salt);
        System.out.println(formPass2DBPass);
        // b7797cce01b4b131b433b6acf4add449

        String inputPass2DBPass = MD5Utils.inputPass2DBPass(testPassword,salt);
        System.out.println(inputPass2DBPass);

        System.out.println(formPass2DBPass.equals(inputPass2DBPass));

        System.out.println("Md5UtilsEncodeTest finish <---------------");


    }


}

package com.dengshuo.spikeaction.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @Author deng shuo
 * @Date 5/22/21 21:48
 * @Version 1.0
 *
 * 测试界面 - 测试界面跳转
 */
@Controller
@RequestMapping("/spike")
public class HelloController {

    @RequestMapping("/hello")
    public String helloTest(Model model){
        model.addAttribute("name","spikeAction project");
        return "hello";
    }


}

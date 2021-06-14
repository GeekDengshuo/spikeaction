package com.dengshuo.spikeaction.controller;

import com.dengshuo.spikeaction.service.IUserService;
import com.dengshuo.spikeaction.vo.LoginVo;
import com.dengshuo.spikeaction.vo.ResponseBean;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

/**
 * @Author deng shuo
 * @Date 5/23/21 10:16
 * @Version 1.0
 * 实现登录跳转
 */
@Controller
@RequestMapping("/login")
@Slf4j
public class LoginController {

    @Autowired
    IUserService iUserService;

    /**
     * 跳转登录界面
     * @return 前端登录界面名称
     */
    @RequestMapping("/toLogin")
    public String toLoginPage(){
        return "login";
    }


    @RequestMapping("/toRegister")
    public String toRegisterPage(){ return "register";}

    /**
     * 执行登录
     * @return
     */
    @RequestMapping("/doLogin")
    @ResponseBody
    public ResponseBean doLogin(@Valid LoginVo loginVo,
                                HttpServletRequest request, HttpServletResponse response){
        log.info("{}", loginVo);
        return iUserService.doLogin(loginVo,request,response);
    }

    /**
     * 用户注册
     * @param loginVo
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/doRegister")
    @ResponseBody
    public ResponseBean doRegister(@Valid LoginVo loginVo,
                                   HttpServletRequest request,HttpServletResponse response){
        log.info("{}",loginVo);
        return iUserService.doRegister(loginVo,request,response);
    }
}

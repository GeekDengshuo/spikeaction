package com.dengshuo.spikeaction.controller;


import com.dengshuo.spikeaction.pojo.User;
import com.dengshuo.spikeaction.vo.ResponseBean;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author dengshuo
 * @since 2021-05-23
 */
@RestController
@RequestMapping("/user")
public class UserController {

    /**
     * 用来做jMeter测试
     * 单一用户
     * @param user
     * @return
     */
    @RequestMapping("/info")
    public ResponseBean success(User user){
        return ResponseBean.success();
    }

}

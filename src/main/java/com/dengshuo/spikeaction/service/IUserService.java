package com.dengshuo.spikeaction.service;

import com.dengshuo.spikeaction.pojo.User;
import com.baomidou.mybatisplus.extension.service.IService;
import com.dengshuo.spikeaction.vo.LoginVo;
import com.dengshuo.spikeaction.vo.ResponseBean;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author dengshuo
 * @since 2021-05-23
 */
public interface IUserService extends IService<User> {


    /**
     *
     * 功能: 实现用户登录
     *
     * @param loginVo
     * @return
     */
    ResponseBean doLogin(LoginVo loginVo, HttpServletRequest request, HttpServletResponse response);

    /**
     * 功能: 实现用户注册
     * @param loginVo
     * @param request
     * @param response
     * @return
     */
    ResponseBean doRegister(LoginVo loginVo, HttpServletRequest request, HttpServletResponse response);

    /**
     * 用户是否登录校验
     * 功能: 根据cookie获取用户
     *
     * @param userTicket
     * @return
     */
    User getUserByCookie(String userTicket,HttpServletRequest request,HttpServletResponse response);

    /**
     * 修改用户密码
     * @param userTicket
     * @param password
     * @param request
     * @param response
     * @return
     * 通过用户ticket更新密码
     */
    ResponseBean updatePassword(String userTicket, String password, HttpServletRequest request,
                            HttpServletResponse response);


}

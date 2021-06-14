package com.dengshuo.spikeaction.service.impl;

import com.dengshuo.spikeaction.exception.GlobalException;
import com.dengshuo.spikeaction.pojo.User;
import com.dengshuo.spikeaction.mapper.UserMapper;
import com.dengshuo.spikeaction.service.IUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dengshuo.spikeaction.utils.CookieUtils;
import com.dengshuo.spikeaction.utils.MD5Utils;
import com.dengshuo.spikeaction.utils.UUIDUtils;
import com.dengshuo.spikeaction.utils.ValidationUtils;
import com.dengshuo.spikeaction.vo.LoginVo;
import com.dengshuo.spikeaction.vo.ResponseBean;
import com.dengshuo.spikeaction.vo.ResponseBeanEnum;
import com.sun.org.apache.regexp.internal.RE;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author dengshuo
 * @since 2021-05-23
 */
@Service
@Slf4j
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public ResponseBean doLogin(LoginVo loginVo, HttpServletRequest request, HttpServletResponse response) {

        String mobile = loginVo.getMobile();
        String password = loginVo.getPassword();


        //IsMobile注解实现校验

//        // 前端validation ,后端也需要校验参数
//        if(StringUtils.isEmpty(mobile) || StringUtils.isEmpty(password))
//            return ResponseBean.error(ResponseBeanEnum.LOGIN_ERROR);
//
//        // 参数校验
//        if(!ValidationUtils.isMobile(mobile)){
//            return ResponseBean.error(ResponseBeanEnum.MOBILE_ERROR);
//        }

        User user = userMapper.selectById(mobile);
        if(null == user){
            //return ResponseBean.error(ResponseBeanEnum.LOGIN_ERROR);
            throw new GlobalException(ResponseBeanEnum.LOGIN_ERROR);
        }

        // 判断密码是否正确
        // TODO
        if(!MD5Utils.formPass2DBPass(password,user.getSlat()).
                                equals(user.getPassword())){
            //return ResponseBean.error(ResponseBeanEnum.LOGIN_ERROR);
            throw new GlobalException(ResponseBeanEnum.LOGIN_ERROR);
        }
        // cookie
        String ticket = UUIDUtils.uuid();

        // Spring Session中存储
        // request.getSession().setAttribute(ticket,user);

        // Redis存储代替Session
        // 用户信息存储redis中
        redisTemplate.opsForValue().set("user:"+ticket,user);

        CookieUtils.setCookie(request,response,"userTicket",ticket);

        return ResponseBean.success();
    }

    @Override
    public ResponseBean doRegister(LoginVo loginVo, HttpServletRequest request, HttpServletResponse response) {
        String mobile = loginVo.getMobile();
        String password = loginVo.getPassword();
        //String nickname = loginVo.getNickname();

        // 判断用户是否存在
        User user = userMapper.selectById(mobile);

        if(null != user){
            throw new GlobalException(ResponseBeanEnum.ALREADY_REGISTER_ERROR);
        }
        // 注册新用户
        /**

         *private Long id;
         *private String nickname;
         *private String password;
         *private String slat;
         *private String head;
         *private Date registerDate;
         *private Date lastLoginDate;
         *private Integer loginCount;
         */

        User registerUser = new User();
        String salt = "1a2b3c4d";
        registerUser.setId(Long.parseLong(mobile));
        registerUser.setPassword(MD5Utils.formPass2DBPass(password,salt));
        registerUser.setNickname(null);
        registerUser.setRegisterDate(new Date());
        registerUser.setSlat(salt);
        registerUser.setHead(null);
        registerUser.setLoginCount(0);
        registerUser.setLastLoginDate(null);

        log.info("{}",registerUser);
        log.info("{mapper insert new user}");

        userMapper.insert(registerUser);


        return ResponseBean.success();
    }

    @Override
    public User getUserByCookie(String userTicket,HttpServletRequest request,
                                HttpServletResponse response) {

        if(StringUtils.isEmpty(userTicket)){
            return null;
        }
        // 用户一致性 + 对象缓存
        User user = (User)redisTemplate.opsForValue().get("user:" + userTicket);
        if(null != user){
            CookieUtils.setCookie(request,response,"userTicket",userTicket);
        }
        return user;
    }

    @Override
    public ResponseBean updatePassword(String userTicket, String password,
                                       HttpServletRequest request,
                                       HttpServletResponse response) {
        User user = getUserByCookie(userTicket,request,response);
        if(null == user){
            throw new GlobalException(ResponseBeanEnum.MOBILE_NOT_REGISTER_ERROR);
        }

        // update
        user.setPassword(MD5Utils.inputPass2DBPass(password,user.getSlat()));

        int result = userMapper.updateById(user);
        if(1 == result){
            // cache aside 模式
            // 数据库更新成功,删除对应缓存
            redisTemplate.delete("user:"+userTicket);
            return ResponseBean.success();
        }
        return ResponseBean.error(ResponseBeanEnum.PASSWORD_UPDATE_ERROR);
    }
}

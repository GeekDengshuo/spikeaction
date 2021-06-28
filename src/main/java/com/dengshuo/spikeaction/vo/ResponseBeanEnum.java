package com.dengshuo.spikeaction.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @Author deng shuo
 * @Date 5/23/21 10:34
 * @Version 1.0
 *
 * 公共的返回对象枚举
 */
@Getter
@ToString
@AllArgsConstructor
public enum  ResponseBeanEnum {

    // common
    SUCCESS(200,"success"),
    ERROR(500,"服务器异常,请联系管理员"),

    // login 4000xx
    LOGIN_ERROR(400001,"用户或密码不正确"),
    MOBILE_ERROR(400002,"手机号码有误,请输入正确的手机号"),
    BIND_ERROR(400003,"参数校验异常"),
    MOBILE_NOT_REGISTER_ERROR(400004,"手机号码未注册"),
    PASSWORD_UPDATE_ERROR(400005,"用户密码更新失败"),
    SESSION_ERROR(400006,"用户不存在"),

    // register 4100xx
    ALREADY_REGISTER_ERROR(410001,"该手机用户已经注册,请直接登录"),

    // spike 500xx
    EMPTY_STOCK(500010,"无剩余商品,库存不足"),
    LIMIT_ERROR(500011,"秒杀商品,限购一件"),
    REDIS_LOCK_ERROR(500012,"Redission加锁失败"),

    // order
    ORDER_NOT_EXIST(6000010,"订单信息不存在"),



    ;

    private final Integer code;
    private final String msg;
}

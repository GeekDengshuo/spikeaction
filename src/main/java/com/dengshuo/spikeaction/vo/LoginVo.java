package com.dengshuo.spikeaction.vo;

import com.dengshuo.spikeaction.validator.IsMobile;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;

/**
 * 登录参数
 *
 * @Author deng shuo
 * @Date 5/23/21 13:46
 * @Version 1.0
 */
@Data
public class LoginVo {

    @NotNull
    @IsMobile
    private String mobile;

    @NotNull
    @Length(min = 32)
    private String password;

}

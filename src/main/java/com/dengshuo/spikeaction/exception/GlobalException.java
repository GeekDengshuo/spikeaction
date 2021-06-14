package com.dengshuo.spikeaction.exception;

import com.dengshuo.spikeaction.vo.ResponseBeanEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 全局异常类
 *
 * @Author deng shuo
 * @Date 5/23/21 15:17
 * @Version 1.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class GlobalException extends RuntimeException{

    private ResponseBeanEnum responseBeanEnum;
}

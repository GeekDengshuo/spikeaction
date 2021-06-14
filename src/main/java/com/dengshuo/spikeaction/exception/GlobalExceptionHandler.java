package com.dengshuo.spikeaction.exception;

import com.dengshuo.spikeaction.vo.ResponseBean;
import com.dengshuo.spikeaction.vo.ResponseBeanEnum;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;



/**
 * 异常处理类
 * @Author deng shuo
 * @Date 5/23/21 15:18
 * @Version 1.0
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseBean ExceptionHandler(Exception e){
        if(e instanceof GlobalException){
           GlobalException ge= (GlobalException)e;
           return ResponseBean.error(ge.getResponseBeanEnum());
        }else if( e instanceof BindException){
            BindException be = (BindException)e;
            ResponseBean responseBean =  ResponseBean.error(ResponseBeanEnum.BIND_ERROR);

            responseBean.setMsg("参数校验异常"+
                    be.getBindingResult().getAllErrors().get(0).getDefaultMessage());
        }
        return ResponseBean.error(ResponseBeanEnum.ERROR);
    }
}

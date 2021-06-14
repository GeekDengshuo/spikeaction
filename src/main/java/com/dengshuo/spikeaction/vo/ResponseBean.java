package com.dengshuo.spikeaction.vo;

import lombok.*;

/**
 * @Author deng shuo
 * @Date 5/23/21 10:33
 * @Version 1.0
 *
 * 公共返回对象
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResponseBean {

    private Integer code;
    private String msg;
    private Object obj;

    /**
     * 成功的返回
     * @return
     */
    public static ResponseBean success(){
        return new ResponseBean(ResponseBeanEnum.SUCCESS.getCode(),
                                ResponseBeanEnum.SUCCESS.getMsg(),
                            null);
    }
    public static ResponseBean success(Object obj){
        return new ResponseBean(ResponseBeanEnum.SUCCESS.getCode(),
                ResponseBeanEnum.SUCCESS.getMsg(),
                obj);
    }

    /**
     * 失败的返回
     * @param responseBeanEnum 失败的类型及错误码
     * @return
     */
    public static ResponseBean error(ResponseBeanEnum responseBeanEnum){
        return new ResponseBean(responseBeanEnum.getCode(),
                responseBeanEnum.getMsg(),
                null);
    }
    public static ResponseBean error(ResponseBeanEnum responseBeanEnum,Object obj){
        return new ResponseBean(responseBeanEnum.getCode(),
                responseBeanEnum.getMsg(),
                obj);
    }
}

package com.dengshuo.spikeaction.controller;


import com.dengshuo.spikeaction.pojo.User;
import com.dengshuo.spikeaction.service.IOrderService;
import com.dengshuo.spikeaction.vo.OrderDetailVo;
import com.dengshuo.spikeaction.vo.ResponseBean;
import com.dengshuo.spikeaction.vo.ResponseBeanEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 订单表 前端控制器
 * </p>
 *
 * @author dengshuo
 * @since 2021-05-29
 */
@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private IOrderService iOrderService;

    /**
     * 功能描述: 订单详情
     * @param user
     * @param orderId
     * @return
     */
    @RequestMapping("/detail")
    @ResponseBody
    public ResponseBean orderDetail(User user,Long orderId){
        if(null == user){
            return ResponseBean.error(ResponseBeanEnum.SESSION_ERROR);
        }

        OrderDetailVo detailVo = iOrderService.detail(orderId);

        return ResponseBean.success(detailVo);
    }
}

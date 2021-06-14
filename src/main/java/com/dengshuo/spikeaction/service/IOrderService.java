package com.dengshuo.spikeaction.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.dengshuo.spikeaction.pojo.Order;
import com.dengshuo.spikeaction.pojo.User;
import com.dengshuo.spikeaction.vo.GoodsVo;
import com.dengshuo.spikeaction.vo.OrderDetailVo;

/**
 * <p>
 * 订单表 服务类
 * </p>
 *
 * @author dengshuo
 * @since 2021-05-29
 */
public interface IOrderService extends IService<Order> {

    /**
     * 功能描述:秒杀
     *
     * @param user
     * @param goods
     * @return
     */
    Order spike(User user, GoodsVo goods);

    /**
     * 功能描述:订单详情
     * @param orderId
     * @return
     */
    OrderDetailVo detail(Long orderId);

}

package com.dengshuo.spikeaction.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.dengshuo.spikeaction.pojo.OrderSpike;
import com.dengshuo.spikeaction.pojo.User;
import com.dengshuo.spikeaction.vo.GoodsVo;
import com.dengshuo.spikeaction.vo.ResponseBean;

/**
 * <p>
 * 秒杀订单表 服务类
 * </p>
 *
 * @author dengshuo
 * @since 2021-05-29
 */
public interface IOrderSpikeService extends IService<OrderSpike> {

    ResponseBean spikeRedissonLock(User user, GoodsVo spikeGoods);

}

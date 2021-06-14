package com.dengshuo.spikeaction.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dengshuo.spikeaction.mapper.OrderSpikeMapper;
import com.dengshuo.spikeaction.pojo.OrderSpike;
import com.dengshuo.spikeaction.service.IOrderSpikeService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 秒杀订单表 服务实现类
 * </p>
 *
 * @author dengshuo
 * @since 2021-05-29
 */
@Service
public class OrderSpikeServiceImpl extends ServiceImpl<OrderSpikeMapper, OrderSpike> implements IOrderSpikeService {

}

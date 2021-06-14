package com.dengshuo.spikeaction.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dengshuo.spikeaction.exception.GlobalException;
import com.dengshuo.spikeaction.mapper.OrderMapper;
import com.dengshuo.spikeaction.pojo.GoodsSpike;
import com.dengshuo.spikeaction.pojo.Order;
import com.dengshuo.spikeaction.pojo.OrderSpike;
import com.dengshuo.spikeaction.pojo.User;
import com.dengshuo.spikeaction.service.IGoodsService;
import com.dengshuo.spikeaction.service.IGoodsSpikeService;
import com.dengshuo.spikeaction.service.IOrderService;
import com.dengshuo.spikeaction.service.IOrderSpikeService;
import com.dengshuo.spikeaction.vo.GoodsVo;
import com.dengshuo.spikeaction.vo.OrderDetailVo;
import com.dengshuo.spikeaction.vo.ResponseBeanEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * <p>
 * 订单表 服务实现类
 * </p>
 *
 * @author dengshuo
 * @since 2021-05-29
 */
@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements IOrderService {

    @Autowired
    private IGoodsSpikeService iGoodsSpikeService;

    @Autowired
    private IOrderSpikeService iOrderSpikeService;

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private IGoodsService iGoodsService;

    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 秒杀实现
     *
     * 将秒杀商品减库存操作
     * @param user
     * @param goods
     * @return
     */
    @Transactional
    @Override
    public Order spike(User user, GoodsVo goods) {

        /* 获取秒杀商品 */
        // TODO 是否线程安全,出现超卖
        GoodsSpike goodsSpike = iGoodsSpikeService.getOne(new QueryWrapper<GoodsSpike>()
                .eq("goods_id", goods.getId()));

        //goodsSpike.setGoodsSpikeStock(goodsSpike.getGoodsSpikeStock()-1);

        boolean SpikeUpdateResult = iGoodsSpikeService.update(new UpdateWrapper<GoodsSpike>().
                setSql("goodsSpikeStock = goodsSpikeStock -1").
                eq("id",goods.getId()).
                gt("goodsSpikeStock",0));

        if(!SpikeUpdateResult) return  null;

        //iGoodsSpikeService.updateById(goodsSpike);

        /* 生成订单 */
        // TODO 不够优雅,代码可抽象
        Order order = new Order();
        order.setUserId(user.getId());
        order.setGoodsId(goods.getId());
        order.setDeliveryAddressId(0L);
        order.setGoodsName(goods.getGoodsName());
        order.setGoodsCount(1);
        order.setGoodsPrice(goodsSpike.getSpikePrice());
        order.setOrderChannel(1);
        order.setStatus(0);
        order.setCreateTime(new Date());
        //order.setPayDate();

        orderMapper.insert(order);

        /* 生成秒杀订单 */
        OrderSpike orderSpike = new OrderSpike();
        orderSpike.setGoodsId(goods.getId());
        orderSpike.setOrderId(order.getId());
        orderSpike.setUserId(user.getId());

        iOrderSpikeService.save(orderSpike);

        /*订单信息放入缓存中*/
        redisTemplate.opsForValue().set("order:"+order.getUserId()+":"+order.getGoodsId(),orderSpike);

        /* 返回订单 */
        return order;
    }

    @Override
    public OrderDetailVo detail(Long orderId) {
        if(null == orderId){
            throw new GlobalException(ResponseBeanEnum.ORDER_NOT_EXIST);
        }
        Order order = orderMapper.selectById(orderId);
        GoodsVo goodsVo = iGoodsService.findGoodsVoById(order.getGoodsId());

        OrderDetailVo orderDetailVo = new OrderDetailVo();
        orderDetailVo.setOrder(order);
        orderDetailVo.setGoodsVo(goodsVo);

        return orderDetailVo;
    }
}

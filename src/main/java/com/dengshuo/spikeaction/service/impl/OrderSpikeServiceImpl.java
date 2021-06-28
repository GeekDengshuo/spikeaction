package com.dengshuo.spikeaction.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dengshuo.spikeaction.distributed.redis.RedissonLockUtil;
import com.dengshuo.spikeaction.mapper.OrderMapper;
import com.dengshuo.spikeaction.mapper.OrderSpikeMapper;
import com.dengshuo.spikeaction.pojo.GoodsSpike;
import com.dengshuo.spikeaction.pojo.Order;
import com.dengshuo.spikeaction.pojo.OrderSpike;
import com.dengshuo.spikeaction.pojo.User;
import com.dengshuo.spikeaction.service.IGoodsSpikeService;
import com.dengshuo.spikeaction.service.IOrderSpikeService;
import com.dengshuo.spikeaction.vo.GoodsVo;
import com.dengshuo.spikeaction.vo.ResponseBean;
import com.dengshuo.spikeaction.vo.ResponseBeanEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.concurrent.TimeUnit;

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


    @Autowired
    private IGoodsSpikeService iGoodsSpikeService;

    @Autowired
    private IOrderSpikeService iOrderSpikeService;

    @Autowired
    private OrderMapper orderMapper;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public ResponseBean spikeRedissonLock(User user, GoodsVo spikeGoods) {

        boolean res = false;
        try{
            res = RedissonLockUtil.tryLock(spikeGoods.getId()+"", TimeUnit.SECONDS,3,10);

            if(res){
                /* 获取秒杀商品 */
                GoodsSpike goodsSpike = iGoodsSpikeService.getOne(new QueryWrapper<GoodsSpike>()
                        .eq("goods_id", spikeGoods.getId()));

                /* 更新数据库商品数量 */
                boolean SpikeUpdateResult = iGoodsSpikeService.update(new UpdateWrapper<GoodsSpike>().
                        setSql("goodsSpikeStock = goodsSpikeStock -1").
                        eq("id",spikeGoods.getId()));

                /* 生成订单 */
                Order order = new Order();
                order.setUserId(user.getId());
                order.setGoodsId(spikeGoods.getId());
                order.setDeliveryAddressId(0L);
                order.setGoodsName(spikeGoods.getGoodsName());
                order.setGoodsCount(1);
                order.setGoodsPrice(goodsSpike.getSpikePrice());
                order.setOrderChannel(1);
                order.setStatus(0);
                order.setCreateTime(new Date());
                //order.setPayDate();

                orderMapper.insert(order);

                /* 生成秒杀订单 */
                OrderSpike orderSpike = new OrderSpike();
                orderSpike.setGoodsId(spikeGoods.getId());
                orderSpike.setOrderId(order.getId());
                orderSpike.setUserId(user.getId());

                iOrderSpikeService.save(orderSpike);
            }else{
                return ResponseBean.error(ResponseBeanEnum.REDIS_LOCK_ERROR);
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if(res){
                RedissonLockUtil.unlock(spikeGoods.getId()+"");
            }
        }

        return ResponseBean.success();
    }
}

package com.dengshuo.spikeaction.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.dengshuo.spikeaction.pojo.Order;
import com.dengshuo.spikeaction.pojo.OrderSpike;
import com.dengshuo.spikeaction.pojo.User;
import com.dengshuo.spikeaction.service.IGoodsService;
import com.dengshuo.spikeaction.service.IOrderService;
import com.dengshuo.spikeaction.service.IOrderSpikeService;
import com.dengshuo.spikeaction.vo.GoodsVo;
import com.dengshuo.spikeaction.vo.ResponseBean;
import com.dengshuo.spikeaction.vo.ResponseBeanEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 秒杀功能实现
 *
 * @Author deng shuo
 * @Date 5/30/21 11:02
 * @Version 1.0
 */
@Controller
@RequestMapping("/spike")
@Slf4j
public class SpikeController {

    @Autowired
    private IGoodsService iGoodsService;

    @Autowired
    private IOrderSpikeService iOrderSpikeService;

    @Autowired
    private IOrderService iOrderService;

    @Autowired
    private RedisTemplate redisTemplate;


    @RequestMapping(value = "/doSpike",method = RequestMethod.POST)
    @ResponseBody
    public ResponseBean doSpike(Model model, User user,Long goodsId){

        log.info("{进入请求 spike/doSpike}",goodsId);
        if(null == user){
            return ResponseBean.error(ResponseBeanEnum.SESSION_ERROR);
        }

        GoodsVo goods = iGoodsService.findGoodsVoById(goodsId);

        /* 判断存库 - 从DB获取商品的库存,不可从前端界面读取(容易被篡改)*/
        if(goods.getGoodsSpikeStock() < 1){
            /* 秒杀失败 - 库存为0 */

            return ResponseBean.error(ResponseBeanEnum.EMPTY_STOCK);
        }

        /* Mysql查询判断是否重复抢购 */
//        OrderSpike orderSpike = iOrderSpikeService.getOne(new QueryWrapper<OrderSpike>()
//                .eq("user_id", user.getId())
//                .eq("goods_id", goodsId));
        /* Redis 存储*/
        OrderSpike orderSpike = (OrderSpike)redisTemplate.opsForValue().get("order:" + user.getId() + ":" + goodsId);

        log.info("orderSpike= {}",orderSpike);

        if(null != orderSpike){

            return ResponseBean.error(ResponseBeanEnum.LIMIT_ERROR);
        }

        /* 进入抢购流程 */
        Order order = iOrderService.spike(user,goods);

        //model.addAttribute("order",order);
        // model.addAttribute("goods",goods);


        return ResponseBean.success(order) ;
    }

    @RequestMapping("/doSpike2")
    @ResponseBody
    public String doSpike2(Model model, User user,Long goodsId){
        if(null == user){
            return "login/toLogin";
        }
        model.addAttribute("user",user);

        GoodsVo goods = iGoodsService.findGoodsVoById(goodsId);

        /* 判断存库 - 从DB获取商品的库存,不可从前端界面读取(容易被篡改)*/
        if(goods.getGoodsSpikeStock() < 1){
            /* 秒杀失败 - 库存为0 */
            model.addAttribute("errMsg", ResponseBeanEnum.EMPTY_STOCK.getMsg());
            return "spikeFail";
        }

        /* Mysql查询判断是否重复抢购 */
//        OrderSpike orderSpike = iOrderSpikeService.getOne(new QueryWrapper<OrderSpike>()
//                .eq("user_id", user.getId())
//                .eq("goods_id", goodsId));

        OrderSpike orderSpike = (OrderSpike)redisTemplate.opsForValue().get("order:" + user.getId() + ":" + goodsId);

        if(null != orderSpike){
            model.addAttribute("errMsg", ResponseBeanEnum.LIMIT_ERROR.getMsg());
            return "spikeFail";
        }

        /* 进入抢购流程 */
        Order order = iOrderService.spike(user,goods);

        model.addAttribute("order",order);
        model.addAttribute("goods",goods);


        return "orderDetail";
    }
}

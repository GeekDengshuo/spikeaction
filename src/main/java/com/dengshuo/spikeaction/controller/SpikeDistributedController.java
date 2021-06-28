package com.dengshuo.spikeaction.controller;

import com.dengshuo.spikeaction.pojo.User;
import com.dengshuo.spikeaction.service.IGoodsService;
import com.dengshuo.spikeaction.service.IGoodsSpikeService;
import com.dengshuo.spikeaction.service.IOrderSpikeService;
import com.dengshuo.spikeaction.vo.GoodsVo;
import com.dengshuo.spikeaction.vo.ResponseBean;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @Author deng shuo
 * @Date 6/15/21 22:12
 * @Version 1.0
 *
 * 分布式秒杀
 */
@Controller
@RequestMapping("/spikeDistributed")
@Slf4j
public class SpikeDistributedController {

    private static final int SPIKE_CUSTOMER_NUM = 1000;

    private static final int BLOCKING_QUEUE_SIZE = 10000;

    // 获取CPU核心数
    private static int corePoolSize = Runtime.getRuntime().availableProcessors();

    // CPU密集型   N = CPU核心数 + 1
    // IO密集型    N = CPU核心数 *2
    private static ThreadPoolExecutor executor = new ThreadPoolExecutor(
            corePoolSize,corePoolSize+1,10L,
            TimeUnit.SECONDS,new LinkedBlockingQueue<>(BLOCKING_QUEUE_SIZE));



    @Autowired
    private IGoodsSpikeService iGoodsSpikeService;

    @Autowired
    private IOrderSpikeService iOrderSpikeService;

    @Autowired
    private IGoodsService iGoodsService;
    @PostMapping("/RedisLock")
    public ResponseBean DistributedRedisLock(User user,Long goodsId){

        log.info("开始分布式秒杀-{}","DistributedRedisLock");

        final long spikeId = goodsId;
        for(int i = 0;i< SPIKE_CUSTOMER_NUM;i++){
            user.setId((long)i);
            GoodsVo spikeGoods = iGoodsService.findGoodsVoById(spikeId);
            Runnable task = ()->{
                ResponseBean responseBean = iOrderSpikeService.spikeRedissonLock(user,spikeGoods);
                log.info("用户:{}{}",user.getId(),responseBean.getMsg());
            };
            executor.execute(task);
        }
        try {
            Thread.sleep(10000);

        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return ResponseBean.success();

    }

}

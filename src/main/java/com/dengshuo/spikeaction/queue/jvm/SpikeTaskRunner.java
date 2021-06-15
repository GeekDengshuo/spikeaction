package com.dengshuo.spikeaction.queue.jvm;

import com.dengshuo.spikeaction.pojo.Order;
import com.dengshuo.spikeaction.service.IOrderService;
import com.dengshuo.spikeaction.vo.DetailVo;;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;

/**
 * @Author deng shuo
 * @Date 6/15/21 14:36
 * @Version 1.0
 */
@Slf4j
public class SpikeTaskRunner implements ApplicationRunner {

    @Autowired
    private IOrderService iOrderService;


    @Override
    public void run(ApplicationArguments args) throws Exception {

        // 项目启动便启动等待
        new Thread(()->{
            log.info("{SpikeTaskRunner queue消费线程启动}");

            while(true){
                try{
                    DetailVo detailVo = SpikeQueue.getSpikeQueue().consume();

                    if(detailVo != null){
                        // spike 使用aop锁实现
                        Order order = iOrderService.spike(detailVo.getUser(),detailVo.getGoodsVo());
                        if(order != null){
                            log.info("用户:{},{}",order.getUserId(),"秒杀成功");
                        }
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();

    }
}

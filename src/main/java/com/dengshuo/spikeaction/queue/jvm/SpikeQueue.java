package com.dengshuo.spikeaction.queue.jvm;

import com.dengshuo.spikeaction.vo.DetailVo;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * @Author deng shuo
 * @Date 6/15/21 11:01
 * @Version 1.0
 *
 * 使用JVM提供的阻塞队列实现多线程的同步
 */
public class SpikeQueue {

    private static final int QUEUE_MAX_SIZE   = 100;

    private BlockingQueue<DetailVo> blockingQueue = new LinkedBlockingQueue<>(QUEUE_MAX_SIZE);

    // 私有化构造函数
    private SpikeQueue(){}

    // singleHolder  实现延迟加载
    private static class singleHolder{

        private static SpikeQueue queue = new SpikeQueue();
    }

    public int size(){
        return blockingQueue.size();
    }

    public static SpikeQueue getSpikeQueue(){ return singleHolder.queue;}

    /**
     * 生成压入队列
     * 队列插入满之后，不再接受元素插入
     * @param detailVo
     * @return
     *
     * add(e)   将元素e插入队列,成功返回true;没有可用空间抛出 illegalStateException
     * offer(e) 将元素e插入队列,成功返回true;没有可用空间返回false
     * offer(e, time, unit) 将元素e插入队列设定等待的时间，如果在指定时间内还不能往队列中插入数据则返回false
     * put(e)   队列未满时，直接插入没有返回值；队列满时会阻塞等待，一直等到队列未满时再插入
     */
    public boolean produce(DetailVo detailVo){
        return blockingQueue.offer(detailVo);
    }

    /**
     * 消费出队列
     * @return
     * @throws InterruptedException
     *
     * poll(time) 获取并移除队首元素，在指定的时间内去轮询队列看有没有首元素有则返回，否者超时后返回null
     * poll(timeout,unit) 获取并移除队首元素，在指定的时间内去轮询队列看有没有首元素有则返回，否者超时后返回失败
     * take() 与带超时时间的poll类似不同在于take时候如果当前队列空了它会一直等待其他线程调用notEmpty.signal()才会被唤醒
     */
    public DetailVo consume() throws InterruptedException{
        return blockingQueue.take();
    }
}

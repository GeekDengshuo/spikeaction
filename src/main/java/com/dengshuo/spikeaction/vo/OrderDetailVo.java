package com.dengshuo.spikeaction.vo;

import com.dengshuo.spikeaction.pojo.Order;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 订单详情返回对象
 * @Author deng shuo
 * @Date 6/6/21 15:08
 * @Version 1.0
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderDetailVo {

    private Order order;

    private GoodsVo goodsVo;
}

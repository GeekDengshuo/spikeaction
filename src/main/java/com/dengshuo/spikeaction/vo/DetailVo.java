package com.dengshuo.spikeaction.vo;

import com.dengshuo.spikeaction.pojo.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 详情返回对象
 * @Author deng shuo
 * @Date 6/6/21 15:01
 * @Version 1.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DetailVo {

    private User user;

    private GoodsVo goodsVo;

    private int spikeStatus;

    private int remainSeconds;
}

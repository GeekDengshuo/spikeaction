package com.dengshuo.spikeaction.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.dengshuo.spikeaction.pojo.Goods;
import com.dengshuo.spikeaction.vo.GoodsVo;

import java.util.List;


/**
 * <p>
 * 商品表 服务类
 * </p>
 *
 * @author dengshuo
 * @since 2021-05-29
 */
public interface IGoodsService extends IService<Goods> {
    /**
     * 获取商品列表
     * @return
     */
    List<GoodsVo> findGoodsVo();

    GoodsVo findGoodsVoById(long goodsId);
}

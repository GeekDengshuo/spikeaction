package com.dengshuo.spikeaction.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.dengshuo.spikeaction.pojo.Goods;
import com.dengshuo.spikeaction.vo.GoodsVo;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 * 商品表 Mapper 接口
 * </p>
 *
 * @author dengshuo
 * @since 2021-05-29
 */
@Repository
public interface GoodsMapper extends BaseMapper<Goods> {
    /**
     * 获取商品列表
     * @return
     */
    List<GoodsVo> findGoodsVo();

    /**
     * 获取商品详情
     * @param goodsId
     * @return
     */
    GoodsVo findGoodsVoById(long goodsId);
}

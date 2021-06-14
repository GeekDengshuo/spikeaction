package com.dengshuo.spikeaction.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dengshuo.spikeaction.mapper.GoodsMapper;
import com.dengshuo.spikeaction.pojo.Goods;
import com.dengshuo.spikeaction.service.IGoodsService;
import com.dengshuo.spikeaction.vo.GoodsVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 商品表 服务实现类
 * </p>
 *
 * @author dengshuo
 * @since 2021-05-29
 */
@Service
public class GoodsServiceImpl extends ServiceImpl<GoodsMapper, Goods> implements IGoodsService {

    @Autowired
    private GoodsMapper goodsMapper;

    @Override
    public List<GoodsVo> findGoodsVo() {

        return goodsMapper.findGoodsVo();
    }

    @Override
    public GoodsVo findGoodsVoById(long goodsId) {
        return goodsMapper.findGoodsVoById(goodsId);
    }
}

package com.dengshuo.spikeaction.vo;

import com.dengshuo.spikeaction.pojo.Goods;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 商品返回对象
 *
 * 包含商品和秒杀商品的属性
 * @author dengshuo
 * @since 2021-05-29
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class GoodsVo extends Goods {

	private BigDecimal spikePrice;

	private Integer goodsSpikeStock;

	private Date startDate;

	private Date endDate;
}
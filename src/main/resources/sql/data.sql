DROP TABLE IF EXISTS `t_user`;
CREATE TABLE `t_user`(
    `id` BIGINT(20) NOT NULL COMMENT '用户ID,手机号码',
    `nickname` VARCHAR(255)  DEFAULT NULL  ,
    `password` VARCHAR(255) DEFAULT NULL COMMENT 'MD5(MD5(pass明文+固定salt)+salt)',
    `slat` VARCHAR(10) DEFAULT NULL,
    `head` VARCHAR(128) DEFAULT NULL COMMENT '头像',
    `register_date` DATETIME DEFAULT NULL COMMENT '注册时间',
    `last_login_date` DATETIME NULL COMMENT '最后一次登陆时间',
    `login_count` INT(12) DEFAULT '0' COMMENT '登陆次数',
    PRIMARY KEY (`id`)

)ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COMMENT='用户表';
DROP TABLE IF EXISTS `t_goods`;
CREATE TABLE `t_goods`(
                        `id` BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT '商品ID',
                        `goods_name` VARCHAR(20) DEFAULT NULL COMMENT '商品名称',
                        `goods_title` VARCHAR(64) DEFAULT NULL COMMENT '商品标题',
                        `goods_img` VARCHAR(64) DEFAULT NULL COMMENT '商品图片',
                        `goods_detail` LONGTEXT COMMENT '商品详情',
                        `goods_price` DECIMAL(10,2) DEFAULT '0.00' COMMENT '商品价格',
                        `goods_stock` INT(11) DEFAULT '0' COMMENT '商品库存',
                        PRIMARY KEY(`id`)
)ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COMMENT='商品表';

DROP TABLE IF EXISTS `t_order`;
CREATE TABLE `t_order`(
                          `id` BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT '订单ID',
                          `user_id` BIGINT(20) DEFAULT NULL COMMENT '用户ID',
                          `goods_id` BIGINT(20) DEFAULT NULL COMMENT '商品ID',
                          `delivery_address_id` BIGINT(20) DEFAULT NULL COMMENT '收获地址ID',
                          `goods_name` VARCHAR(16) COMMENT '冗余商品名称',
                          `goods_count` INT(11) DEFAULT '0' COMMENT '商品数量',
                          `goods_price` DECIMAL(10,2) DEFAULT '0.00' COMMENT '商品价格',
                          `order_channel` TINYINT(4) DEFAULT '0' COMMENT '1-PC,2-Andriod,3-ios',
                          `status` TINYINT(4) DEFAULT '0' COMMENT '0-新建未支付,1-已支付,2-已发货，3-已收货,4-已退款,5-完成',
                          `create_time` datetime  DEFAULT NULL COMMENT '订单创建时间',
                          `pay_date` datetime DEFAULT NULL COMMENT '支付时间',

                          PRIMARY KEY(`id`)

)ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8mb4 COMMENT='订单表';

DROP TABLE IF EXISTS `t_goods_spike`;
CREATE TABLE `t_goods_spike`(
                                `id` BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT '秒杀商品ID',
                                `goods_id` BIGINT(20) DEFAULT NULL COMMENT '商品ID',
                                `spike_price` DECIMAL(10,2) DEFAULT '0.00' COMMENT '秒杀价格',
                                `goods_spike_stock` INT(11) DEFAULT '0' COMMENT '秒杀库存数量',
                                `start_date` datetime DEFAULT NULL COMMENT '秒杀开始时间',
                                `end_date` datetime DEFAULT NULL COMMENT '秒杀结束时间',
                                PRIMARY KEY(`id`)
)ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COMMENT='秒杀商品表';

DROP TABLE IF EXISTS `t_order_spike`;
CREATE TABLE `t_order_spike`(
                                `id` BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT '秒杀订单ID',
                                `user_id` BIGINT(20) DEFAULT NULL COMMENT '用户ID',
                                `order_id` BIGINT(20) DEFAULT NULL COMMENT '订单ID',
                                `goods_id` BIGINT(20) DEFAULT NULL COMMENT '商品ID',
                                PRIMARY KEY(`id`)

)ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8mb4 COMMENT='秒杀订单表';


INSERT INTO `t_goods` VALUES(1,'iPhone12','iPhone12 256G 国行','/img/iPhone12.png','iPhone12 256G 国行 4GB','5999',100),
                            (2,'iPhone12Pro','iPhone12 Pro 512G 国行','/img/iPhone12pro.png','iPhone12 256G 国行 6GB','8999',100);

INSERT INTO `t_goods_spike` VALUES(1,1,'1999',10,'2021-06-18 08:00:00','2021-06-18 12:00:00'),
                                  (2,2,'2999',10,'2021-06-18 08:00:00','2021-06-18 12:00:00');


-- d3b1294a61a07da9b49b6e22b2cbd7f9
DROP TABLE IF EXISTS `spikeAction`;
CREATE TABLE `spikeAction` (
                           `spikeAction_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '商品库存id',
                           `name` varchar(120) NOT NULL COMMENT '商品名称',
                           `number` int(11) NOT NULL COMMENT '库存数量',
                           `start_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '秒杀开启时间',
                           `end_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '秒杀结束时间',
                           `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                           `version` int(11) NOT NULL COMMENT '版本号',
                           PRIMARY KEY (`spikeAction_id`),
                           KEY `idx_start_time` (`start_time`),
                           KEY `idx_end_time` (`end_time`),
                           KEY `idx_create_time` (`create_time`)
) ENGINE=InnoDB AUTO_INCREMENT=1004 DEFAULT CHARSET=utf8 COMMENT='秒杀库存表';

/*Data for the table `seckill` */

insert  into `spikeAction`(`spikeAction_id`,`name`,`number`,`start_time`,`end_time`,`create_time`,`version`)
values (1000,'5000元秒杀iphone12',100,'2021-05-10 15:31:53','2021-05-12 15:31:53','2021-05-10 15:31:53',0),
       (1001,'500元秒杀华为耳机',100,'2021-05-10 15:31:53','2021-05-12 15:31:53','2021-05-10 15:31:53',0),
       (1002,'4000元秒杀小米11pro',100,'2021-05-10 15:31:53','2021-05-12 15:31:53','2021-05-10 15:31:53',0),
       (1003,'200元秒杀小米台灯',100,'2021-05-10 15:31:53','2021-05-12 15:31:53','2021-05-10 15:31:53',0);
use bms;

CREATE TABLE card_admin_action (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `batch_id` VARCHAR (32) NOT NULL COMMENT '批次id',
  `action_type` int(1) NOT NULL COMMENT '操作类型1:生成|2:激活|3:暂停|4:禁用|5:导出',
  `name` VARCHAR(32) NOT NULL COMMENT '操作人',
  `reason` VARCHAR(128) COMMENT '操作备注',
  `atime` DATETIME NOT NULL COMMENT '操作时间',
  PRIMARY KEY (`id`)
)ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='卡批次操作记录表';

CREATE TABLE card_admin_action_detail (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `action_id` INT (11) NOT NULL COMMENT '批次id',
  `card_no` VARCHAR(32) NOT NULL COMMENT '操作类型1:生成|2:激活|3:暂停|4:禁用|5:导出',
  `atime` DATETIME NOT NULL COMMENT '操作时间',
  PRIMARY KEY (`id`)
)ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='卡批次操作记录表';

CREATE TABLE card_batch (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `batch_id` VARCHAR (32) NOT NULL COMMENT '批次id',
  `venderid`  int(11) NOT NULL COMMENT '大客户id',
  `card_name` VARCHAR(32) NOT NULL COMMENT '卡名称',
  `card_type` int(11) NOT NULL COMMENT '卡类型1:实物卡|2:电子卡',
  `face_value` decimal(9,2) NOT NULL COMMENT '面值(单位:元,精确到分)',
  `number` int(11) NOT NULL COMMENT '数量',
  `company` VARCHAR(32) COMMENT '实体卡制卡公司',
  `stime` DATETIME NOT NULL COMMENT '卡可用的起始时间',
  `etime` DATETIME NOT NULL COMMENT '卡的过期时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `batch_id` (`batch_id`),
)ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='卡批次记录表';


CREATE TABLE card (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `card_no` VARCHAR (32) NOT NULL COMMENT '卡号',
  `card_password`  VARCHAR (32) NOT NULL COMMENT '卡密码',
  `venderid` int(11) NOT NULL COMMENT '大客户id',
  `bind_user` int(11) NOT NULL DEFAULT 0 '绑定用户的id',
  `bind_type` int(11) NOT NULL COMMENT '绑定方式1:自动|2:手动',
  `face_value` decimal(9,2) NOT NULL '面值(单位:元,精确到分)',
  `balance` decimal(9,2) NOT NULL '余额(单位:元,精确到分)',
  `status` int(11) NOT NULL '卡状态1:未激活|2:已激活|3:已暂停|4:已禁用',
  `stime` DATETIME NOT NULL COMMENT '卡可用的起始时间',
  `etime` DATETIME NOT NULL COMMENT '卡的过期时间',
  PRIMARY KEY (`id`),
)ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='卡信息表';


CREATE TABLE card_user_record (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `card_no` VARCHAR (32) NOT NULL COMMENT '卡号',
  `action_type` int(11) NOT NULL COMMENT '操作类型1:绑定|2:购物|3:退款',
  `order_sn` VARCHAR (32) DEFAULT '' COMMENT '平台订单号',
  `refund_sn` VARCHAR (32) DEFAULT '' COMMENT '退款单号',
  `use_fee` decimal(9,2) DEFAULT '0.00' COMMENT '使用金额',
  `atime` DATETIME NOT NULL COMMENT '卡可用的起始时间',
  PRIMARY KEY (`id`),
)ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='卡操作记录表';

CREATE TABLE t_order_card (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `order_sn`VARCHAR (64) NOT NULL COMMENT '平台订单号',
  `card_no` varchar(32) NOT NULL COMMENT '卡号',
  `balance` decimal(9,2) NOT NULL COMMENT '使用前余额(单位:元,精确到分)',
  `use_fee` decimal(9,2) NOT NULL COMMENT '使用金额',
  `ctime` DATETIME NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
)ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='订单充值卡使用记录表';

ALTER TABLE t_order_refund ADD COLUMN card_refund_price decimal(9,2) DEFAULT '0.00' COMMENT '退款到卡的金额' AFTER refund_price;



-- #20181023bms商品id字段修改
ALTER TABLE t_order_aftersale MODIFY COLUMN product_id bigint(20);
ALTER TABLE t_order_invoice_detail MODIFY COLUMN product_id bigint(20);
ALTER TABLE t_order_product MODIFY COLUMN product_id bigint(20);
ALTER TABLE t_order_refund MODIFY COLUMN product_id bigint(20);


-- 20190214字段改动
UPDATE t_order_vender_refund SET refund_status = 0 WHERE refund_status = 1;
UPDATE t_order_vender_refund SET refund_status = 1 WHERE refund_status = 2;

-- 20190217退款模块重构王豪阳
CREATE TABLE `t_order_refund_batch` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `venderid` int(11) NOT NULL COMMENT '客户id',
  `user_id` int(11) NOT NULL COMMENT '用户id',
  `user_cash_refund_price` decimal(9,2) DEFAULT '0.00' COMMENT '退款给用户到第三方账户的金额',
  `user_card_refund_price` decimal(9,2) DEFAULT '0.00' COMMENT '退款到卡的金额',
  `vender_refund_price` decimal(9,2) DEFAULT '0.00' COMMENT '退款给大客户的金额',
  `refund_type` tinyint(2) NOT NULL COMMENT '退款类型 1:微信|2:支付宝|3:卡(卡+微信=微信, 卡+支付宝=支付宝)',
  `user_refund_status` tinyint(2) DEFAULT '0' COMMENT '用户退款状态 0:未退款|1:已退款',
  `vender_refund_status` tinyint(2) DEFAULT '0' COMMENT '大客户退款状态 0:未退款|1:已退款',
  `order_sn` varchar(64) NOT NULL COMMENT '平台订单号',
  `pay_order_sn` varchar(64) NOT NULL COMMENT '微信,支付宝等支付订单号',
  `refund_no` varchar(64) NOT NULL COMMENT '退款单号',
  `refund_order_sn` varchar(64) DEFAULT NULL COMMENT '第三方退款单号',
  `unq_id` varchar(32) NOT NULL COMMENT '退款唯一id(声明是哪次退款)',
  `refund_reason` VARCHAR (32) DEFAULT '' COMMENT '退款原因',
  `ctime` datetime NOT NULL COMMENT '退款时间',
  `utime` datetime NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY (`refund_no`),
  UNIQUE KEY (`unq_id`),
) ENGINE=InnoDB AUTO_INCREMENT=9470 DEFAULT CHARSET=utf8 COMMENT='退款';

CREATE TABLE `t_order_refund_detail` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `order_sn` varchar(64) NOT NULL COMMENT '平台订单号',
  `refund_no` varchar(64) NOT NULL COMMENT '退款单号',
  `product_id` bigint(20) NOT NULL COMMENT '商品id',
  `count` int(11) NOT NULL DEFAULT '1' COMMENT '商品数量',
  `user_cash_refund_price` decimal(9,2) DEFAULT '0.00' COMMENT '退款给用户到第三方账户的金额',
  `user_card_refund_price` decimal(9,2) DEFAULT '0.00' COMMENT '退款给用户到卡的金额',
  `vender_refund_price` decimal(9,2) DEFAULT '0.00' COMMENT '退款给大客户金额',
  `ctime` datetime NOT NULL COMMENT '退款时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY (`order_sn`, `refund_no`, `product_id`),
) ENGINE=InnoDB AUTO_INCREMENT=9470 DEFAULT CHARSET=utf8 COMMENT='退款明细';
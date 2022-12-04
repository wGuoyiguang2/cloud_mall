 CREATE TABLE `t_wx_scan` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `appid` varchar(32) NOT NULL DEFAULT '' COMMENT '公众号的appid',
  `openid` VARCHAR(32) NOT NULL DEFAULT '' COMMENT '微信用户的id',
  `ticket` varchar(120) NOT NULL DEFAULT '' COMMENT '微信二维码票据',
  `type` tinyint(1) NOT NULL DEFAULT '1' COMMENT '二维码用途1:扫码登录',
  `value` varchar(256) NOT NULL COMMENT '值',
  `valuemd5num` varchar(32) NOT NULL COMMENT '值得MD5',
  `source` VARCHAR (64) NOT NULL DEFAULT '' COMMENT '二维码来源',
  `scantime` DATETIME  COMMENT '扫描时间',
  `ctime` DATETIME NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`),
  KEY `idx_valuemd5sum` (`valuemd5num`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='二维码生成记录表';
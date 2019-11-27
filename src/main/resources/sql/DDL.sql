CREATE TABLE `wx_cfg` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT '自增主键',
  `type` varchar(20) NOT NULL COMMENT '配置项类型',
  `name` varchar(20) NOT NULL COMMENT '配置项名称',
  `value` varchar(255) DEFAULT NULL COMMENT '配置项值',
  `parent_id` int(11) DEFAULT NULL COMMENT '父ID',
  `platform` varchar(36) NOT NULL COMMENT '公众号标识',
  `enabled` tinyint(1) DEFAULT '1' COMMENT '是否可用，1：可用，0：不可用',
  `desc` varchar(255) DEFAULT NULL COMMENT '描述信息',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8;

CREATE TABLE `wx_token` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT '自增主键',
  `platform` varchar(20) DEFAULT NULL COMMENT '公众号标识',
  `token_type` varchar(20) NOT NULL COMMENT 'token类型，access_token：基础token，jsapi_token：jsapi_ticket',
  `access_token` text NOT NULL COMMENT 'token值',
  `expires_in` int(11) NOT NULL COMMENT '失效时长',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `last_upd_time` timestamp NULL DEFAULT '0000-00-00 00:00:00' COMMENT '最后一次更新时间',
  `refresh_count` int(11) DEFAULT NULL COMMENT '刷新次数',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8;
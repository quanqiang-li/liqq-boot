/*系统用户*/
CREATE TABLE `sys_user` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `username` varchar(50) DEFAULT NULL COMMENT '用户名',
  `password` varchar(64) DEFAULT NULL COMMENT '密码',
  `mobile` varchar(20) DEFAULT NULL COMMENT '手机号',
  `realname` varchar(50) DEFAULT NULL COMMENT '真实姓名',
  `identity_number` varchar(21) DEFAULT NULL COMMENT '身份证号',
  `email` varchar(50) DEFAULT NULL COMMENT '邮箱',
  `birthday` datetime DEFAULT NULL COMMENT '生日',
  `sex` tinyint(1) DEFAULT NULL COMMENT '性别 0 男 1 女',
  `address` varchar(255) DEFAULT NULL COMMENT '地址',
  `head_img_url` varchar(255) DEFAULT NULL COMMENT '头像',
  `password_expired_date` datetime DEFAULT NULL COMMENT '密码过期时间 null永不过期',
  `account_expired_date` datetime DEFAULT NULL COMMENT '帐号过期时间 null永不过期',
  `enabled` tinyint(1) DEFAULT NULL COMMENT '是否可用 0可用 1禁用',
  `account_non_locked` tinyint(1) DEFAULT NULL COMMENT '是否锁定 0 未锁定 1锁定',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `idx_user_name` (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='系统用户';

/*系统角色*/
CREATE TABLE `sys_role` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `code` varchar(20) DEFAULT NULL COMMENT '代码',
  `name` varchar(50) DEFAULT NULL COMMENT '名称',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `idx_role_code` (`code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='系统角色';


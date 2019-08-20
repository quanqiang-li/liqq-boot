/*系统字典表*/
CREATE TABLE `sys_dict` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `code` varchar(50) DEFAULT NULL COMMENT '代码,如表名,业务分类',
  `name` varchar(50) DEFAULT NULL COMMENT '名称,对应code',
  `k` varchar(50) DEFAULT NULL COMMENT '键',
  `v` varchar(50) DEFAULT NULL COMMENT '值',
  `sort` int(2) DEFAULT NULL COMMENT '排序',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `idx_code_key` (`code`,`key`) USING BTREE COMMENT '一个类型下不能有两个以上相同key'
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 COMMENT='系统字典表';

INSERT INTO `test`.`sys_dict` (`id`, `code`, `name`, `k`, `v`, `sort`, `create_time`, `update_time`) VALUES ('1', 'sex', '性别', '0', '男', '1', '2019-08-20 21:03:55', NULL);
INSERT INTO `test`.`sys_dict` (`id`, `code`, `name`, `k`, `v`, `sort`, `create_time`, `update_time`) VALUES ('2', 'sex', '性别', '1', '女', '2', '2019-08-20 21:03:59', NULL);


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
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `idx_role_code` (`code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='系统角色';

/*系统用户角色关联表*/
CREATE TABLE `sys_user_role` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `user_id` int(11) DEFAULT NULL COMMENT '用户id',
  `role_id` int(11) DEFAULT NULL COMMENT '角色id',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='系统用户角色关联表';

/*系统资源表*/
CREATE TABLE `sys_resource` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `parent_id` int(11) NOT NULL COMMENT '父级id',
  `code` varchar(50) DEFAULT NULL COMMENT '资源代码',
  `name` varchar(50) DEFAULT NULL COMMENT '资源名称',
  `href` varchar(255) DEFAULT NULL COMMENT '资源路径',
  `sort` int(4) DEFAULT NULL COMMENT '咨询排序',
  `type` tinyint(1) DEFAULT NULL COMMENT '资源类型 0菜单 1功能',
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='系统资源表';

/*系统角色资源关联表*/
CREATE TABLE `sys_role_resource` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `role_id` int(11) DEFAULT NULL COMMENT '角色id',
  `resource_id` int(11) DEFAULT NULL COMMENT '资源id',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='系统角色资源关联表';
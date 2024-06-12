-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP DATABASE IF EXISTS `test`;
CREATE DATABASE IF NOT EXISTS `test`;
USE `test`;
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user`  (
                             `id` bigint(20) NOT NULL COMMENT '用户id',
                             `username` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '账户',
                             `password` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '用户密码密文',
                             `phone` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '手机号码',
                             `real_name` varchar(60) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '真实名称',
                             `nick_name` varchar(60) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '昵称',
                             `email` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '邮箱(唯一)',
                             `status` tinyint(4) NULL DEFAULT 1 COMMENT '账户状态(1.正常 2.锁定 )',
                             `sex` tinyint(4) NULL DEFAULT 1 COMMENT '性别(1.男 2.女)',
                             `deleted` tinyint(4) NULL DEFAULT 1 COMMENT '是否删除(1未删除；0已删除)',
                             `create_id` bigint(20) NULL DEFAULT NULL COMMENT '创建人',
                             `update_id` bigint(20) NULL DEFAULT NULL COMMENT '更新人',
                             `create_where` tinyint(4) NULL DEFAULT 1 COMMENT '创建来源(1.web 2.android 3.ios )',
                             `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
                             `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
                             PRIMARY KEY (`id`) USING BTREE,
                             UNIQUE INDEX `unique_username`(`username`) USING BTREE COMMENT '用户名唯一'
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '用户表' ROW_FORMAT = COMPACT;

-- ----------------------------
-- Records of sys_user
-- ----------------------------
INSERT INTO `sys_user` VALUES (1237361915165020161, 'admin', '$2a$10$JqoiFCw4LUj184ghgynYp.4kW5BVeAZYjKqu7xEKceTaq7X3o4I4W', '13888888888', '小池', '超级管理员', '875267425@qq.com', 1, 1, 1, NULL, 1237361915165020161, 1, '2019-09-22 19:38:05', '2020-04-07 18:08:52');
INSERT INTO `sys_user` VALUES (1237365636208922624, 'test', '$2a$10$BGys1N0SGdynf6HzdARzUeXZN7JZq5MBE5C6b/oZD108eIYGuq.rK', '16666666666', NULL, NULL, NULL, 1, 2, 1, 1237361915165020161, 1237361915165020161, 1, '2020-03-10 13:12:12', '2020-04-06 18:11:58');
INSERT INTO `sys_user` VALUES (1246071816909361152, 'test123', '$2a$10$aT26Bk4wqx0DC6PuwpxLDuiHa121g1qsTgbH5.bxf14VPv4PRtLg.', '13666666666', 'test测试账号', '测试账号', '222222222@qq.com', 1, 1, 1, NULL, 1237361915165020161, 1, '2020-04-03 21:47:27', '2021-12-22 00:37:48');
INSERT INTO `sys_user` VALUES (1246347518934126592, 'aaaaaa啊啊啊啊', '$2a$10$l1H90jy1UyZaHnsnZ7qCHOL8d83RUZn8A0N0aIEHWfGe2u.LmYkES', '13333333336', 'aaaaa', 'aaaaaa', 'aaaaaaaa@qq.com', 1, 1, 1, NULL, 1237361915165020161, 1, '2020-04-04 16:02:59', '2020-04-04 17:18:34');
INSERT INTO `sys_user` VALUES (1246352746546860032, 'bb啵啵c呃呃呃呃呃', '$2a$10$ex6BCxAd.ubD6ogPgj/jKOvr1HYcczXnGUXGSiP2Lh8uTcSD7dngK', '13888888888', 'bbbbb', 'bbbbb', 'bbbbbbbbbb@qq.com', 1, 2, 1, NULL, 1247515643591397376, 2, '2020-04-04 16:23:46', '2021-12-25 14:41:52');
INSERT INTO `sys_user` VALUES (1246362842827984896, 'justTest123', '$2a$10$31JFwSh4bGCD/b.rwnKYHeHlqP5q3hTQZGr3nsSJW2HujULNfMrii', '13555555556', 'formceshiddd', 'formceshiddd', 'bbb888888@qq.com', 1, 1, 1, NULL, 1237361915165020161, 1, '2020-04-04 17:03:53', '2021-12-21 22:46:28');
INSERT INTO `sys_user` VALUES (1246368763562037248, '水水水123', '$2a$10$FDofRcNN18MbTGFHXQLSf.wsmFGozn3JUTVIvTiaiqRNthrdxKTzG', '15777777778', '水水水水123', '水水水水123', 'qq55555@qq.com', 1, 1, 1, NULL, 1237361915165020161, 2, '2020-04-04 17:27:24', '2020-04-04 17:44:00');
INSERT INTO `sys_user` VALUES (1247078461865070592, 'ddddddd', '$2a$10$Sw2Ql7BnqqX2WCE7UZxoP.x5UeQe/7QlBLD.8WQgA5VFETi04aN5S', '13222222222', 'ddddd', 'dddddd', '55555555@qq.com', 1, 1, 1, NULL, NULL, 1, '2020-04-06 16:27:30', '2021-12-22 00:38:18');
INSERT INTO `sys_user` VALUES (1247078545952477184, 'ccccccccc', '$2a$10$BdjM5j3wiVHF1XHymLxaxOfMeh4.uF7rnETKaNUB37yVWylFKwSRK', '13555555555', 'ccccc', 'cccc', '22222555@qq.com', 1, 2, 0, NULL, NULL, 1, '2020-04-06 16:27:50', '2021-12-22 00:33:34');
INSERT INTO `sys_user` VALUES (1247078658519207936, 'xxxxxxxxx', '$2a$10$1/RUJ7Na1tsgUfYnygnlAead0odJBhREJbb.7G2pW5YAaIX/WJenO', '13333333333', 'xxxxxxxxx', 'xxxxxxx', '2444444445@qq.com', 1, 2, 1, NULL, 1237361915165020161, 1, '2020-04-06 16:28:17', '2021-12-17 11:34:56');
INSERT INTO `sys_user` VALUES (1247078839641837568, '8888888888', '', '13999999999', '888888', '888777', '888888888@qq.com', 1, 1, 0, NULL, 1237361915165020161, 1, '2020-04-06 16:29:00', '2021-12-17 12:24:13');
INSERT INTO `sys_user` VALUES (1247079478228815872, 'eeeeeeeeee', '$2a$10$3EsSOzRQ7SheqTvf3I9l9.hnpjAAIyRnGnLLYA28CWb0niFgN6iry', '13688888888', 'eeee', 'eee', 'eeeeee@qq.com', 1, 1, 0, NULL, NULL, 1, '2020-04-06 16:31:32', '2021-12-22 00:35:32');
INSERT INTO `sys_user` VALUES (1247462611247828992, 'ssssss', '$2a$10$BDHPYj6a7hT7wz.cbC6uGOVV57r7C0CmrM59EMBtdxo34astzQLJS', '13333333333', 'ssssss', 'ssssss', '333333@qq.com', 1, 1, 1, NULL, NULL, 1, '2020-04-07 17:53:58', NULL);
INSERT INTO `sys_user` VALUES (1247515643591397376, 'admin123', '$2a$10$RlBzDJm2IOb5...piM.yEObU.r0D6Jd5XrszKzu/r3mFRPTx0gQbi', '13699999999', 'admin测试', 'admin测试', 'admin123@qq.com', 1, 1, 1, NULL, 1237361915165020161, 1, '2020-04-07 21:24:42', '2020-04-08 12:04:46');
INSERT INTO `sys_user` VALUES (1473295684005400576, 'admin222', 'admin222', '17711929393', 'laofang', 'admin222', '666@163.com', 1, 1, NULL, NULL, NULL, NULL, '2021-12-21 22:13:58', '2021-12-21 22:13:58');
INSERT INTO `sys_user` VALUES (1473296022544453632, 'test111', 'test11133', '18733456789', 'test111', 'test111', '444@163.com', 1, 1, NULL, NULL, NULL, 1, '2021-12-21 22:15:20', '2021-12-21 22:15:20');
INSERT INTO `sys_user` VALUES (1473296822679244800, 'test222', 'test123456', '18766253534', 'test222', 'test222', '666@qq.com', 1, 1, 1, NULL, NULL, 1, '2021-12-21 22:18:30', '2021-12-21 22:18:31');
INSERT INTO `sys_user` VALUES (1506926674485321728, 'zhaoliu777', '$2a$10$FbHiQxtc0v6lSy34cTLMNeOIZKCpLDeo.QPqLPx6IZQMiNt5x.2aK', '18811111111', 'adddd', 'adminad', '111@163.com', 1, 1, 1, NULL, NULL, 1, '2022-03-24 17:31:31', '2022-03-24 17:31:31');

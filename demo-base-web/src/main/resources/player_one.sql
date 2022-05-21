/*
 Navicat Premium Data Transfer

 Source Server         : PHPSTUDY
 Source Server Type    : MySQL
 Source Server Version : 80026
 Source Host           : localhost:3306
 Source Schema         : player_one

 Target Server Type    : MySQL
 Target Server Version : 80026
 File Encoding         : 65001

 Date: 29/12/2021 15:54:26
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for address
-- ----------------------------
DROP TABLE IF EXISTS `address`;
CREATE TABLE `address`  (
  `id` int(0) NOT NULL AUTO_INCREMENT,
  `user_id` int(0) NOT NULL,
  `address` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `detail` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `mobile` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `tag` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `is_default` tinyint(0) NOT NULL DEFAULT 0,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 9 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of address
-- ----------------------------
INSERT INTO `address` VALUES (4, 2, '北京市市辖区丰台区', '阿达镇', '12121212213', '', '阿巴斯', 0);
INSERT INTO `address` VALUES (7, 2, '北京市市辖区东城区', '北京大学1号楼', '12313112112', '', '速度', 1);
INSERT INTO `address` VALUES (8, 2, '辽宁省沈阳市于洪区', '书店镇', '12312312311', '', '美术馆', 0);
INSERT INTO `address` VALUES (9, 2, '北京市市辖区丰台区', '阿达镇', '12121212214', '', '阿巴斯', 0);

-- ----------------------------
-- Table structure for api
-- ----------------------------
DROP TABLE IF EXISTS `api`;
CREATE TABLE `api`  (
  `id` int(0) NOT NULL AUTO_INCREMENT,
  `api_name` varchar(64) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL,
  `url` varchar(64) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = MyISAM AUTO_INCREMENT = 6 CHARACTER SET = utf8 COLLATE = utf8_unicode_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of api
-- ----------------------------
INSERT INTO `api` VALUES (1, '接口文档', '/doc.html');
INSERT INTO `api` VALUES (2, '获取用户列表', '/api/user/findUserPage');
INSERT INTO `api` VALUES (3, '获取用户信息，通过jwt', '/api/user/findUserByJWT');
INSERT INTO `api` VALUES (4, '获取统计数据', '/api/shop/fetchDashBoard');
INSERT INTO `api` VALUES (5, '修改用户enable', '/api/user/**');

-- ----------------------------
-- Table structure for banner
-- ----------------------------
DROP TABLE IF EXISTS `banner`;
CREATE TABLE `banner`  (
  `id` int(0) NOT NULL,
  `goods_id` int(0) NOT NULL,
  `banner_url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `deadline` timestamp(0) NOT NULL,
  `enable` tinyint(0) NOT NULL DEFAULT 0,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of banner
-- ----------------------------

-- ----------------------------
-- Table structure for cart
-- ----------------------------
DROP TABLE IF EXISTS `cart`;
CREATE TABLE `cart`  (
  `id` int(0) NOT NULL AUTO_INCREMENT,
  `goods_id` int(0) NOT NULL,
  `num` int(0) NOT NULL,
  `category_id` int(0) NULL DEFAULT NULL,
  `price` decimal(10, 2) NOT NULL,
  `user_id` int(0) NOT NULL,
  `is_active` tinyint(0) NOT NULL DEFAULT 1,
  `create_time` timestamp(0) NOT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `onlyone`(`goods_id`, `user_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 33 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of cart
-- ----------------------------
INSERT INTO `cart` VALUES (16, 20, 25, 6, 544.00, 2, 0, '2021-11-19 11:07:57');
INSERT INTO `cart` VALUES (25, 23, 1, 21, 655.00, 2, 0, '2021-11-19 18:36:32');
INSERT INTO `cart` VALUES (26, 26, 1, 24, 3322.00, 2, 1, '2021-11-19 18:36:34');
INSERT INTO `cart` VALUES (29, 22, 1, 21, 466.00, 2, 0, '2021-11-19 18:42:04');
INSERT INTO `cart` VALUES (30, 13, 1, 6, 122.00, 2, 0, '2021-11-19 19:01:44');

-- ----------------------------
-- Table structure for comment
-- ----------------------------
DROP TABLE IF EXISTS `comment`;
CREATE TABLE `comment`  (
  `id` int(0) NOT NULL AUTO_INCREMENT,
  `content` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `user_id` int(0) NOT NULL,
  `goods_id` int(0) NOT NULL,
  `star` int(0) NOT NULL DEFAULT 5,
  `like_count` int(0) NOT NULL DEFAULT 0,
  `create_time` timestamp(0) NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of comment
-- ----------------------------
INSERT INTO `comment` VALUES (1, 'good idea!!!', 2, 28, 5, 3, '2021-11-02 13:37:54');
INSERT INTO `comment` VALUES (2, 'gooadsfasd!', 3, 28, 5, 2, '2021-11-13 13:38:10');
INSERT INTO `comment` VALUES (3, 'adfadfad', 2, 28, 5, 1, '2021-11-12 13:38:26');

-- ----------------------------
-- Table structure for comment_inset
-- ----------------------------
DROP TABLE IF EXISTS `comment_inset`;
CREATE TABLE `comment_inset`  (
  `id` int(0) NOT NULL AUTO_INCREMENT,
  `comment_id` int(0) NOT NULL,
  `url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of comment_inset
-- ----------------------------

-- ----------------------------
-- Table structure for dlc
-- ----------------------------
DROP TABLE IF EXISTS `dlc`;
CREATE TABLE `dlc`  (
  `id` int(0) NOT NULL,
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `type` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `goods_id` int(0) NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of dlc
-- ----------------------------

-- ----------------------------
-- Table structure for goods
-- ----------------------------
DROP TABLE IF EXISTS `goods`;
CREATE TABLE `goods`  (
  `id` int(0) NOT NULL AUTO_INCREMENT,
  `goods_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `category_id` int(0) NULL DEFAULT NULL,
  `description` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL,
  `price` decimal(10, 2) NULL DEFAULT NULL,
  `stock` int(0) NULL DEFAULT NULL,
  `sales` int(0) NULL DEFAULT 0,
  `cover_url` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `is_on` tinyint(0) UNSIGNED NOT NULL DEFAULT 0 COMMENT '0：下架、1：上架、2：补货中',
  `is_recommend` tinyint(1) NOT NULL DEFAULT 0,
  `create_time` timestamp(0) NULL DEFAULT NULL,
  `update_time` timestamp(0) NULL DEFAULT NULL,
  PRIMARY KEY (`id`, `goods_name`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of goods
-- ----------------------------
INSERT INTO `goods` VALUES (1, '阿斯特拉的高级骑士 奥斯卡', 7, '<p>尺寸:<strong>Einheitsgröße</strong>  |  颜色:<strong>Keine Farbe</strong></p><h3 style=\"text-align:start;text-indent:2em;\">产品描述</h3><p style=\"text-align:start;text-indent:2em;\">TV动画《Fate/Grand Order -绝对魔兽战线巴比伦尼亚》中的&quot;吉尔伽美什&quot;在figma中登场!<br/><br/>●通过灵活且精确的figma原创关节零件,可以再现各种姿势。<br/>●关键部位采用软质素材,在不影响整体造型的前提下确保可动范围。<br/>准备了充满自信的&quot;不敌脸&quot;、战斗时的&quot;呐喊表情&quot;、时折的冷酷&quot;看下脸&quot;。<br/>●配件附带「石板」、「石板用支柱」、「斧」。<br/>包装盒内的figma专用底座带有可动支柱,可应对多种场景。</p><div class=\"media-wrap image-wrap\"><img class=\"media-wrap image-wrap\" src=\"https://player-one.oss-cn-beijing.aliyuncs.com/goods/2021-11-15/1636961738372.jpg\"/></div><p>12121212asdfa</p><p>阿斯顿</p><p>啊撒打发阿斯顿发射点阿斯顿阿斯顿</p><p><span style=\"font-size:64px\">图片</span>：</p><div class=\"media-wrap image-wrap\"><img src=\"https://player-one.oss-cn-beijing.aliyuncs.com/goods/2021-11-15/1636966145735.jpg\"/></div><p></p><p></p><p></p>', 192.00, 222, 12, '/goods/2021-11-08/1636356405046.jpg', 1, 1, '2021-09-17 05:54:49', '2021-11-15 16:49:08');
INSERT INTO `goods` VALUES (2, '怪物猎人世界：zinogre', 8, '<p>asaaaaaa12</p><p></p><p>12啊撒打发</p>', 222.00, 122, 1, '/goods/2021-11-08/1636356437029.jpg', 1, 1, '2021-09-17 08:54:52', '2021-11-08 15:27:20');
INSERT INTO `goods` VALUES (3, 'Xeno\'Jiiva Beta 盔甲版 DX Ver', 8, '<p style=\"text-align:start;text-indent:2em;\" id=\"title\" class=\"a-size-large a-spacing-none\"><span style=\"font-size:24px\"><span style=\"line-height:32\">Xeno&#x27;Jiiva Beta 盔甲版 DX Ver</span></span></p>', 822.00, 222, 2, '/goods/2021-11-12/1636727823144.jpg', 1, 0, '2021-11-12 22:37:07', '2021-11-12 22:37:07');
INSERT INTO `goods` VALUES (13, '佐伊', 6, '<p>啊撒打发</p>', 122.00, 25, 77, '/goods/2021-11-08/1636356487099.jpg', 1, 0, '2021-11-08 15:28:12', '2021-11-08 15:28:12');
INSERT INTO `goods` VALUES (14, '怪物猎人 Creator\'s Model 雄火龙', 8, '<p></p><div class=\"media-wrap image-wrap\"><img src=\"https://player-one.oss-cn-beijing.aliyuncs.com/goods/2021-11-15/1636961716623.jpg\"/></div><p>啊撒打发</p>', 444.00, 24, 0, '/goods/2021-11-09/1636418114315.jpg', 1, 0, '2021-11-06 15:28:49', '2021-11-15 15:35:21');
INSERT INTO `goods` VALUES (15, '盲僧', 6, '<p>啊撒打发</p>', 155.00, 151, 33, '/goods/2021-11-08/1636356555827.jpg', 1, 0, '2021-11-08 15:29:19', '2021-11-08 15:29:19');
INSERT INTO `goods` VALUES (16, 'Icebone Rioreus ', 8, '<p>啊撒打发</p>', 333.00, 312, 22, '/goods/2021-11-12/1636727503407.jpg', 1, 0, '2021-11-12 22:31:47', '2021-11-12 22:31:47');
INSERT INTO `goods` VALUES (17, '雷乌斯·变化', 8, '<p>怪物猎人：世界 猎人 </p>', 888.00, 444, 51, '/goods/2021-11-12/1636727642200.jpg', 1, 0, '2021-11-12 22:34:07', '2021-11-12 22:34:07');
INSERT INTO `goods` VALUES (18, '银狼α Ver. DX ', 8, '<p style=\"text-align:start;text-indent:2em;\" id=\"title\" class=\"a-size-large a-spacing-none\"><span style=\"font-size:24px\"><span style=\"line-height:32\">银狼α Ver. DX</span></span> </p>', 877.00, 321, 77, '/goods/2021-11-12/1636727705561.jpg', 1, 0, '2021-11-12 22:35:13', '2021-11-12 22:35:13');
INSERT INTO `goods` VALUES (20, '光辉', 6, '<p>ADF </p>', 544.00, 123, 0, '/goods/2021-11-14/1636886059359.jpg', 1, 0, '2021-11-14 18:34:37', '2021-11-14 18:34:37');
INSERT INTO `goods` VALUES (21, '《刺客信条：英灵殿》主角狼吻者艾沃尔', 21, '<p>品牌名称：<strong>刺客信条</strong></p><p style=\"text-align:start;text-indent:2em;\" class=\"attr-list-hd tm-clear\"><em>产品参数：</em></p><ul><li>品牌: 刺客信条</li><li>型号: 刺客信条：英灵殿 手办</li><li>适用年龄: 3周岁以上</li><li>尺寸: 《刺客信条：英灵殿》</li><li>颜色分类: 狼吻者艾沃尔手办</li><li>出售状态: 预售</li></ul>', 655.00, 542, 0, '/goods/2021-11-15/1636976487069.jpg', 1, 0, '2021-11-15 19:41:42', '2021-11-15 19:41:42');
INSERT INTO `goods` VALUES (22, '刺客信条起源 巴耶克', 21, '<p>品牌名称：<strong>purearts</strong></p><p style=\"text-align:start;text-indent:2em;\" class=\"attr-list-hd tm-clear\"><em>产品参数：</em></p><ul><li>品牌: purearts</li><li>型号: 3307216014768</li><li>适用年龄: 14周岁以上</li><li>热门系列: pvc系列</li><li>出售状态: 现货</li><li>货号: 3307216014768</li><li>ACG作品名: 刺客信条起源</li></ul>', 466.00, 42, 0, '/goods/2021-11-15/1636976526192.jpg', 1, 0, '2021-11-15 19:42:17', '2021-11-15 19:42:17');
INSERT INTO `goods` VALUES (23, '《刺客信条：奥德赛》亚历克西欧斯', 21, '<p>品牌名称：<strong>purearts</strong></p><p style=\"text-align:start;text-indent:2em;\" class=\"attr-list-hd tm-clear\"><em>产品参数：</em></p><ul><li>品牌: purearts</li><li>型号: 3307216058892</li><li>适用年龄: 14周岁以上</li><li>热门系列: pvc系列</li><li>出售状态: 现货</li><li>货号: 3307216058892</li><li>ACG作品名: 刺客信条：奥德赛</li></ul>', 655.00, 52, 0, '/goods/2021-11-15/1636976556067.jpg', 1, 0, '2021-11-15 19:42:44', '2021-11-15 19:42:44');
INSERT INTO `goods` VALUES (24, '《刺客信条：叛变》变节者谢伊', 21, '<p>品牌名称：<strong>Ubisoft</strong></p><p style=\"text-align:start;text-indent:2em;\" class=\"attr-list-hd tm-clear\"><em>产品参数：</em></p><ul><li>品牌: Ubisoft</li><li>型号: 3307216058878</li><li>材质: PVC</li><li>颜色分类: 变节者谢伊</li><li>出售状态: 现货</li><li>大小: 高度240mm</li><li>ACG作品名: 刺客信条</li></ul>', 444.00, 41, 0, '/goods/2021-11-15/1636976578874.jpg', 1, 0, '2021-11-15 19:43:06', '2021-11-15 19:43:06');
INSERT INTO `goods` VALUES (25, '天彗龙 普通版怒版 CFB怪物猎人', 8, '<ul><li>品牌: capcom</li><li>型号: cfb</li><li>适用年龄: 6周岁以上</li><li>尺寸: 模型长约22cm宽约25cm高约22cm</li><li>热门系列: pvc系列</li><li>颜色分类: 怒版，正版无外盒，带内包吸塑盒，配件齐全，打包给力 普通版，正版无外盒，带内包吸塑盒，配件齐全，打包给力</li><li>出售状态: 现货</li><li>作品来源: 游戏</li><li>动漫地区: 日本</li><li>款式: 静态</li><li>ACG作品名: 怪物猎人</li></ul>', 433.00, 123, 0, '/goods/2021-11-15/1636977878808.jpg', 1, 0, '2021-11-15 20:04:47', '2021-11-15 20:04:47');
INSERT INTO `goods` VALUES (26, '魔兽世界希尔瓦娜斯', 24, '<p>品牌名称：<strong>暴雪</strong></p><p style=\"text-align:start;text-indent:2em;\" class=\"attr-list-hd tm-clear\"><em>产品参数：</em></p><ul><li>品牌: 暴雪</li><li>型号: BXYS001</li><li>适用年龄: 14周岁以上</li><li>尺寸: 大号</li><li>热门系列: 树脂雕像手办模型</li><li>颜色分类: 希尔瓦娜斯Sylvanas</li><li>出售状态: 现货</li><li>货号: BXBX21FK5023220CE</li><li>动漫地区: 美国</li><li>ACG作品名: 魔兽世界希尔瓦娜斯·风行者S</li></ul>', 3322.00, 31, 0, '/goods/2021-11-15/1636977994056.jpg', 1, 0, '2021-11-15 20:06:48', '2021-11-15 20:06:48');
INSERT INTO `goods` VALUES (27, '魔兽世界吉安娜', 24, '<p>品牌名称：<strong>暴雪</strong></p><p style=\"text-align:start;text-indent:2em;\" class=\"attr-list-hd tm-clear\"><em>产品参数：</em></p><ul><li>品牌: 暴雪</li><li>型号: BXYS002</li><li>适用年龄: 14周岁以上</li><li>尺寸: 大号</li><li>热门系列: 树脂雕像手办模型</li><li>颜色分类: 吉安娜Jaina</li><li>出售状态: 现货</li><li>货号: BXBX21FK5023419CD</li><li>动漫地区: 美国</li><li>ACG作品名: 魔兽世界吉安娜·普罗德摩尔J</li></ul>', 3211.00, 41, 0, '/goods/2021-11-15/1636978060623.jpg', 1, 0, '2021-11-15 20:07:47', '2021-11-15 20:07:47');
INSERT INTO `goods` VALUES (28, '守望先锋美', 23, '<p style=\"text-align:start;text-indent:2em;\" class=\"attr-list-hd tm-clear\"><em>产品参数：</em></p><ul><li>品牌: 暴雪</li><li>型号: BXYS005</li><li>适用年龄: 14周岁以上</li><li>尺寸: 美雕像</li><li>热门系列: 树脂雕像手办模型</li><li>颜色分类: 美Mei</li><li>出售状态: 现货</li><li>货号: BXYS005</li><li>动漫地区: 美国</li><li>ACG作品名: 守望先锋周美灵Mei</li></ul><p></p><div class=\"media-wrap image-wrap\"><img src=\"https://player-one.oss-cn-beijing.aliyuncs.com/goods/2021-11-15/1636978184427.jpg\"/></div><p></p>', 1820.00, 44, 0, '/goods/2021-11-15/1636978151911.jpg', 1, 0, '2021-11-15 20:09:47', '2021-11-15 20:09:47');
INSERT INTO `goods` VALUES (29, 'Monster Hunter怪物猎人限定版历战王灭尽龙', 8, '<ul><li>品牌: capcom</li><li>型号: Nergigante</li><li>适用年龄: 14周岁以上</li><li>颜色分类: Nergigante历战王（已到货） 巨大灭尽龙</li><li>出售状态: 预售</li><li>作品来源: 游戏</li><li>动漫地区: 日本</li><li>款式: 静态</li><li>ACG作品名: 怪物猎人</li></ul><p></p><div class=\"media-wrap image-wrap\"><img src=\"https://player-one.oss-cn-beijing.aliyuncs.com/goods/2021-11-18/1637235719676.jpg\"/></div><p></p><div class=\"media-wrap image-wrap\"><img src=\"https://player-one.oss-cn-beijing.aliyuncs.com/goods/2021-11-18/1637235722269.png\"/></div><p></p>', 1088.00, 41, 0, '/goods/2021-11-18/1637235673851.jpg', 0, 0, '2021-11-18 19:42:08', '2021-11-18 19:42:08');
INSERT INTO `goods` VALUES (30, 'Spark Thinker Studio 怪物猎人 怪猎 雷狼龙 头部雕像 可挂墙', 8, '<p><span style=\"color:#000000\"><span style=\"font-size:14px\"><span style=\"background-color:#ffffff\">团队：SPARK THINKER STUDIO</span></span></span></p><p><span style=\"color:#000000\"><span style=\"font-size:14px\"><span style=\"background-color:#ffffff\">作品：怪物猎人系列第二弹----雷狼龙头部雕像</span></span></span></p><p><span style=\"color:#000000\"><span style=\"font-size:14px\"><span style=\"background-color:#ffffff\">尺寸：46*32*46（cm）</span></span></span></p><p><span style=\"color:#000000\"><span style=\"font-size:14px\"><span style=\"background-color:#ffffff\">材质：宝丽石+手工喷涂</span></span></span></p><p><span style=\"color:#000000\"><span style=\"font-size:14px\"><span style=\"background-color:#ffffff\">配置：本体+挂墙+摆放支架</span></span></span></p><p><span style=\"color:#000000\"><span style=\"font-size:14px\"><span style=\"background-color:#ffffff\">体数：398体（分两批，第一批199体）</span></span></span></p><p><span style=\"color:#000000\"><span style=\"font-size:14px\"><span style=\"background-color:#ffffff\">价格：第一批预定总价1380元，定金500元（10月出货）</span></span></span></p><p><span style=\"color:#000000\"><span style=\"font-size:14px\"><span style=\"background-color:#ffffff\">           第二批预定1480元，订金600元（出货时间待定）</span></span></span></p><p><span style=\"color:#000000\"><span style=\"font-size:14px\"><span style=\"background-color:#ffffff\">预计出货时间：2021年10月</span></span></span></p><p><span style=\"color:#000000\"><span style=\"font-size:14px\"><span style=\"background-color:#ffffff\">配送方式：德邦或顺丰保价到付</span></span></span></p><p><span style=\"color:#000000\"><span style=\"font-size:14px\"><span style=\"background-color:#ffffff\">特别福利：眼睛及其他部分采用特殊图层处理，特殊条件下有颜色变化。接近游戏中雷狼龙愤怒的状态。采用仿真毛发处理特殊部位，角部与头部磁吸链接。</span></span></span></p><p></p><div class=\"media-wrap image-wrap\"><img src=\"https://player-one.oss-cn-beijing.aliyuncs.com/goods/2021-11-18/1637235790482.png\"/></div><p></p>', 433.00, 21, 0, '/goods/2021-11-18/1637235781540.png', 0, 0, '2021-11-18 19:43:16', '2021-11-18 19:43:16');

-- ----------------------------
-- Table structure for goods_category
-- ----------------------------
DROP TABLE IF EXISTS `goods_category`;
CREATE TABLE `goods_category`  (
  `id` int(0) NOT NULL AUTO_INCREMENT,
  `pid` int(0) NOT NULL DEFAULT 0,
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `enable` tinyint(0) NOT NULL DEFAULT 1,
  `sort` int(0) NOT NULL DEFAULT 0,
  `create_time` timestamp(0) NULL DEFAULT NULL,
  `update_time` timestamp(0) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 222 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of goods_category
-- ----------------------------
INSERT INTO `goods_category` VALUES (1, 0, '育碧', 1, 6, '2021-09-23 09:21:10', '2021-09-23 09:21:10');
INSERT INTO `goods_category` VALUES (2, 0, '拳头', 1, 1, '2021-09-23 09:21:10', '2021-09-23 09:21:10');
INSERT INTO `goods_category` VALUES (3, 0, '万代', 1, 3, '2021-09-23 09:21:10', '2021-09-23 09:21:10');
INSERT INTO `goods_category` VALUES (4, 0, '卡普空', 1, 0, '2021-09-23 09:21:10', '2021-09-23 09:21:10');
INSERT INTO `goods_category` VALUES (5, 0, '任天堂', 1, 5, '2021-09-23 09:21:10', '2021-09-23 09:21:10');
INSERT INTO `goods_category` VALUES (6, 2, '英雄联盟', 1, 2, '2021-09-23 09:21:10', '2021-09-23 09:21:10');
INSERT INTO `goods_category` VALUES (7, 3, '黑暗之魂', 1, 1, '2021-09-23 09:21:10', '2021-09-23 09:21:10');
INSERT INTO `goods_category` VALUES (8, 4, '怪物猎人', 1, 1, '2021-09-23 09:21:10', '2021-09-23 09:21:10');
INSERT INTO `goods_category` VALUES (9, 4, '生化危机', 1, 2, '2021-09-23 09:21:10', '2021-12-27 21:35:40');
INSERT INTO `goods_category` VALUES (10, 5, '口袋妖怪', 1, 1, '2021-09-23 09:21:10', '2021-09-23 09:21:10');
INSERT INTO `goods_category` VALUES (11, 5, '马里奥系列', 1, 2, '2021-09-23 09:21:10', '2021-09-23 09:21:10');
INSERT INTO `goods_category` VALUES (20, 1, '彩虹六号', 1, 0, '2021-11-09 18:56:35', '2021-11-09 19:33:12');
INSERT INTO `goods_category` VALUES (21, 1, '刺客信条', 1, 0, '2021-11-15 19:40:43', '2021-11-15 19:40:43');
INSERT INTO `goods_category` VALUES (22, 0, '暴雪', 1, 0, '2021-11-15 20:01:59', '2021-11-15 20:01:59');
INSERT INTO `goods_category` VALUES (23, 22, '守望先锋', 1, 0, '2021-11-15 20:02:17', '2021-11-15 20:02:17');
INSERT INTO `goods_category` VALUES (24, 22, '魔兽世界', 1, 0, '2021-11-15 20:02:26', '2021-11-15 20:02:26');

-- ----------------------------
-- Table structure for goods_collection
-- ----------------------------
DROP TABLE IF EXISTS `goods_collection`;
CREATE TABLE `goods_collection`  (
  `id` int(0) NOT NULL AUTO_INCREMENT,
  `goods_id` int(0) NOT NULL,
  `user_id` int(0) NOT NULL,
  `create_time` timestamp(0) NOT NULL,
  `is_delete` tinyint(0) NOT NULL DEFAULT 0,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 13 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of goods_collection
-- ----------------------------
INSERT INTO `goods_collection` VALUES (11, 1, 2, '2021-11-19 13:16:43', 0);
INSERT INTO `goods_collection` VALUES (12, 2, 2, '2021-11-20 08:44:46', 0);
INSERT INTO `goods_collection` VALUES (13, 28, 2, '2021-11-23 17:55:03', 0);

-- ----------------------------
-- Table structure for goods_order
-- ----------------------------
DROP TABLE IF EXISTS `goods_order`;
CREATE TABLE `goods_order`  (
  `id` int(0) NOT NULL AUTO_INCREMENT,
  `user_id` int(0) NOT NULL,
  `price` decimal(10, 2) NOT NULL,
  `state` tinyint(0) NOT NULL COMMENT '0：代表未付款 1：代表未发货 2：代表未确认收货 3：已完成订单',
  `create_time` timestamp(0) NOT NULL,
  `pay_time` timestamp(0) NULL DEFAULT NULL,
  `deliver_time` datetime(0) NULL DEFAULT NULL,
  `receive_time` timestamp(0) NULL DEFAULT NULL,
  `comment_time` timestamp(0) NULL DEFAULT NULL,
  `finish_time` timestamp(0) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of goods_order
-- ----------------------------
INSERT INTO `goods_order` VALUES (1, 1, 22.00, 100, '2021-12-11 18:49:27', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `goods_order` VALUES (2, 10, 22.00, 100, '2021-12-11 18:49:38', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `goods_order` VALUES (3, 2, 23.00, 100, '2021-12-11 19:43:58', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `goods_order` VALUES (4, 2, 21123.00, 101, '2021-12-11 19:44:15', '2021-12-11 20:36:04', NULL, NULL, NULL, NULL);
INSERT INTO `goods_order` VALUES (5, 2, 21123.00, 100, '2021-12-11 19:44:18', NULL, NULL, NULL, NULL, NULL);

-- ----------------------------
-- Table structure for goods_order_goods
-- ----------------------------
DROP TABLE IF EXISTS `goods_order_goods`;
CREATE TABLE `goods_order_goods`  (
  `id` int(0) NOT NULL AUTO_INCREMENT,
  `order_id` int(0) NOT NULL,
  `goods_id` int(0) NOT NULL,
  `num` int(0) NOT NULL,
  `cur_price` decimal(10, 2) NOT NULL,
  `title` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `pic_url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `is_cancel` tinyint(0) NOT NULL DEFAULT 0,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of goods_order_goods
-- ----------------------------

-- ----------------------------
-- Table structure for inset
-- ----------------------------
DROP TABLE IF EXISTS `inset`;
CREATE TABLE `inset`  (
  `id` int(0) NOT NULL AUTO_INCREMENT,
  `goods_id` int(0) NOT NULL,
  `url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `sort` int(0) NULL DEFAULT 0,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 7 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of inset
-- ----------------------------
INSERT INTO `inset` VALUES (1, 1, '/goods/inset/阿斯特拉的高级骑士 奥斯卡.jpg', 0);
INSERT INTO `inset` VALUES (2, 1, '/goods/inset/71l4WynDoUL._AC_SL1000_.jpg', 0);
INSERT INTO `inset` VALUES (4, 28, '/goods/inset/O1CN016zELVl1oiD4Fl0yhK_%21%212200847155258.jpg', 0);
INSERT INTO `inset` VALUES (5, 28, '/goods/inset/O1CN017LsNpw1oiD4JXsWYl_!!2200847155258.jpg', 0);
INSERT INTO `inset` VALUES (6, 28, '/goods/inset/O1CN01Onn3WQ1oiD4GMCpuT_!!2200847155258.jpg', 0);
INSERT INTO `inset` VALUES (7, 28, '/goods/inset/O1CN01yHsOZw1oiD4G2Hddy_!!2200847155258.jpg', 0);

-- ----------------------------
-- Table structure for menu
-- ----------------------------
DROP TABLE IF EXISTS `menu`;
CREATE TABLE `menu`  (
  `id` bigint(0) NOT NULL AUTO_INCREMENT,
  `menu_pid` bigint(0) NOT NULL COMMENT '父菜单id',
  `menu_pids` varchar(128) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL COMMENT '当前菜单所有父菜单',
  `is_leaf` bit(1) NOT NULL COMMENT '0:不是叶子节点 1是',
  `menu_name` varchar(16) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  `url` varchar(64) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL,
  `icon` varchar(45) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL,
  `sort` int(0) NULL DEFAULT NULL COMMENT '排序',
  `level` int(0) NOT NULL COMMENT '菜单等级',
  `status` bit(1) NOT NULL COMMENT '0启用1禁用',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = MyISAM AUTO_INCREMENT = 11 CHARACTER SET = utf8 COLLATE = utf8_unicode_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of menu
-- ----------------------------
INSERT INTO `menu` VALUES (1, 0, '[0]', b'0', '首页', '/', 'el-icon-star-off', 1, 1, b'0');
INSERT INTO `menu` VALUES (2, 1, '[0],[1]', b'0', '系统管理', '/users', 'el-icon-setting  ', 1, 2, b'0');
INSERT INTO `menu` VALUES (3, 1, '[0],[1]', b'1', '日志管理', '/syslog', 'el-icon-date', 2, 2, b'0');
INSERT INTO `menu` VALUES (5, 2, '[1],[2]', b'1', '商品管理', '/syscommodity', 'el-icon-goods', 4, 3, b'0');
INSERT INTO `menu` VALUES (10, 2, '[1],[2]', b'1', '角色管理', '/role', 'el-icon-view', 1, 3, b'0');
INSERT INTO `menu` VALUES (6, 2, '[1],[2]', b'1', '用户管理', '/users', 'el-icon-user', 1, 3, b'0');
INSERT INTO `menu` VALUES (7, 1, '[0]', b'1', '系统设置', '/sysset', 'el-icon-info', 1000, 1, b'0');
INSERT INTO `menu` VALUES (8, 1, '[0]', b'1', 'csrfce', '/helloCSRF', 'el-icon-remove-outline', 1000, 1, b'0');
INSERT INTO `menu` VALUES (9, 2, '[1],[2]', b'1', '菜单管理', '/menu', 'el-icon-menu', 2, 3, b'0');

-- ----------------------------
-- Table structure for promotion
-- ----------------------------
DROP TABLE IF EXISTS `promotion`;
CREATE TABLE `promotion`  (
  `id` int(0) NOT NULL,
  `goods_id` int(0) NOT NULL,
  `deadline` timestamp(0) NOT NULL,
  `create_time` timestamp(0) NOT NULL,
  `promotion_price` int(0) NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of promotion
-- ----------------------------

-- ----------------------------
-- Table structure for role
-- ----------------------------
DROP TABLE IF EXISTS `role`;
CREATE TABLE `role`  (
  `id` bigint(0) NOT NULL AUTO_INCREMENT,
  `role_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL COMMENT '角色名称（汉字）',
  `role_desr` varchar(128) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  `role_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  `sort` int(0) NOT NULL,
  `status` bit(1) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = MyISAM AUTO_INCREMENT = 5 CHARACTER SET = utf8 COLLATE = utf8_unicode_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of role
-- ----------------------------
INSERT INTO `role` VALUES (1, '管理员', '查看所有', 'admin', 1, b'0');
INSERT INTO `role` VALUES (2, '普通用户', '查看部分', 'common', 2, b'0');
INSERT INTO `role` VALUES (3, '仓库管理员', 'stock管理', 'stockAdmin', 3, b'0');
INSERT INTO `role` VALUES (4, '订单管理员', 'order管理', 'orderAdmin', 4, b'0');

-- ----------------------------
-- Table structure for role_api
-- ----------------------------
DROP TABLE IF EXISTS `role_api`;
CREATE TABLE `role_api`  (
  `role_id` int(0) NOT NULL,
  `api_id` int(0) NOT NULL,
  INDEX `api_id`(`api_id`) USING BTREE
) ENGINE = MyISAM AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_unicode_ci ROW_FORMAT = Fixed;

-- ----------------------------
-- Records of role_api
-- ----------------------------
INSERT INTO `role_api` VALUES (1, 2);
INSERT INTO `role_api` VALUES (1, 1);
INSERT INTO `role_api` VALUES (1, 4);
INSERT INTO `role_api` VALUES (1, 5);
INSERT INTO `role_api` VALUES (2, 5);

-- ----------------------------
-- Table structure for role_menu
-- ----------------------------
DROP TABLE IF EXISTS `role_menu`;
CREATE TABLE `role_menu`  (
  `role_id` int(0) NOT NULL,
  `menu_id` int(0) NULL DEFAULT NULL,
  INDEX `role_id`(`role_id`) USING BTREE,
  INDEX `menu_id`(`menu_id`) USING BTREE
) ENGINE = MyISAM AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_unicode_ci ROW_FORMAT = Fixed;

-- ----------------------------
-- Records of role_menu
-- ----------------------------
INSERT INTO `role_menu` VALUES (1, 1);
INSERT INTO `role_menu` VALUES (1, 2);
INSERT INTO `role_menu` VALUES (1, 3);
INSERT INTO `role_menu` VALUES (1, 4);
INSERT INTO `role_menu` VALUES (1, 5);
INSERT INTO `role_menu` VALUES (2, 5);
INSERT INTO `role_menu` VALUES (2, 4);
INSERT INTO `role_menu` VALUES (2, 1);
INSERT INTO `role_menu` VALUES (1, 8);

-- ----------------------------
-- Table structure for stock
-- ----------------------------
DROP TABLE IF EXISTS `stock`;
CREATE TABLE `stock`  (
  `id` int(0) UNSIGNED NOT NULL AUTO_INCREMENT,
  `name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT ' ' COMMENT '名称',
  `count` int(0) NOT NULL COMMENT '库存',
  `sale` int(0) NOT NULL COMMENT '已售',
  `version` int(0) NOT NULL COMMENT '乐观锁，版本号',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of stock
-- ----------------------------
INSERT INTO `stock` VALUES (1, 'ps5', 100, 84, 84);
INSERT INTO `stock` VALUES (2, 'xbox series x', 200, 42, 42);

-- ----------------------------
-- Table structure for stock_order
-- ----------------------------
DROP TABLE IF EXISTS `stock_order`;
CREATE TABLE `stock_order`  (
  `id` int(0) UNSIGNED NOT NULL AUTO_INCREMENT,
  `sid` int(0) NOT NULL COMMENT '库存id',
  `name` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '商品名称',
  `create_time` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 108 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of stock_order
-- ----------------------------
INSERT INTO `stock_order` VALUES (1, 1, 'ps5', '2021-10-06 11:14:28');
INSERT INTO `stock_order` VALUES (2, 1, 'ps5', '2021-10-06 11:14:28');
INSERT INTO `stock_order` VALUES (3, 1, 'ps5', '2021-10-06 11:14:28');
INSERT INTO `stock_order` VALUES (4, 1, 'ps5', '2021-10-06 11:14:28');
INSERT INTO `stock_order` VALUES (5, 1, 'ps5', '2021-10-06 11:14:29');
INSERT INTO `stock_order` VALUES (6, 1, 'ps5', '2021-10-06 11:14:29');
INSERT INTO `stock_order` VALUES (7, 1, 'ps5', '2021-10-06 11:14:29');
INSERT INTO `stock_order` VALUES (8, 1, 'ps5', '2021-10-06 11:14:30');
INSERT INTO `stock_order` VALUES (9, 1, 'ps5', '2021-10-06 11:14:30');
INSERT INTO `stock_order` VALUES (10, 1, 'ps5', '2021-10-06 11:14:30');
INSERT INTO `stock_order` VALUES (11, 1, 'ps5', '2021-10-06 11:14:30');
INSERT INTO `stock_order` VALUES (12, 1, 'ps5', '2021-10-06 11:14:30');
INSERT INTO `stock_order` VALUES (13, 1, 'ps5', '2021-10-06 11:14:31');
INSERT INTO `stock_order` VALUES (14, 1, 'ps5', '2021-10-06 11:14:31');
INSERT INTO `stock_order` VALUES (15, 1, 'ps5', '2021-10-06 11:14:31');
INSERT INTO `stock_order` VALUES (16, 1, 'ps5', '2021-10-06 11:14:31');
INSERT INTO `stock_order` VALUES (17, 1, 'ps5', '2021-10-06 11:14:31');
INSERT INTO `stock_order` VALUES (18, 1, 'ps5', '2021-10-06 11:14:32');
INSERT INTO `stock_order` VALUES (19, 1, 'ps5', '2021-10-06 11:14:32');
INSERT INTO `stock_order` VALUES (20, 1, 'ps5', '2021-10-06 11:14:32');
INSERT INTO `stock_order` VALUES (21, 1, 'ps5', '2021-10-06 11:14:32');
INSERT INTO `stock_order` VALUES (22, 1, 'ps5', '2021-10-06 11:14:33');
INSERT INTO `stock_order` VALUES (23, 1, 'ps5', '2021-10-06 11:14:33');
INSERT INTO `stock_order` VALUES (24, 1, 'ps5', '2021-10-06 11:14:33');
INSERT INTO `stock_order` VALUES (25, 1, 'ps5', '2021-10-06 11:15:03');
INSERT INTO `stock_order` VALUES (26, 1, 'ps5', '2021-10-06 11:15:03');
INSERT INTO `stock_order` VALUES (27, 1, 'ps5', '2021-10-06 11:15:04');
INSERT INTO `stock_order` VALUES (28, 1, 'ps5', '2021-10-06 11:15:04');
INSERT INTO `stock_order` VALUES (29, 1, 'ps5', '2021-10-06 11:15:05');
INSERT INTO `stock_order` VALUES (30, 1, 'ps5', '2021-10-06 11:15:05');
INSERT INTO `stock_order` VALUES (31, 1, 'ps5', '2021-10-06 11:15:05');
INSERT INTO `stock_order` VALUES (32, 1, 'ps5', '2021-10-06 11:15:05');
INSERT INTO `stock_order` VALUES (33, 1, 'ps5', '2021-10-06 11:15:05');
INSERT INTO `stock_order` VALUES (34, 1, 'ps5', '2021-10-06 11:15:05');
INSERT INTO `stock_order` VALUES (35, 1, 'ps5', '2021-10-06 11:15:06');
INSERT INTO `stock_order` VALUES (36, 1, 'ps5', '2021-10-06 11:15:06');
INSERT INTO `stock_order` VALUES (37, 1, 'ps5', '2021-10-06 11:15:06');
INSERT INTO `stock_order` VALUES (38, 1, 'ps5', '2021-10-06 11:15:06');
INSERT INTO `stock_order` VALUES (39, 1, 'ps5', '2021-10-06 11:15:06');
INSERT INTO `stock_order` VALUES (40, 1, 'ps5', '2021-10-06 11:15:06');
INSERT INTO `stock_order` VALUES (41, 1, 'ps5', '2021-10-06 11:15:07');
INSERT INTO `stock_order` VALUES (42, 1, 'ps5', '2021-10-06 11:15:07');
INSERT INTO `stock_order` VALUES (43, 1, 'ps5', '2021-10-06 11:15:07');
INSERT INTO `stock_order` VALUES (44, 1, 'ps5', '2021-10-06 11:15:07');
INSERT INTO `stock_order` VALUES (45, 1, 'ps5', '2021-10-06 11:17:57');
INSERT INTO `stock_order` VALUES (46, 1, 'ps5', '2021-10-06 11:17:57');
INSERT INTO `stock_order` VALUES (47, 1, 'ps5', '2021-10-06 11:17:57');
INSERT INTO `stock_order` VALUES (48, 1, 'ps5', '2021-10-06 11:17:57');
INSERT INTO `stock_order` VALUES (49, 1, 'ps5', '2021-10-06 11:17:57');
INSERT INTO `stock_order` VALUES (50, 1, 'ps5', '2021-10-06 11:17:57');
INSERT INTO `stock_order` VALUES (51, 1, 'ps5', '2021-10-06 11:17:57');
INSERT INTO `stock_order` VALUES (52, 1, 'ps5', '2021-10-06 11:17:57');
INSERT INTO `stock_order` VALUES (53, 1, 'ps5', '2021-10-06 11:17:58');
INSERT INTO `stock_order` VALUES (54, 1, 'ps5', '2021-10-06 11:17:58');
INSERT INTO `stock_order` VALUES (55, 1, 'ps5', '2021-10-06 11:17:59');
INSERT INTO `stock_order` VALUES (56, 1, 'ps5', '2021-10-06 11:17:59');
INSERT INTO `stock_order` VALUES (57, 1, 'ps5', '2021-10-06 11:17:59');
INSERT INTO `stock_order` VALUES (58, 1, 'ps5', '2021-10-06 11:17:59');
INSERT INTO `stock_order` VALUES (59, 1, 'ps5', '2021-10-06 11:18:00');
INSERT INTO `stock_order` VALUES (60, 1, 'ps5', '2021-10-06 11:18:00');
INSERT INTO `stock_order` VALUES (61, 1, 'ps5', '2021-10-06 11:18:00');
INSERT INTO `stock_order` VALUES (62, 1, 'ps5', '2021-10-06 11:18:00');
INSERT INTO `stock_order` VALUES (63, 1, 'ps5', '2021-10-06 11:18:00');
INSERT INTO `stock_order` VALUES (64, 1, 'ps5', '2021-10-06 11:18:00');
INSERT INTO `stock_order` VALUES (65, 1, 'ps5', '2021-10-06 11:18:00');
INSERT INTO `stock_order` VALUES (66, 1, 'ps5', '2021-10-06 11:18:01');
INSERT INTO `stock_order` VALUES (67, 1, 'ps5', '2021-10-06 11:18:01');
INSERT INTO `stock_order` VALUES (68, 1, 'ps5', '2021-10-06 11:18:01');
INSERT INTO `stock_order` VALUES (69, 1, 'ps5', '2021-10-06 11:18:01');
INSERT INTO `stock_order` VALUES (70, 1, 'ps5', '2021-10-06 11:18:01');
INSERT INTO `stock_order` VALUES (71, 1, 'ps5', '2021-10-06 11:18:02');
INSERT INTO `stock_order` VALUES (72, 1, 'ps5', '2021-10-06 11:18:02');
INSERT INTO `stock_order` VALUES (73, 1, 'ps5', '2021-10-06 11:18:02');
INSERT INTO `stock_order` VALUES (74, 1, 'ps5', '2021-10-06 11:18:03');
INSERT INTO `stock_order` VALUES (75, 1, 'ps5', '2021-10-06 11:18:03');
INSERT INTO `stock_order` VALUES (76, 1, 'ps5', '2021-10-06 11:18:03');
INSERT INTO `stock_order` VALUES (77, 1, 'ps5', '2021-10-06 11:18:03');
INSERT INTO `stock_order` VALUES (78, 1, 'ps5', '2021-10-06 11:18:03');
INSERT INTO `stock_order` VALUES (79, 1, 'ps5', '2021-10-06 11:18:03');
INSERT INTO `stock_order` VALUES (80, 1, 'ps5', '2021-10-06 11:18:04');
INSERT INTO `stock_order` VALUES (81, 1, 'ps5', '2021-10-06 11:18:04');
INSERT INTO `stock_order` VALUES (82, 1, 'ps5', '2021-10-06 11:18:04');
INSERT INTO `stock_order` VALUES (83, 1, 'ps5', '2021-10-06 11:18:05');
INSERT INTO `stock_order` VALUES (84, 1, 'ps5', '2021-10-06 11:18:05');
INSERT INTO `stock_order` VALUES (85, 1, 'ps5', '2021-10-06 11:18:05');
INSERT INTO `stock_order` VALUES (86, 1, 'ps5', '2021-10-06 11:18:05');
INSERT INTO `stock_order` VALUES (87, 1, 'ps5', '2021-10-06 11:18:05');
INSERT INTO `stock_order` VALUES (88, 1, 'ps5', '2021-10-06 11:18:05');
INSERT INTO `stock_order` VALUES (89, 1, 'ps5', '2021-10-06 11:18:06');
INSERT INTO `stock_order` VALUES (90, 1, 'ps5', '2021-10-06 11:18:06');
INSERT INTO `stock_order` VALUES (91, 1, 'ps5', '2021-10-06 11:18:06');
INSERT INTO `stock_order` VALUES (92, 1, 'ps5', '2021-10-06 11:27:59');
INSERT INTO `stock_order` VALUES (93, 1, 'ps5', '2021-10-06 11:28:00');
INSERT INTO `stock_order` VALUES (94, 1, 'ps5', '2021-10-06 11:28:01');
INSERT INTO `stock_order` VALUES (95, 1, 'ps5', '2021-10-06 11:28:02');
INSERT INTO `stock_order` VALUES (96, 1, 'ps5', '2021-10-06 11:28:02');
INSERT INTO `stock_order` VALUES (97, 1, 'ps5', '2021-10-06 11:28:02');
INSERT INTO `stock_order` VALUES (98, 1, 'ps5', '2021-10-06 11:28:02');
INSERT INTO `stock_order` VALUES (99, 1, 'ps5', '2021-10-06 11:28:02');
INSERT INTO `stock_order` VALUES (100, 1, 'ps5', '2021-10-06 11:28:02');
INSERT INTO `stock_order` VALUES (101, 1, 'ps5', '2021-10-06 11:28:02');
INSERT INTO `stock_order` VALUES (102, 1, 'ps5', '2021-10-06 11:28:02');
INSERT INTO `stock_order` VALUES (103, 1, 'ps5', '2021-10-06 11:28:03');
INSERT INTO `stock_order` VALUES (104, 1, 'ps5', '2021-10-06 11:28:03');
INSERT INTO `stock_order` VALUES (105, 1, 'ps5', '2021-10-06 11:28:03');
INSERT INTO `stock_order` VALUES (106, 1, 'ps5', '2021-10-06 11:28:03');
INSERT INTO `stock_order` VALUES (107, 1, 'ps5', '2021-10-06 11:28:03');
INSERT INTO `stock_order` VALUES (108, 1, 'ps5', '2021-10-06 11:28:04');

-- ----------------------------
-- Table structure for token
-- ----------------------------
DROP TABLE IF EXISTS `token`;
CREATE TABLE `token`  (
  `id` int(0) NOT NULL,
  `JWTHeaderName` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `Expiration` date NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = MyISAM AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of token
-- ----------------------------

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `id` bigint(0) NOT NULL AUTO_INCREMENT,
  `avatar_url` varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL,
  `username` varchar(64) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  `password` varchar(64) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  `enable` tinyint(1) NULL DEFAULT 1,
  `gender` tinyint(0) NULL DEFAULT NULL,
  `phone_number` varchar(16) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL,
  `dept_id` int(0) NULL DEFAULT NULL,
  `email` varchar(32) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL,
  `update_time` timestamp(0) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(0),
  `create_time` timestamp(0) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `username`(`username`) USING BTREE,
  UNIQUE INDEX `email`(`email`) USING BTREE,
  UNIQUE INDEX `phone_number`(`phone_number`) USING BTREE
) ENGINE = MyISAM AUTO_INCREMENT = 16 CHARACTER SET = utf8 COLLATE = utf8_unicode_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES (1, 'https://player-one.oss-cn-beijing.aliyuncs.com/goods/2021-11-08/1636356437029.jpg', 'admin', '$2a$10$ECXj2f30G31aoxVrYYgxHenUHUm67e21xmCGPyZDnn8Jddqx85DzW', 1, 1, '123456879', 1, 'hahahah', '2021-11-16 20:37:10', '2021-02-01 13:32:29');
INSERT INTO `user` VALUES (2, 'https://player-one.oss-cn-beijing.aliyuncs.com/avatar/2021-12-10/uBRvdgyMHwiMQMvHi9ze.jpg', 'user', '$2a$10$ECXj2f30G31aoxVrYYgxHenUHUm67e21xmCGPyZDnn8Jddqx85DzW', 1, 1, '12312389262', 2, 'aa22@aasaa.com', '2021-12-10 15:00:32', '2021-04-01 13:32:38');
INSERT INTO `user` VALUES (3, 'https://player-one.oss-cn-beijing.aliyuncs.com/goods/2021-11-08/1636356437029.jpg', 'user2', '$2a$10$ECXj2f30G31aoxVrYYgxHenUHUm67e21xmCGPyZDnn8Jddqx85DzW', 1, 1, '3232', 3, 'asdf@ads.com', '2021-11-16 20:37:22', '2021-10-01 13:32:44');
INSERT INTO `user` VALUES (4, NULL, 'user3', '$2a$10$ECXj2f30G31aoxVrYYgxHenUHUm67e21xmCGPyZDnn8Jddqx85DzW', 1, 0, '1223112', 1, 'sadaddsa', '2021-11-14 11:00:22', '2021-09-08 13:32:47');
INSERT INTO `user` VALUES (8, NULL, 'user8', '$2a$10$ECXj2f30G31aoxVrYYgxHenUHUm67e21xmCGPyZDnn8Jddqx85DzW', 1, 0, '1223112121', 1, 'sadaddsa@QQ.COM', '2021-11-14 11:00:23', '2021-09-08 13:32:47');
INSERT INTO `user` VALUES (5, NULL, 'user4', '$2a$10$ECXj2f30G31aoxVrYYgxHenUHUm67e21xmCGPyZDnn8Jddqx85DzW', 0, 2, '1223122', 1, 'sad', '2021-11-14 14:27:34', '2021-09-08 13:32:47');
INSERT INTO `user` VALUES (6, NULL, 'user5', '$2a$10$ECXj2f30G31aoxVrYYgxHenUHUm67e21xmCGPyZDnn8Jddqx85DzW', 0, 2, '1223154', 1, 'AAA', '2021-11-14 14:27:35', '2021-09-08 13:32:47');
INSERT INTO `user` VALUES (7, NULL, 'user7', '$2a$10$ECXj2f30G31aoxVrYYgxHenUHUm67e21xmCGPyZDnn8Jddqx85DzW', 1, 2, '12231443', 1, 'SAAS', '2021-11-14 14:27:36', '2021-09-08 13:32:47');
INSERT INTO `user` VALUES (9, NULL, 'admin1', 'admin', 1, NULL, NULL, NULL, '123123@qq.com', '2021-10-23 14:49:11', '2021-10-23 14:49:11');
INSERT INTO `user` VALUES (10, NULL, 'asdf', 'asdfasd', 0, NULL, NULL, NULL, 'asd@ad.com', '2021-10-25 20:01:16', '2021-10-23 15:00:12');
INSERT INTO `user` VALUES (11, NULL, 'asd', 'asdf', 0, NULL, NULL, NULL, 'ads@qq.com', '2021-10-25 20:01:15', '2021-10-23 15:28:48');
INSERT INTO `user` VALUES (12, NULL, '121212', '12121212', 1, NULL, NULL, NULL, '1231233@qq.com', '2021-11-14 11:00:09', '2021-10-23 15:30:54');
INSERT INTO `user` VALUES (13, NULL, '222', '121212', 1, NULL, NULL, NULL, '222@111.com', '2021-10-23 15:36:30', '2021-10-23 15:36:30');
INSERT INTO `user` VALUES (14, NULL, '1212122', '12121212', 1, NULL, NULL, NULL, '3', '2021-11-14 11:00:11', '2021-10-23 18:14:00');
INSERT INTO `user` VALUES (15, NULL, 'admin2', 'admin', 1, NULL, NULL, NULL, '121@as.com', '2021-10-29 17:42:18', '2021-10-29 17:42:18');

-- ----------------------------
-- Table structure for user_role
-- ----------------------------
DROP TABLE IF EXISTS `user_role`;
CREATE TABLE `user_role`  (
  `user_id` bigint(0) NULL DEFAULT NULL,
  `role_id` bigint(0) NULL DEFAULT NULL
) ENGINE = MyISAM AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_unicode_ci ROW_FORMAT = Fixed;

-- ----------------------------
-- Records of user_role
-- ----------------------------
INSERT INTO `user_role` VALUES (1, 1);
INSERT INTO `user_role` VALUES (2, 2);
INSERT INTO `user_role` VALUES (3, 2);
INSERT INTO `user_role` VALUES (4, 2);

SET FOREIGN_KEY_CHECKS = 1;

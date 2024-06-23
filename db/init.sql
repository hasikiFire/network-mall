CREATE TABLE `user` (
  -- 主键
  `id` INT unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  -- 用户ID
  `user_id` INT NOT NULL UNIQUE COMMENT '用户ID',
  -- 名字
  `name` VARCHAR(255) NOT NULL COMMENT '名字',
  -- 邮箱
  `email` VARCHAR(64) NOT NULL COMMENT '邮箱',
  `password_hash` VARCHAR(255) NOT NULL COMMENT '哈希值',
  `salt` VARCHAR(255) NOT NULL COMMENT '哈希盐值',
  -- 状态 1 正常 0 无效
  `status` tinyint(2) NOT NULL COMMENT '状态 1 正常 0 无效 2 已禁用（触发审计规则）',
  -- 邀请人ID
  `inviter_user_id` INT COMMENT '邀请人ID',
  -- 邀请代码
  `invite_code` VARCHAR(255) COMMENT '邀请代码',
  created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `deleted` tinyint(2) DEFAULT '0' COMMENT '是否已删除 1：已删除 0：未删除',
  PRIMARY KEY (`id`)
) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4 COMMENT = '用户表';

CREATE TABLE `roles` (
  `id` INT AUTO_INCREMENT PRIMARY KEY,
  `user_id` INT NOT NULL UNIQUE COMMENT '用户ID',
  `role_name` varchar(16) NOT NULL COMMENT 'admin:管理员，user（普通用户）'
);

CREATE TABLE package (
  `id` INT unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  package_id INT NOT NULL COMMENT '套餐主键',
  package_name VARCHAR(100) NOT NULL COMMENT '套餐名称',
  package_desc VARCHAR(100) COMMENT '套餐描述',
  original_price decimal(10, 6) NOT NULL COMMENT '商品原价',
  sale_price decimal(10, 6) NOT NULL DEFAULT '0.000000' COMMENT '商品销售价',
  discount DECIMAL(5, 2) COMMENT '折扣百分比',
  discount_start_date TIMESTAMP COMMENT '折扣开始日期',
  discount_end_date TIMESTAMP COMMENT '折扣结束日期',
  data_allowance INT NOT NULL COMMENT '数据流量限额（单位：GB）',
  device_limit INT COMMENT '设备数量限制',
  speed_limit INT COMMENT '速率限制（单位：Mbps）',
  `deleted` tinyint(2) DEFAULT '0' COMMENT '是否已删除 1：已删除 0：未删除',
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (id)
) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4 COMMENT '套餐表';

-- 订单相关-start
CREATE TABLE pay_order (
  `id` INT unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  order_code varchar(50) NOT NULL COMMENT '订单号',
  -- 外键
  user_id INT NOT NULL COMMENT '用户ID',
  -- 外键
  package_id INT NOT NULL COMMENT '套餐计划主键',
  order_create_time TIMESTAMP NOT NULL COMMENT '订单创建日期',
  order_expire_time TIMESTAMP NOT NULL COMMENT '订单过期日期',
  order_status varchar(16) NOT NULL DEFAULT 'wait_pay' COMMENT '订单状态， wait_pay(待支付)、
  paid(已支付)refunding退款中)、refunded(已退款)、closed(订单关闭)',
  order_remark varchar(128) COMMENT '订单备注',
  pay_amount decimal(10, 6) NOT NULL DEFAULT '0.000000' COMMENT '支付金额',
  -- 支付
  pay_time timestamp NOT NULL COMMENT '支付时间',
  pay_way varchar(32) NOT NULL COMMENT '支付方式: wxpay(微信支付)、alipay支付宝支付),USTD(加密货币交易)',
  pay_seene varchar(32) NOT NULL COMMENT 'ONLINE_PAY(在线支付)、QRCODE_SCAN_PAY（扫码支), QRCODE_SHOW_PAY(付款码支付)',
  pay_status varchar(16) NOT NULL DEFAULT 'wait_pay' COMMENT '支付状态， waiting(待支付)、success(支付成功)，failed(支付失败)',
  `platform_coupon_id` varchar(32) DEFAULT NULL COMMENT '平台优惠券ID',
  `platform_coupon_amount` decimal(10, 6) DEFAULT NULL COMMENT '平台优惠券优惠金额',
  `supplier_id` varchar(32) NOT NULL COMMENT '收款商户ID',
  -- 退款 
  `refund_no` timestamp NULL DEFAULT NULL COMMENT '退款单号',
  `refund_req_time` timestamp NULL DEFAULT NULL COMMENT '退款请求时间',
  `refund_time` timestamp NULL DEFAULT NULL COMMENT '退款时间',
  `refund_amount` decimal(10, 6) DEFAULT '0.000000' COMMENT '退款金额',
  refund_status varchar(16) NOT NULL DEFAULT 'wait_pay' COMMENT '退款状态，refunding(退款中)、part_refunded(部分退款)、all_refunded(全部退款)、rejected(已拒绝',
  `deleted` tinyint(2) DEFAULT '0' COMMENT '是否已删除 1：已删除 0：未删除',
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (id),
  KEY `order_code` (`order_code`)
) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4 COMMENT '订单表';

CREATE TABLE `pay_order_tradelog` (
  `id` INT unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `user_id` int unsigned NOT NULL COMMENT '用户ID',
  `user_agent` varchar(512) NOT NULL COMMENT '用户请求代理信息',
  `order_code` varchar(32) NOT NULL COMMENT '业务订单号',
  `supplier_id` varchar(32) NOT NULL COMMENT '商户号',
  `trade_way` varchar(16) NOT NULL COMMENT '交易方式:wxpay(微信交易), alipay(支付宝交易), USTD(加密货币交易)',
  `trade_type` varchar(16) NOT NULL COMMENT '交易类型:facePage(当面付), h5Pay(H5支付)、appPay(APP支付)、nativePay(扫码支付)、unifedPay(公众号支付)、
  refund(退款)、calback(回调)、cancel(撤单)',
  `trade_req_data` text NOT NULL COMMENT '交易请求数据',
  `trade_req_time` TIMESTAMP NOT NULL COMMENT '交易请求时间',
  `trade_resp_data` text NOT NULL COMMENT '交易返回数据',
  `trade_resp_time` TIMESTAMP NOT NULL COMMENT '交易返回时间',
  `create_time` TIMESTAMP NOT NULL COMMENT '数据创建时间',
  `update_time` TIMESTAMP NOT NULL COMMENT '数据修改时间',
  PRIMARY KEY (`id`)
) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4 COMMENT = '支付订单交易记录表';

CREATE TABLE `pay_order_refund` (
  `id` INT unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `user_id` int NOT NULL COMMENT '用户ID',
  `order_code` varchar(32) NOT NULL COMMENT '业务订单号',
  `refund_no` varchar(32) NOT NULL COMMENT '退款单号',
  `refund_amount` decimal(8, 2) unsigned NOT NULL COMMENT '退款金额',
  `refund_req_time` TIMESTAMP NOT NULL COMMENT '退款请求时间',
  `refund_finish_time` TIMESTAMP NOT NULL COMMENT '退款完成时间',
  `create_time` TIMESTAMP NOT NULL COMMENT '数据创建时间',
  `update_time` TIMESTAMP NOT NULL COMMENT '数据最近一次修改时间',
  PRIMARY KEY (`id`)
) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4 COMMENT = '订单退款表';

CREATE TABLE order_snapshot (
  `id` INT unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  order_code varchar(50) NOT NULL COMMENT '订单号',
  package_id INT NOT NULL COMMENT '套餐主键',
  package_name VARCHAR(100) NOT NULL COMMENT '套餐名称',
  package_desc VARCHAR(100) COMMENT '套餐描述',
  package_unit tinyint NOT NULL DEFAULT '0' COMMENT '计费周期，0=每月，1=季度，2=年度',
  original_price decimal(10, 6) NOT NULL COMMENT '商品原价',
  sale_price decimal(10, 6) NOT NULL DEFAULT '0.000000' COMMENT '商品销售价',
  discount DECIMAL(5, 2) COMMENT '折扣百分比',
  discount_start_date TIMESTAMP COMMENT '折扣开始日期',
  discount_end_date TIMESTAMP COMMENT '折扣结束日期',
  data_allowance INT NOT NULL COMMENT '数据流量限额（单位：GB）',
  device_limit INT COMMENT '设备数量限制',
  speed_limit INT COMMENT '速率限制（单位：Mbps）',
  `deleted` tinyint(2) DEFAULT '0' COMMENT '是否已删除 1：已删除 0：未删除',
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (id),
  FOREIGN KEY (order_code) REFERENCES pay_order(order_code)
) COMMENT '订单快照表';

CREATE TABLE usage_record (
  `id` INT unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  purchase_id INT NOT NULL COMMENT '已购套餐ID',
  -- 外键 TODO 需要套餐快照
  package_id INT NOT NULL COMMENT '套餐计划主键',
  -- 外键
  user_id INT NOT NULL COMMENT '用户ID',
  purchase_status INT NOT NULL DEFAULT '0' COMMENT '套餐状态 0:未开始 1：生效中 2：流量已用尽 3：已过期 ',
  purchase_start_time TIMESTAMP NOT NULL COMMENT '开始日期',
  purchase_end_time TIMESTAMP NOT NULL COMMENT '结束日期',
  consumed_data_transfer INT COMMENT '用户已消耗的流量（以GB为单位）',
  device_num INT COMMENT '在线的设备数量',
  subscription_link VARCHAR(255) COMMENT '订阅链接',
  docker_container_name VARCHAR(255) NOT NULL COMMENT 'Docker容器name。格式：用户名称_套餐主键_计划主键',
  created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `deleted` tinyint(2) DEFAULT '0' COMMENT '是否已删除 1：已删除 0：未删除',
  PRIMARY KEY (id)
) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4 COMMENT '用户已购套餐记录表';

-- 订单相关-end
-- 服务器、容器-start
CREATE TABLE foreign_server (
  `id` INT unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  server_id INT NOT NULL COMMENT '服务器的ID',
  server_name VARCHAR(32) NOT NULL COMMENT '服务器的名称',
  supplier VARCHAR(32) NOT NULL COMMENT '服务器的服务商',
  domain_name VARCHAR(255) COMMENT '服务器的域名(会变动)',
  ip_address VARCHAR(32) NOT NULL COMMENT '服务器的IP地址(会变动)',
  start_date TIMESTAMP NOT NULL COMMENT '服务器启动日期',
  monthly_fee decimal(10, 6) NOT NULL COMMENT '每月费用，单位（美元）',
  -- discount DECIMAL(5, 2) COMMENT '折扣百分比',
  -- actual_fee decimal(10, 6) NOT NULL COMMENT '实际费用，单位（美元）',
  total_monthly_data_transfer INT NOT NULL COMMENT '服务器每月的总流量（以GB为单位）',
  consumed_data_transfer INT NOT NULL COMMENT '服务器已消耗的流量（以GB为单位）',
  ---------------------------------------
  operating_system VARCHAR(255) COMMENT '服务器的操作系统',
  cpu_cores INT COMMENT '服务器的CPU核心数',
  ram_gb INT COMMENT '服务器的总RAM大小（以GB为单位）',
  remaining_ram_gb INT COMMENT '服务器剩余的RAM大小（以GB为单位）',
  storage_gb INT COMMENT '服务器的总存储大小（以GB为单位）',
  consumed_storage_gb INT COMMENT '服务器已使用的存储大小（以GB为单位）',
  status INT NOT NULL COMMENT '服务器的状态。 1：活动，2：已暂停',
  `deleted` tinyint(2) DEFAULT '0' COMMENT '是否已删除 1：已删除 0：未删除',
  created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (id)
) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4 COMMENT = '服务器信息表';

CREATE TABLE docker_container (
  `id` INT unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  container_id INT NOT NULL COMMENT '容器的ID',
  container_name VARCHAR(255) NOT NULL COMMENT '容器的名称',
  -- 外键
  server_id INT NOT NULL COMMENT '关联的服务器ID',
  -- 外键
  user_id INT NOT NULL COMMENT '用户ID',
  -- 外键
  package_id INT NOT NULL COMMENT '套餐计划主键。取速度限制，设备限制等信息',
  -- 外键
  purchase_id INT NOT NULL COMMENT '已购套餐主键。取到期时间等信息',
  -- 创建后更新
  bound_port INT NOT NULL COMMENT '与容器绑定的端口',
  `deleted` tinyint(2) DEFAULT '0' COMMENT '是否已删除 1：已删除 0：未删除',
  created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (id)
) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4 COMMENT = 'docker容器信息表';

-- 服务器、容器-end
-- 钱包、返利--start
CREATE TABLE wallets (
  id INT AUTO_INCREMENT PRIMARY KEY COMMENT '主键ID',
  user_id INT NOT NULL COMMENT '用户ID',
  balance DECIMAL(10, 6) NOT NULL COMMENT '余额',
  currency VARCHAR(10) NOT NULL DEFAULT '1' COMMENT '货币类型（1：人民币 2: USDT） ',
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (id)
) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4 COMMENT = '钱包表';

CREATE TABLE promotion_links (
  id INT AUTO_INCREMENT PRIMARY KEY COMMENT '主键ID',
  promotion_code VARCHAR(255) NOT NULL COMMENT '链接代码',
  user_id INT NOT NULL COMMENT '用户ID',
  status TINYINT NOT NULL DEFAULT 1 COMMENT '状态（0：暂停，1：活动）',
  registration_time INT NOT NULL DEFAULT 0 COMMENT '注册成功次数',
  purchase_time INT NOT NULL DEFAULT 0 COMMENT '购买次数',
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (id)
) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4 COMMENT = '推广链接表';

CREATE TABLE rebate_records (
  id INT AUTO_INCREMENT PRIMARY KEY COMMENT '主键ID',
  user_id INT NOT NULL COMMENT '用户ID',
  promotion_code VARCHAR(255) NOT NULL COMMENT '推广链接代码',
  package_id INT NOT NULL COMMENT '套餐ID',
  rebate_amount DECIMAL(10, 6) NOT NULL COMMENT '返利金额',
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (id)
) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4 COMMENT = '返利记录表';

-- 钱包、返利--end
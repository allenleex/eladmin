-- ==================== 资产基础信息表 ====================
DROP TABLE IF EXISTS zjsj_asset;
CREATE TABLE zjsj_asset (
  id BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '资产唯一ID',
  asset_no VARCHAR(50) NOT NULL UNIQUE COMMENT '固定资产编号(RFID/UUID)',
  name VARCHAR(100) NOT NULL COMMENT '资产名称',
  category_code VARCHAR(20) NOT NULL COMMENT '分类编码',
  model VARCHAR(50) COMMENT '规格型号',
  unit VARCHAR(10) COMMENT '计量单位',
  original_value DECIMAL(18,2) COMMENT '资产原值',
  status ENUM('在库','领用','借用','租赁中','维修中','待处置') NOT NULL,
  is_turnover TINYINT(1) DEFAULT 0 COMMENT '是否周转物资',
  storage_area_id BIGINT COMMENT '存放场地ID',
  warehouse_id BIGINT COMMENT '仓库ID',
  storage_location VARCHAR(50) COMMENT '货位号',
  purchase_date DATE COMMENT '购置日期',
  estimated_life INT COMMENT '预计使用年限',
  create_by VARCHAR(255) DEFAULT NULL COMMENT '创建者',
  update_by VARCHAR(255) DEFAULT NULL COMMENT '更新者',
  create_time DATETIME DEFAULT NULL COMMENT '创建日期',
  update_time DATETIME DEFAULT NULL COMMENT '更新时间'
) COMMENT '资产主表';

DROP TABLE IF EXISTS zjsj_asset_category;
CREATE TABLE zjsj_asset_category (
  category_code VARCHAR(20) PRIMARY KEY COMMENT '分类编码',
  category_name VARCHAR(50) NOT NULL COMMENT '分类名称',
  parent_code VARCHAR(20) COMMENT '上级分类编码',
  is_equipment TINYINT(1) DEFAULT 0 COMMENT '是否设备类',
  create_by VARCHAR(255) DEFAULT NULL COMMENT '创建者',
  update_by VARCHAR(255) DEFAULT NULL COMMENT '更新者',
  create_time DATETIME DEFAULT NULL COMMENT '创建日期',
  update_time DATETIME DEFAULT NULL COMMENT '更新时间'
) COMMENT '资产品种型号表';

-- ==================== 空间资源表 ====================
DROP TABLE IF EXISTS zjsj_project;
CREATE TABLE zjsj_project (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  project_no VARCHAR(30) NOT NULL UNIQUE COMMENT '项目编号',
  name VARCHAR(100) NOT NULL COMMENT '项目名称',
  type ENUM('房屋建筑','市政工程','交通工程') COMMENT '项目类型',
  scale VARCHAR(200) COMMENT '项目规模描述',
  location POINT COMMENT '地理位置(GIS坐标)',
  responsible_org VARCHAR(50) COMMENT '责任主体单位',
  status ENUM('筹备','在建','竣工'),
  create_by VARCHAR(255) DEFAULT NULL COMMENT '创建者',
  update_by VARCHAR(255) DEFAULT NULL COMMENT '更新者',
  create_time DATETIME DEFAULT NULL COMMENT '创建日期',
  update_time DATETIME DEFAULT NULL COMMENT '更新时间'
) COMMENT '工程项目表';

DROP TABLE IF EXISTS zjsj_storage_area;
CREATE TABLE zjsj_storage_area (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  name VARCHAR(100) NOT NULL COMMENT '场地名称',
  type ENUM('总仓','分仓','项目现场') NOT NULL,
  location POINT COMMENT '地理坐标',
  capacity VARCHAR(100) COMMENT '场地容量描述',
  manager_emp_id BIGINT COMMENT '负责人ID',
  create_by VARCHAR(255) DEFAULT NULL COMMENT '创建者',
  update_by VARCHAR(255) DEFAULT NULL COMMENT '更新者',
  create_time DATETIME DEFAULT NULL COMMENT '创建日期',
  update_time DATETIME DEFAULT NULL COMMENT '更新时间'
) COMMENT '资产存放场地表';

DROP TABLE IF EXISTS zjsj_warehouse;
CREATE TABLE zjsj_warehouse (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  name VARCHAR(50) NOT NULL COMMENT '仓库名称',
  code VARCHAR(20) UNIQUE COMMENT '仓库编码',
  storage_area_id BIGINT NOT NULL COMMENT '所属场地ID',
  capacity INT COMMENT '仓库容量(平方米)',
  create_by VARCHAR(255) DEFAULT NULL COMMENT '创建者',
  update_by VARCHAR(255) DEFAULT NULL COMMENT '更新者',
  create_time DATETIME DEFAULT NULL COMMENT '创建日期',
  update_time DATETIME DEFAULT NULL COMMENT '更新时间'
) COMMENT '仓库信息表';

-- ==================== 业务流程表 ====================
DROP TABLE IF EXISTS zjsj_lease_order;
CREATE TABLE zjsj_lease_order (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  order_no VARCHAR(30) NOT NULL UNIQUE COMMENT '租赁单号',
  project_id BIGINT NOT NULL COMMENT '工程项目ID',
  start_date DATE NOT NULL COMMENT '租赁开始日',
  end_date DATE NOT NULL COMMENT '租赁结束日',
  total_fee DECIMAL(18,2) COMMENT '租赁总费用',
  status ENUM('申请中','执行中','已完成','已结算'),
  create_by VARCHAR(255) DEFAULT NULL COMMENT '创建者',
  update_by VARCHAR(255) DEFAULT NULL COMMENT '更新者',
  create_time DATETIME DEFAULT NULL COMMENT '创建日期',
  update_time DATETIME DEFAULT NULL COMMENT '更新时间'
) COMMENT '租赁订单表';

DROP TABLE IF EXISTS zjsj_asset_inout;
CREATE TABLE zjsj_asset_inout (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  asset_id BIGINT NOT NULL COMMENT '资产ID',
  type ENUM('入库','出库','移库') NOT NULL,
  ref_order_id BIGINT COMMENT '关联单号',
  warehouse_id BIGINT COMMENT '仓库ID',
  before_location VARCHAR(50) COMMENT '移库前货位',
  after_location VARCHAR(50) COMMENT '移库后货位',
  operator_id BIGINT NOT NULL COMMENT '操作人',
  create_by VARCHAR(255) DEFAULT NULL COMMENT '创建者',
  update_by VARCHAR(255) DEFAULT NULL COMMENT '更新者',
  create_time DATETIME DEFAULT NULL COMMENT '创建日期',
  update_time DATETIME DEFAULT NULL COMMENT '更新时间'
) COMMENT '资产出入库表';

DROP TABLE IF EXISTS zjsj_maintenance_record;
CREATE TABLE zjsj_maintenance_record (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  asset_id BIGINT NOT NULL COMMENT '资产ID',
  type ENUM('保养','维修') NOT NULL,
  cost DECIMAL(18,2) COMMENT '维护费用',
  content TEXT COMMENT '维护内容',
  result ENUM('完成','待跟进','需报废') COMMENT '处理结果',
  maintenance_date DATE NOT NULL,
  create_by VARCHAR(255) DEFAULT NULL COMMENT '创建者',
  update_by VARCHAR(255) DEFAULT NULL COMMENT '更新者',
  create_time DATETIME DEFAULT NULL COMMENT '创建日期',
  update_time DATETIME DEFAULT NULL COMMENT '更新时间'
) COMMENT '资产维护表';

-- ==================== 物联网设备表 ====================
DROP TABLE IF EXISTS zjsj_electronic_tag;
CREATE TABLE zjsj_electronic_tag (
  id VARCHAR(50) PRIMARY KEY COMMENT '标签物理ID',
  type ENUM('RFID','GPS') NOT NULL,
  bind_asset_id BIGINT COMMENT '绑定资产ID',
  bind_time DATETIME COMMENT '绑定时间',
  health_status ENUM('正常','低电','故障'),
  last_check_time DATETIME COMMENT '最后检测时间',
  create_by VARCHAR(255) DEFAULT NULL COMMENT '创建者',
  update_by VARCHAR(255) DEFAULT NULL COMMENT '更新者',
  create_time DATETIME DEFAULT NULL COMMENT '创建日期',
  update_time DATETIME DEFAULT NULL COMMENT '更新时间'
) COMMENT '电子标签表';

DROP TABLE IF EXISTS zjsj_drone_inspection;
CREATE TABLE zjsj_drone_inspection (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  drone_id VARCHAR(30) NOT NULL COMMENT '无人机编号',
  storage_area_id BIGINT NOT NULL COMMENT '巡检场地ID',
  path_data JSON COMMENT '巡检路径坐标集',
  result_summary TEXT COMMENT '异常汇总',
  inspection_time DATETIME NOT NULL,
  create_by VARCHAR(255) DEFAULT NULL COMMENT '创建者',
  update_by VARCHAR(255) DEFAULT NULL COMMENT '更新者',
  create_time DATETIME DEFAULT NULL COMMENT '创建日期',
  update_time DATETIME DEFAULT NULL COMMENT '更新时间'
) COMMENT '无人机巡检表';

-- ==================== 组织架构表 ====================
DROP TABLE IF EXISTS zjsj_organization;
CREATE TABLE zjsj_organization (
  org_code VARCHAR(20) PRIMARY KEY COMMENT '机构编码',
  org_name VARCHAR(50) NOT NULL COMMENT '机构名称',
  org_type ENUM('公司','分公司','项目部') NOT NULL,
  parent_org_code VARCHAR(20) COMMENT '上级机构编码',
  create_by VARCHAR(255) DEFAULT NULL COMMENT '创建者',
  update_by VARCHAR(255) DEFAULT NULL COMMENT '更新者',
  create_time DATETIME DEFAULT NULL COMMENT '创建日期',
  update_time DATETIME DEFAULT NULL COMMENT '更新时间'
) COMMENT '企业组织机构表';

DROP TABLE IF EXISTS zjsj_employee;
CREATE TABLE zjsj_employee (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  emp_no VARCHAR(20) NOT NULL UNIQUE COMMENT '工号',
  name VARCHAR(30) NOT NULL,
  org_code VARCHAR(20) NOT NULL COMMENT '所属机构',
  position VARCHAR(30) COMMENT '职位',
  contact VARCHAR(20) COMMENT '联系方式',
  create_by VARCHAR(255) DEFAULT NULL COMMENT '创建者',
  update_by VARCHAR(255) DEFAULT NULL COMMENT '更新者',
  create_time DATETIME DEFAULT NULL COMMENT '创建日期',
  update_time DATETIME DEFAULT NULL COMMENT '更新时间'
) COMMENT '员工档案表';

-- ==================== 统计分析表 ====================
DROP TABLE IF EXISTS zjsj_turnover_profit;
CREATE TABLE zjsj_turnover_profit (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  asset_id BIGINT NOT NULL COMMENT '周转物资ID',
  year YEAR NOT NULL COMMENT '统计年份',
  quarter TINYINT COMMENT '季度(1-4)',
  lease_days INT COMMENT '租赁天数',
  income DECIMAL(18,2) COMMENT '产生收益',
  utilization_rate DECIMAL(5,2) COMMENT '使用率(%)',
  create_by VARCHAR(255) DEFAULT NULL COMMENT '创建者',
  update_by VARCHAR(255) DEFAULT NULL COMMENT '更新者',
  create_time DATETIME DEFAULT NULL COMMENT '创建日期',
  update_time DATETIME DEFAULT NULL COMMENT '更新时间'
) COMMENT '周转物资收益表';


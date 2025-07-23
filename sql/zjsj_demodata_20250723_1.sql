# 1. 资产分类表 (zjsj_asset_category) - 5条
INSERT INTO zjsj_asset_category
(category_code, category_name, parent_code, is_equipment, create_by, create_time, update_by, update_time)
VALUES 
('EQP-001', '大型施工设备', NULL, 1, 'system', '2025-01-01 09:00:00', 'system', '2025-01-01 09:00:00'),
('EQP-002', '运输车辆', NULL, 1, 'system', '2025-01-01 09:00:00', 'system', '2025-01-01 09:00:00'),
('EQP-003', '工程仪器', 'EQP-001', 1, 'system', '2025-01-01 09:00:00', 'system', '2025-01-01 09:00:00'),
('TOOL-001', '电动工具', NULL, 0, 'system', '2025-01-01 09:00:00', 'system', '2025-01-01 09:00:00'),
('SAFE-001', '安全防护设备', NULL, 0, 'system', '2025-01-01 09:00:00', 'system', '2025-01-01 09:00:00');

# 2. 工程项目表 (zjsj_project) - 5条
INSERT INTO zjsj_project
(id, project_no, name, type, scale, location, responsible_org, status, create_by, create_time, update_by, update_time)
VALUES 
(1, 'PRJ-GZ-2025-001', '广州金融中心', 'HOUSE', '总高450米，98层', '113.327 23.116', '广州分公司', 'GOING', 'admin', '2025-02-10 10:00:00', 'admin', '2025-06-15 14:30:00'),
(2, 'PRJ-GZ-2025-002', '珠江新城隧道', 'CITY', '全长3.2公里', '113.321 23.122', '市政工程部', 'GOING', 'admin', '2025-01-15 09:30:00', 'admin', '2025-05-20 11:20:00'),
(3, 'PRJ-GZ-2025-003', '白云机场T3航站楼', 'HOUSE', '建筑面积45万平方米', '113.309 23.392', '机场项目部', 'GOING', 'admin', '2025-03-05 14:00:00', 'admin', '2025-07-18 16:45:00'),
(4, 'PRJ-GZ-2025-004', '南沙港码头扩建', 'CITY', '新增5个10万吨泊位', '113.602 22.795', '南沙项目部', 'READY', 'admin', '2025-04-22 11:00:00', 'admin', '2025-06-30 10:15:00'),
(5, 'PRJ-GZ-2025-005', '广佛环线城轨', 'TRANS', '全长35公里', '113.115 23.025', '轨道交通部', 'DONE', 'admin', '2025-02-28 15:30:00', 'admin', '2025-07-12 09:40:00');

# 3. 存放场地表 (zjsj_storage_area) - 10条 ENUM('CENTRAL', 'SEPARATE', 'PROJECT')
INSERT INTO zjsj_storage_area
(id, name, type, location, capacity, manager_emp_id, create_by, create_time, update_by, update_time)
VALUES 
(1, '广州中心总仓', 'CENTRAL', '113.295 23.136', '10,000平方米', 1001, 'admin', '2025-01-10 08:00:00', 'admin', '2025-06-01 14:20:00'),
(2, '白云分仓', 'SEPARATE', '113.279 23.226', '5,000平方米', 1002, 'admin', '2025-01-12 09:15:00', 'admin', '2025-05-15 11:30:00'),
(3, '金融中心项目现场', 'PROJECT', '113.327 23.116', '工地北侧', 1003, 'admin', '2025-02-15 10:30:00', 'admin', '2025-07-10 16:40:00'),
(4, '南沙港项目现场', 'PROJECT', '113.602 22.795', '码头A区', 1004, 'admin', '2025-03-20 13:20:00', 'admin', '2025-06-25 10:50:00'),
(5, '珠江新城项目现场', 'PROJECT', '113.321 23.122', '隧道东入口', 1005, 'admin', '2025-01-25 14:45:00', 'admin', '2025-07-05 15:20:00'),
(6, '机场项目现场', 'PROJECT', '113.309 23.392', 'T3航站楼西侧', 1006, 'admin', '2025-03-10 11:10:00', 'admin', '2025-06-20 09:30:00'),
(7, '番禺分仓', 'SEPARATE', '113.385 22.938', '3,000平方米', 1007, 'admin', '2025-02-05 15:00:00', 'admin', '2025-05-28 14:15:00'),
(8, '黄埔分仓', 'SEPARATE', '113.466 23.102', '4,000平方米', 1008, 'admin', '2025-01-18 10:20:00', 'admin', '2025-06-10 11:40:00'),
(9, '广佛项目现场', 'PROJECT', '113.115 23.025', '陈村段工地', 1009, 'admin', '2025-03-01 09:45:00', 'admin', '2025-07-08 10:25:00'),
(10, '南沙分仓', 'SEPARATE', '113.537 22.801', '6,000平方米', 1010, 'admin', '2025-04-01 14:00:00', 'admin', '2025-06-18 16:30:00');

# 4. 仓库信息表 (zjsj_warehouse) - 3条
INSERT INTO zjsj_warehouse
(id, name, code, storage_area_id, capacity, create_by, create_time, update_by, update_time)
VALUES 
(1, '中心总仓-A区', 'WH-GZ-001', 1, 4000, 'admin', '2025-01-15 09:00:00', 'admin', '2025-05-20 14:30:00'),
(2, '中心总仓-B区', 'WH-GZ-002', 1, 3500, 'admin', '2025-01-15 09:00:00', 'admin', '2025-05-20 14:30:00'),
(3, '白云设备仓', 'WH-BY-001', 2, 2000, 'admin', '2025-01-20 10:30:00', 'admin', '2025-06-05 11:15:00');

# 5. 资产主表 (zjsj_asset) - 100条
-- 使用存储过程批量生成100条资产数据
DELIMITER //
CREATE PROCEDURE generate_assets()
BEGIN
  DECLARE i INT DEFAULT 1;
  DECLARE asset_type VARCHAR(20);
  DECLARE asset_status VARCHAR(20);
  DECLARE is_turnover_flag TINYINT;
  
  WHILE i <= 100 DO
    -- 设置资产类型和状态
    IF i <= 30 THEN
      SET asset_type = 'EQP-001'; -- 大型设备
      SET is_turnover_flag = 1;
    ELSEIF i <= 60 THEN
      SET asset_type = 'EQP-002'; -- 运输车辆
      SET is_turnover_flag = 1;
    ELSEIF i <= 70 THEN
      SET asset_type = 'EQP-003'; -- 工程仪器
      SET is_turnover_flag = 0;
    ELSEIF i <= 85 THEN
      SET asset_type = 'TOOL-001'; -- 电动工具
      SET is_turnover_flag = 0;
    ELSE
      SET asset_type = 'SAFE-001'; -- 安全设备
      SET is_turnover_flag = 0;
    END IF;
    
    -- 设置资产状态
    SET asset_status = CASE 
      WHEN i % 6 = 0 THEN 'INSTOCK'
      WHEN i % 6 = 1 THEN 'ISSUED'
      WHEN i % 6 = 2 THEN 'BORROWED'
      WHEN i % 6 = 3 THEN 'LEASE'
      WHEN i % 6 = 4 THEN 'MAINTENANCE'
      ELSE 'DISPOSED'
    END;
    
    INSERT INTO zjsj_asset
    (id, asset_no, name, category_code, model, unit, original_value, status, is_turnover, storage_area_id, warehouse_id, storage_location, purchase_date, estimated_life, create_by, create_time, update_by, update_time)
    VALUES (
      i,
      CONCAT('ASSET-2025-', LPAD(i, 4, '0')),
      CASE 
        WHEN asset_type = 'EQP-001' THEN CONCAT('塔式起重机QTZ', FLOOR(60 + RAND() * 40))
        WHEN asset_type = 'EQP-002' THEN CONCAT('混凝土泵车SY', FLOOR(5000 + RAND() * 1000))
        WHEN asset_type = 'EQP-003' THEN CONCAT('全站仪TS', FLOOR(10 + RAND() * 10))
        WHEN asset_type = 'TOOL-001' THEN CONCAT('电动扳手DB-', FLOOR(10 + RAND() * 20))
        ELSE CONCAT('安全爬梯PT-', FLOOR(1 + RAND() * 5), 'M')
      END,
      asset_type,
      CASE 
        WHEN asset_type = 'EQP-001' THEN CONCAT('QTZ', FLOOR(60 + RAND() * 40))
        WHEN asset_type = 'EQP-002' THEN CONCAT('SY', FLOOR(5000 + RAND() * 1000), 'THB')
        WHEN asset_type = 'EQP-003' THEN CONCAT('TS', FLOOR(10 + RAND() * 10))
        WHEN asset_type = 'TOOL-001' THEN CONCAT('DB-', FLOOR(10 + RAND() * 20))
        ELSE CONCAT('PT-', FLOOR(1 + RAND() * 5), 'M')
      END,
      CASE 
        WHEN asset_type IN ('EQP-001', 'EQP-002') THEN '台'
        WHEN asset_type = 'EQP-003' THEN '套'
        WHEN asset_type = 'TOOL-001' THEN '把'
        ELSE '套'
      END,
      CASE 
        WHEN asset_type = 'EQP-001' THEN FLOOR(300000 + RAND() * 300000)
        WHEN asset_type = 'EQP-002' THEN FLOOR(800000 + RAND() * 500000)
        WHEN asset_type = 'EQP-003' THEN FLOOR(50000 + RAND() * 50000)
        WHEN asset_type = 'TOOL-001' THEN FLOOR(800 + RAND() * 1000)
        ELSE FLOOR(5000 + RAND() * 10000)
      END,
      asset_status,
      is_turnover_flag,
      CASE 
        WHEN asset_status = '在库' THEN FLOOR(1 + RAND() * 3)
        WHEN asset_status IN ('租赁中', 'MAINTAIN中') THEN NULL
        ELSE FLOOR(3 + RAND() * 7)
      END,
      CASE 
        WHEN asset_status = '在库' THEN FLOOR(1 + RAND() * 3)
        ELSE NULL
      END,
      CASE 
        WHEN asset_status = '在库' THEN CONCAT(CHAR(65 + FLOOR(RAND() * 3)), '-', LPAD(FLOOR(1 + RAND() * 20), 2, '0'), '-', LPAD(FLOOR(1 + RAND() * 10), 2, '0'))
        ELSE NULL
      END,
      DATE_SUB('2025-01-01', INTERVAL FLOOR(RAND() * 36) MONTH),
      CASE 
        WHEN asset_type IN ('EQP-001', 'EQP-002') THEN 8 + FLOOR(RAND() * 5)
        WHEN asset_type = 'EQP-003' THEN 5 + FLOOR(RAND() * 3)
        ELSE 3 + FLOOR(RAND() * 4)
      END,
      'system',
      DATE_ADD('2025-01-01', INTERVAL FLOOR(RAND() * 180) DAY),
      'system',
      DATE_ADD('2025-01-01', INTERVAL FLOOR(RAND() * 180) DAY)
    );
    
    SET i = i + 1;
  END WHILE;
END //
DELIMITER ;
CALL generate_assets();
DROP PROCEDURE generate_assets;

# 6. 电子标签表 (zjsj_electronic_tag) - 100条
-- 生成100条电子标签数据，与资产一对一绑定
INSERT INTO zjsj_electronic_tag
SELECT 
  CONCAT(IF(type = 'EQP-002', 'GPS-', 'RFID-'), LPAD(FLOOR(100000 + RAND() * 900000), 8, '0')) AS id,
  IF(type = 'EQP-002', 'GPS', 'RFID') AS type,
  id AS bind_asset_id,
  DATE_ADD(create_time, INTERVAL FLOOR(RAND() * 3) HOUR) AS bind_time,
  CASE 
    WHEN RAND() < 0.9 THEN '正常'
    WHEN RAND() < 0.7 THEN '低电'
    ELSE '故障'
  END AS health_status,
  DATE_ADD(update_time, INTERVAL -FLOOR(RAND() * 30) DAY) AS last_check_time,
  create_by,
  create_time,
  update_by,
  update_time
FROM zjsj_asset;

# 7. 租赁订单表 (zjsj_lease_order) - 200条
-- 生成200条租赁订单数据
INSERT INTO zjsj_lease_order
SELECT 
  seq AS id,
  CONCAT('LEASE-2025', LPAD(FLOOR(1 + RAND() * 12), 2, '0'), '-', LPAD(seq, 3, '0')) AS order_no,
  FLOOR(1 + RAND() * 5) AS project_id,
  DATE_ADD('2025-01-01', INTERVAL FLOOR(RAND() * 180) DAY) AS start_date,
  DATE_ADD(start_date, INTERVAL FLOOR(30 + RAND() * 120) DAY) AS end_date,
  ROUND(5000 + RAND() * 20000, 2) AS total_fee,
  CASE 
    WHEN end_date < CURDATE() THEN 
      CASE WHEN RAND() < 0.8 THEN '已结算' ELSE '已完成' END
    WHEN start_date > CURDATE() THEN '申请中'
    ELSE '执行中'
  END AS status,
  CONCAT('user', LPAD(FLOOR(1 + RAND() * 10), 2, '0')) AS create_by,
  DATE_ADD('2025-01-01', INTERVAL FLOOR(RAND() * 180) DAY) AS create_time,
  CONCAT('user', LPAD(FLOOR(1 + RAND() * 10), 2, '0')) AS update_by,
  DATE_ADD(create_time, INTERVAL FLOOR(RAND() * 30) DAY) AS update_time
FROM (
  SELECT @rownum := @rownum + 1 AS seq
  FROM (SELECT 1 UNION SELECT 2 UNION SELECT 3 UNION SELECT 4 UNION SELECT 5) t1,
       (SELECT 1 UNION SELECT 2 UNION SELECT 3 UNION SELECT 4 UNION SELECT 5) t2,
       (SELECT 1 UNION SELECT 2 UNION SELECT 3 UNION SELECT 4 UNION SELECT 5) t3,
       (SELECT 1 UNION SELECT 2 UNION SELECT 3 UNION SELECT 4 UNION SELECT 5) t4,
       (SELECT @rownum := 0) r
  LIMIT 200
) seq_nums;

# 8. 资产出入库表 (zjsj_asset_inout) - 200条
-- 生成200条出入库记录
INSERT INTO zjsj_asset_inout
SELECT 
  seq AS id,
  FLOOR(1 + RAND() * 100) AS asset_id,
  CASE 
    WHEN seq % 3 = 0 THEN '入库'
    WHEN seq % 3 = 1 THEN '出库'
    ELSE '移库'
  END AS type,
  CASE 
    WHEN type = '出库' THEN FLOOR(1 + RAND() * 200)
    ELSE NULL
  END AS ref_order_id,
  CASE 
    WHEN type != '移库' THEN FLOOR(1 + RAND() * 3)
    ELSE NULL
  END AS warehouse_id,
  CASE 
    WHEN type = '移库' THEN CONCAT(CHAR(65 + FLOOR(RAND() * 3)), '-', LPAD(FLOOR(1 + RAND() * 20), 2, '0'), '-', LPAD(FLOOR(1 + RAND() * 10), 2, '0'))
    ELSE NULL
  END AS before_location,
  CASE 
    WHEN type IN ('入库', '移库') THEN CONCAT(CHAR(65 + FLOOR(RAND() * 3)), '-', LPAD(FLOOR(1 + RAND() * 20), 2, '0'), '-', LPAD(FLOOR(1 + RAND() * 10), 2, '0'))
    ELSE NULL
  END AS after_location,
  FLOOR(1001 + RAND() * 20) AS operator_id,
  CONCAT('user', LPAD(FLOOR(1 + RAND() * 10), 2, '0')) AS create_by,
  DATE_ADD('2025-01-01', INTERVAL FLOOR(RAND() * 180) DAY) AS create_time,
  CONCAT('user', LPAD(FLOOR(1 + RAND() * 10), 2, '0')) AS update_by,
  DATE_ADD(create_time, INTERVAL FLOOR(RAND() * 7) DAY) AS update_time
FROM (
  SELECT @rownum := @rownum + 1 AS seq
  FROM (SELECT 1 UNION SELECT 2 UNION SELECT 3 UNION SELECT 4 UNION SELECT 5) t1,
       (SELECT 1 UNION SELECT 2 UNION SELECT 3 UNION SELECT 4 UNION SELECT 5) t2,
       (SELECT 1 UNION SELECT 2 UNION SELECT 3 UNION SELECT 4 UNION SELECT 5) t3,
       (SELECT 1 UNION SELECT 2 UNION SELECT 3 UNION SELECT 4 UNION SELECT 5) t4,
       (SELECT @rownum := 0) r
  LIMIT 200
) seq_nums;

# 9. 维护记录表 (zjsj_maintenance_record) - 30条
INSERT INTO zjsj_maintenance_record
(id, asset_id, type, cost, content, result, maintenance_date, create_by, create_time, update_by, update_time)
VALUES 
(1, 2, 'RAPAIR', 12500.00, '液压系统维护更换密封件', 'DONE', '2025-05-15', 'maint01', '2025-05-16 10:30:00', 'maint01', '2025-05-20 14:15:00'),
(2, 1, 'MAINTAIN', 8500.00, '季度MAINTAIN：结构检查、润滑系统维护', 'DONE', '2025-04-10', 'maint02', '2025-04-11 09:20:00', 'maint02', '2025-04-15 11:40:00'),
(3, 5, 'RAPAIR', 3200.00, '安全锁扣更换', 'DONE', '2025-06-22', 'maint03', '2025-06-23 14:50:00', 'maint03', '2025-06-25 10:30:00'),
(4, 3, 'MAINTAIN', 1500.00, '年度校准与清洁', 'DONE', '2025-03-18', 'maint04', '2025-03-19 11:10:00', 'maint04', '2025-03-22 09:45:00'),
(5, 1, 'RAPAIR', 28500.00, '电机绕组更换', 'SCRAP', '2025-07-10', 'maint05', '2025-07-11 15:20:00', 'maint05', '2025-07-15 14:30:00'),
(6, 4, 'MAINTAIN', 3200.00, '常规润滑MAINTAIN', 'DONE', '2025-02-28', 'maint06', '2025-03-01 09:15:00', 'maint06', '2025-03-05 10:45:00'),
(7, 7, 'RAPAIR', 8500.00, '液压泵更换', 'DONE', '2025-05-05', 'maint07', '2025-05-06 14:30:00', 'maint07', '2025-05-10 11:20:00'),
(8, 2, 'MAINTAIN', 4500.00, '电气系统检查', 'DONE', '2025-06-10', 'maint08', '2025-06-11 10:40:00', 'maint08', '2025-06-15 09:30:00'),
(9, 9, 'RAPAIR', 12500.00, '传动系统大修', 'FOLLOW', '2025-07-18', 'maint09', '2025-07-19 16:20:00', 'maint09', '2025-07-22 14:15:00'),
(10, 6, 'MAINTAIN', 2800.00, '安全装置检测', 'DONE', '2025-04-22', 'maint10', '2025-04-23 11:30:00', 'maint10', '2025-04-25 10:20:00'),
(11, 8, 'RAPAIR', 6500.00, '控制系统升级', 'DONE', '2025-03-15', 'maint11', '2025-03-16 14:50:00', 'maint11', '2025-03-20 11:40:00'),
(12, 3, 'MAINTAIN', 1800.00, '光学部件清洁', 'DONE', '2025-05-28', 'maint12', '2025-05-29 10:15:00', 'maint12', '2025-06-02 09:25:00'),
(13, 10, 'RAPAIR', 9500.00, '发动机大修', 'DONE', '2025-06-05', 'maint13', '2025-06-06 15:30:00', 'maint13', '2025-06-10 14:20:00'),
(14, 4, 'MAINTAIN', 3500.00, '液压系统换油', 'DONE', '2025-07-12', 'maint14', '2025-07-13 11:45:00', 'maint14', '2025-07-18 10:30:00'),
(15, 1, 'RAPAIR', 42000.00, '主结构加固', 'SCRAP', '2025-04-05', 'maint15', '2025-04-06 16:40:00', 'maint15', '2025-04-10 15:20:00'),
(16, 5, 'MAINTAIN', 2900.00, '安全装置测试', 'DONE', '2025-03-22', 'maint16', '2025-03-23 10:50:00', 'maint16', '2025-03-25 09:40:00'),
(17, 7, 'RAPAIR', 7800.00, '电气控制系统RAPAIR', 'DONE', '2025-05-15', 'maint17', '2025-05-16 14:20:00', 'maint17', '2025-05-20 11:30:00'),
(18, 2, 'MAINTAIN', 5200.00, '年度全面检查', 'DONE', '2025-06-18', 'maint18', '2025-06-19 11:10:00', 'maint18', '2025-06-22 10:00:00'),
(19, 9, 'RAPAIR', 11500.00, '液压缸更换', 'FOLLOW', '2025-07-05', 'maint19', '2025-07-06 15:50:00', 'maint19', '2025-07-10 14:30:00'),
(20, 6, 'MAINTAIN', 3100.00, '传动系统润滑', 'DONE', '2025-04-30', 'maint20', '2025-05-01 10:40:00', 'maint20', '2025-05-05 09:20:00'),
(21, 8, 'RAPAIR', 8800.00, '电子元件更换', 'DONE', '2025-03-28', 'maint21', '2025-03-29 14:30:00', 'maint21', '2025-04-02 11:20:00'),
(22, 3, 'MAINTAIN', 2100.00, '精度校准', 'DONE', '2025-06-08', 'maint22', '2025-06-09 10:15:00', 'maint22', '2025-06-12 09:05:00'),
(23, 10, 'RAPAIR', 10500.00, '变速箱RAPAIR', 'DONE', '2025-05-20', 'maint23', '2025-05-21 16:20:00', 'maint23', '2025-05-25 15:00:00'),
(24, 4, 'MAINTAIN', 3800.00, '液压管路检查', 'DONE', '2025-07-15', 'maint24', '2025-07-16 11:50:00', 'maint24', '2025-07-20 10:40:00'),
(25, 1, 'RAPAIR', 38500.00, '主梁更换', 'SCRAP', '2025-04-12', 'maint25', '2025-04-13 15:30:00', 'maint25', '2025-04-18 14:10:00'),
(26, 5, 'MAINTAIN', 2700.00, '安全锁检查', 'DONE', '2025-03-30', 'maint26', '2025-03-31 10:20:00', 'maint26', '2025-04-03 09:10:00'),
(27, 7, 'RAPAIR', 7200.00, '传感器更换', 'DONE', '2025-06-25', 'maint27', '2025-06-26 14:40:00', 'maint27', '2025-06-30 11:30:00'),
(28, 2, 'MAINTAIN', 4800.00, '电气系统维护', 'DONE', '2025-05-10', 'maint28', '2025-05-11 11:00:00', 'maint28', '2025-05-15 09:50:00'),
(29, 9, 'RAPAIR', 10800.00, '液压泵RAPAIR', 'FOLLOW', '2025-07-22', 'maint29', '2025-07-23 16:10:00', 'maint29', '2025-07-27 14:50:00'),
(30, 6, 'MAINTAIN', 2950.00, '常规检查维护', 'DONE', '2025-04-18', 'maint30', '2025-04-19 10:30:00', 'maint30', '2025-04-22 09:20:00');

# 10. 无人机巡检表 (zjsj_drone_inspection) - 30条
INSERT INTO zjsj_drone_inspection
(id, drone_id, storage_area_id, path_data, result_summary, inspection_time, create_by, create_time, update_by, update_time)
VALUES 
(1, 'DRONE-GZ-01', 1, '{"path": [[113.295,23.136], [113.297,23.138], [113.293,23.135]]}', '发现3处未扫码资产', '2025-05-10 10:30:00', 'drone_opt', '2025-05-10 11:00:00', 'drone_opt', '2025-05-10 11:00:00'),
(2, 'DRONE-GZ-02', 3, '{"path": [[113.327,23.116], [113.329,23.118], [113.325,23.114]]}', '场地西侧堆放异常', '2025-06-15 14:20:00', 'drone_opt', '2025-06-15 15:10:00', 'drone_opt', '2025-06-15 15:10:00'),
(3, 'DRONE-GZ-01', 5, '{"path": [[113.321,23.122], [113.323,23.124], [113.319,23.120]]}', '2号区域积水严重', '2025-04-25 09:45:00', 'drone_opt', '2025-04-25 10:30:00', 'drone_opt', '2025-04-25 10:30:00'),
(4, 'DRONE-GZ-03', 2, '{"path": [[113.279,23.226], [113.281,23.228], [113.277,23.224]]}', '盘点差异率0.8%', '2025-07-05 11:20:00', 'drone_opt', '2025-07-05 12:15:00', 'drone_opt', '2025-07-05 12:15:00'),
(5, 'DRONE-GZ-02', 4, '{"path": [[113.602,22.795], [113.604,22.797], [113.600,22.793]]}', '发现1台设备标签脱落', '2025-05-30 15:40:00', 'drone_opt', '2025-05-30 16:30:00', 'drone_opt', '2025-05-30 16:30:00'),
(6, 'DRONE-GZ-01', 6, '{"path": [[113.309,23.392], [113.311,23.394], [113.307,23.390]]}', '3处资产位置偏移', '2025-06-22 09:30:00', 'drone_opt', '2025-06-22 10:20:00', 'drone_opt', '2025-06-22 10:20:00'),
(7, 'DRONE-GZ-03', 7, '{"path": [[113.385,22.938], [113.387,22.940], [113.383,22.936]]}', '盘点差异率1.2%', '2025-05-15 14:00:00', 'drone_opt', '2025-05-15 14:50:00', 'drone_opt', '2025-05-15 14:50:00'),
(8, 'DRONE-GZ-02', 8, '{"path": [[113.466,23.102], [113.468,23.104], [113.464,23.100]]}', '发现2处堆放不规范', '2025-07-12 10:15:00', 'drone_opt', '2025-07-12 11:05:00', 'drone_opt', '2025-07-12 11:05:00'),
(9, 'DRONE-GZ-01', 9, '{"path": [[113.115,23.025], [113.117,23.027], [113.113,23.023]]}', '1台设备被遮挡', '2025-06-05 13:45:00', 'drone_opt', '2025-06-05 14:35:00', 'drone_opt', '2025-06-05 14:35:00'),
(10, 'DRONE-GZ-03', 10, '{"path": [[113.537,22.801], [113.539,22.803], [113.535,22.799]]}', '盘点差异率0.5%', '2025-05-20 15:30:00', 'drone_opt', '2025-05-20 16:20:00', 'drone_opt', '2025-05-20 16:20:00'),
(11, 'DRONE-GZ-02', 1, '{"path": [[113.295,23.136], [113.297,23.138], [113.293,23.135]]}', '发现1处安全隐患', '2025-07-18 09:00:00', 'drone_opt', '2025-07-18 09:50:00', 'drone_opt', '2025-07-18 09:50:00'),
(12, 'DRONE-GZ-01', 3, '{"path": [[113.327,23.116], [113.329,23.118], [113.325,23.114]]}', '设备分布图更新完成', '2025-06-28 11:20:00', 'drone_opt', '2025-06-28 12:10:00', 'drone_opt', '2025-06-28 12:10:00'),
(13, 'DRONE-GZ-03', 5, '{"path": [[113.321,23.122], [113.323,23.124], [113.319,23.120]]}', '积水区域已标记', '2025-05-05 14:40:00', 'drone_opt', '2025-05-05 15:30:00', 'drone_opt', '2025-05-05 15:30:00'),
(14, 'DRONE-GZ-02', 2, '{"path": [[113.279,23.226], [113.281,23.228], [113.277,23.224]]}', '盘点差异率0.3%', '2025-07-08 10:50:00', 'drone_opt', '2025-07-08 11:40:00', 'drone_opt', '2025-07-08 11:40:00'),
(15, 'DRONE-GZ-01', 4, '{"path": [[113.602,22.795], [113.604,22.797], [113.600,22.793]]}', '标签补拍完成', '2025-06-12 13:15:00', 'drone_opt', '2025-06-12 14:05:00', 'drone_opt', '2025-06-12 14:05:00'),
(16, 'DRONE-GZ-03', 6, '{"path": [[113.309,23.392], [113.311,23.394], [113.307,23.390]]}', '3处资产位置已调整', '2025-05-25 15:20:00', 'drone_opt', '2025-05-25 16:10:00', 'drone_opt', '2025-05-25 16:10:00'),
(17, 'DRONE-GZ-02', 7, '{"path": [[113.385,22.938], [113.387,22.940], [113.383,22.936]]}', '盘点差异率1.5%', '2025-07-22 09:40:00', 'drone_opt', '2025-07-22 10:30:00', 'drone_opt', '2025-07-22 10:30:00'),
(18, 'DRONE-GZ-01', 8, '{"path": [[113.466,23.102], [113.468,23.104], [113.464,23.100]]}', '堆放问题已整改', '2025-06-18 11:30:00', 'drone_opt', '2025-06-18 12:20:00', 'drone_opt', '2025-06-18 12:20:00'),
(19, 'DRONE-GZ-03', 9, '{"path": [[113.115,23.025], [113.117,23.027], [113.113,23.023]]}', '遮挡问题已解决', '2025-05-15 14:50:00', 'drone_opt', '2025-05-15 15:40:00', 'drone_opt', '2025-05-15 15:40:00'),
(20, 'DRONE-GZ-02', 10, '{"path": [[113.537,22.801], [113.539,22.803], [113.535,22.799]]}', '盘点差异率0.7%', '2025-07-15 10:10:00', 'drone_opt', '2025-07-15 11:00:00', 'drone_opt', '2025-07-15 11:00:00'),
(21, 'DRONE-GZ-01', 1, '{"path": [[113.295,23.136], [113.297,23.138], [113.293,23.135]]}', '例行巡检无异常', '2025-06-08 13:40:00', 'drone_opt', '2025-06-08 14:30:00', 'drone_opt', '2025-06-08 14:30:00'),
(22, 'DRONE-GZ-03', 3, '{"path": [[113.327,23.116], [113.329,23.118], [113.325,23.114]]}', '安全通道检查完成', '2025-05-22 15:00:00', 'drone_opt', '2025-05-22 15:50:00', 'drone_opt', '2025-05-22 15:50:00'),
(23, 'DRONE-GZ-02', 5, '{"path": [[113.321,23.122], [113.323,23.124], [113.319,23.120]]}', '积水区域复查合格', '2025-07-20 09:20:00', 'drone_opt', '2025-07-20 10:10:00', 'drone_opt', '2025-07-20 10:10:00'),
(24, 'DRONE-GZ-01', 2, '{"path": [[113.279,23.226], [113.281,23.228], [113.277,23.224]]}', '盘点差异率0.9%', '2025-06-25 11:50:00', 'drone_opt', '2025-06-25 12:40:00', 'drone_opt', '2025-06-25 12:40:00'),
(25, 'DRONE-GZ-03', 4, '{"path": [[113.602,22.795], [113.604,22.797], [113.600,22.793]]}', '标签补拍完成', '2025-05-18 14:10:00', 'drone_opt', '2025-05-18 15:00:00', 'drone_opt', '2025-05-18 15:00:00'),
(26, 'DRONE-GZ-02', 6, '{"path": [[113.309,23.392], [113.311,23.394], [113.307,23.390]]}', '3处资产位置已确认', '2025-07-10 10:30:00', 'drone_opt', '2025-07-10 11:20:00', 'drone_opt', '2025-07-10 11:20:00'),
(27, 'DRONE-GZ-01', 7, '{"path": [[113.385,22.938], [113.387,22.940], [113.383,22.936]]}', '盘点差异率1.1%', '2025-06-15 13:20:00', 'drone_opt', '2025-06-15 14:10:00', 'drone_opt', '2025-06-15 14:10:00'),
(28, 'DRONE-GZ-03', 8, '{"path": [[113.466,23.102], [113.468,23.104], [113.464,23.100]]}', '堆放整改复查合格', '2025-05-28 15:40:00', 'drone_opt', '2025-05-28 16:30:00', 'drone_opt', '2025-05-28 16:30:00'),
(29, 'DRONE-GZ-02', 9, '{"path": [[113.115,23.025], [113.117,23.027], [113.113,23.023]]}', '例行巡检无异常', '2025-07-25 09:50:00', 'drone_opt', '2025-07-25 10:40:00', 'drone_opt', '2025-07-25 10:40:00'),
(30, 'DRONE-GZ-01', 10, '{"path": [[113.537,22.801], [113.539,22.803], [113.535,22.799]]}', '盘点差异率0.6%', '2025-06-20 11:30:00', 'drone_opt', '2025-06-20 12:20:00', 'drone_opt', '2025-06-20 12:20:00');

# 11. 周转物资收益表 (zjsj_turnover_profit) - 10条
INSERT INTO zjsj_turnover_profit
(id, asset_id, year, quarter, lease_days, income, utilization_rate, create_by, create_time, update_by, update_time)
VALUES 
(1, 1, 2025, 2, 85, 126000.00, 92.50, 'finance01', '2025-07-10 14:00:00', 'finance01', '2025-07-10 14:00:00'),
(2, 2, 2025, 2, 120, 235000.00, 88.30, 'finance02', '2025-07-12 10:30:00', 'finance02', '2025-07-12 10:30:00'),
(3, 5, 2025, 2, 90, 187000.00, 85.20, 'finance03', '2025-07-15 11:20:00', 'finance03', '2025-07-15 11:20:00'),
(4, 1, 2025, 1, 75, 98000.00, 82.60, 'finance01', '2025-04-05 09:45:00', 'finance01', '2025-04-05 09:45:00'),
(5, 3, 2025, 1, 60, 74500.00, 78.90, 'finance02', '2025-04-10 14:20:00', 'finance02', '2025-04-10 14:20:00'),
(6, 7, 2025, 1, 80, 112000.00, 84.50, 'finance03', '2025-04-15 11:30:00', 'finance03', '2025-04-15 11:30:00'),
(7, 4, 2025, 3, 95, 145000.00, 90.20, 'finance01', '2025-10-20 15:00:00', 'finance01', '2025-10-20 15:00:00'),
(8, 6, 2025, 3, 70, 89000.00, 79.80, 'finance02', '2025-10-25 10:45:00', 'finance02', '2025-10-25 10:45:00'),
(9, 8, 2025, 4, 110, 168000.00, 87.60, 'finance03', '2026-01-05 09:30:00', 'finance03', '2026-01-05 09:30:00'),
(10, 9, 2025, 4, 85, 132000.00, 83.90, 'finance01', '2026-01-10 14:15:00', 'finance01', '2026-01-10 14:15:00');


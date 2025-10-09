-- 插入城市数据
INSERT INTO cities (city_code, city_name, province, country, timezone) VALUES 
('BJ', '北京', '北京市', '中国', 'Asia/Shanghai'),
('SH', '上海', '上海市', '中国', 'Asia/Shanghai'),
('GZ', '广州', '广东省', '中国', 'Asia/Shanghai'),
('SZ', '深圳', '广东省', '中国', 'Asia/Shanghai'),
('HZ', '杭州', '浙江省', '中国', 'Asia/Shanghai'),
('NJ', '南京', '江苏省', '中国', 'Asia/Shanghai'),
('CD', '成都', '四川省', '中国', 'Asia/Shanghai'),
('WH', '武汉', '湖北省', '中国', 'Asia/Shanghai');

-- 插入节假日类型数据
INSERT INTO holiday_types (type_code, type_name, description, is_national) VALUES 
('NEW_YEAR', '元旦', '公历新年', true),
('SPRING_FESTIVAL', '春节', '农历新年', true),
('TOMB_SWEEPING', '清明节', '清明节', true),
('LABOR_DAY', '劳动节', '五一劳动节', true),
('DRAGON_BOAT', '端午节', '端午节', true),
('MID_AUTUMN', '中秋节', '中秋节', true),
('NATIONAL_DAY', '国庆节', '国庆节', true),
('LOCAL_HOLIDAY', '地方节假日', '地方特色节假日', false);

-- 插入2024年国家法定节假日（以北京为例）
INSERT INTO holidays (city_id, holiday_type_id, holiday_date, holiday_name, "year", is_workday, is_active) VALUES 
-- 元旦
(1, 1, '2024-01-01', '元旦', 2024, false, true),
-- 春节
(1, 2, '2024-02-10', '春节', 2024, false, true),
(1, 2, '2024-02-11', '春节', 2024, false, true),
(1, 2, '2024-02-12', '春节', 2024, false, true),
(1, 2, '2024-02-13', '春节', 2024, false, true),
(1, 2, '2024-02-14', '春节', 2024, false, true),
(1, 2, '2024-02-15', '春节', 2024, false, true),
(1, 2, '2024-02-16', '春节', 2024, false, true),
(1, 2, '2024-02-17', '春节', 2024, false, true),
-- 清明节
(1, 3, '2024-04-04', '清明节', 2024, false, true),
(1, 3, '2024-04-05', '清明节', 2024, false, true),
(1, 3, '2024-04-06', '清明节', 2024, false, true),
-- 劳动节
(1, 4, '2024-05-01', '劳动节', 2024, false, true),
(1, 4, '2024-05-02', '劳动节', 2024, false, true),
(1, 4, '2024-05-03', '劳动节', 2024, false, true),
(1, 4, '2024-05-04', '劳动节', 2024, false, true),
(1, 4, '2024-05-05', '劳动节', 2024, false, true),
-- 端午节
(1, 5, '2024-06-10', '端午节', 2024, false, true),
-- 中秋节
(1, 6, '2024-09-15', '中秋节', 2024, false, true),
(1, 6, '2024-09-16', '中秋节', 2024, false, true),
(1, 6, '2024-09-17', '中秋节', 2024, false, true),
-- 国庆节
(1, 7, '2024-10-01', '国庆节', 2024, false, true),
(1, 7, '2024-10-02', '国庆节', 2024, false, true),
(1, 7, '2024-10-03', '国庆节', 2024, false, true),
(1, 7, '2024-10-04', '国庆节', 2024, false, true),
(1, 7, '2024-10-05', '国庆节', 2024, false, true),
(1, 7, '2024-10-06', '国庆节', 2024, false, true),
(1, 7, '2024-10-07', '国庆节', 2024, false, true);

-- 插入补班日
INSERT INTO holidays (city_id, holiday_type_id, holiday_date, holiday_name, is_workday, "year", is_active) VALUES 
(1, 2, '2024-02-04', '春节调休补班', true, 2024, true),
(1, 2, '2024-02-18', '春节调休补班', true, 2024, true),
(1, 4, '2024-04-28', '劳动节调休补班', true, 2024, true),
(1, 6, '2024-09-14', '中秋节调休补班', true, 2024, true),
(1, 7, '2024-09-29', '国庆节调休补班', true, 2024, true),
(1, 7, '2024-10-12', '国庆节调休补班', true, 2024, true);

-- 为其他城市复制相同的节假日数据（简化处理，实际应用中可能有差异）
INSERT INTO holidays (city_id, holiday_type_id, holiday_date, holiday_name, "year", is_workday, is_active)
SELECT 2, holiday_type_id, holiday_date, holiday_name, "year", is_workday, is_active FROM holidays WHERE city_id = 1;

INSERT INTO holidays (city_id, holiday_type_id, holiday_date, holiday_name, "year", is_workday, is_active)
SELECT 3, holiday_type_id, holiday_date, holiday_name, "year", is_workday, is_active FROM holidays WHERE city_id = 1;

INSERT INTO holidays (city_id, holiday_type_id, holiday_date, holiday_name, "year", is_workday, is_active)
SELECT 4, holiday_type_id, holiday_date, holiday_name, "year", is_workday, is_active FROM holidays WHERE city_id = 1;

INSERT INTO holidays (city_id, holiday_type_id, holiday_date, holiday_name, "year", is_workday, is_active)
SELECT 5, holiday_type_id, holiday_date, holiday_name, "year", is_workday, is_active FROM holidays WHERE city_id = 1;

INSERT INTO holidays (city_id, holiday_type_id, holiday_date, holiday_name, "year", is_workday, is_active)
SELECT 6, holiday_type_id, holiday_date, holiday_name, "year", is_workday, is_active FROM holidays WHERE city_id = 1;

INSERT INTO holidays (city_id, holiday_type_id, holiday_date, holiday_name, "year", is_workday, is_active)
SELECT 7, holiday_type_id, holiday_date, holiday_name, "year", is_workday, is_active FROM holidays WHERE city_id = 1;

INSERT INTO holidays (city_id, holiday_type_id, holiday_date, holiday_name, "year", is_workday, is_active)
SELECT 8, holiday_type_id, holiday_date, holiday_name, "year", is_workday, is_active FROM holidays WHERE city_id = 1;

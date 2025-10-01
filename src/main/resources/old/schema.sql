-- H2数据库兼容版本的初始化脚本

-- 创建城市表
CREATE TABLE cities (
    id IDENTITY PRIMARY KEY,
    city_code VARCHAR(10) UNIQUE NOT NULL,
    city_name VARCHAR(100) NOT NULL,
    province VARCHAR(50) NOT NULL,
    country VARCHAR(50) DEFAULT '中国',
    timezone VARCHAR(50) DEFAULT 'Asia/Shanghai',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- 创建节假日类型表
CREATE TABLE holiday_types (
    id IDENTITY PRIMARY KEY,
    type_code VARCHAR(20) UNIQUE NOT NULL,
    type_name VARCHAR(50) NOT NULL,
    description TEXT,
    is_national BOOLEAN DEFAULT true,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- 创建节假日表
CREATE TABLE holidays (
    id IDENTITY PRIMARY KEY,
    city_id INTEGER,
    holiday_type_id INTEGER,
    holiday_date DATE NOT NULL,
    holiday_name VARCHAR(100) NOT NULL,
    is_workday BOOLEAN DEFAULT false, -- 是否为补班日
    is_active BOOLEAN DEFAULT true,
    "year" INTEGER NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_holidays_city FOREIGN KEY (city_id) REFERENCES cities(id),
    CONSTRAINT fk_holidays_type FOREIGN KEY (holiday_type_id) REFERENCES holiday_types(id),
    CONSTRAINT uk_holidays_city_date UNIQUE(city_id, holiday_date)
);

-- 创建工作日规则表
CREATE TABLE workday_rules (
    id IDENTITY PRIMARY KEY,
    city_id INTEGER,
    rule_name VARCHAR(100) NOT NULL,
    weekend_days VARCHAR(20) DEFAULT '6,0', -- 周末日期，0=周日，6=周六
    is_default BOOLEAN DEFAULT false,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_workday_rules_city FOREIGN KEY (city_id) REFERENCES cities(id)
);

-- 创建日期计算日志表
CREATE TABLE calculation_logs (
    id IDENTITY PRIMARY KEY,
    city_id INTEGER,
    start_date DATE NOT NULL,
    end_date DATE NOT NULL,
    days_requested INTEGER NOT NULL,
    date_type VARCHAR(20) NOT NULL, -- NATURAL, WORKDAY
    skip_holiday BOOLEAN NOT NULL,
    actual_days INTEGER NOT NULL,
    skipped_holidays TEXT, -- JSON格式存储跳过的节假日
    calculation_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    client_ip VARCHAR(45),
    CONSTRAINT fk_calculation_logs_city FOREIGN KEY (city_id) REFERENCES cities(id)
);

-- 创建索引
CREATE INDEX idx_holidays_city_date ON holidays(city_id, holiday_date);
CREATE INDEX idx_holidays_year ON holidays("year");
CREATE INDEX idx_calculation_logs_date ON calculation_logs(calculation_time);
CREATE INDEX idx_cities_code ON cities(city_code);

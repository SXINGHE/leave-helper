CREATE TABLE employees (
       id UUID PRIMARY KEY,
       lan_id VARCHAR(50) UNIQUE NOT NULL,  -- 工号唯一
       name VARCHAR(100) NOT NULL,
       gender VARCHAR(10) NOT NULL DEFAULT 'male',
       age INTEGER,
       position VARCHAR(100),
       department VARCHAR(100),

       created_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,
       updated_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP
);

-- 索引
CREATE INDEX idx_employees_lan_id ON employees(lan_id);

-- 更新时间触发器（同前）
CREATE TRIGGER update_employees_updated_at
    BEFORE UPDATE ON employees
    FOR EACH ROW
    EXECUTE FUNCTION update_updated_at_column();

COMMENT ON TABLE employees IS '员工基本信息表';


CREATE TABLE insurance_policies (
    id UUID PRIMARY KEY,
    region VARCHAR(100) NOT NULL UNIQUE,  -- 如 '北京市', '广东省'
    reward_days INTEGER NOT NULL,         -- 地方奖励假天数
    cesarean_additional_days INTEGER NOT NULL DEFAULT 15,  -- 剖宫产/难产加假
    multiple_birth_additional_days INTEGER NOT NULL DEFAULT 15, -- 每多一婴加假
    late_pregnancy_bonus_days INTEGER NOT NULL DEFAULT 0,   -- 晚育假（如有）
    is_active BOOLEAN NOT NULL DEFAULT TRUE,  -- 是否启用
    effective_from DATE,       -- 生效日期
    effective_to DATE,         -- 失效日期（用于历史政策）

    created_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP
);

-- 索引
CREATE INDEX idx_insurance_policies_region ON insurance_policies(region);
CREATE INDEX idx_insurance_policies_active ON insurance_policies(is_active);

-- 更新时间触发器...

COMMENT ON TABLE insurance_policies IS '各地生育保险政策配置表';


CREATE TABLE maternity_calculations (
    id UUID PRIMARY KEY,

-- 外键关联
    employee_id BIGINT NOT NULL REFERENCES employees(id) ON DELETE CASCADE,
    policy_id BIGINT NOT NULL REFERENCES insurance_policies(id) ON DELETE RESTRICT,

-- 测算输入参数
    expected_delivery_date DATE NOT NULL,
    delivery_type VARCHAR(20) NOT NULL DEFAULT 'single_vaginal', -- 可枚举
    is_cesarean BOOLEAN NOT NULL DEFAULT FALSE,
    is_multiple_birth BOOLEAN NOT NULL DEFAULT FALSE,
    multiple_birth_count INTEGER CHECK (multiple_birth_count >= 2),
    is_late_pregnancy BOOLEAN NOT NULL DEFAULT FALSE,
    insurance_months INTEGER NOT NULL,

-- 薪酬数据
    employee_avg_salary NUMERIC(10,2) NOT NULL,
    company_avg_salary NUMERIC(10,2) NOT NULL,

-- 计算结果（摘要）
    total_maternity_leave_days INTEGER NOT NULL,
    maternity_allowance NUMERIC(12,2) NOT NULL,
    employer_top_up NUMERIC(12,2) NOT NULL,
    total_cost_to_employer NUMERIC(12,2) NOT NULL,

-- 状态与操作
    status VARCHAR(20) NOT NULL DEFAULT 'completed'
        CHECK (status IN ('completed', 'pending', 'canceled')),
    calculated_by VARCHAR(100) NOT NULL,
    calculated_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,
    notes TEXT
);

-- 索引
CREATE INDEX idx_maternity_employee ON maternity_calculations(employee_id);
CREATE INDEX idx_maternity_policy ON maternity_calculations(policy_id);
CREATE INDEX idx_maternity_calculated_at ON maternity_calculations(calculated_at);
CREATE INDEX idx_maternity_status ON maternity_calculations(status);

-- 更新时间触发器...

COMMENT ON TABLE maternity_calculations IS '产假测算主记录表（每次测算一条）';
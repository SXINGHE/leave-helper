-- t_calendar_type
CREATE TABLE IF NOT EXISTS t_calendar_type (
  id           BIGSERIAL PRIMARY KEY,
  code         VARCHAR(20)  NOT NULL UNIQUE,
  name         VARCHAR(50)  NOT NULL,
  description  TEXT,
  created_at   TIMESTAMP
);

-- t_city
CREATE TABLE IF NOT EXISTS t_city (
  id          BIGSERIAL PRIMARY KEY,
  city_code   VARCHAR(10)   NOT NULL UNIQUE,
  city_name   VARCHAR(100)  NOT NULL,
  province    VARCHAR(50)   NOT NULL,
  country     VARCHAR(50)   DEFAULT '中国',
  timezone    VARCHAR(50)   DEFAULT 'Asia/Shanghai',
  created_at  TIMESTAMP,
  updated_at  TIMESTAMP
);

-- t_comment
CREATE TABLE IF NOT EXISTS t_comment (
  id            BIGSERIAL PRIMARY KEY,
  comment_code  VARCHAR(100),
  page_name     VARCHAR(100),
  description   TEXT
);

-- t_policy
CREATE TABLE IF NOT EXISTS t_policy (
  id                     BIGSERIAL PRIMARY KEY,
  city_name              VARCHAR(255),
  statutory_policy       JSONB,
  dystocia_policy        JSONB,
  more_infant_policy     JSONB,
  other_extended_policy  JSONB,
  abortion_policy        JSONB,
  allowance_policy       JSONB
);

-- 推荐索引：常按城市查询政策
CREATE INDEX IF NOT EXISTS idx_t_policy_city_name ON t_policy (city_name);

-- t_special_date
CREATE TABLE IF NOT EXISTS t_special_date (
  id             BIGSERIAL PRIMARY KEY,
  calendar_code  VARCHAR(255) NOT NULL,
  calendar_date  DATE         NOT NULL,
  description    VARCHAR(100) NOT NULL,
  is_workday     BOOLEAN      NOT NULL DEFAULT FALSE,
  is_active      BOOLEAN      NOT NULL DEFAULT TRUE,
  "year"         INTEGER      NOT NULL,
  created_at     TIMESTAMP,
  updated_at     TIMESTAMP
);

-- 推荐索引：覆盖你的仓储查询条件
-- 1) 通过 calendar_code + calendar_date 范围 + is_workday/is_active 过滤
CREATE INDEX IF NOT EXISTS idx_t_special_date_code_date_work_active
  ON t_special_date (calendar_code, calendar_date, is_workday, is_active);

-- 2) 通过 calendar_code + year 过滤
CREATE INDEX IF NOT EXISTS idx_t_special_date_code_year
  ON t_special_date (calendar_code, "year");

  CREATE TABLE tickets (
      id BIGSERIAL PRIMARY KEY,
      title VARCHAR(255) NOT NULL,
      description TEXT,
      status VARCHAR(50) NOT NULL DEFAULT 'PENDING',
      created_by VARCHAR(255) NOT NULL,
      approved_by VARCHAR(255),
      comments TEXT,
      created_at TIMESTAMP WITH TIME ZONE NOT NULL,
      updated_at TIMESTAMP WITH TIME ZONE NOT NULL,

      -- 外键约束，关联到users表的username字段
      CONSTRAINT fk_tickets_created_by
          FOREIGN KEY (created_by)
          REFERENCES users(username) ON DELETE CASCADE,

      CONSTRAINT fk_tickets_approved_by
          FOREIGN KEY (approved_by)
          REFERENCES users(username) ON DELETE SET NULL
  );

  -- 添加索引以优化查询性能
  CREATE INDEX idx_tickets_created_by ON tickets (created_by);
  CREATE INDEX idx_tickets_status ON tickets (status);
  CREATE INDEX idx_tickets_created_at ON tickets (created_at);


  -- 创建 t_leave_history 表
  CREATE TABLE t_leave_history (
      id BIGSERIAL PRIMARY KEY,
      staff_name VARCHAR(255),
      city_code VARCHAR(50),
      leave_start_date DATE,
      leave_detail JSONB,
      allowance_detail JSONB,
      calculate_comments JSONB,
      abortion BOOLEAN DEFAULT FALSE
  );

  -- 添加注释
  COMMENT ON TABLE t_leave_history IS '员工请假历史记录表';
  COMMENT ON COLUMN t_leave_history.id IS '主键ID';
  COMMENT ON COLUMN t_leave_history.staff_name IS '员工姓名';
  COMMENT ON COLUMN t_leave_history.city_code IS '城市代码';
  COMMENT ON COLUMN t_leave_history.leave_start_date IS '请假开始日期';
  COMMENT ON COLUMN t_leave_history.leave_detail IS '请假详情(JSON格式)';
  COMMENT ON COLUMN t_leave_history.allowance_detail IS '津贴详情(JSON格式)';
  COMMENT ON COLUMN t_leave_history.calculate_comments IS '计算说明(JSON格式)';
  COMMENT ON COLUMN t_leave_history.abortion IS '是否为流产';

  -- 创建索引
  CREATE INDEX idx_leave_history_staff_name ON t_leave_history(staff_name);
  CREATE INDEX idx_leave_history_leave_start_date ON t_leave_history(leave_start_date);
  CREATE INDEX idx_leave_history_city_code ON t_leave_history(city_code);
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
-- 2025年中国大陆法定节假日及调班安排

-- 元旦：1月1日放假，共1天
INSERT INTO public.t_special_date
(id, calendar_code, calendar_date, description, is_workday, is_active, "year", created_at, updated_at)
VALUES(nextval('t_special_date_id_seq'::regclass), 'CN', '2025-01-01', '元旦', false, true, 2025, null, null);

-- 春节：1月28日至2月4日放假调休，共8天。1月26日（星期日）、2月8日（星期六）上班
INSERT INTO public.t_special_date
(id, calendar_code, calendar_date, description, is_workday, is_active, "year", created_at, updated_at)
VALUES(nextval('t_special_date_id_seq'::regclass), 'CN', '2025-01-26', '春节-调班', true, true, 2025, null, null);

INSERT INTO public.t_special_date
(id, calendar_code, calendar_date, description, is_workday, is_active, "year", created_at, updated_at)
VALUES(nextval('t_special_date_id_seq'::regclass), 'CN', '2025-01-28', '春节', false, true, 2025, null, null);

INSERT INTO public.t_special_date
(id, calendar_code, calendar_date, description, is_workday, is_active, "year", created_at, updated_at)
VALUES(nextval('t_special_date_id_seq'::regclass), 'CN', '2025-01-29', '春节', false, true, 2025, null, null);

INSERT INTO public.t_special_date
(id, calendar_code, calendar_date, description, is_workday, is_active, "year", created_at, updated_at)
VALUES(nextval('t_special_date_id_seq'::regclass), 'CN', '2025-01-30', '春节', false, true, 2025, null, null);

INSERT INTO public.t_special_date
(id, calendar_code, calendar_date, description, is_workday, is_active, "year", created_at, updated_at)
VALUES(nextval('t_special_date_id_seq'::regclass), 'CN', '2025-01-31', '春节', false, true, 2025, null, null);

INSERT INTO public.t_special_date
(id, calendar_code, calendar_date, description, is_workday, is_active, "year", created_at, updated_at)
VALUES(nextval('t_special_date_id_seq'::regclass), 'CN', '2025-02-01', '春节', false, true, 2025, null, null);

INSERT INTO public.t_special_date
(id, calendar_code, calendar_date, description, is_workday, is_active, "year", created_at, updated_at)
VALUES(nextval('t_special_date_id_seq'::regclass), 'CN', '2025-02-02', '春节', false, true, 2025, null, null);

INSERT INTO public.t_special_date
(id, calendar_code, calendar_date, description, is_workday, is_active, "year", created_at, updated_at)
VALUES(nextval('t_special_date_id_seq'::regclass), 'CN', '2025-02-03', '春节', false, true, 2025, null, null);

INSERT INTO public.t_special_date
(id, calendar_code, calendar_date, description, is_workday, is_active, "year", created_at, updated_at)
VALUES(nextval('t_special_date_id_seq'::regclass), 'CN', '2025-02-04', '春节', false, true, 2025, null, null);

INSERT INTO public.t_special_date
(id, calendar_code, calendar_date, description, is_workday, is_active, "year", created_at, updated_at)
VALUES(nextval('t_special_date_id_seq'::regclass), 'CN', '2025-02-08', '春节-调班', true, true, 2025, null, null);

-- 清明节：4月4日至6日放假调休，共3天
INSERT INTO public.t_special_date
(id, calendar_code, calendar_date, description, is_workday, is_active, "year", created_at, updated_at)
VALUES(nextval('t_special_date_id_seq'::regclass), 'CN', '2025-04-04', '清明', false, true, 2025, null, null);

INSERT INTO public.t_special_date
(id, calendar_code, calendar_date, description, is_workday, is_active, "year", created_at, updated_at)
VALUES(nextval('t_special_date_id_seq'::regclass), 'CN', '2025-04-05', '清明', false, true, 2025, null, null);

INSERT INTO public.t_special_date
(id, calendar_code, calendar_date, description, is_workday, is_active, "year", created_at, updated_at)
VALUES(nextval('t_special_date_id_seq'::regclass), 'CN', '2025-04-06', '清明', false, true, 2025, null, null);

-- 劳动节：5月1日至5日放假调休，共5天。4月27日（星期日）上班
INSERT INTO public.t_special_date
(id, calendar_code, calendar_date, description, is_workday, is_active, "year", created_at, updated_at)
VALUES(nextval('t_special_date_id_seq'::regclass), 'CN', '2025-04-27', '五一-调班', true, true, 2025, null, null);

INSERT INTO public.t_special_date
(id, calendar_code, calendar_date, description, is_workday, is_active, "year", created_at, updated_at)
VALUES(nextval('t_special_date_id_seq'::regclass), 'CN', '2025-05-01', '五一', false, true, 2025, null, null);

INSERT INTO public.t_special_date
(id, calendar_code, calendar_date, description, is_workday, is_active, "year", created_at, updated_at)
VALUES(nextval('t_special_date_id_seq'::regclass), 'CN', '2025-05-02', '五一', false, true, 2025, null, null);

INSERT INTO public.t_special_date
(id, calendar_code, calendar_date, description, is_workday, is_active, "year", created_at, updated_at)
VALUES(nextval('t_special_date_id_seq'::regclass), 'CN', '2025-05-03', '五一', false, true, 2025, null, null);

INSERT INTO public.t_special_date
(id, calendar_code, calendar_date, description, is_workday, is_active, "year", created_at, updated_at)
VALUES(nextval('t_special_date_id_seq'::regclass), 'CN', '2025-05-04', '五一', false, true, 2025, null, null);

INSERT INTO public.t_special_date
(id, calendar_code, calendar_date, description, is_workday, is_active, "year", created_at, updated_at)
VALUES(nextval('t_special_date_id_seq'::regclass), 'CN', '2025-05-05', '五一', false, true, 2025, null, null);

-- 端午节：5月31日至6月2日放假调休，共3天
INSERT INTO public.t_special_date
(id, calendar_code, calendar_date, description, is_workday, is_active, "year", created_at, updated_at)
VALUES(nextval('t_special_date_id_seq'::regclass), 'CN', '2025-05-31', '端午', false, true, 2025, null, null);

INSERT INTO public.t_special_date
(id, calendar_code, calendar_date, description, is_workday, is_active, "year", created_at, updated_at)
VALUES(nextval('t_special_date_id_seq'::regclass), 'CN', '2025-06-01', '端午', false, true, 2025, null, null);

INSERT INTO public.t_special_date
(id, calendar_code, calendar_date, description, is_workday, is_active, "year", created_at, updated_at)
VALUES(nextval('t_special_date_id_seq'::regclass), 'CN', '2025-06-02', '端午', false, true, 2025, null, null);

-- 中秋节：10月6日放假，与国庆节连休
-- 国庆节：10月1日至7日放假调休，共7天。9月28日（星期日）、10月11日（星期六）上班
INSERT INTO public.t_special_date
(id, calendar_code, calendar_date, description, is_workday, is_active, "year", created_at, updated_at)
VALUES(nextval('t_special_date_id_seq'::regclass), 'CN', '2025-09-28', '中秋国庆-调班', true, true, 2025, null, null);

INSERT INTO public.t_special_date
(id, calendar_code, calendar_date, description, is_workday, is_active, "year", created_at, updated_at)
VALUES(nextval('t_special_date_id_seq'::regclass), 'CN', '2025-10-01', '中秋国庆', false, true, 2025, null, null);

INSERT INTO public.t_special_date
(id, calendar_code, calendar_date, description, is_workday, is_active, "year", created_at, updated_at)
VALUES(nextval('t_special_date_id_seq'::regclass), 'CN', '2025-10-02', '中秋国庆', false, true, 2025, null, null);

INSERT INTO public.t_special_date
(id, calendar_code, calendar_date, description, is_workday, is_active, "year", created_at, updated_at)
VALUES(nextval('t_special_date_id_seq'::regclass), 'CN', '2025-10-03', '中秋国庆', false, true, 2025, null, null);

INSERT INTO public.t_special_date
(id, calendar_code, calendar_date, description, is_workday, is_active, "year", created_at, updated_at)
VALUES(nextval('t_special_date_id_seq'::regclass), 'CN', '2025-10-04', '中秋国庆', false, true, 2025, null, null);

INSERT INTO public.t_special_date
(id, calendar_code, calendar_date, description, is_workday, is_active, "year", created_at, updated_at)
VALUES(nextval('t_special_date_id_seq'::regclass), 'CN', '2025-10-05', '中秋国庆', false, true, 2025, null, null);

INSERT INTO public.t_special_date
(id, calendar_code, calendar_date, description, is_workday, is_active, "year", created_at, updated_at)
VALUES(nextval('t_special_date_id_seq'::regclass), 'CN', '2025-10-06', '中秋国庆', false, true, 2025, null, null);

INSERT INTO public.t_special_date
(id, calendar_code, calendar_date, description, is_workday, is_active, "year", created_at, updated_at)
VALUES(nextval('t_special_date_id_seq'::regclass), 'CN', '2025-10-07', '中秋国庆', false, true, 2025, null, null);

INSERT INTO public.t_special_date
(id, calendar_code, calendar_date, description, is_workday, is_active, "year", created_at, updated_at)
VALUES(nextval('t_special_date_id_seq'::regclass), 'CN', '2025-10-11', '中秋国庆-调班', true, true, 2025, null, null);

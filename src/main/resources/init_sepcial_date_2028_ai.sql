-- 2028年中国大陆法定节假日及调班安排

-- 元旦：1月1日放假，与周末连休
INSERT INTO public.t_special_date
(id, calendar_code, calendar_date, description, is_workday, is_active, "year", created_at, updated_at)
VALUES(nextval('t_special_date_id_seq'::regclass), 'CN', '2028-01-01', '元旦', false, true, 2028, null, null);

-- 春节：1月26日放假调休，共7天。1月24日、1月23日、2月2日、2月3日上班
INSERT INTO public.t_special_date
(id, calendar_code, calendar_date, description, is_workday, is_active, "year", created_at, updated_at)
VALUES(nextval('t_special_date_id_seq'::regclass), 'CN', '2028-01-24', '春节-调班', true, true, 2028, null, null);
INSERT INTO public.t_special_date
(id, calendar_code, calendar_date, description, is_workday, is_active, "year", created_at, updated_at)
VALUES(nextval('t_special_date_id_seq'::regclass), 'CN', '2028-01-23', '春节-调班', true, true, 2028, null, null);
INSERT INTO public.t_special_date
(id, calendar_code, calendar_date, description, is_workday, is_active, "year", created_at, updated_at)
VALUES(nextval('t_special_date_id_seq'::regclass), 'CN', '2028-01-25', '春节', false, true, 2028, null, null);
INSERT INTO public.t_special_date
(id, calendar_code, calendar_date, description, is_workday, is_active, "year", created_at, updated_at)
VALUES(nextval('t_special_date_id_seq'::regclass), 'CN', '2028-01-26', '春节', false, true, 2028, null, null);
INSERT INTO public.t_special_date
(id, calendar_code, calendar_date, description, is_workday, is_active, "year", created_at, updated_at)
VALUES(nextval('t_special_date_id_seq'::regclass), 'CN', '2028-01-27', '春节', false, true, 2028, null, null);
INSERT INTO public.t_special_date
(id, calendar_code, calendar_date, description, is_workday, is_active, "year", created_at, updated_at)
VALUES(nextval('t_special_date_id_seq'::regclass), 'CN', '2028-01-28', '春节', false, true, 2028, null, null);
INSERT INTO public.t_special_date
(id, calendar_code, calendar_date, description, is_workday, is_active, "year", created_at, updated_at)
VALUES(nextval('t_special_date_id_seq'::regclass), 'CN', '2028-01-29', '春节', false, true, 2028, null, null);
INSERT INTO public.t_special_date
(id, calendar_code, calendar_date, description, is_workday, is_active, "year", created_at, updated_at)
VALUES(nextval('t_special_date_id_seq'::regclass), 'CN', '2028-01-30', '春节', false, true, 2028, null, null);
INSERT INTO public.t_special_date
(id, calendar_code, calendar_date, description, is_workday, is_active, "year", created_at, updated_at)
VALUES(nextval('t_special_date_id_seq'::regclass), 'CN', '2028-01-31', '春节', false, true, 2028, null, null);
INSERT INTO public.t_special_date
(id, calendar_code, calendar_date, description, is_workday, is_active, "year", created_at, updated_at)
VALUES(nextval('t_special_date_id_seq'::regclass), 'CN', '2028-02-01', '春节', false, true, 2028, null, null);
INSERT INTO public.t_special_date
(id, calendar_code, calendar_date, description, is_workday, is_active, "year", created_at, updated_at)
VALUES(nextval('t_special_date_id_seq'::regclass), 'CN', '2028-02-02', '春节-调班', true, true, 2028, null, null);
INSERT INTO public.t_special_date
(id, calendar_code, calendar_date, description, is_workday, is_active, "year", created_at, updated_at)
VALUES(nextval('t_special_date_id_seq'::regclass), 'CN', '2028-02-03', '春节-调班', true, true, 2028, null, null);

-- 清明节：4月5日放假
INSERT INTO public.t_special_date
(id, calendar_code, calendar_date, description, is_workday, is_active, "year", created_at, updated_at)
VALUES(nextval('t_special_date_id_seq'::regclass), 'CN', '2028-04-05', '清明', false, true, 2028, null, null);

-- 劳动节：5月1日至5月5日放假调休，共5天。4月30日、4月29日上班
INSERT INTO public.t_special_date
(id, calendar_code, calendar_date, description, is_workday, is_active, "year", created_at, updated_at)
VALUES(nextval('t_special_date_id_seq'::regclass), 'CN', '2028-04-30', '五一-调班', true, true, 2028, null, null);
INSERT INTO public.t_special_date
(id, calendar_code, calendar_date, description, is_workday, is_active, "year", created_at, updated_at)
VALUES(nextval('t_special_date_id_seq'::regclass), 'CN', '2028-04-29', '五一-调班', true, true, 2028, null, null);
INSERT INTO public.t_special_date
(id, calendar_code, calendar_date, description, is_workday, is_active, "year", created_at, updated_at)
VALUES(nextval('t_special_date_id_seq'::regclass), 'CN', '2028-05-01', '五一', false, true, 2028, null, null);
INSERT INTO public.t_special_date
(id, calendar_code, calendar_date, description, is_workday, is_active, "year", created_at, updated_at)
VALUES(nextval('t_special_date_id_seq'::regclass), 'CN', '2028-05-02', '五一', false, true, 2028, null, null);
INSERT INTO public.t_special_date
(id, calendar_code, calendar_date, description, is_workday, is_active, "year", created_at, updated_at)
VALUES(nextval('t_special_date_id_seq'::regclass), 'CN', '2028-05-03', '五一', false, true, 2028, null, null);
INSERT INTO public.t_special_date
(id, calendar_code, calendar_date, description, is_workday, is_active, "year", created_at, updated_at)
VALUES(nextval('t_special_date_id_seq'::regclass), 'CN', '2028-05-04', '五一', false, true, 2028, null, null);
INSERT INTO public.t_special_date
(id, calendar_code, calendar_date, description, is_workday, is_active, "year", created_at, updated_at)
VALUES(nextval('t_special_date_id_seq'::regclass), 'CN', '2028-05-05', '五一', false, true, 2028, null, null);

-- 端午节：5月28日至5月30日放假调休，共3天
INSERT INTO public.t_special_date
(id, calendar_code, calendar_date, description, is_workday, is_active, "year", created_at, updated_at)
VALUES(nextval('t_special_date_id_seq'::regclass), 'CN', '2028-05-28', '端午', false, true, 2028, null, null);
INSERT INTO public.t_special_date
(id, calendar_code, calendar_date, description, is_workday, is_active, "year", created_at, updated_at)
VALUES(nextval('t_special_date_id_seq'::regclass), 'CN', '2028-05-29', '端午', false, true, 2028, null, null);
INSERT INTO public.t_special_date
(id, calendar_code, calendar_date, description, is_workday, is_active, "year", created_at, updated_at)
VALUES(nextval('t_special_date_id_seq'::regclass), 'CN', '2028-05-30', '端午', false, true, 2028, null, null);

-- 中秋节：10月3日至10月5日放假调休，共3天
INSERT INTO public.t_special_date
(id, calendar_code, calendar_date, description, is_workday, is_active, "year", created_at, updated_at)
VALUES(nextval('t_special_date_id_seq'::regclass), 'CN', '2028-10-03', '中秋', false, true, 2028, null, null);
INSERT INTO public.t_special_date
(id, calendar_code, calendar_date, description, is_workday, is_active, "year", created_at, updated_at)
VALUES(nextval('t_special_date_id_seq'::regclass), 'CN', '2028-10-04', '中秋', false, true, 2028, null, null);
INSERT INTO public.t_special_date
(id, calendar_code, calendar_date, description, is_workday, is_active, "year", created_at, updated_at)
VALUES(nextval('t_special_date_id_seq'::regclass), 'CN', '2028-10-05', '中秋', false, true, 2028, null, null);

-- 国庆节：10月1日至10月7日放假调休，共7天。9月30日、9月29日上班
INSERT INTO public.t_special_date
(id, calendar_code, calendar_date, description, is_workday, is_active, "year", created_at, updated_at)
VALUES(nextval('t_special_date_id_seq'::regclass), 'CN', '2028-09-30', '国庆-调班', true, true, 2028, null, null);
INSERT INTO public.t_special_date
(id, calendar_code, calendar_date, description, is_workday, is_active, "year", created_at, updated_at)
VALUES(nextval('t_special_date_id_seq'::regclass), 'CN', '2028-09-29', '国庆-调班', true, true, 2028, null, null);
INSERT INTO public.t_special_date
(id, calendar_code, calendar_date, description, is_workday, is_active, "year", created_at, updated_at)
VALUES(nextval('t_special_date_id_seq'::regclass), 'CN', '2028-10-01', '国庆', false, true, 2028, null, null);
INSERT INTO public.t_special_date
(id, calendar_code, calendar_date, description, is_workday, is_active, "year", created_at, updated_at)
VALUES(nextval('t_special_date_id_seq'::regclass), 'CN', '2028-10-02', '国庆', false, true, 2028, null, null);
INSERT INTO public.t_special_date
(id, calendar_code, calendar_date, description, is_workday, is_active, "year", created_at, updated_at)
VALUES(nextval('t_special_date_id_seq'::regclass), 'CN', '2028-10-03', '国庆', false, true, 2028, null, null);
INSERT INTO public.t_special_date
(id, calendar_code, calendar_date, description, is_workday, is_active, "year", created_at, updated_at)
VALUES(nextval('t_special_date_id_seq'::regclass), 'CN', '2028-10-04', '国庆', false, true, 2028, null, null);
INSERT INTO public.t_special_date
(id, calendar_code, calendar_date, description, is_workday, is_active, "year", created_at, updated_at)
VALUES(nextval('t_special_date_id_seq'::regclass), 'CN', '2028-10-05', '国庆', false, true, 2028, null, null);
INSERT INTO public.t_special_date
(id, calendar_code, calendar_date, description, is_workday, is_active, "year", created_at, updated_at)
VALUES(nextval('t_special_date_id_seq'::regclass), 'CN', '2028-10-06', '国庆', false, true, 2028, null, null);
INSERT INTO public.t_special_date
(id, calendar_code, calendar_date, description, is_workday, is_active, "year", created_at, updated_at)
VALUES(nextval('t_special_date_id_seq'::regclass), 'CN', '2028-10-07', '国庆', false, true, 2028, null, null);
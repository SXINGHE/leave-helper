-- 2035年中国大陆法定节假日及调班安排

-- 元旦：1月1日放假，与周末连休
INSERT INTO public.t_special_date
(id, calendar_code, calendar_date, description, is_workday, is_active, "year", created_at, updated_at)
VALUES(nextval('t_special_date_id_seq'::regclass), 'CN', '2035-01-01', '元旦', false, true, 2035, null, null);

-- 春节：2月8日放假调休，共7天。2月6日、2月5日、2月15日、2月16日上班
INSERT INTO public.t_special_date
(id, calendar_code, calendar_date, description, is_workday, is_active, "year", created_at, updated_at)
VALUES(nextval('t_special_date_id_seq'::regclass), 'CN', '2035-02-06', '春节-调班', true, true, 2035, null, null);
INSERT INTO public.t_special_date
(id, calendar_code, calendar_date, description, is_workday, is_active, "year", created_at, updated_at)
VALUES(nextval('t_special_date_id_seq'::regclass), 'CN', '2035-02-05', '春节-调班', true, true, 2035, null, null);
INSERT INTO public.t_special_date
(id, calendar_code, calendar_date, description, is_workday, is_active, "year", created_at, updated_at)
VALUES(nextval('t_special_date_id_seq'::regclass), 'CN', '2035-02-07', '春节', false, true, 2035, null, null);
INSERT INTO public.t_special_date
(id, calendar_code, calendar_date, description, is_workday, is_active, "year", created_at, updated_at)
VALUES(nextval('t_special_date_id_seq'::regclass), 'CN', '2035-02-08', '春节', false, true, 2035, null, null);
INSERT INTO public.t_special_date
(id, calendar_code, calendar_date, description, is_workday, is_active, "year", created_at, updated_at)
VALUES(nextval('t_special_date_id_seq'::regclass), 'CN', '2035-02-09', '春节', false, true, 2035, null, null);
INSERT INTO public.t_special_date
(id, calendar_code, calendar_date, description, is_workday, is_active, "year", created_at, updated_at)
VALUES(nextval('t_special_date_id_seq'::regclass), 'CN', '2035-02-10', '春节', false, true, 2035, null, null);
INSERT INTO public.t_special_date
(id, calendar_code, calendar_date, description, is_workday, is_active, "year", created_at, updated_at)
VALUES(nextval('t_special_date_id_seq'::regclass), 'CN', '2035-02-11', '春节', false, true, 2035, null, null);
INSERT INTO public.t_special_date
(id, calendar_code, calendar_date, description, is_workday, is_active, "year", created_at, updated_at)
VALUES(nextval('t_special_date_id_seq'::regclass), 'CN', '2035-02-12', '春节', false, true, 2035, null, null);
INSERT INTO public.t_special_date
(id, calendar_code, calendar_date, description, is_workday, is_active, "year", created_at, updated_at)
VALUES(nextval('t_special_date_id_seq'::regclass), 'CN', '2035-02-13', '春节', false, true, 2035, null, null);
INSERT INTO public.t_special_date
(id, calendar_code, calendar_date, description, is_workday, is_active, "year", created_at, updated_at)
VALUES(nextval('t_special_date_id_seq'::regclass), 'CN', '2035-02-14', '春节', false, true, 2035, null, null);
INSERT INTO public.t_special_date
(id, calendar_code, calendar_date, description, is_workday, is_active, "year", created_at, updated_at)
VALUES(nextval('t_special_date_id_seq'::regclass), 'CN', '2035-02-15', '春节-调班', true, true, 2035, null, null);
INSERT INTO public.t_special_date
(id, calendar_code, calendar_date, description, is_workday, is_active, "year", created_at, updated_at)
VALUES(nextval('t_special_date_id_seq'::regclass), 'CN', '2035-02-16', '春节-调班', true, true, 2035, null, null);

-- 清明节：4月4日放假
INSERT INTO public.t_special_date
(id, calendar_code, calendar_date, description, is_workday, is_active, "year", created_at, updated_at)
VALUES(nextval('t_special_date_id_seq'::regclass), 'CN', '2035-04-04', '清明', false, true, 2035, null, null);

-- 劳动节：5月1日至5月5日放假调休，共5天。4月30日、4月29日上班
INSERT INTO public.t_special_date
(id, calendar_code, calendar_date, description, is_workday, is_active, "year", created_at, updated_at)
VALUES(nextval('t_special_date_id_seq'::regclass), 'CN', '2035-04-30', '五一-调班', true, true, 2035, null, null);
INSERT INTO public.t_special_date
(id, calendar_code, calendar_date, description, is_workday, is_active, "year", created_at, updated_at)
VALUES(nextval('t_special_date_id_seq'::regclass), 'CN', '2035-04-29', '五一-调班', true, true, 2035, null, null);
INSERT INTO public.t_special_date
(id, calendar_code, calendar_date, description, is_workday, is_active, "year", created_at, updated_at)
VALUES(nextval('t_special_date_id_seq'::regclass), 'CN', '2035-05-01', '五一', false, true, 2035, null, null);
INSERT INTO public.t_special_date
(id, calendar_code, calendar_date, description, is_workday, is_active, "year", created_at, updated_at)
VALUES(nextval('t_special_date_id_seq'::regclass), 'CN', '2035-05-02', '五一', false, true, 2035, null, null);
INSERT INTO public.t_special_date
(id, calendar_code, calendar_date, description, is_workday, is_active, "year", created_at, updated_at)
VALUES(nextval('t_special_date_id_seq'::regclass), 'CN', '2035-05-03', '五一', false, true, 2035, null, null);
INSERT INTO public.t_special_date
(id, calendar_code, calendar_date, description, is_workday, is_active, "year", created_at, updated_at)
VALUES(nextval('t_special_date_id_seq'::regclass), 'CN', '2035-05-04', '五一', false, true, 2035, null, null);
INSERT INTO public.t_special_date
(id, calendar_code, calendar_date, description, is_workday, is_active, "year", created_at, updated_at)
VALUES(nextval('t_special_date_id_seq'::regclass), 'CN', '2035-05-05', '五一', false, true, 2035, null, null);

-- 端午节：6月10日至6月12日放假调休，共3天
INSERT INTO public.t_special_date
(id, calendar_code, calendar_date, description, is_workday, is_active, "year", created_at, updated_at)
VALUES(nextval('t_special_date_id_seq'::regclass), 'CN', '2035-06-10', '端午', false, true, 2035, null, null);
INSERT INTO public.t_special_date
(id, calendar_code, calendar_date, description, is_workday, is_active, "year", created_at, updated_at)
VALUES(nextval('t_special_date_id_seq'::regclass), 'CN', '2035-06-11', '端午', false, true, 2035, null, null);
INSERT INTO public.t_special_date
(id, calendar_code, calendar_date, description, is_workday, is_active, "year", created_at, updated_at)
VALUES(nextval('t_special_date_id_seq'::regclass), 'CN', '2035-06-12', '端午', false, true, 2035, null, null);

-- 中秋节：9月16日至9月18日放假调休，共3天
INSERT INTO public.t_special_date
(id, calendar_code, calendar_date, description, is_workday, is_active, "year", created_at, updated_at)
VALUES(nextval('t_special_date_id_seq'::regclass), 'CN', '2035-09-16', '中秋', false, true, 2035, null, null);
INSERT INTO public.t_special_date
(id, calendar_code, calendar_date, description, is_workday, is_active, "year", created_at, updated_at)
VALUES(nextval('t_special_date_id_seq'::regclass), 'CN', '2035-09-17', '中秋', false, true, 2035, null, null);
INSERT INTO public.t_special_date
(id, calendar_code, calendar_date, description, is_workday, is_active, "year", created_at, updated_at)
VALUES(nextval('t_special_date_id_seq'::regclass), 'CN', '2035-09-18', '中秋', false, true, 2035, null, null);

-- 国庆节：10月1日至10月7日放假调休，共7天。9月30日、9月29日上班
INSERT INTO public.t_special_date
(id, calendar_code, calendar_date, description, is_workday, is_active, "year", created_at, updated_at)
VALUES(nextval('t_special_date_id_seq'::regclass), 'CN', '2035-09-30', '国庆-调班', true, true, 2035, null, null);
INSERT INTO public.t_special_date
(id, calendar_code, calendar_date, description, is_workday, is_active, "year", created_at, updated_at)
VALUES(nextval('t_special_date_id_seq'::regclass), 'CN', '2035-09-29', '国庆-调班', true, true, 2035, null, null);
INSERT INTO public.t_special_date
(id, calendar_code, calendar_date, description, is_workday, is_active, "year", created_at, updated_at)
VALUES(nextval('t_special_date_id_seq'::regclass), 'CN', '2035-10-01', '国庆', false, true, 2035, null, null);
INSERT INTO public.t_special_date
(id, calendar_code, calendar_date, description, is_workday, is_active, "year", created_at, updated_at)
VALUES(nextval('t_special_date_id_seq'::regclass), 'CN', '2035-10-02', '国庆', false, true, 2035, null, null);
INSERT INTO public.t_special_date
(id, calendar_code, calendar_date, description, is_workday, is_active, "year", created_at, updated_at)
VALUES(nextval('t_special_date_id_seq'::regclass), 'CN', '2035-10-03', '国庆', false, true, 2035, null, null);
INSERT INTO public.t_special_date
(id, calendar_code, calendar_date, description, is_workday, is_active, "year", created_at, updated_at)
VALUES(nextval('t_special_date_id_seq'::regclass), 'CN', '2035-10-04', '国庆', false, true, 2035, null, null);
INSERT INTO public.t_special_date
(id, calendar_code, calendar_date, description, is_workday, is_active, "year", created_at, updated_at)
VALUES(nextval('t_special_date_id_seq'::regclass), 'CN', '2035-10-05', '国庆', false, true, 2035, null, null);
INSERT INTO public.t_special_date
(id, calendar_code, calendar_date, description, is_workday, is_active, "year", created_at, updated_at)
VALUES(nextval('t_special_date_id_seq'::regclass), 'CN', '2035-10-06', '国庆', false, true, 2035, null, null);
INSERT INTO public.t_special_date
(id, calendar_code, calendar_date, description, is_workday, is_active, "year", created_at, updated_at)
VALUES(nextval('t_special_date_id_seq'::regclass), 'CN', '2035-10-07', '国庆', false, true, 2035, null, null);
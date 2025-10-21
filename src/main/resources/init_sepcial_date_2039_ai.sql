-- 2039年中国大陆法定节假日及调班安排

-- 元旦：1月1日放假，与周末连休
INSERT INTO public.t_special_date
(id, calendar_code, calendar_date, description, is_workday, is_active, "year", created_at, updated_at)
VALUES(nextval('t_special_date_id_seq'::regclass), 'CN', '2039-01-01', '元旦', false, true, 2039, null, null);

-- 春节：1月24日放假调休，共7天。1月22日、1月21日、1月31日、2月1日上班
INSERT INTO public.t_special_date
(id, calendar_code, calendar_date, description, is_workday, is_active, "year", created_at, updated_at)
VALUES(nextval('t_special_date_id_seq'::regclass), 'CN', '2039-01-22', '春节-调班', true, true, 2039, null, null);
INSERT INTO public.t_special_date
(id, calendar_code, calendar_date, description, is_workday, is_active, "year", created_at, updated_at)
VALUES(nextval('t_special_date_id_seq'::regclass), 'CN', '2039-01-21', '春节-调班', true, true, 2039, null, null);
INSERT INTO public.t_special_date
(id, calendar_code, calendar_date, description, is_workday, is_active, "year", created_at, updated_at)
VALUES(nextval('t_special_date_id_seq'::regclass), 'CN', '2039-01-23', '春节', false, true, 2039, null, null);
INSERT INTO public.t_special_date
(id, calendar_code, calendar_date, description, is_workday, is_active, "year", created_at, updated_at)
VALUES(nextval('t_special_date_id_seq'::regclass), 'CN', '2039-01-24', '春节', false, true, 2039, null, null);
INSERT INTO public.t_special_date
(id, calendar_code, calendar_date, description, is_workday, is_active, "year", created_at, updated_at)
VALUES(nextval('t_special_date_id_seq'::regclass), 'CN', '2039-01-25', '春节', false, true, 2039, null, null);
INSERT INTO public.t_special_date
(id, calendar_code, calendar_date, description, is_workday, is_active, "year", created_at, updated_at)
VALUES(nextval('t_special_date_id_seq'::regclass), 'CN', '2039-01-26', '春节', false, true, 2039, null, null);
INSERT INTO public.t_special_date
(id, calendar_code, calendar_date, description, is_workday, is_active, "year", created_at, updated_at)
VALUES(nextval('t_special_date_id_seq'::regclass), 'CN', '2039-01-27', '春节', false, true, 2039, null, null);
INSERT INTO public.t_special_date
(id, calendar_code, calendar_date, description, is_workday, is_active, "year", created_at, updated_at)
VALUES(nextval('t_special_date_id_seq'::regclass), 'CN', '2039-01-28', '春节', false, true, 2039, null, null);
INSERT INTO public.t_special_date
(id, calendar_code, calendar_date, description, is_workday, is_active, "year", created_at, updated_at)
VALUES(nextval('t_special_date_id_seq'::regclass), 'CN', '2039-01-29', '春节', false, true, 2039, null, null);
INSERT INTO public.t_special_date
(id, calendar_code, calendar_date, description, is_workday, is_active, "year", created_at, updated_at)
VALUES(nextval('t_special_date_id_seq'::regclass), 'CN', '2039-01-30', '春节', false, true, 2039, null, null);
INSERT INTO public.t_special_date
(id, calendar_code, calendar_date, description, is_workday, is_active, "year", created_at, updated_at)
VALUES(nextval('t_special_date_id_seq'::regclass), 'CN', '2039-01-31', '春节-调班', true, true, 2039, null, null);
INSERT INTO public.t_special_date
(id, calendar_code, calendar_date, description, is_workday, is_active, "year", created_at, updated_at)
VALUES(nextval('t_special_date_id_seq'::regclass), 'CN', '2039-02-01', '春节-调班', true, true, 2039, null, null);

-- 清明节：4月4日放假
INSERT INTO public.t_special_date
(id, calendar_code, calendar_date, description, is_workday, is_active, "year", created_at, updated_at)
VALUES(nextval('t_special_date_id_seq'::regclass), 'CN', '2039-04-04', '清明', false, true, 2039, null, null);

-- 劳动节：5月1日至5月5日放假调休，共5天。4月30日、4月29日上班
INSERT INTO public.t_special_date
(id, calendar_code, calendar_date, description, is_workday, is_active, "year", created_at, updated_at)
VALUES(nextval('t_special_date_id_seq'::regclass), 'CN', '2039-04-30', '五一-调班', true, true, 2039, null, null);
INSERT INTO public.t_special_date
(id, calendar_code, calendar_date, description, is_workday, is_active, "year", created_at, updated_at)
VALUES(nextval('t_special_date_id_seq'::regclass), 'CN', '2039-04-29', '五一-调班', true, true, 2039, null, null);
INSERT INTO public.t_special_date
(id, calendar_code, calendar_date, description, is_workday, is_active, "year", created_at, updated_at)
VALUES(nextval('t_special_date_id_seq'::regclass), 'CN', '2039-05-01', '五一', false, true, 2039, null, null);
INSERT INTO public.t_special_date
(id, calendar_code, calendar_date, description, is_workday, is_active, "year", created_at, updated_at)
VALUES(nextval('t_special_date_id_seq'::regclass), 'CN', '2039-05-02', '五一', false, true, 2039, null, null);
INSERT INTO public.t_special_date
(id, calendar_code, calendar_date, description, is_workday, is_active, "year", created_at, updated_at)
VALUES(nextval('t_special_date_id_seq'::regclass), 'CN', '2039-05-03', '五一', false, true, 2039, null, null);
INSERT INTO public.t_special_date
(id, calendar_code, calendar_date, description, is_workday, is_active, "year", created_at, updated_at)
VALUES(nextval('t_special_date_id_seq'::regclass), 'CN', '2039-05-04', '五一', false, true, 2039, null, null);
INSERT INTO public.t_special_date
(id, calendar_code, calendar_date, description, is_workday, is_active, "year", created_at, updated_at)
VALUES(nextval('t_special_date_id_seq'::regclass), 'CN', '2039-05-05', '五一', false, true, 2039, null, null);

-- 端午节：5月27日至5月29日放假调休，共3天
INSERT INTO public.t_special_date
(id, calendar_code, calendar_date, description, is_workday, is_active, "year", created_at, updated_at)
VALUES(nextval('t_special_date_id_seq'::regclass), 'CN', '2039-05-27', '端午', false, true, 2039, null, null);
INSERT INTO public.t_special_date
(id, calendar_code, calendar_date, description, is_workday, is_active, "year", created_at, updated_at)
VALUES(nextval('t_special_date_id_seq'::regclass), 'CN', '2039-05-28', '端午', false, true, 2039, null, null);
INSERT INTO public.t_special_date
(id, calendar_code, calendar_date, description, is_workday, is_active, "year", created_at, updated_at)
VALUES(nextval('t_special_date_id_seq'::regclass), 'CN', '2039-05-29', '端午', false, true, 2039, null, null);

-- 中秋节：10月2日至10月4日放假调休，共3天
INSERT INTO public.t_special_date
(id, calendar_code, calendar_date, description, is_workday, is_active, "year", created_at, updated_at)
VALUES(nextval('t_special_date_id_seq'::regclass), 'CN', '2039-10-02', '中秋', false, true, 2039, null, null);
INSERT INTO public.t_special_date
(id, calendar_code, calendar_date, description, is_workday, is_active, "year", created_at, updated_at)
VALUES(nextval('t_special_date_id_seq'::regclass), 'CN', '2039-10-03', '中秋', false, true, 2039, null, null);
INSERT INTO public.t_special_date
(id, calendar_code, calendar_date, description, is_workday, is_active, "year", created_at, updated_at)
VALUES(nextval('t_special_date_id_seq'::regclass), 'CN', '2039-10-04', '中秋', false, true, 2039, null, null);

-- 国庆节：10月1日至10月7日放假调休，共7天。9月30日、9月29日上班
INSERT INTO public.t_special_date
(id, calendar_code, calendar_date, description, is_workday, is_active, "year", created_at, updated_at)
VALUES(nextval('t_special_date_id_seq'::regclass), 'CN', '2039-09-30', '国庆-调班', true, true, 2039, null, null);
INSERT INTO public.t_special_date
(id, calendar_code, calendar_date, description, is_workday, is_active, "year", created_at, updated_at)
VALUES(nextval('t_special_date_id_seq'::regclass), 'CN', '2039-09-29', '国庆-调班', true, true, 2039, null, null);
INSERT INTO public.t_special_date
(id, calendar_code, calendar_date, description, is_workday, is_active, "year", created_at, updated_at)
VALUES(nextval('t_special_date_id_seq'::regclass), 'CN', '2039-10-01', '国庆', false, true, 2039, null, null);
INSERT INTO public.t_special_date
(id, calendar_code, calendar_date, description, is_workday, is_active, "year", created_at, updated_at)
VALUES(nextval('t_special_date_id_seq'::regclass), 'CN', '2039-10-02', '国庆', false, true, 2039, null, null);
INSERT INTO public.t_special_date
(id, calendar_code, calendar_date, description, is_workday, is_active, "year", created_at, updated_at)
VALUES(nextval('t_special_date_id_seq'::regclass), 'CN', '2039-10-03', '国庆', false, true, 2039, null, null);
INSERT INTO public.t_special_date
(id, calendar_code, calendar_date, description, is_workday, is_active, "year", created_at, updated_at)
VALUES(nextval('t_special_date_id_seq'::regclass), 'CN', '2039-10-04', '国庆', false, true, 2039, null, null);
INSERT INTO public.t_special_date
(id, calendar_code, calendar_date, description, is_workday, is_active, "year", created_at, updated_at)
VALUES(nextval('t_special_date_id_seq'::regclass), 'CN', '2039-10-05', '国庆', false, true, 2039, null, null);
INSERT INTO public.t_special_date
(id, calendar_code, calendar_date, description, is_workday, is_active, "year", created_at, updated_at)
VALUES(nextval('t_special_date_id_seq'::regclass), 'CN', '2039-10-06', '国庆', false, true, 2039, null, null);
INSERT INTO public.t_special_date
(id, calendar_code, calendar_date, description, is_workday, is_active, "year", created_at, updated_at)
VALUES(nextval('t_special_date_id_seq'::regclass), 'CN', '2039-10-07', '国庆', false, true, 2039, null, null);
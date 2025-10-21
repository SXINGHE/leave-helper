-- 2045年中国大陆法定节假日及调班安排

-- 元旦：1月1日放假，与周末连休
INSERT INTO public.t_special_date
(id, calendar_code, calendar_date, description, is_workday, is_active, "year", created_at, updated_at)
VALUES(nextval('t_special_date_id_seq'::regclass), 'CN', '2045-01-01', '元旦', false, true, 2045, null, null);

-- 春节：2月17日放假调休，共7天。2月15日、2月14日、2月24日、2月25日上班
INSERT INTO public.t_special_date
(id, calendar_code, calendar_date, description, is_workday, is_active, "year", created_at, updated_at)
VALUES(nextval('t_special_date_id_seq'::regclass), 'CN', '2045-02-15', '春节-调班', true, true, 2045, null, null);
INSERT INTO public.t_special_date
(id, calendar_code, calendar_date, description, is_workday, is_active, "year", created_at, updated_at)
VALUES(nextval('t_special_date_id_seq'::regclass), 'CN', '2045-02-14', '春节-调班', true, true, 2045, null, null);
INSERT INTO public.t_special_date
(id, calendar_code, calendar_date, description, is_workday, is_active, "year", created_at, updated_at)
VALUES(nextval('t_special_date_id_seq'::regclass), 'CN', '2045-02-16', '春节', false, true, 2045, null, null);
INSERT INTO public.t_special_date
(id, calendar_code, calendar_date, description, is_workday, is_active, "year", created_at, updated_at)
VALUES(nextval('t_special_date_id_seq'::regclass), 'CN', '2045-02-17', '春节', false, true, 2045, null, null);
INSERT INTO public.t_special_date
(id, calendar_code, calendar_date, description, is_workday, is_active, "year", created_at, updated_at)
VALUES(nextval('t_special_date_id_seq'::regclass), 'CN', '2045-02-18', '春节', false, true, 2045, null, null);
INSERT INTO public.t_special_date
(id, calendar_code, calendar_date, description, is_workday, is_active, "year", created_at, updated_at)
VALUES(nextval('t_special_date_id_seq'::regclass), 'CN', '2045-02-19', '春节', false, true, 2045, null, null);
INSERT INTO public.t_special_date
(id, calendar_code, calendar_date, description, is_workday, is_active, "year", created_at, updated_at)
VALUES(nextval('t_special_date_id_seq'::regclass), 'CN', '2045-02-20', '春节', false, true, 2045, null, null);
INSERT INTO public.t_special_date
(id, calendar_code, calendar_date, description, is_workday, is_active, "year", created_at, updated_at)
VALUES(nextval('t_special_date_id_seq'::regclass), 'CN', '2045-02-21', '春节', false, true, 2045, null, null);
INSERT INTO public.t_special_date
(id, calendar_code, calendar_date, description, is_workday, is_active, "year", created_at, updated_at)
VALUES(nextval('t_special_date_id_seq'::regclass), 'CN', '2045-02-22', '春节', false, true, 2045, null, null);
INSERT INTO public.t_special_date
(id, calendar_code, calendar_date, description, is_workday, is_active, "year", created_at, updated_at)
VALUES(nextval('t_special_date_id_seq'::regclass), 'CN', '2045-02-23', '春节', false, true, 2045, null, null);
INSERT INTO public.t_special_date
(id, calendar_code, calendar_date, description, is_workday, is_active, "year", created_at, updated_at)
VALUES(nextval('t_special_date_id_seq'::regclass), 'CN', '2045-02-24', '春节-调班', true, true, 2045, null, null);
INSERT INTO public.t_special_date
(id, calendar_code, calendar_date, description, is_workday, is_active, "year", created_at, updated_at)
VALUES(nextval('t_special_date_id_seq'::regclass), 'CN', '2045-02-25', '春节-调班', true, true, 2045, null, null);

-- 清明节：4月4日放假
INSERT INTO public.t_special_date
(id, calendar_code, calendar_date, description, is_workday, is_active, "year", created_at, updated_at)
VALUES(nextval('t_special_date_id_seq'::regclass), 'CN', '2045-04-04', '清明', false, true, 2045, null, null);

-- 劳动节：5月1日至5月5日放假调休，共5天。4月30日、4月29日上班
INSERT INTO public.t_special_date
(id, calendar_code, calendar_date, description, is_workday, is_active, "year", created_at, updated_at)
VALUES(nextval('t_special_date_id_seq'::regclass), 'CN', '2045-04-30', '五一-调班', true, true, 2045, null, null);
INSERT INTO public.t_special_date
(id, calendar_code, calendar_date, description, is_workday, is_active, "year", created_at, updated_at)
VALUES(nextval('t_special_date_id_seq'::regclass), 'CN', '2045-04-29', '五一-调班', true, true, 2045, null, null);
INSERT INTO public.t_special_date
(id, calendar_code, calendar_date, description, is_workday, is_active, "year", created_at, updated_at)
VALUES(nextval('t_special_date_id_seq'::regclass), 'CN', '2045-05-01', '五一', false, true, 2045, null, null);
INSERT INTO public.t_special_date
(id, calendar_code, calendar_date, description, is_workday, is_active, "year", created_at, updated_at)
VALUES(nextval('t_special_date_id_seq'::regclass), 'CN', '2045-05-02', '五一', false, true, 2045, null, null);
INSERT INTO public.t_special_date
(id, calendar_code, calendar_date, description, is_workday, is_active, "year", created_at, updated_at)
VALUES(nextval('t_special_date_id_seq'::regclass), 'CN', '2045-05-03', '五一', false, true, 2045, null, null);
INSERT INTO public.t_special_date
(id, calendar_code, calendar_date, description, is_workday, is_active, "year", created_at, updated_at)
VALUES(nextval('t_special_date_id_seq'::regclass), 'CN', '2045-05-04', '五一', false, true, 2045, null, null);
INSERT INTO public.t_special_date
(id, calendar_code, calendar_date, description, is_workday, is_active, "year", created_at, updated_at)
VALUES(nextval('t_special_date_id_seq'::regclass), 'CN', '2045-05-05', '五一', false, true, 2045, null, null);

-- 端午节：6月19日至6月21日放假调休，共3天
INSERT INTO public.t_special_date
(id, calendar_code, calendar_date, description, is_workday, is_active, "year", created_at, updated_at)
VALUES(nextval('t_special_date_id_seq'::regclass), 'CN', '2045-06-19', '端午', false, true, 2045, null, null);
INSERT INTO public.t_special_date
(id, calendar_code, calendar_date, description, is_workday, is_active, "year", created_at, updated_at)
VALUES(nextval('t_special_date_id_seq'::regclass), 'CN', '2045-06-20', '端午', false, true, 2045, null, null);
INSERT INTO public.t_special_date
(id, calendar_code, calendar_date, description, is_workday, is_active, "year", created_at, updated_at)
VALUES(nextval('t_special_date_id_seq'::regclass), 'CN', '2045-06-21', '端午', false, true, 2045, null, null);

-- 中秋节：9月25日至9月27日放假调休，共3天
INSERT INTO public.t_special_date
(id, calendar_code, calendar_date, description, is_workday, is_active, "year", created_at, updated_at)
VALUES(nextval('t_special_date_id_seq'::regclass), 'CN', '2045-09-25', '中秋', false, true, 2045, null, null);
INSERT INTO public.t_special_date
(id, calendar_code, calendar_date, description, is_workday, is_active, "year", created_at, updated_at)
VALUES(nextval('t_special_date_id_seq'::regclass), 'CN', '2045-09-26', '中秋', false, true, 2045, null, null);
INSERT INTO public.t_special_date
(id, calendar_code, calendar_date, description, is_workday, is_active, "year", created_at, updated_at)
VALUES(nextval('t_special_date_id_seq'::regclass), 'CN', '2045-09-27', '中秋', false, true, 2045, null, null);

-- 国庆节：10月1日至10月7日放假调休，共7天。9月30日、9月29日上班
INSERT INTO public.t_special_date
(id, calendar_code, calendar_date, description, is_workday, is_active, "year", created_at, updated_at)
VALUES(nextval('t_special_date_id_seq'::regclass), 'CN', '2045-09-30', '国庆-调班', true, true, 2045, null, null);
INSERT INTO public.t_special_date
(id, calendar_code, calendar_date, description, is_workday, is_active, "year", created_at, updated_at)
VALUES(nextval('t_special_date_id_seq'::regclass), 'CN', '2045-09-29', '国庆-调班', true, true, 2045, null, null);
INSERT INTO public.t_special_date
(id, calendar_code, calendar_date, description, is_workday, is_active, "year", created_at, updated_at)
VALUES(nextval('t_special_date_id_seq'::regclass), 'CN', '2045-10-01', '国庆', false, true, 2045, null, null);
INSERT INTO public.t_special_date
(id, calendar_code, calendar_date, description, is_workday, is_active, "year", created_at, updated_at)
VALUES(nextval('t_special_date_id_seq'::regclass), 'CN', '2045-10-02', '国庆', false, true, 2045, null, null);
INSERT INTO public.t_special_date
(id, calendar_code, calendar_date, description, is_workday, is_active, "year", created_at, updated_at)
VALUES(nextval('t_special_date_id_seq'::regclass), 'CN', '2045-10-03', '国庆', false, true, 2045, null, null);
INSERT INTO public.t_special_date
(id, calendar_code, calendar_date, description, is_workday, is_active, "year", created_at, updated_at)
VALUES(nextval('t_special_date_id_seq'::regclass), 'CN', '2045-10-04', '国庆', false, true, 2045, null, null);
INSERT INTO public.t_special_date
(id, calendar_code, calendar_date, description, is_workday, is_active, "year", created_at, updated_at)
VALUES(nextval('t_special_date_id_seq'::regclass), 'CN', '2045-10-05', '国庆', false, true, 2045, null, null);
INSERT INTO public.t_special_date
(id, calendar_code, calendar_date, description, is_workday, is_active, "year", created_at, updated_at)
VALUES(nextval('t_special_date_id_seq'::regclass), 'CN', '2045-10-06', '国庆', false, true, 2045, null, null);
INSERT INTO public.t_special_date
(id, calendar_code, calendar_date, description, is_workday, is_active, "year", created_at, updated_at)
VALUES(nextval('t_special_date_id_seq'::regclass), 'CN', '2045-10-07', '国庆', false, true, 2045, null, null);
import sys
from datetime import date, timedelta
from lunardate import LunarDate

def get_spring_festival(year):
    """Return the date of Chinese New Year (Spring Festival) for the given year"""
    spring_festival_dates = {
        2023: date(2023, 1, 22),
        2024: date(2024, 2, 10),
        2025: date(2025, 1, 29),
        2026: date(2026, 2, 17),
        2027: date(2027, 2, 6),
        2028: date(2028, 1, 26),
        2029: date(2029, 2, 13),
        2030: date(2030, 2, 3),
        2031: date(2031, 1, 23),
        2032: date(2032, 2, 11),
        2033: date(2033, 1, 31),
        2034: date(2034, 2, 19),
        2035: date(2035, 2, 8),
        2036: date(2036, 1, 28),
        2037: date(2037, 2, 15),
        2038: date(2038, 2, 4),
        2039: date(2039, 1, 24),
        2040: date(2040, 2, 12),
        2041: date(2041, 2, 1),
        2042: date(2042, 1, 22),
        2043: date(2043, 2, 10),
        2044: date(2044, 1, 30),
        2045: date(2045, 2, 17),
        2046: date(2046, 2, 6)
    }
    return spring_festival_dates.get(year)

def get_qingming_date(year):
    """Return the date of Qingming Festival (April 4th or 5th)"""
    return date(year, 4, 4 if year % 4 != 0 else 5)

def get_dragon_boat_festival(year):
    """Return the date of Dragon Boat Festival (5th day of 5th lunar month)"""
    lunar_date = LunarDate(year, 5, 5)
    return lunar_date.toSolarDate()

def get_mid_autumn_festival(year):
    """Return the date of Mid-Autumn Festival (15th day of 8th lunar month)"""
    lunar_date = LunarDate(year, 8, 15)
    return lunar_date.toSolarDate()

def generate_sql(year):
    """Generate SQL for special dates in the given year"""
    sql_lines = []
    sql_lines.append(f"-- {year}年中国大陆法定节假日及调班安排\n")
    
    # New Year's Day (January 1st)
    new_year = date(year, 1, 1)
    sql_lines.append(f"-- 元旦：1月1日放假，与周末连休")
    sql_lines.append(generate_sql_row(new_year, '元旦', False))
    
    # Spring Festival
    spring_festival = get_spring_festival(year)
    spring_festival_eve = spring_festival - timedelta(days=1)
    spring_festival_day2 = spring_festival + timedelta(days=1)
    spring_festival_day3 = spring_festival + timedelta(days=2)
    spring_festival_day4 = spring_festival + timedelta(days=3)
    spring_festival_day5 = spring_festival + timedelta(days=4)
    spring_festival_day6 = spring_festival + timedelta(days=5)
    spring_festival_day7 = spring_festival + timedelta(days=6)
    
    # Add workdays around Spring Festival (usually 2 days before and after)
    workday_before1 = spring_festival - timedelta(days=2)
    workday_before2 = spring_festival - timedelta(days=3)
    workday_after1 = spring_festival_day7 + timedelta(days=1)
    workday_after2 = spring_festival_day7 + timedelta(days=2)
    
    sql_lines.append(f"\n-- 春节：{spring_festival.month}月{spring_festival.day}日放假调休，共7天。{workday_before1.month}月{workday_before1.day}日、{workday_before2.month}月{workday_before2.day}日、{workday_after1.month}月{workday_after1.day}日、{workday_after2.month}月{workday_after2.day}日上班")
    sql_lines.append(generate_sql_row(workday_before1, '春节-调班', True))
    sql_lines.append(generate_sql_row(workday_before2, '春节-调班', True))
    sql_lines.append(generate_sql_row(spring_festival_eve, '春节', False))
    sql_lines.append(generate_sql_row(spring_festival, '春节', False))
    sql_lines.append(generate_sql_row(spring_festival_day2, '春节', False))
    sql_lines.append(generate_sql_row(spring_festival_day3, '春节', False))
    sql_lines.append(generate_sql_row(spring_festival_day4, '春节', False))
    sql_lines.append(generate_sql_row(spring_festival_day5, '春节', False))
    sql_lines.append(generate_sql_row(spring_festival_day6, '春节', False))
    sql_lines.append(generate_sql_row(spring_festival_day7, '春节', False))
    sql_lines.append(generate_sql_row(workday_after1, '春节-调班', True))
    sql_lines.append(generate_sql_row(workday_after2, '春节-调班', True))
    
    # Qingming Festival
    qingming = get_qingming_date(year)
    sql_lines.append(f"\n-- 清明节：{qingming.month}月{qingming.day}日放假")
    sql_lines.append(generate_sql_row(qingming, '清明', False))
    
    # Labor Day (May 1st-5th)
    labor_day = date(year, 5, 1)
    labor_day2 = labor_day + timedelta(days=1)
    labor_day3 = labor_day + timedelta(days=2)
    labor_day4 = labor_day + timedelta(days=3)
    labor_day5 = labor_day + timedelta(days=4)
    
    # Add workdays around Labor Day
    workday_before_labor1 = labor_day - timedelta(days=1)
    workday_before_labor2 = labor_day - timedelta(days=2)
    
    sql_lines.append(f"\n-- 劳动节：{labor_day.month}月{labor_day.day}日至{labor_day5.month}月{labor_day5.day}日放假调休，共5天。{workday_before_labor1.month}月{workday_before_labor1.day}日、{workday_before_labor2.month}月{workday_before_labor2.day}日上班")
    sql_lines.append(generate_sql_row(workday_before_labor1, '五一-调班', True))
    sql_lines.append(generate_sql_row(workday_before_labor2, '五一-调班', True))
    sql_lines.append(generate_sql_row(labor_day, '五一', False))
    sql_lines.append(generate_sql_row(labor_day2, '五一', False))
    sql_lines.append(generate_sql_row(labor_day3, '五一', False))
    sql_lines.append(generate_sql_row(labor_day4, '五一', False))
    sql_lines.append(generate_sql_row(labor_day5, '五一', False))
    
    # Dragon Boat Festival
    dragon_boat = get_dragon_boat_festival(year)
    dragon_boat2 = dragon_boat + timedelta(days=1)
    dragon_boat3 = dragon_boat + timedelta(days=2)
    
    sql_lines.append(f"\n-- 端午节：{dragon_boat.month}月{dragon_boat.day}日至{dragon_boat3.month}月{dragon_boat3.day}日放假调休，共3天")
    sql_lines.append(generate_sql_row(dragon_boat, '端午', False))
    sql_lines.append(generate_sql_row(dragon_boat2, '端午', False))
    sql_lines.append(generate_sql_row(dragon_boat3, '端午', False))
    
    # Mid-Autumn Festival
    mid_autumn = get_mid_autumn_festival(year)
    mid_autumn2 = mid_autumn + timedelta(days=1)
    mid_autumn3 = mid_autumn + timedelta(days=2)
    
    sql_lines.append(f"\n-- 中秋节：{mid_autumn.month}月{mid_autumn.day}日至{mid_autumn3.month}月{mid_autumn3.day}日放假调休，共3天")
    sql_lines.append(generate_sql_row(mid_autumn, '中秋', False))
    sql_lines.append(generate_sql_row(mid_autumn2, '中秋', False))
    sql_lines.append(generate_sql_row(mid_autumn3, '中秋', False))
    
    # National Day (October 1st-7th)
    national_day = date(year, 10, 1)
    national_day2 = national_day + timedelta(days=1)
    national_day3 = national_day + timedelta(days=2)
    national_day4 = national_day + timedelta(days=3)
    national_day5 = national_day + timedelta(days=4)
    national_day6 = national_day + timedelta(days=5)
    national_day7 = national_day + timedelta(days=6)
    
    # Add workdays around National Day
    workday_before_national1 = national_day - timedelta(days=1)
    workday_before_national2 = national_day - timedelta(days=2)
    
    sql_lines.append(f"\n-- 国庆节：{national_day.month}月{national_day.day}日至{national_day7.month}月{national_day7.day}日放假调休，共7天。{workday_before_national1.month}月{workday_before_national1.day}日、{workday_before_national2.month}月{workday_before_national2.day}日上班")
    sql_lines.append(generate_sql_row(workday_before_national1, '国庆-调班', True))
    sql_lines.append(generate_sql_row(workday_before_national2, '国庆-调班', True))
    sql_lines.append(generate_sql_row(national_day, '国庆', False))
    sql_lines.append(generate_sql_row(national_day2, '国庆', False))
    sql_lines.append(generate_sql_row(national_day3, '国庆', False))
    sql_lines.append(generate_sql_row(national_day4, '国庆', False))
    sql_lines.append(generate_sql_row(national_day5, '国庆', False))
    sql_lines.append(generate_sql_row(national_day6, '国庆', False))
    sql_lines.append(generate_sql_row(national_day7, '国庆', False))
    
    return "\n".join(sql_lines)

def generate_sql_row(date_obj, description, is_workday):
    """Generate a single SQL row for the given date and description"""
    return f"INSERT INTO public.t_special_date\n" \
           f"(id, calendar_code, calendar_date, description, is_workday, is_active, \"year\", created_at, updated_at)\n" \
           f"VALUES(nextval('t_special_date_id_seq'::regclass), 'CN', '{date_obj.isoformat()}', '{description}', {str(is_workday).lower()}, true, {date_obj.year}, null, null);"

def main():
    start_year = 2026
    end_year = 2046
    
    for year in range(start_year, end_year + 1):
        sql_content = generate_sql(year)
        filename = f"init_sepcial_date_{year}_ai.sql"
        with open(filename, 'w', encoding='utf-8') as f:
            f.write(sql_content)
        print(f"Generated {filename}")

if __name__ == "__main__":
    main()

package com.ocbc.ms.repository;


import com.ocbc.ms.entity.SpecialDate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.time.LocalDate;
import java.util.List;

@Repository
public interface SpecialDateRepository extends JpaRepository<SpecialDate, Long> {

    
    @Query("SELECT h FROM t_special_date h WHERE h.calendarCode = :calendarCode " +
           "AND h.calendarDate >= :startDate AND h.isActive = true")
    List<SpecialDate> getAllHolidayByStartDate(@Param("calendarCode") String calendarCode,
                                                @Param("startDate") LocalDate startDate);
    @Query("SELECT h FROM t_special_date h WHERE h.calendarCode = :calendarCode " +
            "AND h.year = :year AND h.isActive = true")
    List<SpecialDate> findByCalendarCodeAndYear(String calendarCode, Integer year);


    @Query("SELECT h FROM t_special_date h WHERE h.calendarCode = :calendarCode " +
            "AND h.calendarDate >= :startDate AND h.calendarDate <= :endDate " +
            "And h.isWorkday = true AND h.isActive = true")
    List<SpecialDate> findExtraWorkDaysByCalendarCodeAndYear(String calendarCode, LocalDate startDate, LocalDate endDate);

    @Query("SELECT h FROM t_special_date h WHERE h.calendarCode = :calendarCode " +
            "AND h.calendarDate >= :startDate AND h.calendarDate <= :endDate " +
            "And h.isWorkday = false AND h.isActive = true")
    List<SpecialDate> findHolidayByCalendarCodeAndYear(String calendarCode, LocalDate startDate, LocalDate endDate);

}

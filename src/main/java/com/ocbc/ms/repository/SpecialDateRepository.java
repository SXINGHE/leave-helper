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

    @Query("SELECT h FROM SpecialDate h WHERE h.calendarCode = :calendarCode " +
           "AND h.calendarDate >= :startDate AND h.isActive = true")
    List<SpecialDate> getAllHolidayByStartDate(@Param("calendarCode") String calendarCode,
                                               @Param("startDate") LocalDate startDate);
    @Query("SELECT h FROM SpecialDate h WHERE h.calendarCode = :calendarCode " +
            "AND h.year = :year AND h.isActive = true")
    List<SpecialDate> findByCalendarCodeAndYear(@Param("calendarCode") String calendarCode, @Param("year") Integer year);


    @Query("SELECT h FROM SpecialDate h WHERE h.calendarCode = :calendarCode " +
            "AND h.calendarDate >= :startDate AND h.calendarDate <= :endDate " +
            "AND h.isWorkday = true AND h.isActive = true")
    List<SpecialDate> findExtraWorkDaysByCalendarCodeAndYear(@Param("calendarCode") String calendarCode, @Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);

    @Query("SELECT h FROM SpecialDate h WHERE h.calendarCode = :calendarCode " +
            "AND h.calendarDate >= :startDate AND h.calendarDate <= :endDate " +
            "AND h.isWorkday = false AND h.isActive = true")
    List<SpecialDate> findHolidayByCalendarCodeAndYear(@Param("calendarCode") String calendarCode, @Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);

}

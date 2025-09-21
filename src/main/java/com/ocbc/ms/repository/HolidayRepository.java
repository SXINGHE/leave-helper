package com.ocbc.ms.repository;


import com.ocbc.ms.entity.Holiday;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.time.LocalDate;
import java.util.List;

@Repository
public interface HolidayRepository extends JpaRepository<Holiday, Long> {
    
    @Query("SELECT h FROM Holiday h WHERE h.city.cityCode = :cityCode " +
           "AND h.holidayDate BETWEEN :startDate AND :endDate " +
           "AND h.isActive = true")
    List<Holiday> findHolidaysByCityAndDateRange(@Param("cityCode") String cityCode,
                                                 @Param("startDate") LocalDate startDate,
                                                 @Param("endDate") LocalDate endDate);
    
    @Query("SELECT h FROM Holiday h WHERE h.city.cityCode = :cityCode " +
           "AND h.holidayDate = :date AND h.isActive = true")
    List<Holiday> findHolidaysByCityAndDate(@Param("cityCode") String cityCode,
                                           @Param("date") LocalDate date);
    
    @Query("SELECT h FROM Holiday h WHERE h.city.cityCode = :cityCode " +
           "AND h.year = :year AND h.isActive = true " +
           "ORDER BY h.holidayDate")
    List<Holiday> findHolidaysByCityAndYear(@Param("cityCode") String cityCode,
                                           @Param("year") Integer year);
    
    @Query("SELECT h FROM Holiday h WHERE h.city.cityCode = :cityCode " +
           "AND h.isWorkday = true AND h.isActive = true " +
           "AND h.holidayDate BETWEEN :startDate AND :endDate")
    List<Holiday> findWorkdaysByCityAndDateRange(@Param("cityCode") String cityCode,
                                                @Param("startDate") LocalDate startDate,
                                                @Param("endDate") LocalDate endDate);
}

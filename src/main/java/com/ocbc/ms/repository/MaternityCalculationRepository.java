package com.ocbc.ms.repository;


import com.ocbc.ms.entity.MaternityCalSummary;
import com.ocbc.ms.entity.MaternityCalculation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.ZonedDateTime;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Repository
public interface MaternityCalculationRepository extends JpaRepository<MaternityCalculation, UUID> {

    @Query("SELECT " +
            "SUM(CASE WHEN m.status = 'COMPLETED' THEN 1 ELSE 0 END) as completedCalculation, " +
            "SUM(CASE WHEN m.status = 'PENDING' THEN 1 ELSE 0 END) as pendingCalculation, " +
            "COUNT(m) as totalCalculation " +
            "FROM MaternityCalculation m " +
            "WHERE m.calculatedAt >= :startDate AND m.calculatedAt <= :endDate")
    MaternityCalSummary countMaternityCalculationSummary(@Param("startDate") Date startDate, @Param("endDate") Date endDate);

}

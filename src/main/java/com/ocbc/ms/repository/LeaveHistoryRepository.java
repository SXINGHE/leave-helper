package com.ocbc.ms.repository;

import com.ocbc.ms.entity.LeaveHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LeaveHistoryRepository extends JpaRepository<LeaveHistory, Long> {

}

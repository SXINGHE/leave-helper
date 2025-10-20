package com.ocbc.ms.repository;

import com.ocbc.ms.entity.Policy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface PolicyRepository extends JpaRepository<Policy, Long> {

    @Query("SELECT h FROM Policy h WHERE h.cityCode = :cityCode ")
    Optional<Policy> findByCityCode(String cityCode);
    
    @Modifying
    @Transactional
    @Query("DELETE FROM Policy p WHERE p.id = :id")
    void hardDeleteById(Long id);
}

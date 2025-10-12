package com.ocbc.ms.repository;

import com.ocbc.ms.entity.Policy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PolicyRepository extends JpaRepository<Policy, Long> {

    @Query("SELECT h FROM Policy h WHERE h.cityCode = :cityCode ")
    Optional<Policy> findByCityCode(String cityCode);
}

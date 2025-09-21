package com.ocbc.ms.repository;


import com.ocbc.ms.entity.City;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface CityRepository extends JpaRepository<City, Long> {
    
    Optional<City> findByCityCode(String cityCode);
    
    boolean existsByCityCode(String cityCode);
}

package com.ocbc.ms.repository;

import com.ocbc.ms.model.Policy;
import org.springframework.stereotype.Repository;

import javax.swing.text.html.Option;
import java.util.Optional;

@Repository
public interface PolicyRepository {


    Optional<Policy> findByCityNameAndCompanyName(String cityName, String companyName);
}

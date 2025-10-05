package com.ocbc.ms;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EntityScan(basePackages = {
        "com.ocbc.ms.hrtools.model",        // HR Tools entities
        "com.ocbc.ms.entity"                 // Other entities
})
@EnableJpaRepositories(basePackages = {
        "com.ocbc.ms.hrtools.repository",   // HR Tools repositories
        "com.ocbc.ms.repository"            // Other repositories
})
public class LeaveHelperApplication {

	public static void main(String[] args) {
		SpringApplication.run(LeaveHelperApplication.class, args);
	}

}

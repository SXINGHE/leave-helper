package com.ocbc.ms;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EntityScan(basePackages = {
        "com.ocbc.ms.entity"         // 实体类扫描路径
})
@EnableJpaRepositories(basePackages = {
        "com.ocbc.ms.repository"     // Repository 扫描路径
})
public class LeaveHelperApplication {

	public static void main(String[] args) {
		SpringApplication.run(LeaveHelperApplication.class, args);
	}

}

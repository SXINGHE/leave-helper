package com.ocbc.ms.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;

@Entity
@Table(name = "t_city")
@Data
@NoArgsConstructor
public class City {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "city_code", unique = true, nullable = false, length = 10)
    private String cityCode;
    
    @Column(name = "city_name", nullable = false, length = 100)
    private String cityName;
    
    @Column(name = "province", nullable = false, length = 50)
    private String province;
    
    @Column(name = "country", length = 50)
    private String country = "中国";
    
    @Column(name = "timezone", length = 50)
    private String timezone = "Asia/Shanghai";
    
    @Column(name = "created_at")
    @CreatedDate
    private LocalDateTime createdAt;
    
    @Column(name = "updated_at")
    @LastModifiedDate
    private LocalDateTime updatedAt;
}

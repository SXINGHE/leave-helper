package com.ocbc.ms.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;

@Entity
@Table(name = "holiday_types")
@Data
@NoArgsConstructor
@RequiredArgsConstructor
public class HolidayType {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "type_code", unique = true, nullable = false, length = 20)
    private String typeCode;
    
    @Column(name = "type_name", nullable = false, length = 50)
    private String typeName;
    
    @Column(name = "description", columnDefinition = "TEXT")
    private String description;
    
    @Column(name = "is_national")
    private Boolean isNational = true;
    
    @Column(name = "created_at")
    @CreatedDate
    private LocalDateTime createdAt;

}

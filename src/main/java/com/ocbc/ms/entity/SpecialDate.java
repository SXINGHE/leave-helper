package com.ocbc.ms.entity;

import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "t_special_date")
@Data
@NoArgsConstructor
@RequiredArgsConstructor
public class SpecialDate {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "calendar_code", nullable = false)
    private String calendarCode;
    
    @Column(name = "calendar_date", nullable = false)
    private LocalDate calendarDate;
    
    @Column(name = "description", nullable = false, length = 100)
    private String description;
    
    @Column(name = "is_workday")
    private Boolean isWorkday = false;
    
    @Column(name = "is_active")
    private Boolean isActive = true;
    
    @Column(name = "\"year\"", nullable = false)
    private Integer year;

    @Column(name = "created_at")
    @CreatedDate
    private LocalDateTime createdAt;
    
    @Column(name = "updated_at")
    @LastModifiedDate
    private LocalDateTime updatedAt;

}

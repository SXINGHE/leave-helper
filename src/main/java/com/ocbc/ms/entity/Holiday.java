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
@Table(name = "holidays")
@Data
@NoArgsConstructor
@RequiredArgsConstructor
public class Holiday {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
/*    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "city_id")
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private City city;*/
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "holiday_type_id")
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private HolidayType holidayType;
    
    @Column(name = "holiday_date", nullable = false)
    private LocalDate holidayDate;
    
    @Column(name = "holiday_name", nullable = false, length = 100)
    private String holidayName;
    
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

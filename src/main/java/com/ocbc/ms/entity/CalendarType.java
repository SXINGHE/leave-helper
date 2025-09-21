package com.ocbc.ms.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;

@Entity
@Table(name = "t_calendar_type")
@Data
@NoArgsConstructor
@RequiredArgsConstructor
public class CalendarType {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * default
     */
    @Column(name = "code", unique = true, nullable = false, length = 20)
    private String code;

    /**
     * default
     */
    @Column(name = "name", nullable = false, length = 50)
    private String name;

    /**
     * 默认日历/*宗教专用日历
     */
    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @Column(name = "created_at")
    @CreatedDate
    private LocalDateTime createdAt;

}

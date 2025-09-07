package com.ocbc.ms.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.UUID;

@Entity
@Table(name = "maternity_calculations")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class MaternityCalculation {
    @Id
    @Column(name = "id")
    private UUID id;

    // 测算输入参数
    @Column(name = "expected_delivery_date", nullable = false)
    private LocalDate expectedDeliveryDate;

    @Column(name = "delivery_type", nullable = false)
    private String deliveryType = "single_vaginal";

    @Column(name = "is_cesarean", nullable = false)
    private Boolean isCesarean = false;

    @Column(name = "is_multiple_birth", nullable = false)
    private Boolean isMultipleBirth = false;

    @Column(name = "multiple_birth_count")
    private Integer multipleBirthCount;

    @Column(name = "is_late_pregnancy", nullable = false)
    private Boolean isLatePregnancy = false;

    @Column(name = "insurance_months", nullable = false)
    private Integer insuranceMonths;

    // 薪酬数据
    @Column(name = "employee_avg_salary", nullable = false)
    private BigDecimal employeeAvgSalary;

    @Column(name = "company_avg_salary", nullable = false)
    private BigDecimal companyAvgSalary;

    // 计算结果（摘要）
    @Column(name = "total_maternity_leave_days", nullable = false)
    private Integer totalMaternityLeaveDays;

    @Column(name = "maternity_allowance", nullable = false)
    private BigDecimal maternityAllowance;

    @Column(name = "employer_top_up", nullable = false)
    private BigDecimal employerTopUp;

    @Column(name = "total_cost_to_employer", nullable = false)
    private BigDecimal totalCostToEmployer;

    // 状态与操作
    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    private CalculationStatus status = CalculationStatus.COMPLETED;

    @Column(name = "calculated_by", nullable = false)
    private String calculatedBy;

    @Column(name = "calculated_at", nullable = false)
    private ZonedDateTime calculatedAt;

    @Column(name = "updated_at", nullable = false)
    private ZonedDateTime updatedAt;

    @Column(name = "notes")
    private String notes;

    // Enums
    public enum CalculationStatus {
        COMPLETED, PENDING, CANCELED
    }
}

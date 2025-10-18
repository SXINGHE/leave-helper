package com.ocbc.ms.entity;


import com.ocbc.ms.dto.AllowanceDetail;
import com.ocbc.ms.dto.CalculateComments;
import com.ocbc.ms.dto.LeaveDetail;
import lombok.Data;
import jakarta.persistence.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.time.LocalDate;

@Data
@Entity
@Table(name = "t_leave_history")
public class LeaveHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "staff_name")
    private String staffName;
    @Column(name = "city_code")
    private String cityCode;
    @Column(name = "leave_start_date")
    private LocalDate leaveStartDate;
    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "leave_detail", columnDefinition = "jsonb")
    private LeaveDetail leaveDetail;
    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "allowance_detail", columnDefinition = "jsonb")
    private AllowanceDetail allowanceDetail;
    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "calculate_comments", columnDefinition = "jsonb")
    private CalculateComments calculateComments;
    /**
     * 是否为流产
     */
    @Column(name = "abortion")
    private boolean abortion;

}

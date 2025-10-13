package com.ocbc.ms.entity;


import com.ocbc.ms.dto.AllowanceDetail;
import com.ocbc.ms.dto.CalculateComments;
import com.ocbc.ms.dto.LeaveDetail;
import lombok.Data;
import jakarta.persistence.*;

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
    @Column(name = "leave_detail")
    private LeaveDetail leaveDetail;
    @Column(name = "allowance_detail")
    private AllowanceDetail allowanceDetail;
    @Column(name = "calculate_comments")
    private CalculateComments calculateComments;
    /**
     * 是否为流产
     */
    @Column(name = "abortion")
    private boolean abortion;

}

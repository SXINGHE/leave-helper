package com.ocbc.ms.entity;

import com.ocbc.ms.model.MaternityLeaveCalculateRequest;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class MaternityLeaveHistory {

    private String id;
    private MaternityLeaveCalculateRequest maternityLeaveCalculateRequest;
    private LocalDate leaveBeginDate;
    private LocalDate leaveEndDate;
    private BigDecimal allowance;

    /**
     * TO check with team if we need this two fields or not
     */
    private boolean isCalculateByLatestCalendar;
    private boolean isCalculateByLatestPolicy;

}

package com.ocbc.ms.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class MaternityLeaveCalculateResponse {

    private LocalDate leaveEndDate;

    /*

     */
    private BigDecimal allowance;

    /*
        obj
     */
    private LeaveCalculateDetail leaveCalculateDetail;
}

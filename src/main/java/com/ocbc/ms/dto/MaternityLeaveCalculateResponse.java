package com.ocbc.ms.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class MaternityLeaveCalculateResponse {

    private String leaveEndDate;

    /*

     */
    private BigDecimal allowance;

    /*
        obj
     */
    private String leaveCalculateDetail;
}

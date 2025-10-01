package com.ocbc.ms.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class LeaveDetail {

    private LocalDate leaveStartDate;

    private LocalDate leaveEndDate;
}

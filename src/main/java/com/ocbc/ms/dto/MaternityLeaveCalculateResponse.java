package com.ocbc.ms.dto;

import com.ocbc.ms.model.MaternityLeaveDatePolicy;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;

@Data
public class MaternityLeaveCalculateResponse {

    private LocalDate leaveEndDate;

    private AllowanceDetail allowanceDetail;

    private LeaveDetail leaveDetail;

    private CalculateComments calculateComments;

    private long currentLeaveDays = 0L;

    public MaternityLeaveCalculateResponse() {
        this.calculateComments = new CalculateComments();
        this.calculateComments.setDescriptionList(new ArrayList<>());
        this.allowanceDetail = new AllowanceDetail();
        this.leaveDetail = new LeaveDetail();
    }

}

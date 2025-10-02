package com.ocbc.ms.dto;

import lombok.Data;

import java.time.LocalDate;
import java.util.ArrayList;

@Data
public class CalculateResponse {

    private AllowanceDetail allowanceDetail;

    private LeaveDetail leaveDetail;

    private CalculateComments calculateComments;

    public CalculateResponse() {
        this.calculateComments = new CalculateComments();
        this.calculateComments.setDescriptionList(new ArrayList<>());
        this.allowanceDetail = new AllowanceDetail();
        this.leaveDetail = new LeaveDetail();
    }

}

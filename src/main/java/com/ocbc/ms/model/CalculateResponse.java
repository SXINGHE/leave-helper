package com.ocbc.ms.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDate;
import java.util.ArrayList;

@Data
@Schema(description = "Response for maternity leave calculation")
public class CalculateResponse {

    @Schema(description = "Allowance calculation details")
    private AllowanceDetail allowanceDetail;

    @Schema(description = "Leave date and duration details")
    private LeaveDetail leaveDetail;

    @Schema(description = "Calculation comments and descriptions")
    private CalculateComments calculateComments;

    public CalculateResponse() {
        this.calculateComments = new CalculateComments();
        this.calculateComments.setDescriptionList(new ArrayList<>());
        this.allowanceDetail = new AllowanceDetail();
        this.leaveDetail = new LeaveDetail();
    }

}

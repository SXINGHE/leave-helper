package com.ocbc.ms.dto.history;

import com.ocbc.ms.dto.AllowanceDetail;
import com.ocbc.ms.dto.CalculateComments;
import com.ocbc.ms.dto.LeaveDetail;
import lombok.Data;

import java.time.LocalDate;

@Data
public class SaveCalculationRequest {

     private String staffName;
     private String cityCode;
     private LocalDate leaveStartDate;
     private LeaveDetail leaveDetail;
     private AllowanceDetail allowanceDetail;
     private CalculateComments calculateComments;
}

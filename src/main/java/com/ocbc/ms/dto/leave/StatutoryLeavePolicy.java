package com.ocbc.ms.dto.leave;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema
public class StatutoryLeavePolicy extends LeavePolicy {

    @Schema(description = "法定产假天数", example = "98")
    private int leaveDays;
}

package com.ocbc.ms.dto.calendar;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.util.List;

@Data
public class SpecialDateSetupRequest {

    private String calendarCode;
    /*
     * 公共假日
     */
    private List<SpecialDto> publicHolidays;
    /*
     * 额外的工作日
     */
    private List<SpecialDto> extraWorkdays;
}

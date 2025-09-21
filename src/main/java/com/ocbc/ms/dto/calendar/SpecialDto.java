package com.ocbc.ms.dto.calendar;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;

@Data
public class SpecialDto {

    @NotNull(message = "节假日日期不能为空")
    private LocalDate calendarDate;

    private String description;

    private Boolean isWorkday;
}

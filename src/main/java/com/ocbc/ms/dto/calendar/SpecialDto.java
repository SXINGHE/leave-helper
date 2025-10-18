package com.ocbc.ms.dto.calendar;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;

@Data
public class SpecialDto {

    private LocalDate calendarDate;
    private String description;
}

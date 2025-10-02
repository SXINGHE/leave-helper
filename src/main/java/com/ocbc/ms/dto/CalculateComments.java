package com.ocbc.ms.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

@Data
@Schema(description = "Calculation comments and descriptions")
public class CalculateComments {

    @Schema(description = "List of calculation descriptions and notes", example = "[\"Statutory leave: 98 days\", \"Additional leave for multiple births: 15 days\"]")
    private List<String> descriptionList;
}

package com.ocbc.ms.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Data
@Schema(description = "Request for calculating maternity leave dates")
public class DateCalculateRequest {

    @Schema(description = "Staff name", example = "Zhang San")
    private String staffName;
    /**
     * yyyyMMdd
     */
    @Schema(description = "Child birth date in yyyyMMdd format", example = "20240315")
    private String childBirthdate;

    /**
     * 胎儿数量
     * 默认为1
     */
    @Schema(description = "Number of infants", example = "1", defaultValue = "1")
    private int infantNumber = 1;
    /**
     * 第几胎？
     * 默认为1
     */
    @Schema(description = "Delivery sequence number", example = "1", defaultValue = "1")
    private int deliverySequence = 1;

    /**
     * 是否流产？
     * 默认为否
     */
    @Schema(description = "Is abortion case", example = "false", defaultValue = "false")
    private boolean abortion;

    @Schema(description = "Is dystocia case", example = "false")
    private boolean dystocia;

    /**
     * drop down list
     */
    @Schema(description = "City name for policy lookup", example = "Guangzhou")
    private String cityName;

    /**
     * E2P/OC
     */
    @Schema(description = "Company name", example = "E2P")
    private String companyName;

    @Schema(description = "Leave start date", example = "2024-03-01")
    private LocalDate leaveStartDate;

    @Schema(description = "Calendar code", example = "CN")
    private String calendarCode;

    /**
     * 流产假计算参数
     */
    @Schema(description = "Pregnancy days for abortion leave calculation", example = "90")
    private int regnancyDays;
    @Schema(description = "Is ectopic pregnancy", example = "false")
    private boolean ectopicPregnancy;
    @Schema(description = "Recommended abortion leave days", example = "15")
    private int recommendAbortionLeaveDays;

    /**
     * 难产假计算参数
     * 默认为空
     * 难产类型
     * Guang Zhou 难产（剖腹产、会阴Ⅲ度破裂）另加30天；吸引产、钳产、臀位牵引产另加15天；
     */
    @Schema(description = "Dystocia type codes for additional leave calculation", example = "[\"CESAREAN\"]")
    private List<String> dystociaCodeList;
     /**
     * 其他产假计算参数
     * 默认为空
     */
    @Schema(description = "Extended leave type codes", example = "[\"UUID\"]")
    private String extendedCode;
    @Schema(description = "Abortion leave type codes", example = "[\"UUID\"]")
    private String abortionCode;

    /**
     *
     */
    @Schema(description = "当规则代码命中且规则中假期天数为空时，会根据此map中的值来计算假期天数，目前仅有流产假有此场景", example = "{\"UUID1\": 15, \"UUID2\": 30}")
    private Map<String, Integer> dynamicLeaveDaysMap;


}

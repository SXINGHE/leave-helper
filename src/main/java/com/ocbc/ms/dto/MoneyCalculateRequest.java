package com.ocbc.ms.dto;

import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class MoneyCalculateRequest {

    private String staffName;
    /**
     * yyyyMMdd
     */
    private String childBirthdate;

    /**
     * 胎儿数量
     * 默认为1
     */
    private int infantNumber = 1;
    /**
     * 第几胎？
     * 默认为1
     */
    private int deliverySequence = 1;

    /**
     * 是否流产？
     * 默认为否
     */
    private boolean abortion;

    /**
     * drop down list
     */
    private String cityName;

    /**
     * E2P/OC
     */
    private String companyName;

    private LocalDate leaveStartDate;

    private String calendarCode;

    /**
     * 流产假计算参数
     */
    private int regnancyDays;
    private boolean ectopicPregnancy;
    private int recommendAbortionLeaveDays;

    /**
     * 难产假计算参数
     * 默认为空
     * 难产类型
     * Guang Zhou 难产（剖腹产、会阴Ⅲ度破裂）另加30天；吸引产、钳产、臀位牵引产另加15天；
     */
    private List<String> dystociaCodeList;


}

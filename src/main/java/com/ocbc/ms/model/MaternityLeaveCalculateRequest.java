package com.ocbc.ms.model;

import com.ocbc.ms.constants.ParturitionTypeEnum;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class MaternityLeaveCalculateRequest {

    private String staffName;
    /**
     * 默认为1
     * 部分城市多胎时需要根据这个计算额外假期
     */
    private String childNumber;
    /*
        drop down list
     */
    private String cityName;
    /*
        E2P/OC
     */
    private String corpName;

    private LocalDate leaveStartDate;

    /**
     * 部分城市剖腹产有额外假期
     */
    private ParturitionTypeEnum parturitionType;

    private BigDecimal lastYearAverageSalary;


}

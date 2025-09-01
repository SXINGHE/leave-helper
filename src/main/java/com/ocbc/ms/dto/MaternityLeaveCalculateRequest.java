package com.ocbc.ms.dto;

import lombok.Data;

@Data
public class MaternityLeaveCalculateRequest {

    private String staffName;
    /**
     * yyyyMMdd
     */
    private String childBirthdate;
    /*
        默认为1
     */
    private String childNumber;

    /*
        drop down list
     */
    private String cityName;

    /*
        E2P/OC
     */
    private String companyName;

    private String leaveStartDate;


}

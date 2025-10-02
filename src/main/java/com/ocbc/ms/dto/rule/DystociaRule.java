package com.ocbc.ms.dto.rule;

import lombok.Data;

@Data
public class DystociaRule {

    private int leaveDays;
    /*
     *  难产假特殊规则代码
     */
    private String dystociaCode;
    /*
     *  默认
     *  难产（剖腹产、会阴Ⅲ度破裂）
     *  吸引产、钳产、臀位牵引产另加15天
     */
    private String description;
}

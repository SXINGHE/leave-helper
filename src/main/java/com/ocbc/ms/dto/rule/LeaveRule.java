package com.ocbc.ms.dto.rule;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "产假规则数据")
public class LeaveRule {

    @Schema(description = "规则命中时假期天数,如未可变值则此值设空，前端识别此值没空时应根据其描述给出输入框", example = "15")
    private Integer leaveDays;
    @Schema(description = "规则代码，唯一标识，随机生成的uuid", example = "UUID")
    private String ruleCode;
    @Schema(description = "规则描述", example = "吸引产、钳产、臀位牵引产另加15天/妊娠满4个月流产，42天")
    private String description;

}

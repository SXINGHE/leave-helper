package com.ocbc.ms.dto.policy;

import com.ocbc.ms.dto.allowance.AllowancePolicy;
import com.ocbc.ms.dto.leave.*;
import io.swagger.v3.oas.annotations.media.Schema;

import lombok.Data;

@Data
@Schema(description = "产假政策数据")
public class PolicyDto {

    @Schema(description = "政策ID", example = "1")
    private Long id;

    @Schema(description = "城市名称", example = "Shanghai")
    private String cityName;

    @Schema(description = "法定产假政策配置")
    private StatutoryLeavePolicy statutoryPolicy;

    @Schema(description = "难产假政策配置")
    private DystociaLeavePolicy dystociaPolicy;

    @Schema(description = "多胎产假政策配置")
    private MoreInfantLeavePolicy moreInfantPolicy;

    @Schema(description = "其他延长产假政策配置")
    private OtherExtendedLeavePolicy otherExtendedPolicy;

    @Schema(description = "流产假政策配置")
    private AbortionLeavePolicy abortionPolicy;

    @Schema(description = "产假津贴政策配置")
    private AllowancePolicy allowancePolicy;

}
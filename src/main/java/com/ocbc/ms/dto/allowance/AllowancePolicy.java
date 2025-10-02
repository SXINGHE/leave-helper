package com.ocbc.ms.dto.allowance;


import com.ocbc.ms.dto.rule.DifferenceCompensationRule;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
@Schema
public class AllowancePolicy {

    /**
     * 基础计算基数，为月平均工资或月缴费平均工资，由HR填写实际金额
     */
    private List<CorpSalaryDetail> corpSalaryDetailList;
    /**
     * 津贴基数计算分子，大部分城市为1，成都为12
     */
    private BigDecimal numerator;
    /**
     * 津贴基数计算分母，大部分城市为30，成都为365，天津为30.4
     */
    private BigDecimal denominator;

    /**
     * 享受生育津贴天数
     */
    private int allowanceDays;
     /**
      * 目标账户类型，为企业/个人
      * 本系统仅记录，此字段不实际参与处理
      */
    private String targetAccountType;

    private DifferenceCompensationRule differenceCompensationRule;
     /**
      * 政府发放金额
      */
    private BigDecimal govAllowance;


}

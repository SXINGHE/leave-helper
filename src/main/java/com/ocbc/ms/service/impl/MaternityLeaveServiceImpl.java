package com.ocbc.ms.service.impl;

import com.ocbc.ms.constants.ParturitionTypeEnum;
import com.ocbc.ms.entity.LeaveRules;
import com.ocbc.ms.model.MaternityLeaveCalculateRequest;
import com.ocbc.ms.model.MaternityLeaveCalculateResponse;
import com.ocbc.ms.service.MaternityLeaveService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Service
public class MaternityLeaveServiceImpl implements MaternityLeaveService {
    @Override
    public MaternityLeaveCalculateResponse calculateMaternityLeave(MaternityLeaveCalculateRequest maternityLeaveCalculateRequest) {
        var response = new MaternityLeaveCalculateResponse();
        /**
         * step 1. find policy by city and corp
         * step 2. calculate leave end date by the input params and LeaveRules list in policy
         * step 3. calculate allowance based on the leave end date and allowanceRules in policy
         * step 4. generate response
         */
        return response;
    }


    /**
     * 需要返回产假结束时间
     * 以及
     * 补贴计算的日期天数
     * @param leaveStartDate
     * @param leaveRules
     * @return
     */
    private LocalDate getLeaveEndDate(LocalDate leaveStartDate, List<LeaveRules> leaveRules) {

        return LocalDate.now();
    }

    /**
     * 根据各城市规则计算该员工要缴纳的五险一金金额
     * @return 应缴费金额
     */
    private BigDecimal getSocialSecurity() {
        return BigDecimal.ONE;
    }

    /**
     *
     * 顺产和难产计算天数不一样,单胎和多胎也不一样
     * @param leaveRange 计算津贴时有效天数
     * @param corpAverageDailySalary 公司平均工资（每天）
     * @return leaveRange*corpAverageDailySalary
     */
    private BigDecimal getAllowance(int leaveRange, BigDecimal corpAverageDailySalary) {

        return BigDecimal.ONE;
    }

}

package com.ocbc.ms.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.ocbc.ms.dto.allowance.AllowancePolicy;
import com.ocbc.ms.dto.leave.*;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.type.SqlTypes;
import org.hibernate.annotations.JdbcTypeCode;
import jakarta.persistence.Column;


/**
 * we should find policy by city name and Company name
 */
@Entity
@Table(name = "t_policy")
@Data
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class Policy {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "city_code")
    private String cityCode;
    /**
     * 目前仅厦门有最大产假天数，为180天
     */
    @Column(name = "max_leave_days")
    private Integer maxLeaveDays;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "statutory_policy", columnDefinition = "jsonb")
    private StatutoryLeavePolicy statutoryPolicy;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "dystocia_policy", columnDefinition = "jsonb")
    private DystociaLeavePolicy dystociaPolicy;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "more_infant_policy", columnDefinition = "jsonb")
    private MoreInfantLeavePolicy moreInfantPolicy;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "other_extended_policy", columnDefinition = "jsonb")
    private OtherExtendedLeavePolicy otherExtendedPolicy;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "abortion_policy", columnDefinition = "jsonb")
    private AbortionLeavePolicy abortionPolicy;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "allowance_policy", columnDefinition = "jsonb")
    private AllowancePolicy allowancePolicy;


}

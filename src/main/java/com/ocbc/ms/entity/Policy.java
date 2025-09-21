package com.ocbc.ms.entity;

import com.ocbc.ms.model.MaternityLeaveDatePolicy;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;import org.hibernate.type.SqlTypes;
import org.hibernate.annotations.JdbcTypeCode;
import jakarta.persistence.Column;


/**
 * we should find policy by city name and Company name
 */
@Entity
@Table(name = "t_policy")
@Data
@NoArgsConstructor
@RequiredArgsConstructor
public class Policy {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "city_name")
    private String cityName;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "statutory_policy", columnDefinition = "jsonb")
    private MaternityLeaveDatePolicy statutoryPolicy;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "dystocia_policy", columnDefinition = "jsonb")
    private MaternityLeaveDatePolicy dystociaPolicy;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "more_infant_policy", columnDefinition = "jsonb")
    private MaternityLeaveDatePolicy moreInfantPolicy;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "other_extended_policy", columnDefinition = "jsonb")
    private MaternityLeaveDatePolicy otherExtendedPolicy;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "abortion_policy", columnDefinition = "jsonb")
    private MaternityLeaveDatePolicy abortionPolicy;

}

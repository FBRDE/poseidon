package com.nnk.springboot.domain;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
@Table(name = "curvepoint")
@NoArgsConstructor @AllArgsConstructor @Getter @Setter
public class CurvePoint {
    // Map columns in data table CURVEPOINT with corresponding java fields
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    Integer id;
    @Column(name="curve_id")
    @NotNull(message = "CurveId mut not be null")
    Integer curveId;
    @Column(name="as_of_date")
    LocalDateTime asOfDate;
    @Column(name="term")
    @Digits(integer = 10 /*precision*/, fraction = 2 /*scale*/,message = "Term must have just 2 digit after '.' and 10 integer before '.'")
    Double term;
    @Column(name="value")
    @Digits(integer = 10 /*precision*/, fraction = 2 /*scale*/,message = "Value must have just 2 digit after '.' and 10 integer before '.'")
    Double value;
    @Column(name="creation_date")
    LocalDateTime creationDate;

    public CurvePoint(int i, double t, double v)
    {
        curveId=i;
        term=t;
        value=v;
    }

}

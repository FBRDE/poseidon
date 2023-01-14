package com.nnk.springboot.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@NoArgsConstructor @AllArgsConstructor @Getter  @Setter
@Entity
@Table(name = "rulename")
public class RuleName {
    // Map columns in data table RULENAME with corresponding java fields
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    Integer id;
    @NotBlank(message = "Name is mandatory")
    @Column(name="name")
    String name;
    @NotBlank(message = "Description is mandatory")
    @Column(name="description")
    String description;
    @Column(name="json")
    String json;
    @Column(name="template")
    String template;
    @Column(name="sql_str")
    String sqlStr;
    @Column(name="sql_part")
    String sqlPart;

    public RuleName(String nameV, String descriptionV, String jsonV, String templateV, String sqlV, String sql_partV) {
        name=nameV;
        description=descriptionV;
        json=jsonV;
        template=templateV;
        sqlStr=sqlV;
        sqlPart=sql_partV;
    }
}

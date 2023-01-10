package com.nnk.springboot.domain;

import com.nnk.springboot.validator.ValidPassword;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "users")
public class User {
    // Map columns in data table user with corresponding java fields
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer id;
    @NotBlank(message = "Username is mandatory")
    @Column(name="username")
    private String userName;
    @NotBlank(message = "Password is mandatory")
    @ValidPassword
    private String password;
    @Column(name="fullname")
    private String fullName;
    @NotBlank(message = "Role is mandatory")
    private String role;

    public User(String userName, String role,String password) {
        this.userName=userName;
        this.role=role;
        this.password=password;
    }

}

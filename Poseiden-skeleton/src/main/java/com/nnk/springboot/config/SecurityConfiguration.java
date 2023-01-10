package com.nnk.springboot.config;


import com.nnk.springboot.services.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {
    @Autowired
    DataSource dataSource;

    @Bean
    public UserDetailsService userDetailsService() {
        return new CustomUserDetailsService();
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http
                .authorizeRequests()
                .antMatchers("/login")
                .permitAll();
        http.authorizeRequests()
                .antMatchers("/", "/bidList/**", "/curvePoint/**",
                        "/rating/**", "/ruleName/**", "/trade/**","/home","/logout")
                .authenticated().antMatchers("/user/**").hasAuthority("ADMIN");

        http.formLogin().defaultSuccessUrl("/home").and()
                .logout().logoutSuccessUrl("/");

        return http.build();
    }

}

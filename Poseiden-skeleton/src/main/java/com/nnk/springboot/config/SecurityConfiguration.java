package com.nnk.springboot.config;


import com.nnk.springboot.services.CustomUserDetailsService;
import com.nnk.springboot.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import java.io.IOException;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {


    @Bean
    public UserDetailsService userDetailsService() {
        return new CustomUserDetailsService();
    }
    @Autowired
    private  CustomOAuth2UserService customOAuth2UserService;
    @Autowired
    DataSource dataSource;
    @Autowired
    private UserService userService;

    @Bean
    public BCryptPasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http
                .authorizeRequests()
                .antMatchers("/login","/oauth2/**")
                .permitAll();
        http.authorizeRequests().antMatchers("/home").hasRole("USER");
        http.authorizeRequests()
                .antMatchers("/", "/bidList/**", "/curvePoint/**",
                        "/rating/**", "/ruleName/**", "/trade/**","/home","/logout")
                .authenticated().antMatchers("/user/**").hasAuthority("ADMIN").and().csrf().disable().httpBasic();

        http.formLogin()
                .loginPage("/login")
                .loginProcessingUrl("/login")
                .defaultSuccessUrl("/home");
        http.logout().logoutSuccessUrl("/login");


        http.oauth2Login().loginPage("/login").userInfoEndpoint().userService(customOAuth2UserService)
                .and()
                .successHandler(new AuthenticationSuccessHandler() {
                    @Override
                    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                                        Authentication authentication) throws IOException {

                        String userName=null;

                        //gmail
                        if(authentication.getPrincipal() instanceof DefaultOidcUser)
                        {
                            DefaultOidcUser oauthUser = (DefaultOidcUser) authentication.getPrincipal();
                            userName = oauthUser.getAttribute("email");

                        }
                        //facebook
                        else if(authentication.getPrincipal() instanceof CustomOAuth2User)
                        {
                            CustomOAuth2User oauthUser = (CustomOAuth2User) authentication.getPrincipal();
                            userName = oauthUser.getAttribute("email");

                        }

                        userService.processOAuthPostLogin(userName );
                        response.sendRedirect("/home");
                    }
                })
        ;

        return http.build();
    }
    @Bean
    public PersistentTokenRepository persistentTokenRepository() {
        JdbcTokenRepositoryImpl tokenRepository=new JdbcTokenRepositoryImpl();
        tokenRepository.setDataSource(dataSource);
        return tokenRepository;
    }
}

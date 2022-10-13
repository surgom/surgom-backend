//package com.greentea.surgom.security;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.http.SessionCreationPolicy;
//import org.springframework.security.web.SecurityFilterChain;
//
//@Configuration
//@EnableWebSecurity
//public class SecurityConfig {
//    @Bean
//    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//        http
//                .csrf().disable();
//
//        http
//                .authorizeRequests()
//                .antMatchers("/naver").permitAll()
//                .anyRequest().authenticated();
//
//        http
//                .sessionManagement()
//                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
//
//        http
//                .exceptionHandling()
//                .accessDeniedHandler(jwtAccessDeniedHandler)
//                .authenticationEntryPoint(jwtAutenticationEntryPoint);
//
//        http
//                .apply(new JwtSecurityConfig(jwtTokenProvider));
//
//        return http.build();
//    }
//}

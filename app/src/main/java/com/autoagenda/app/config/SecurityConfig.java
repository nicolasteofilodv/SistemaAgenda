package com.autoagenda.app.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
   
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        var conf = http.authorizeHttpRequests
        ((authorize) -> authorize.requestMatchers("/register")
        .permitAll()
        .requestMatchers("/login").permitAll()
        .anyRequest()
        .authenticated()
    );

        conf.formLogin((formLogin) -> formLogin.loginPage("/login"));
    
        
        return conf.build();
    
    }
}

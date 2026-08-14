package com.autoagenda.app.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
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
        .requestMatchers("/*/agenda").permitAll()
        .anyRequest()
        .authenticated()
    );

        conf.formLogin((formLogin) -> formLogin.loginPage("/login"));
    
        
        return conf.build();
    
    }

   @Bean
   public static PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
   } 
}

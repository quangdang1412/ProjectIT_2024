package com.webbanhang.webbanhang.Config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(requests -> requests
                .requestMatchers("/","/static/**","/template/**","/error","/api/**", "/assets/**","/signup","/register","/shop","/ImageProduct/**","/detail/**","/error","/locations").permitAll()
                .requestMatchers("/admin/Order").hasAnyRole("SELLER", "SHIPPER","ADMIN")
                .requestMatchers("/admin/Order?type=1").hasRole("SELLER")
                .requestMatchers("/admin/Order?type=2", "/admin/Order?type=3").hasRole("SHIPPER")
                .requestMatchers("/admin/**").hasRole("ADMIN")
                .anyRequest().authenticated()
                );
                
        http.formLogin(form -> form
                .loginPage("/login").defaultSuccessUrl("/")
                .permitAll()
        );
        http.logout(logout -> logout
                .logoutSuccessUrl("/")
        );
        http.csrf(AbstractHttpConfigurer::disable);

//      http.exceptionHandling(exceptionHandling -> exceptionHandling
//              .accessDeniedPage("/404")
//      );

        return http.build();
    }
    
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
}
package com.webbanhang.webbanhang.Config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor

public class WebSecurityConfig {
    
    private final AuthenticationFilter authenticationFilter;
    private final AuthenticationProvider authenticationProvider;


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(requests -> requests
                .requestMatchers(
                    "/", 
                    "/static/**", 
                    "/template/**", 
                    "/error", 
                    "/api/**", 
                    "/assets/**", 
                    "/signup", 
                    "/register", 
                    "/shop", 
                    "/ImageProduct/**", 
                    "/detail/**", 
                    "/locations"
                ).permitAll()
                .requestMatchers("/admin/Order").hasAnyRole("SELLER", "SHIPPER", "ADMIN")
                .requestMatchers("/admin/Order?type=1").hasRole("SELLER")
                .requestMatchers("/admin/Order?type=2", "/admin/Order?type=3").hasRole("SHIPPER")
                .requestMatchers("/admin/**").hasRole("ADMIN")
                .anyRequest().authenticated()
            )
            .sessionManagement(session -> session
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .authenticationProvider(authenticationProvider)
            .addFilterBefore(authenticationFilter, UsernamePasswordAuthenticationFilter.class)
            .formLogin(form -> form
                .loginPage("/login")
                .defaultSuccessUrl("/")
                .permitAll()
            )
            .logout(logout -> logout
                .logoutSuccessUrl("/")
            )
            .exceptionHandling(exception -> exception
                .authenticationEntryPoint((request, response, authException) -> 
                    response.sendError(403, "Access Denied")  // Trả về lỗi 403 khi chưa đăng nhập
                )
            )
            .httpBasic(Customizer.withDefaults());

            
        http.csrf(AbstractHttpConfigurer::disable);
        
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
}

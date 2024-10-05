package com.webbanhang.webbanhang.Config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
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
    private  String[] WHITE_LITS ={ "/",
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
            "/locations",
            "/login",
            "/inforuser",
            "/changePassword",
            "/yourOrder",
            "/cart",
            "/checkout",
            "/test"};
    private String []role_more = {"SELLER", "SHIPPER", "ADMIN"};

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(requests -> requests
                .requestMatchers( WHITE_LITS).permitAll()
                .requestMatchers("/admin/Order").hasAnyAuthority(role_more)
                .requestMatchers("/admin/**").hasAuthority("ADMIN")
                .anyRequest().authenticated()
            )
            .sessionManagement(session -> session
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .formLogin(Customizer.withDefaults()) // Sử dụng trang login mặc định của Spring Security
            .addFilterBefore(authenticationFilter, UsernamePasswordAuthenticationFilter.class) // Thêm JWT filter
            .authenticationProvider(authenticationProvider) // Cấu hình provider
            .exceptionHandling(exception -> exception
                .authenticationEntryPoint((request, response, authException) ->
                    response.sendError(403, "Access Denied")
                )
            );
            http.exceptionHandling(exceptionHandling -> exceptionHandling
                          .accessDeniedPage("/404")
            );
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

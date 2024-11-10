package com.webbanhang.webbanhang.Config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@Slf4j
public class WebSecurityConfig {

    private final AuthenticationFilter authenticationFilter;
    private final AuthenticationProvider authenticationProvider;
    private final String[] WHITE_LITS ={ "/",
            "/static/**",
            "/template/**",
            "/signinoauth2",
            "/loginSuccess",
            "/error",
            "/api/auth/**",
            "/assets/**",
            "/asset-admin/**",
            "/signup",
            "/register",
            "/shop",
            "/ImageProduct/**",
            "/api/addtocart/**",
            "/detail/**",
            "/locations",
            "/login/**",
            "/inforuser",
            "/changePassword",
            "/reset-password",
            "/yourOrder/**",
            "/cart",
            "/checkout",
            "/admin/**",
            "/test",
            "/api/payments/**"
        };
    private final String[] EMPLOYEE_LIST={
            "/api/order/update",
    };
    private final String[] ADMIN_LIST={
            "/api/order/update",
            "/api/order/delete/**",
            "/api/other/**",
            "/api/product/add",
            "/api/product/update",
            "/api/dashboard/**",
            "/api/product/delete/**",
            "/api/supplier/**",
            "/api/user/add",
            "/api/user/delete/**",
            "/api/user/update"
    };
    private final String []role_more = {"SELLER", "ADMIN"};

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(requests -> requests
                .requestMatchers( WHITE_LITS).permitAll()
                .requestMatchers(EMPLOYEE_LIST).hasAnyAuthority(role_more)
                .requestMatchers(ADMIN_LIST).hasAuthority("ADMIN")
                .anyRequest().authenticated()
            )
            .oauth2Login(Customizer.withDefaults())
            .sessionManagement(session -> session
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .formLogin(Customizer.withDefaults())// Sử dụng trang login mặc định của Spring Security
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
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
}

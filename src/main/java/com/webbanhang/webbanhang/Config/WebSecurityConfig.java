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
import org.springframework.web.cors.CorsConfiguration;

import jakarta.servlet.http.HttpServletResponse;
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
            "/api/auth/**",
            "/assets/**",
            "/asset-admin/**",
            "/signup",
            "/register",
            "/shop",
            "/ImageProduct/**",
            "/detail/**",
            "/locations",
            "/login",
            "/inforuser",
            "/changePassword",
            "/yourOrder/**",
            "/cart",
            "/checkout",
            "/admin/**",
            "/test"};
    private String[] EMPLOYEE_LIST={
            "/api/order/update",
    };
    private String[] ADMIN_LIST={
            "/api/order/update",
            "/api/order/delete/**",
            "/api/other/**",
            "/api/product/add",
            "/api/product/update",
            "/api/product/delete/**",
            "/api/supplier/**",
            "/api/user/add",
            "/api/user/delete/**",
            "/api/user/update"
    };
    private String []role_more = {"SELLER", "SHIPPER", "ADMIN"};

    @Bean
    
public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    http
        .authorizeHttpRequests(requests -> requests
            .requestMatchers(WHITE_LITS).permitAll()
            .requestMatchers(EMPLOYEE_LIST).hasAnyAuthority("SELLER", "SHIPPER", "ADMIN")
            .requestMatchers(ADMIN_LIST).hasAuthority("ADMIN")
            .anyRequest().authenticated()
        )
        .sessionManagement(session -> session
            .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
        .oauth2Login(oauth2 -> oauth2
            .loginPage("/login")  
            .defaultSuccessUrl("/home", true)
        )
        .addFilterBefore(authenticationFilter, UsernamePasswordAuthenticationFilter.class)
        .authenticationProvider(authenticationProvider)
        .exceptionHandling(exception -> exception
            .authenticationEntryPoint((request, response, authException) ->
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized")
            )
            .accessDeniedHandler((request, response, accessDeniedException) ->
                response.sendRedirect("/404")
            )
        )
        .cors(cors -> cors.configurationSource(request -> new CorsConfiguration().applyPermitDefaultValues()))
        .csrf(AbstractHttpConfigurer::disable);
    
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

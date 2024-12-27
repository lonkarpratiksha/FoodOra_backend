package com.foodora.config;

import java.util.Arrays;
import java.util.Collections;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

import io.micrometer.common.lang.Nullable;
import jakarta.servlet.http.HttpServletRequest;

@Configuration
@EnableWebSecurity
public class AppConfig {
    
    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        
        http.sessionManagement(management->management.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .authorizeHttpRequests(Authorize-> Authorize
                .requestMatchers("/api/admin/**").hasAnyRole("RESTAURANT_OWNER","ADMIN")
                .requestMatchers("/api/**").authenticated()
                .anyRequest().permitAll()
            ).addFilterBefore(new JwtTokenValidator(),BasicAuthenticationFilter.class)
            .csrf(csrf->csrf.disable())
            .cors(cors->cors.configurationSource(corsConfigurationSource()));
        return http.build();
    }

    private CorsConfigurationSource corsConfigurationSource(){
        return new CorsConfigurationSource() {

            @Override
            @Nullable
            public CorsConfiguration getCorsConfiguration(@SuppressWarnings("null") HttpServletRequest request) {
                CorsConfiguration cfg=new CorsConfiguration();
                cfg.setAllowedOrigins(Arrays.asList(
                    "http://localhost:3000"
                ));
                cfg.setAllowedMethods(Collections.singletonList("*"));
                cfg.setAllowCredentials(true);
                cfg.setAllowedHeaders(Collections.singletonList("*"));
                cfg.setExposedHeaders(Arrays.asList("Authorization"));
                cfg.setMaxAge(3600L);
    
                return cfg;
            }
            
        };
    }

    @Bean
    PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

}

// How This Code Works in Practice
// Incoming requests are passed through the SecurityFilterChain.
// Requests are evaluated against the authorization rules.
// JWT tokens (if present) are validated by the JwtTokenValidator filter.
// Only requests meeting the criteria are processed further.
// CORS policies are applied to allow cross-origin requests from specific origins.
// Passwords stored or verified in the system use bcrypt hashing.

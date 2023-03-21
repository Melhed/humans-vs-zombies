package com.example.backendhvz.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.web.SecurityFilterChain;

@EnableWebSecurity
@Configuration
public class SecurityConfig {
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .cors().and()
                // Sessions will not be used
                .sessionManagement().disable()
                // Disable CSRF -- not necessary when there are no sessions
                .csrf().disable()
                // Enable security for http requests
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/api/v1/game").permitAll()
                        .requestMatchers("/api/v1/user/**").permitAll()
                        .requestMatchers("/api/v1/game/3/squad").hasRole("hvz-user")
                        // All other endpoints are protected
                        .anyRequest().authenticated()
                )
                .oauth2ResourceServer()
                .jwt()
                .jwtAuthenticationConverter(jwtAuthenticationConverter());
        return http.build();
    }
}

package com.troyadevclub.integraservicios.config.security;

import lombok.extern.log4j.Log4j2;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.Collections;

@EnableWebSecurity
@Configuration
@Log4j2
class WebSecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .cors(cors -> cors.configurationSource(corsConfigurationSource()))
            .csrf(AbstractHttpConfigurer::disable)
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .addFilterBefore(new JWTFilterConfig(), UsernamePasswordAuthenticationFilter.class)
            .authorizeHttpRequests((authz) -> authz
                .requestMatchers("/v3/api-docs/**",
                        "/swagger-ui/**",
                        "/swagger-ui.html").permitAll()
                .requestMatchers(HttpMethod.GET, "/auth/test").permitAll()
                .requestMatchers(HttpMethod.POST, "/auth/**").permitAll()
                .requestMatchers(HttpMethod.GET, "/resource/**").hasAnyRole("UNIDAD", "CLIENTE")
                .requestMatchers(HttpMethod.POST, "/resource/**").hasRole("UNIDAD")
                .requestMatchers(HttpMethod.GET, "/schedule/**").hasAnyRole("UNIDAD", "CLIENTE")
                .requestMatchers(HttpMethod.PUT, "/schedule/**").hasRole("UNIDAD")
                .requestMatchers(HttpMethod.GET, "/feature/**").hasAnyRole("UNIDAD", "CLIENTE", "EMPLEADO")
                .requestMatchers(HttpMethod.POST, "/feature/**").hasRole("UNIDAD")
                .requestMatchers(HttpMethod.POST, "/booking/**").hasAnyRole("UNIDAD", "EMPLEADO", "CLIENTE")
                .requestMatchers(HttpMethod.POST, "/loan/**").hasAnyRole("UNIDAD", "EMPLEADO", "CLIENTE")
                .requestMatchers(HttpMethod.GET, "/user/**").hasAnyRole("EMPLEADO", "CLIENTE", "UNIDAD")
                .requestMatchers(HttpMethod.POST, "/user/**").hasAnyRole("EMPLEADO", "CLIENTE")
                .requestMatchers(HttpMethod.GET, "/external/**").permitAll()
                .anyRequest().authenticated()
            );
        return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        final CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOriginPatterns(Collections.singletonList("*"));
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT"));
        configuration.setAllowCredentials(true);
        configuration.setAllowedHeaders(Collections.singletonList("*"));
        configuration.setExposedHeaders(Arrays.asList("X-Auth-Token","Authorization","Access-Control-Allow-Origin","Access-Control-Allow-Credentials"));
        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

}
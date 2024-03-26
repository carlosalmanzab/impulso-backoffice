package com.impulso.impulsobackoffice.auth.infrastructure.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import com.impulso.impulsobackoffice.auth.infrastructure.middleware.BearerJwtAuthenticationProvider;

@Configuration
@EnableWebSecurity(debug = true)
public class SecurityConfig {

        private final BearerJwtAuthenticationProvider bearerJwtAuthenticationProvider;

        private static final String[] WHITE_LIST = {
                        "/api/v3/api-docs",
                        "/api/v3/api-docs/**",
                        "/api/swagger-ui/**",
                        "/api/swagger-ui.html",
                        "/api/swagger-resources",
                        "/api/swagger-resources/**",
                        "/api/auth/**",
                        "/api/auth/login",
                        "/auth/login"
        };

        SecurityConfig(BearerJwtAuthenticationProvider bearerJwtAuthenticationProvider) {
                this.bearerJwtAuthenticationProvider = bearerJwtAuthenticationProvider;
        }

        @Bean
        public AuthenticationManager authenticationManager(AuthenticationConfiguration conf) throws Exception {
                return conf.getAuthenticationManager();
        }

        @Bean
        public PasswordEncoder passwordEncoder() {
                return new BCryptPasswordEncoder();
        }

        @Bean
        public SecurityFilterChain filterChain(
                        HttpSecurity http) throws Exception {
                return http.csrf(csrfConf -> csrfConf.disable())
                                .cors(cors -> cors.disable())
                                .authorizeHttpRequests(requests -> 
                                        requests.requestMatchers(HttpMethod.POST, WHITE_LIST).permitAll()
                                                        .requestMatchers(HttpMethod.GET, WHITE_LIST).permitAll()
                                                        .requestMatchers(HttpMethod.POST, WHITE_LIST).permitAll()
                                                        .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                                                        .requestMatchers(HttpMethod.HEAD, "/**").permitAll()
                                                        .anyRequest().authenticated()
                                )
                                .sessionManagement(session -> session
                                                .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                                .logout(logout -> logout.logoutUrl("/api/auth/logout"))
                                .addFilterBefore(
                                                bearerJwtAuthenticationProvider,
                                                BasicAuthenticationFilter.class)
                                .build();
        }

}

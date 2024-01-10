package com.example.verbvaultjava.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfiguration {
    private final JwtAuthenticationFilter jwtAuthFilter;
    private final AuthenticationProvider authenticationProvider;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/users/register", "/users/authenticate", "/swagger-ui/**", "v3/api-docs/swagger-config", "v3/api-docs").permitAll()
                        .requestMatchers(HttpMethod.POST, "/courses").hasAuthority("ADMIN")
                        .requestMatchers(HttpMethod.GET, "/courses/{courseId}", "/courses", "/courses/{courseId}/words", "/courses/{courseId}/words/random").permitAll()
                        .requestMatchers(HttpMethod.POST, "/courses/{courseId}/users/{userId}",
                                "/courses/{courseId}/words/translate", "/courses/{courseId}/words/foreign").permitAll()
                       .requestMatchers("/users/{userId}/**", "/users", "/users/words/**").permitAll()
                        .anyRequest().authenticated())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }
}

package com.example.verbvaultjava.config;

import com.example.verbvaultjava.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetailsService;

@Configuration
@RequiredArgsConstructor
public class ApplicationConfig {
    private final UserRepository userRepository;
    @Bean
    public UserDetailsService userDetailsService(){
        return username -> userRepository.findUserByEmail(username)
                .orElseThrow(()-> new IllegalArgumentException("User not found"));
    }
}

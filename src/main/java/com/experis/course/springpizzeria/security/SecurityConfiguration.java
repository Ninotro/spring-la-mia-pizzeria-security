package com.experis.course.springpizzeria.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfiguration {

    @Bean
    public DatabaseUserDetailsService userDetailsService() {
        return new DatabaseUserDetailsService();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        // creo un DaoAuthenticationProvider
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        // gli assegno lo UserDetailsService
        provider.setUserDetailsService(userDetailsService());
        // gli assegno il PasswordEncoder
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }

    // SecurityFilterChain che fa da "dogana"
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests()
                .requestMatchers("/pizzas/**").hasAnyAuthority("ADMIN", "USER")
                .requestMatchers("/pizzas/create", "/pizzas/edit", "/pizzas/delete").hasAuthority("ADMIN")
                .requestMatchers("/offer/**", "/ingredient/**").hasAuthority("ADMIN")
                .anyRequest().permitAll()
                .and().formLogin()
                .and().logout();
        return http.build();

    }

}

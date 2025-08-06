package com.seidelsoft.ERPBackend.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Autowired
    private JwtAuthenticationFilter jwtAuthFilter;
    @Autowired
    private AuthenticationProvider authenticationProvider;

    private final String[] WHITELIST = {"/swagger-ui/**", "/v3/api-docs/**", "/api-docs", "/api/v1/auth/**"};
    private final String[] RESOURCES = {"/login", "/css/**", "/js/**"};
    private final String[] AUTHENTICATED = {"/pages/**"};

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable())
            .authorizeHttpRequests(httpreq ->
                    httpreq.requestMatchers(WHITELIST).permitAll()
                            .requestMatchers(RESOURCES).permitAll()
                            .requestMatchers(AUTHENTICATED).authenticated()
                            .anyRequest().authenticated()
            )
                .sessionManagement(sess -> sess
                        .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED))

                .formLogin(form -> form
                        .loginPage("/login")
                        .defaultSuccessUrl("/pages/home", true)
                        .permitAll()
                )
                .logout(logout -> logout
                        .logoutSuccessUrl("/login?logout")
                        .permitAll()
                )
            .authenticationProvider(authenticationProvider)
            .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
        ;
        return http.build();
    }
}

package com.seidelsoft.ERPBackend.system.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthFilter;
    private final PasswordEncoder passwordEncoder;

    private final String[] WHITELIST = {"/swagger-ui/**", "/v3/api-docs/**", "/api-docs", "/api/v1/auth/**", "/actuator/prometheus"};
    private final String[] RESOURCES = {"/auth/login", "/css/**", "/js/**"};
    private final String[] AUTHENTICATED = {"/pages/**"};

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, AuthenticationProvider authenticationProvider) throws Exception {
        http
            .csrf(AbstractHttpConfigurer::disable)
            .authorizeHttpRequests(httpreq ->
                    httpreq.requestMatchers(WHITELIST).permitAll()
                            .requestMatchers(RESOURCES).permitAll()
                            .requestMatchers(AUTHENTICATED).authenticated()
                            .anyRequest().authenticated()
            )
                .sessionManagement(sess -> sess
                        .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED))

                .formLogin(form -> form
                        .loginPage("/auth/login")
                        .loginProcessingUrl("/login")
                        .defaultSuccessUrl("/pages/home", true)
                        .permitAll()
                )
                .logout(logout -> logout
                        .logoutSuccessUrl("/auth/login?logout")
                        .permitAll()
                )
            .authenticationProvider(authenticationProvider)
            .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
        ;
        return http.build();
    }

    @Bean
    public AuthenticationProvider authenticationProvider(UserDetailsService userDetailsService,
                                                         PasswordEncoder passwordEncoder) {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder);
        return authProvider;
    }


    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

}

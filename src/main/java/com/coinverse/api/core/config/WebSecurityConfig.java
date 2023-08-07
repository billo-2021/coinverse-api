package com.coinverse.api.core.config;

import com.coinverse.api.common.models.RoleEnum;
import com.coinverse.api.core.security.config.ApiAccessDeniedHandler;
import com.coinverse.api.core.security.config.ApiAuthenticationEntryPoint;
import com.coinverse.api.core.security.config.AuthenticationEntryPointErrorProvider;
import com.coinverse.api.core.security.config.JwtAuthenticationFilter;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Collections;
import java.util.List;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurityConfig {
    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final AuthenticationProvider authenticationProvider;
    private final AuthenticationEntryPointErrorProvider authenticationEntryPointErrorProvider;
    private final ApiAccessDeniedHandler apiAccessDeniedHandler;

    @Bean
    ApiAuthenticationEntryPoint apiAuthenticationEntryPoint() {
        return new ApiAuthenticationEntryPoint(authenticationEntryPointErrorProvider);
    }
    
    @Bean
    public SecurityFilterChain securityFilterChain(final HttpSecurity http) throws Exception {
        http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
        http.csrf(AbstractHttpConfigurer::disable);
        // http.cors(cors -> cors.configurationSource(corsConfigurationSource()));
        http.cors(cors -> cors.configurationSource(req -> {
           CorsConfiguration configuration = new CorsConfiguration();
           configuration.setAllowedOrigins(List.of("*"));
           configuration.setAllowedMethods(List.of("*"));
           configuration.setAllowedHeaders(List.of("*"));

//            UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//            source.registerCorsConfiguration("/**", configuration);
//            return source;
           return configuration;
        }));

        http.sessionManagement(sessionManagementConfigurer ->
                sessionManagementConfigurer.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        );
        
        http.anonymous(AbstractHttpConfigurer::disable);
        http.formLogin(AbstractHttpConfigurer::disable);
        http.authenticationProvider(authenticationProvider);
        
        http.exceptionHandling(exConfig -> 
                exConfig.accessDeniedHandler(apiAccessDeniedHandler)
                        .authenticationEntryPoint(apiAuthenticationEntryPoint()
                        )
        );
        configureRouting(http);
        
        return http.build();
    }
    
    private void configureRouting(@NotNull final HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(registry -> registry
                .requestMatchers("/api/v1/authentication/**")
                .permitAll()
                .requestMatchers("/api/v1/profile/**").authenticated()
                .requestMatchers("/api/v1/administration/**").hasAuthority(RoleEnum.ADMIN.getAuthority())
                .requestMatchers("/api/v1/balances/**").authenticated()
                .requestMatchers("/api/v1/transfers/**").authenticated()
                .requestMatchers("/api/v1/trades/**").authenticated()
                .requestMatchers("/**").permitAll()
        );
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(List.of("*"));
        configuration.setAllowedMethods(List.of("GET", "POST", "DELETE", "PUT", "PATCH"));
        configuration.setAllowedHeaders(Collections.singletonList("*"));
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}

package com.example.sistemadereclutamiento.security.Config;


import com.example.sistemadereclutamiento.security.filter.JwtAuthenticationFilter;
import com.example.sistemadereclutamiento.security.service.CustomUserDetailsService;
import com.example.sistemadereclutamiento.shared.exeption.JwtAccesDeniedHandler;
import com.example.sistemadereclutamiento.shared.exeption.JwtAuthenticationEntryPoint;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    private final JwtAccesDeniedHandler jwtAccesDeniedHandler;
    private final CustomUserDetailsService UserDetailService;
    private final JwtAuthenticationFilter filter;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http
                .cors(Customizer.withDefaults())
                .exceptionHandling(exception ->
                        exception
                                .authenticationEntryPoint(jwtAuthenticationEntryPoint)
                                .accessDeniedHandler(jwtAccesDeniedHandler)
                )
                .csrf(crsf -> crsf.disable())
                .userDetailsService(UserDetailService)
                .sessionManagement(sessionManagement ->
                        sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(
                                "/api/auth/**",
                                "/swagger-ui/**",
                                "/v3/api-docs/**",
                                "/uploads/**",
                                "/api/empresas**",
                                "/api/ofertas**"
                        ).permitAll()

                        .requestMatchers(HttpMethod.POST,
                                "/api/v1/usuarios/barbero",
                                "/api/v1/usuarios/cliente",
                                "/api/v1/usuarios/admin"
                        ).hasRole("admin")

                        .requestMatchers(HttpMethod.GET,
                                "/api/v1/usuarios/**"
                        ).hasAnyRole("admin")

                        .requestMatchers(HttpMethod.PUT,
                                "/api/v1/usuarios/**"
                        ).hasRole("admin")

                        .requestMatchers(HttpMethod.GET,
                                "/api/v1/servicios/**",
                                "/api/v1/categorias/**",
                                "/api/v1/barberos/**",
                                "/api/v1/productos/**"
                        ).permitAll()
                        .requestMatchers("/api/v1/barbero/citas/**").
                        hasAnyAuthority("ROLE_barbero")


                        .anyRequest().authenticated()

                )
                .addFilterBefore(filter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }
    @Bean
    public PasswordEncoder passwordEncoder() {
        return  new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception{
        return config.getAuthenticationManager();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {

        CorsConfiguration config = new CorsConfiguration();

        config.setAllowedOrigins(List.of("http://localhost:4200"));
        config.setAllowedMethods(List.of("GET", "POST", "PUT","PATCH", "DELETE", "OPTIONS"));

        config.setAllowedHeaders(List.of(
                "Authorization",
                "Content-Type",
                "Accept"
        ));

        config.setExposedHeaders(List.of("Authorization"));
        config.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);

        return source;
    }
}

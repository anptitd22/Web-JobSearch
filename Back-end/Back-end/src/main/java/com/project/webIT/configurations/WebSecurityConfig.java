package com.project.webIT.configurations;

import com.project.webIT.filters.JwtTokenFilter;
import com.project.webIT.models.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.CorsConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.List;

@Configuration
@EnableMethodSecurity
@RequiredArgsConstructor
public class WebSecurityConfig {
    //kiem tra quyen truy cap
    private final JwtTokenFilter jwtTokenFilter;

    @Value("${api.prefix}")
    private String apiPrefix;

    @Bean
    public SecurityFilterChain securityFilterChain (HttpSecurity httpSecurity) throws Exception {
        httpSecurity.cors(new Customizer<CorsConfigurer<HttpSecurity>>() {
            @Override
            public void customize(CorsConfigurer<HttpSecurity> httpSecurityCorsConfigurer) {
                CorsConfiguration configuration = new CorsConfiguration();
                configuration.setAllowedOrigins(List.of("*"));
                configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS"));
                configuration.setAllowedHeaders(Arrays.asList("authorization", "content-type", "x-auth-token"));
                configuration.setExposedHeaders(List.of("x-auth-token"));
                UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
                source.registerCorsConfiguration("/**",configuration);
                httpSecurityCorsConfigurer.configurationSource(source);
            }
        });
        return httpSecurity
                .csrf(AbstractHttpConfigurer::disable)
                .addFilterBefore(jwtTokenFilter, UsernamePasswordAuthenticationFilter.class)
                .authorizeHttpRequests(requests -> {
                    requests
                            .requestMatchers(
                            String.format("%s/users/register", apiPrefix),
                            String.format("%s/users/login", apiPrefix)
                            )
                            .permitAll()

                            .requestMatchers(
                                    HttpMethod.POST,
                                    String.format("%s/users/**", apiPrefix)).hasRole(Role.USER)

                            .requestMatchers(
                                    HttpMethod.GET,
                                    String.format("%s/roles/**",apiPrefix)).permitAll()

                            .requestMatchers(
                                    HttpMethod.GET,
                                    String.format("%s/functions/**",apiPrefix)).permitAll()

                            .requestMatchers(
                                    HttpMethod.POST,
                                    String.format("%s/functions/**",apiPrefix)).hasRole(Role.ADMIN)

                            .requestMatchers(
                                    HttpMethod.PUT,
                                    String.format("%s/functions/**",apiPrefix)).hasRole(Role.ADMIN)

                            .requestMatchers(
                                    HttpMethod.GET,
                                    String.format("%s/functions**",apiPrefix)).permitAll()

                            .requestMatchers(
                                    HttpMethod.DELETE,
                                    String.format("%s/functions/**",apiPrefix)).hasRole(Role.ADMIN)

                            .requestMatchers(
                                    HttpMethod.POST,
                                    String.format("%s/jobs/**",apiPrefix)).permitAll()

                            .requestMatchers(
                                    HttpMethod.POST,
                                    String.format("%s/jobs/uploads/**",apiPrefix)).permitAll()

                            .requestMatchers(
                                    HttpMethod.POST,
                                    String.format("%s/companies/uploads/**",apiPrefix)).permitAll()

                            .requestMatchers(
                                    HttpMethod.GET,
                                    String.format("%s/jobs**",apiPrefix)).permitAll()

                            .requestMatchers(
                                    HttpMethod.PUT,
                                    String.format("%s/jobs/**",apiPrefix)).hasAnyRole(Role.COMPANY, Role.ADMIN)

                            .requestMatchers(
                                    HttpMethod.DELETE,
                                    String.format("%s/jobs/**",apiPrefix)).hasRole(Role.ADMIN)

                            .requestMatchers(
                                    HttpMethod.POST,
                                    String.format("%s/applied/**",apiPrefix)).hasRole(Role.USER)

                            .requestMatchers(
                                    HttpMethod.PUT,
                                    String.format("%s/applied/**",apiPrefix)).hasRole(Role.ADMIN)

                            .requestMatchers(
                                    HttpMethod.GET,
                                    String.format("%s/applied/**",apiPrefix)).permitAll()

                            .requestMatchers(
                                    HttpMethod.DELETE,
                                    String.format("%s/applied/**",apiPrefix)).hasRole(Role.ADMIN)

                            .requestMatchers(
                                    HttpMethod.POST,
                                    String.format("%s/companies/**",apiPrefix)).permitAll()

                            .requestMatchers(
                                    HttpMethod.PUT,
                                    String.format("%s/companies/**",apiPrefix)).permitAll()

                            .requestMatchers(
                                    HttpMethod.GET,
                                    String.format("%s/companies/**",apiPrefix)).permitAll()

                            .requestMatchers(
                                    HttpMethod.DELETE,
                                    String.format("%s/companies/**",apiPrefix)).hasRole(Role.ADMIN)

                            .requestMatchers(
                                    HttpMethod.POST,
                                    String.format("%s/feedback/**",apiPrefix)).hasAnyRole(Role.COMPANY, Role.ADMIN)

                            .requestMatchers(
                                    HttpMethod.GET,
                                    String.format("%s/feedback/**",apiPrefix)).permitAll()

                            .requestMatchers(
                                    HttpMethod.GET,
                                    String.format("%s/jobs/images/**",apiPrefix)).permitAll()

                            .requestMatchers(
                                    HttpMethod.GET,
                                    String.format("%s/jobs/**",apiPrefix)).permitAll()

                            .requestMatchers(
                                    HttpMethod.GET,
                                    String.format("%s/my-career-center/**", apiPrefix)).hasRole(Role.USER)

                            .requestMatchers(
                                    HttpMethod.POST,
                                    String.format("%s/my-career-center/**", apiPrefix)).hasRole(Role.USER)

                            .requestMatchers(
                                    HttpMethod.PUT,
                                    String.format("%s/users/**", apiPrefix)).hasRole(Role.USER)

                            .requestMatchers(
                                    HttpMethod.GET,
                                    String.format("%s/history/**", apiPrefix)).hasRole(Role.USER)

                            .requestMatchers(
                                    HttpMethod.POST,
                                    String.format("%s/history/**", apiPrefix)).hasRole(Role.USER)

                            .requestMatchers(
                                    HttpMethod.GET,
                                    String.format("%s/industries/**", apiPrefix)).permitAll()

                            .requestMatchers(
                                    HttpMethod.GET,
                                    String.format("%s/dashboard/**", apiPrefix)).permitAll()

                            .requestMatchers(
                                    HttpMethod.POST,
                                    String.format("%s/dashboard/**", apiPrefix)).permitAll()
                            .requestMatchers(
                                    HttpMethod.GET,
                                    String.format("%s/questions/**", apiPrefix)).permitAll()
                            .requestMatchers(
                                    HttpMethod.POST,
                                    String.format("%s/questions/**", apiPrefix)).permitAll()

                            .requestMatchers(
                                    HttpMethod.PUT,
                                    String.format("%s/questions/**", apiPrefix)).permitAll()

                            .requestMatchers(
                                    HttpMethod.DELETE,
                                    String.format("%s/questions/**", apiPrefix)).permitAll()

                            .requestMatchers(
                                    HttpMethod.GET,
                                    String.format("%s/locations/**", apiPrefix)).permitAll()

                            .requestMatchers(
                                    HttpMethod.GET,
                                    String.format("%s/cv/**", apiPrefix)).permitAll()

                            .requestMatchers(
                                    HttpMethod.PUT,
                                    String.format("%s/cv/**", apiPrefix)).permitAll()

                            .requestMatchers(
                                    HttpMethod.DELETE,
                                    String.format("%s/cv/**", apiPrefix)).permitAll()

                            .requestMatchers(
                                    HttpMethod.POST,
                                    String.format("%s/payments/**", apiPrefix)).permitAll()
                            .anyRequest().authenticated();
                        }).build();
    }
}

package com.project.webIT.configurations;

import com.project.webIT.components.JwtTokenFilter;
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
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.List;

@Configuration
@EnableMethodSecurity
@RequiredArgsConstructor
public class WebSecurityConfiguration {
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
                configuration.setAllowedHeaders(Arrays.asList(
                        "authorization",
                        "content-type",
                        "x-auth-token",
                        "x-auth-type",
                        "accept-language"
                ));
                configuration.setExposedHeaders(List.of("x-auth-token"));
//                configuration.setAllowCredentials(true);
                UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
                source.registerCorsConfiguration("/**",configuration);
                httpSecurityCorsConfigurer.configurationSource(source);
            }
        });
        return httpSecurity
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(jwtTokenFilter, UsernamePasswordAuthenticationFilter.class)
                .authorizeHttpRequests(requests -> {
                    requests
                            //PermitAll
                            .requestMatchers(
                                    HttpMethod.GET,
                                    String.format("%s/users/register", apiPrefix),
                                    String.format("%s/users/login", apiPrefix),
                                    String.format("%s/users/auth/**", apiPrefix),
                                    String.format("%s/companies/login", apiPrefix),

                                    String.format("%s/users/**", apiPrefix), // GET
                                    String.format("%s/roles/**", apiPrefix), // GET
                                    String.format("%s/functions/**", apiPrefix), // GET
                                    String.format("%s/functions**", apiPrefix), // GET

                                    String.format("%s/jobs/**", apiPrefix), // GET
                                    String.format("%s/jobs/images/**", apiPrefix),

                                    String.format("%s/applied/**", apiPrefix), // GET
                                    String.format("%s/feedback/**", apiPrefix), // GET
                                    String.format("%s/industries/**", apiPrefix), // GET

                                    String.format("%s/companies/**", apiPrefix), // POST/PUT/GET

                                    String.format("%s/my-companies/**", apiPrefix), // POST/PUT/GET

                                    String.format("%s/dashboard/**", apiPrefix), //GET/POST

                                    String.format("%s/questions/**", apiPrefix), // GET/POST/PUT/DELETE

                                    String.format("%s/locations/**", apiPrefix), // GET

                                    String.format("%s/cv/**", apiPrefix) // GET/PUT/DELETE
                            ).permitAll()

                            .requestMatchers(
                                    HttpMethod.POST,
                                    String.format("%s/jobs/uploads/**", apiPrefix), // POST

                                    String.format("%s/companies/uploads/**", apiPrefix), // POST

                                    String.format("%s/companies/**", apiPrefix), // POST/PUT/GET

                                    String.format("%s/dashboard/**", apiPrefix), // GET/POST

                                    String.format("%s/questions/**", apiPrefix), // GET/POST/PUT/DELETE

                                    String.format("%s/my-companies/**", apiPrefix), // POST/PUT/GET

                                    String.format("%s/cv/**", apiPrefix), // GET/PUT/DELETE

                                    String.format("%s/payments/**", apiPrefix), // POST

                                    String.format("%s/mail/**", apiPrefix) // POST
                            ).permitAll()

                            .requestMatchers(
                                    HttpMethod.PUT,
                                    String.format("%s/companies/**", apiPrefix), // POST/PUT/GET

                                    String.format("%s/questions/**", apiPrefix), // GET/POST/PUT/DELETE

                                    String.format("%s/my-companies/**", apiPrefix), // POST/PUT/GET

                                    String.format("%s/cv/**", apiPrefix) // GET/PUT/DELETE
                            ).permitAll()

                            .requestMatchers(
                                    HttpMethod.DELETE,
                                    String.format("%s/questions/**", apiPrefix), // GET/POST/PUT/DELETE

                                    String.format("%s/cv/**", apiPrefix) // GET/PUT/DELETE
                            ).permitAll()

                            //USER
                            .requestMatchers(HttpMethod.POST, String.format("%s/users/**", apiPrefix)).hasRole(Role.USER)
                            .requestMatchers(HttpMethod.PUT, String.format("%s/users/**", apiPrefix)).hasRole(Role.USER)

                            .requestMatchers(HttpMethod.GET, String.format("%s/my-career-center/**", apiPrefix)).hasRole(Role.USER)
                            .requestMatchers(HttpMethod.POST, String.format("%s/my-career-center/**", apiPrefix)).hasRole(Role.USER)

                            .requestMatchers(HttpMethod.GET, String.format("%s/history/**", apiPrefix)).hasRole(Role.USER)
                            .requestMatchers(HttpMethod.POST, String.format("%s/history/**", apiPrefix)).hasRole(Role.USER)

                            .requestMatchers(HttpMethod.POST, String.format("%s/applied/**", apiPrefix)).hasRole(Role.USER)

                            //COMPANY
                            .requestMatchers(HttpMethod.PUT, String.format("%s/jobs/**", apiPrefix)).hasAnyRole(Role.COMPANY, Role.ADMIN)
                            .requestMatchers(HttpMethod.POST, String.format("%s/feedback/**", apiPrefix)).hasAnyRole(Role.COMPANY, Role.ADMIN)

                            //ADMIN
                            .requestMatchers(HttpMethod.PUT, String.format("%s/functions/**", apiPrefix)).hasRole(Role.ADMIN)
                            .requestMatchers(HttpMethod.DELETE, String.format("%s/functions/**", apiPrefix)).hasRole(Role.ADMIN)

                            .requestMatchers(HttpMethod.DELETE, String.format("%s/jobs/**", apiPrefix)).hasRole(Role.ADMIN)

                            .requestMatchers(HttpMethod.PUT, String.format("%s/applied/**", apiPrefix)).hasRole(Role.ADMIN)
                            .requestMatchers(HttpMethod.DELETE, String.format("%s/applied/**", apiPrefix)).hasRole(Role.ADMIN)

                            .requestMatchers(HttpMethod.DELETE, String.format("%s/companies/**", apiPrefix)).hasRole(Role.ADMIN);
                }).build();
    }
    @Bean
    public DefaultOAuth2UserService oAuth2UserService() {
        return new DefaultOAuth2UserService();
    }
}

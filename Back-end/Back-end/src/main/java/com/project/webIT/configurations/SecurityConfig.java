package com.project.webIT.configurations;

import com.project.webIT.provider.AdminAuthenticationProvider;
import com.project.webIT.provider.CompanyAuthenticationProvider;
import com.project.webIT.provider.UserAuthenticationProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;

import java.util.Arrays;
import java.util.List;

@Configuration
@EnableMethodSecurity(prePostEnabled = true)
@RequiredArgsConstructor
public class SecurityConfig {

    private final UserAuthenticationProvider userAuthProvider;
    private final CompanyAuthenticationProvider companyAuthProvider;
    private final AdminAuthenticationProvider adminAuthenticationProvider;

    //tai khoan
    @Bean
    public AuthenticationManager authenticationManager() {
        List<AuthenticationProvider> providers = Arrays.asList(
                userAuthProvider,
                companyAuthProvider,
                adminAuthenticationProvider
        );
        return new ProviderManager(providers);
    }
}

package com.project.webIT.configurations;

import com.project.webIT.components.CompanyAuthenticationProvider;
import com.project.webIT.components.UserAuthenticationProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;

import java.util.Arrays;
import java.util.List;

@Configuration  //tu dong khoi tao
@RequiredArgsConstructor
public class SecurityConfiguration {

    private final UserAuthenticationProvider userAuthProvider;
    private final CompanyAuthenticationProvider companyAuthProvider;

    //tai khoan
    @Bean
    public AuthenticationManager authenticationManager() {
        List<AuthenticationProvider> providers = Arrays.asList(
                userAuthProvider,
                companyAuthProvider
        );
        return new ProviderManager(providers);
    }
}

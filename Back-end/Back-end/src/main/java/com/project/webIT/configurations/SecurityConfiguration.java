package com.project.webIT.configurations;

import com.project.webIT.components.CompanyAuthenticationProvider;
import com.project.webIT.components.UserAuthenticationProvider;
import com.project.webIT.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Arrays;
import java.util.List;

@Configuration  //tu dong khoi tao
@RequiredArgsConstructor
public class SecurityConfig {

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

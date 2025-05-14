package com.project.webIT.provider;

import com.project.webIT.services.CompanyDetailServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component("companyAuthenticationProvider")
@RequiredArgsConstructor
public class CompanyAuthenticationProvider implements AuthenticationProvider {

    private final CompanyDetailServiceImpl companyDetailsService;
    private final PasswordEncoder passwordEncoder;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String account = authentication.getName();
        String password = authentication.getCredentials().toString();

        UserDetails company = companyDetailsService.loadUserByUsername(account);
        if (!company.isEnabled()) {
            throw new BadCredentialsException("Company account is deactivated");
        }

        if (!passwordEncoder.matches(password, company.getPassword())) {
            throw new BadCredentialsException("Invalid company credentials");
        }

        return new UsernamePasswordAuthenticationToken(company, null, company.getAuthorities());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
    }
}


package com.project.webIT.provider;

import com.project.webIT.services.AdminDetailServiceImpl;
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

@Component("adminAuthenticationProvider")
@RequiredArgsConstructor
public class AdminAuthenticationProvider implements AuthenticationProvider {

    private final AdminDetailServiceImpl adminDetailService;
    private final PasswordEncoder passwordEncoder;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String account = authentication.getName();
        String password = authentication.getCredentials().toString();

        UserDetails admin = adminDetailService.loadUserByUsername(account);
        if (!passwordEncoder.matches(password, admin.getPassword())) {
            throw new BadCredentialsException("Invalid admin credentials");
        }

        return new UsernamePasswordAuthenticationToken(admin, null, admin.getAuthorities());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
    }
}

package com.project.webIT.services;

import com.project.webIT.repositories.CompanyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service("companyDetailsService")
@RequiredArgsConstructor
public class CompanyDetailServiceImpl implements UserDetailsService {

    private final CompanyRepository companyRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return companyRepository.findByAccount(username)
                .orElseThrow(() -> new UsernameNotFoundException("Company not found"));
    }
}

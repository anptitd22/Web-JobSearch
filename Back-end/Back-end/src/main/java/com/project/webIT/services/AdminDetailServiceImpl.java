package com.project.webIT.services;

import com.project.webIT.repositories.AdminRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service("adminDetailsService")
@RequiredArgsConstructor
public class AdminDetailServiceImpl implements UserDetailsService {

    private final AdminRepository adminRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return adminRepository.findByAccount(username)
                .orElseThrow(() -> new UsernameNotFoundException("admin not found"));
    }
}

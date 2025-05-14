package com.project.webIT.services;

import com.project.webIT.dtos.request.AdminDTO;
import com.project.webIT.exceptions.InvalidParamException;
import com.project.webIT.helper.JwtTokenHelper;
import com.project.webIT.models.Admin;
import com.project.webIT.repositories.AdminRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AdminServiceImpl {

    private final AdminRepository adminRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenHelper jwtTokenHelper;

    public String loginAdmin(AdminDTO adminDTO) throws Exception{
        if (adminDTO.getRoleId() != 3){
            throw new InvalidParamException("Can not login account admin");
        }

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        adminDTO.getAccount(),
                        adminDTO.getPassword()
                )
        );

        Admin admin = (Admin) authentication.getPrincipal();

        return jwtTokenHelper.generateTokenFromAdmin(admin);
    }
}

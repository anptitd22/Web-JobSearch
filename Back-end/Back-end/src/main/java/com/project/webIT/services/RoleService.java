package com.project.webIT.services;

import com.project.webIT.models.Role;
import com.project.webIT.repositories.RoleRepository;
import com.project.webIT.services.IService.IRoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RoleService implements IRoleService {
    private final RoleRepository roleRepository;

    @Override
    public List<Role> getAllRoles() {
        return roleRepository.findAll();
    }
}

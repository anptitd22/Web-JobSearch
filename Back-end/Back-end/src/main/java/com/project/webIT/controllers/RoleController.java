package com.project.webIT.controllers;

import com.project.webIT.models.Role;
import com.project.webIT.dtos.response.ObjectResponse;
import com.project.webIT.services.RoleServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("${api.prefix}/roles")
public class RoleController {
    private final RoleServiceImpl roleServiceImpl;

    @GetMapping("")
    public ResponseEntity<ObjectResponse<List<Role>>> getAllRoles() {
        List<Role> roles = roleServiceImpl.getAllRoles();

        return ResponseEntity.ok(
                ObjectResponse.<List<Role>>builder()
                        .status(HttpStatus.OK)
                        .message("Successfully retrieved list of roles")
                        .data(roles)
                        .build()
        );
    }
}

package com.project.webIT.controllers;

import com.project.webIT.dtos.request.AdminDTO;
import com.project.webIT.dtos.response.ObjectResponse;
import com.project.webIT.helper.ValidationHelper;
import com.project.webIT.services.AdminServiceImpl;
import com.project.webIT.services.IService.AdminService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("${api.prefix}/admin")
@RequiredArgsConstructor
@Slf4j
public class AdminController {

    private final AdminServiceImpl adminService;

    @PostMapping("login")
    public ResponseEntity<ObjectResponse<?>> loginAccountAdmin (
            @Valid @RequestBody AdminDTO adminDTO,
            BindingResult result
    ) throws Exception {
        if (result.hasErrors()) {
            return ResponseEntity.badRequest().body(
                    ObjectResponse.<Void>builder()
                            .status(HttpStatus.BAD_REQUEST)
                            .message(ValidationHelper.extractDetailedErrorMessages(result))
                            .build()
            );
        }
        return ResponseEntity.ok().body(
                ObjectResponse.<String>builder()
                        .status(HttpStatus.OK)
                        .message("login successfully")
                        .data(adminService.loginAdmin(adminDTO))
                        .build()
        );
    }
}

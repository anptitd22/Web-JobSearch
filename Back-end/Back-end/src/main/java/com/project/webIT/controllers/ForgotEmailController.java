package com.project.webIT.controllers;

import com.project.webIT.dtos.request.ResetPasswordDTO;
import com.project.webIT.dtos.response.ObjectResponse;
import com.project.webIT.services.EmailServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("${api.prefix}/mail")
public class ForgotEmailController {

    private final EmailServiceImpl emailService;

    @PostMapping("auth/forgot-password")
    public ResponseEntity<ObjectResponse<Void>> forgotPassword(
            @RequestBody Map<String, String> body
    ) throws Exception {
        String email = body.get("email");
        String resetUrl = emailService.getResetUrl(email);
        emailService.sendResetPasswordEmail(email, resetUrl);
        return ResponseEntity.ok(
                ObjectResponse.<Void>builder()
                        .status(HttpStatus.OK)
                        .message("Password reset email sent successfully")
                        .data(null)
                        .build()
        );
    }

    @PostMapping("auth/reset-password")
    public ResponseEntity<ObjectResponse<Void>> resetPassword(
            @RequestBody ResetPasswordDTO resetPasswordDTO
    ) throws Exception {
        emailService.backPassword(resetPasswordDTO);
        return ResponseEntity.ok(
                ObjectResponse.<Void>builder()
                        .status(HttpStatus.OK)
                        .message("Password has been changed successfully")
                        .data(null)
                        .build()
        );
    }
}

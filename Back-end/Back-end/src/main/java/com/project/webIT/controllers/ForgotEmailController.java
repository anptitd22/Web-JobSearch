package com.project.webIT.controllers;

import com.project.webIT.dtos.users.ResetPasswordDTO;
import com.project.webIT.exceptions.DataNotFoundException;
import com.project.webIT.exceptions.InvalidParamException;
import com.project.webIT.models.PasswordResetToken;
import com.project.webIT.models.User;
import com.project.webIT.repositories.PasswordResetTokenRepository;
import com.project.webIT.repositories.UserRepository;
import com.project.webIT.services.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("${api.prefix}/mail")
public class ForgotEmailController {

    private final EmailService emailService;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final PasswordResetTokenRepository passwordResetTokenRepository;

    @PostMapping("auth/forgot-password")
    public ResponseEntity<?> forgotPassword(@RequestBody Map<String, String> body) {
        try{
            String email = body.get("email");
            Optional<User> user = userRepository.findByEmail(email);

            if (user.isEmpty()) {
                return ResponseEntity.badRequest().body("Email không tồn tại");
            }
            if (!user.get().isActive()){
                return ResponseEntity.badRequest().body("account is locked");
            }
            if (!user.get().getGoogleAccountId().isEmpty() || !user.get().getFacebookAccountId().isEmpty()){
                return ResponseEntity.badRequest().body("account is google or facebook");
            }
            Optional<PasswordResetToken> token = passwordResetTokenRepository.findByUserId(user.get().getId());
            String resetUrl = "http://localhost:4200/reset-password?token=";

            if(token.isEmpty()) {
                String newToken = UUID.randomUUID().toString();
                PasswordResetToken prt = new PasswordResetToken(newToken, user.get(), LocalDateTime.now().plusMinutes(30));
                passwordResetTokenRepository.save(prt);
                resetUrl +=  newToken;
            }else{
                PasswordResetToken prt = token.get();
                if (!prt.isActive() || prt.getExpiryDate().isBefore(LocalDateTime.now())) {
                    prt.setToken(UUID.randomUUID().toString());
                    prt.setActive(true);
                    prt.setExpiryDate(LocalDateTime.now().plusMinutes(30));
                    passwordResetTokenRepository.save(prt);
                }
                resetUrl += prt.getToken();
            }
            emailService.sendResetPasswordEmail(email, resetUrl);

            return ResponseEntity.ok(Map.of("message", "Mật khẩu đã được cập nhật thành công"));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @PostMapping("auth/reset-password")
    public ResponseEntity<?> resetPassword(
            @RequestBody ResetPasswordDTO resetPasswordDTO)
    {
        try{
            if(resetPasswordDTO.getRetypePassword().isEmpty()||resetPasswordDTO.getPassword().isEmpty()){
                throw new DataNotFoundException("password cannot blank");
            }
            PasswordResetToken resetToken = passwordResetTokenRepository.findByTokenAndIsActiveTrue(resetPasswordDTO.getToken());
            if (resetToken == null || resetToken.getExpiryDate().isBefore(LocalDateTime.now())) {
                assert resetToken != null;
                if(resetToken.getExpiryDate().isBefore(LocalDateTime.now())){
                    resetToken.setActive(false);
                }
                return ResponseEntity.badRequest().body(Map.of("message","Token không hợp lệ hoặc đã hết hạn"));
            }

            User user = resetToken.getUser();
            if(resetPasswordDTO.getPassword().equals(resetPasswordDTO.getRetypePassword())){
                user.setPassword(passwordEncoder.encode(resetPasswordDTO.getPassword())); // đổi mật khẩu
            }else{
                throw new InvalidParamException("Password and retype not same");
            }
            userRepository.save(user);

            resetToken.setActive(false);
            passwordResetTokenRepository.save(resetToken);

            return ResponseEntity.ok(Map.of("message", "Đổi mật khẩu thành công"));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error",e.getMessage()));
        }
    }
}

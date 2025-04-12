package com.project.webIT.services;

import com.project.webIT.dtos.request.ResetPasswordDTO;
import com.project.webIT.exceptions.DataNotFoundException;
import com.project.webIT.exceptions.InvalidParamException;
import com.project.webIT.models.PasswordResetToken;
import com.project.webIT.models.User;
import com.project.webIT.repositories.PasswordResetTokenRepository;
import com.project.webIT.repositories.UserRepository;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class EmailServiceImpl {

    private final JavaMailSender mailSender;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final PasswordResetTokenRepository passwordResetTokenRepository;

    @Value("${spring.mail.username}")
    private String senderEmail;

    public String getResetUrl(String email) throws Exception{
        Optional<User> user = userRepository.findByEmail(email);

        if (user.isEmpty()) {
            throw new DataNotFoundException("user not found");
        }
        if (!user.get().isActive()){
            throw new DataNotFoundException("user is locker");
        }
        if (!user.get().getGoogleAccountId().isEmpty() || !user.get().getFacebookAccountId().isEmpty()){
            throw new InvalidParamException("account facebook or google");
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
        return resetUrl;
    }

    public void sendResetPasswordEmail(String toEmail, String resetLink) throws Exception {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

        // Cài đặt các thông tin cơ bản của email
        helper.setTo(toEmail); // Người nhận
        helper.setSubject("Đặt lại mật khẩu của bạn"); // Tiêu đề
        helper.setFrom(senderEmail); // Người gửi

        // Tạo nội dung HTML cho email
        String htmlContent = "<p>Xin chào,</p>" +
                "<p>Bạn đã yêu cầu đặt lại mật khẩu.</p>" +
                "<p>Click vào link dưới đây để đổi mật khẩu:</p>" +
                "<a href=\"" + resetLink + "\">Đặt lại mật khẩu</a>" +
                "<p>Link sẽ hết hạn sau 1 giờ.</p>" +
                "<br><p>Trân trọng,<br>Hệ thống của bạn</p>";

        helper.setText(htmlContent, true); // true để gửi email dưới dạng HTML

        // Gửi email
        mailSender.send(message);
    }

    public void backPassword(ResetPasswordDTO resetPasswordDTO) throws Exception {
        if(resetPasswordDTO.getRetypePassword().isEmpty()||resetPasswordDTO.getPassword().isEmpty()){
            throw new DataNotFoundException("password cannot blank");
        }
        PasswordResetToken resetToken = passwordResetTokenRepository.findByTokenAndIsActiveTrue(resetPasswordDTO.getToken());
        if (resetToken == null || resetToken.getExpiryDate().isBefore(LocalDateTime.now())) {
            assert resetToken != null;
            if(resetToken.getExpiryDate().isBefore(LocalDateTime.now())){
                resetToken.setActive(false);
            }
            throw new DataNotFoundException("Token không hợp lệ hoặc đã hết hạn");
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
    }
}

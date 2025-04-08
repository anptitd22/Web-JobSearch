package com.project.webIT.services;

import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender mailSender;
    @Value("${spring.mail.username}")
    private String senderEmail;

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
}

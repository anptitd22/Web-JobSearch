package com.project.webIT.models;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Table (name = "password_reset_token")
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PasswordResetToken {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String token;

    @Column(name= "expiry_date")
    private LocalDateTime expiryDate;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user; // Liên kết tới User cần reset

    @Column(name = "is_active")
    private boolean isActive = true;

    public PasswordResetToken(String token, User user, LocalDateTime expiryDate) {
        this.token = token;
        this.user = user;
        this.expiryDate = expiryDate;
    }

}

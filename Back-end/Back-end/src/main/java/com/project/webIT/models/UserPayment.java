package com.project.webIT.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "user_payments")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserPayment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name="user_id")
    @JsonBackReference
    private User user;

    @Column(name="order_id")
    private String orderId;

    @Column(name="payer_email")
    private String payerEmail;

    @Column(name="amount")
    private Double amount;

    @Column(name="currency")
    private String currency;

    @Column(name="status")
    private String status;

    @Column(name="created_at")
    private LocalDateTime createdAt;
}

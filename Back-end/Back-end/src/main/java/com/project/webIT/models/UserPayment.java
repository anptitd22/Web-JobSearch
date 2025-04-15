package com.project.webIT.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Map;

@Entity
@Table(name = "user_payments")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserPayment extends BaseEntity{
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

    public static final Map<String, String> STATUS_MAP = Map.of(
            "COMPLETED", "Thành công",
            "APPROVED", "Đã duyệt",
            "FAILED", "Thất bại",
            "CANCELED", "Đã hủy"
    );
}

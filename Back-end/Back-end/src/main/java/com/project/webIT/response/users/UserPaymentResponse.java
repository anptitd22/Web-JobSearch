package com.project.webIT.response.users;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.project.webIT.models.User;
import com.project.webIT.models.UserPayment;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.*;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class UserPaymentResponse {

    @JsonProperty("order_id")
    private String orderId;

    @JsonProperty("payer_email")
    private String payerEmail;

    @JsonProperty("amount")
    private Double amount;

    @JsonProperty("currency")
    private String currency;

    private String status;

    @JsonProperty("created_at")
    private LocalDateTime createdAt;

    public static UserPaymentResponse fromUserPayment(UserPayment userPayment){
        return UserPaymentResponse.builder()
                .orderId(userPayment.getOrderId())
                .amount(userPayment.getAmount())
                .payerEmail(userPayment.getPayerEmail())
                .currency(userPayment.getCurrency())
                .status(userPayment.getStatus())
                .createdAt(userPayment.getCreatedAt())
                .build();
    }
}

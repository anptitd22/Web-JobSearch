package com.project.webIT.dtos.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Setter
@Getter
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserPaymentDTO {
    @JsonProperty("order_id")
    private String orderId;

    @JsonProperty("user_id")
    private Long userId;

    @JsonProperty("payer_email")
    private String payerEmail;

    @JsonProperty("amount")
    private Double amount;

    @JsonProperty("currency")
    private String currency;

    @JsonProperty("status")
    private String status;
}

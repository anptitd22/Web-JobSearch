package com.project.webIT.dtos.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Setter
@Getter
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AdminDTO {

    @JsonProperty("role_id")
    @NotNull(message = "role is required")
    private Long roleId;

    @JsonProperty("account")
    @NotBlank(message = "account is required")
    private String account;

    @JsonProperty("password")
    @NotBlank(message = "password is required")
    private String password;
}

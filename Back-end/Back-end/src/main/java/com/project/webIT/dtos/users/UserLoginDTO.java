package com.project.webIT.dtos.users;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Setter
@Getter
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserLoginDTO {
    @JsonProperty("email")
    @NotBlank(message = "email is required")
    private String email;

//    @NotBlank(message = "Password can not be blank")
    private String password;

    @JsonProperty("role_id")
    @NotNull(message = "Role is required")
    private Long roleId;

    @JsonProperty("full_name")
    private String fullName;

    @JsonProperty("phone_number")
    private String phoneNumber;

    @JsonProperty("avatar")
    private String avatar;

    @JsonProperty("google_account_id")
    private String googleAccountId;

    @JsonProperty("facebook_account_id")
    private String facebookAccountId;
}

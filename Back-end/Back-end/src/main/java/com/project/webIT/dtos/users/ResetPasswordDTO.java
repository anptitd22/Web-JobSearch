package com.project.webIT.dtos.users;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ResetPasswordDTO {
    @JsonProperty("token")
    private String token;

    @JsonProperty("retype_password")
    private String retypePassword;

    @JsonProperty("password")
    private String password;
}

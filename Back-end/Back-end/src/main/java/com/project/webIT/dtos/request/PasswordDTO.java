package com.project.webIT.dtos.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PasswordDTO {
    @JsonProperty("current_password")
    private String currentPassword;

    private String password;

    @JsonProperty("retype_password")
    private String retypePassword;
}

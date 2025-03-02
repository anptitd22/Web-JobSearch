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
public class UserLoginDTO {
    @JsonProperty("email")
    @NotBlank(message = "email is required")
    private String email;

    @NotBlank(message = "Password can not be blank")
    private String password;

    @JsonProperty("role_id")
    @NotNull(message = "Role ?")
    private Long roleId;
}

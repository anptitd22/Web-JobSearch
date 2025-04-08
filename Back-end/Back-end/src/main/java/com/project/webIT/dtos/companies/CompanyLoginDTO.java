package com.project.webIT.dtos.companies;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class CompanyLoginDTO {

    @JsonProperty("account")
    @NotBlank(message = "Tài khoản không thể rỗng")
    @Size(min = 5, max = 255, message = "Tài khoản dài từ 5 đến 255 kí tự")
    private String account;

    @JsonProperty("password")
    @NotBlank(message = "Mật khẩu không thể rỗng")
    @Size(min = 6, max = 255, message = "Tài khoản dài từ 6 đến 255 kí tự")
    private String password;

    @JsonProperty("role_id")
    private Long roleID;
}

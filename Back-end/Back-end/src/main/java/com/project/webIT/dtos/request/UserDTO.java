package com.project.webIT.dtos.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.Date;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDTO {
    @JsonProperty("avatar")
    private String avatar;

    @JsonProperty("public_id_images")
    private String publicIdImages;

    @JsonProperty("full_name")
    private String fullName;

    @JsonProperty("first_name")
    @NotBlank(message = "first name is required")
    private String firstName;

    @JsonProperty("last_name")
    @NotBlank(message = "last name is required")
    private String lastName;

    @JsonProperty("phone_number")
    @NotBlank(message = "Phone number is required")
    private String phoneNumber;

    @NotBlank(message = "Email is required")
    private String email;

    private String address;

    @NotBlank(message = "Password can not be blank")
    private String password;

    @JsonProperty("retype_password")
    private String retypePassword;

    @JsonProperty("date_of_birth")
    @NotNull(message = "Date of birth can be blank")
    private Date dateOfBirth;

    @JsonProperty("facebook_account_id")
    private String facebookAccountId;

    @JsonProperty("google_account_id")
    private String googleAccountId;

    @JsonProperty("role_id")
//    @NotNull(message = "Role Id is required")
    private Long roleID;
}

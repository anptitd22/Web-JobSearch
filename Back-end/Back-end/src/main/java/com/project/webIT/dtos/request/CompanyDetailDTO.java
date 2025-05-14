package com.project.webIT.dtos.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CompanyDetailDTO {
    @NotBlank(message = "Name is required")
    private String name;

    @NotBlank(message = "location is required")
    private String location;

    @NotBlank(message = "nation is required")
    private String nation;

    @JsonProperty("phone_number")
    @NotBlank(message = "Phone number is required")
    private String phoneNumber;

    @NotBlank(message = "email is required")
    @Email(message = "invalid email")
    private String email;

    private String description;

    private String logo;

    private String contact;

    private Long size;
}

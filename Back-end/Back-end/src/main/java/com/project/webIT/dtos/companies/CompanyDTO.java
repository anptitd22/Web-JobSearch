package com.project.webIT.dtos.companies;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CompanyDTO {

    @NotBlank(message = "Name is not null")
    private String name;

    private String location;

    private String nation;

    @JsonProperty("phone_number")
    @NotBlank(message = "Phone number is require")

    private String phoneNumber;

    private String email;

    private String description;

    private String thumbnail;

    private String contact;

    private Long size;
}

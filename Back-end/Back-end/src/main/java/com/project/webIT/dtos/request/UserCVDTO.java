package com.project.webIT.dtos.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserCVDTO {

    @Size(min = 5, max = 500, message = "Image's name")
    @JsonProperty("cv_url")
    private String cvUrl;

    @Size(max = 200)
    @JsonProperty("cv_name")
    private String name;

    @JsonProperty("public_id_cv")
    private String publicIdCV;
}

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
public class AppliedJobCVDTO {
    @JsonProperty("applied_job_id")
    @NotNull(message = "applied job id is required")
    private Long appliedJobId;

    @JsonProperty("cv_name")
    private String name;

    @JsonProperty("cv_url")
    private String cvUrl;

    @JsonProperty("public_id_cv")
    private String publicIdCV;
}

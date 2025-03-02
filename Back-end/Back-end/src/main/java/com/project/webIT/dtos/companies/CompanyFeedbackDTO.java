package com.project.webIT.dtos.companies;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CompanyFeedbackDTO {

    @JsonProperty("company_id")
    private Long companyId;

    @JsonProperty("applied_job_id")
    private Long appliedJobId;

    @NotBlank(message = "Status not Blank, Pass or Fail ?")
    private String status;

    private String notification;
}

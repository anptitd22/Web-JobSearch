package com.project.webIT.dtos.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CompanyFeedbackRefuseDTO {

    @JsonProperty("company_id")
    private Long companyId;

    @JsonProperty("applied_job_id")
    private Long appliedJobId;

    @JsonProperty("full_name")
    private String fullName;

    @JsonProperty("job_title")
    private String jobTitle;

    @JsonProperty("reason")
    private String reason;

    @JsonProperty("note")
    private String note;

    @JsonProperty("contact")
    private String contact;

    @JsonProperty("email")
    private String email;
}

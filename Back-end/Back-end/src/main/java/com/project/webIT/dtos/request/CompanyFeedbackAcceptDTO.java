package com.project.webIT.dtos.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.project.webIT.models.AppliedJob;
import com.project.webIT.models.Company;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.time.LocalDateTime;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CompanyFeedbackAcceptDTO {

    @JsonProperty("company_id")
    private Long companyId;

    @JsonProperty("applied_job_id")
    private Long AppliedJobId;

    @JsonProperty("full_name")
    private String fullName;

    @JsonProperty("job_title")
    private String jobTitle;

    @JsonProperty("interview_time")
    private LocalDateTime interviewTime;

    @JsonProperty("address")
    private String address;

    @JsonProperty("contact")
    private String contact;

    @JsonProperty("email")
    private String email;

    @JsonProperty("note")
    private String note;
}

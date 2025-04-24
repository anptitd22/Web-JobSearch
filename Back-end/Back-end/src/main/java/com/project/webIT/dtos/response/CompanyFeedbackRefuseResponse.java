package com.project.webIT.dtos.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.project.webIT.models.CompanyFeedbackAccept;
import com.project.webIT.models.CompanyFeedbackRefuse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class CompanyFeedbackRefuseResponse {
    @JsonProperty("full_name")
    private String fullName;

    @JsonProperty("job_title")
    private String jobTitle;

    @JsonProperty("reason")
    private String reason;

    @JsonProperty("contact")
    private String contact;

    @JsonProperty("email")
    private String email;

    @JsonProperty("note")
    private String note;

    //job
    @JsonProperty("job_name")
    String jobName;

    @JsonProperty("job_salary")
    String jobSalary;

    @JsonProperty("job_locations")
    String jobLocations;

    //company
    @JsonProperty("company_name")
    String companyName;

    @JsonProperty("company_logo")
    String companyLogo;

    @JsonProperty("company_location")
    String companyLocation;

    public static CompanyFeedbackRefuseResponse fromCompanyFeedbackRefuse(CompanyFeedbackRefuse companyFeedbackRefuse){
        return CompanyFeedbackRefuseResponse.builder()
                .fullName(companyFeedbackRefuse.getFullName())
                .jobTitle(companyFeedbackRefuse.getJobTitle())
                .reason(companyFeedbackRefuse.getReason())
                .contact(companyFeedbackRefuse.getContact())
                .email(companyFeedbackRefuse.getEmail())
                .note(companyFeedbackRefuse.getNote())
                .jobName(companyFeedbackRefuse.getAppliedJob().getJob().getName())
                .jobSalary(companyFeedbackRefuse.getAppliedJob().getJob().getSalary())
                .jobLocations(companyFeedbackRefuse.getAppliedJob().getJob().getJobLocations())
                .companyName(companyFeedbackRefuse.getCompany().getName())
                .companyLogo(companyFeedbackRefuse.getCompany().getLogo())
                .companyLocation(companyFeedbackRefuse.getCompany().getLocation())
                .build();
    }
}

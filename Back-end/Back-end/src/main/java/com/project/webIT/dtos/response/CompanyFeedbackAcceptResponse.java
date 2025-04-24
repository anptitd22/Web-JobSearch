package com.project.webIT.dtos.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.project.webIT.models.CompanyFeedbackAccept;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class CompanyFeedbackAcceptResponse {
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

    public static CompanyFeedbackAcceptResponse fromCompanyFeedbackAccept(CompanyFeedbackAccept companyFeedbackAccept){
        return CompanyFeedbackAcceptResponse.builder()
                .fullName(companyFeedbackAccept.getFullName())
                .jobTitle(companyFeedbackAccept.getJobTitle())
                .interviewTime(companyFeedbackAccept.getInterviewTime())
                .address(companyFeedbackAccept.getAddress())
                .contact(companyFeedbackAccept.getContact())
                .email(companyFeedbackAccept.getEmail())
                .note(companyFeedbackAccept.getNote())
                .jobName(companyFeedbackAccept.getAppliedJob().getJob().getName())
                .jobSalary(companyFeedbackAccept.getAppliedJob().getJob().getSalary())
                .jobLocations(companyFeedbackAccept.getAppliedJob().getJob().getJobLocations())
                .companyName(companyFeedbackAccept.getCompany().getName())
                .companyLogo(companyFeedbackAccept.getCompany().getLogo())
                .companyLocation(companyFeedbackAccept.getCompany().getLocation())
                .build();
    }
}

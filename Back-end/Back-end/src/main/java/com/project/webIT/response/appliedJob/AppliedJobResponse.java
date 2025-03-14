package com.project.webIT.response.appliedJob;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.project.webIT.models.AppliedJob;
import com.project.webIT.models.Job;
import lombok.*;

import java.time.LocalDateTime;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AppliedJobResponse {

    @JsonProperty("id")
    private Long id;

    @JsonProperty("user_id")
    private Long userId;

    @JsonProperty("full_name")
    private String fullName;

    private String nationality;
    private String email;

    @JsonProperty("phone_number")
    private String phoneNumber;

    private String address;

    @JsonProperty("job_id")
    private Long jobId;

    @JsonProperty("job_title")
    private String jobTitle;

    @JsonProperty("current_job_level")
    private String currentJobLevel;

    @JsonProperty("highest_education")
    private String highestEducation;

    @JsonProperty("current_industry")
    private String currentIndustry;

    @JsonProperty("current_job_function")
    private String currentJobFunction;

    @JsonProperty("years_of_experience")
    private Long yearsOfExperience;

    private String note;

    @JsonProperty("expected_date")
    private LocalDateTime expectedDate;

    @JsonProperty("apply_date")
    private LocalDateTime applyDate;

    @JsonProperty("is_active")
    private boolean isActive;

    //job
    @JsonProperty("job_name")
    private String jobName;

    @JsonProperty("job_salary")
    private String jobSalary;

    @JsonProperty("job_locations")
    private String jobLocations;

    @JsonProperty("job_description")
    private String description;

    @JsonProperty("job_function_id")
    private Long jobFunctionId;

    @JsonProperty("job_is_active")
    private boolean jobIsActive;

    //company
    @JsonProperty("company_name")
    private String companyName;

    @JsonProperty("company_logo")
    private String companyLogo;

    public static AppliedJobResponse fromAppliedJob (AppliedJob appliedJob){
        AppliedJobResponse appliedJobResponse = AppliedJobResponse.builder()
                .id(appliedJob.getId())
                .userId(appliedJob.getUser().getId())
                .fullName(appliedJob.getFullName())
                .email(appliedJob.getEmail())
                .phoneNumber(appliedJob.getPhoneNumber())
                .address(appliedJob.getAddress())
                .nationality(appliedJob.getNationality())
                .jobId(appliedJob.getJob().getId())
                .jobTitle(appliedJob.getJobTitle())
                .currentJobLevel(appliedJob.getCurrentJobLevel())
                .highestEducation(appliedJob.getHighestEducation())
                .currentIndustry(appliedJob.getCurrentIndustry())
                .currentJobFunction(appliedJob.getCurrentJobFunction())
                .yearsOfExperience(appliedJob.getYearsOfExperience())
                .note(appliedJob.getNote())
                .expectedDate(appliedJob.getExpectedDate())
                .isActive(appliedJob.isActive())
                .jobName(appliedJob.getJob().getName())
                .jobSalary(appliedJob.getJob().getSalary())
                .description(appliedJob.getJob().getDescription())
                .jobLocations(appliedJob.getJob().getJobLocations())
                .jobIsActive(appliedJob.getJob().isActive())
                .companyName(appliedJob.getJob().getCompany().getName())
                .companyLogo(appliedJob.getJob().getCompany().getLogo())
                .build();
        appliedJobResponse.setApplyDate(appliedJob.getApplyDate());
        return appliedJobResponse;
    }
}

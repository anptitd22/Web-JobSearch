package com.project.webIT.dtos.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.project.webIT.models.AppliedJob;
import com.project.webIT.models.AppliedJobCV;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AppliedJobResponse {

    @JsonProperty("id")
    Long id;

    @JsonProperty("user_id")
    Long userId;

    @JsonProperty("full_name")
    String fullName;

    String nationality;

    String email;

    @JsonProperty("cv_list")
    List<AppliedJobCVResponse> appliedJobCVS;

    @JsonProperty("phone_number")
    String phoneNumber;

    String address;

    @JsonProperty("job_id")
    Long jobId;

    @JsonProperty("job_title")
    String jobTitle;

    @JsonProperty("current_job_level")
    String currentJobLevel;

    @JsonProperty("highest_education")
    String highestEducation;

    @JsonProperty("current_industry")
    String currentIndustry;

    @JsonProperty("current_job_function")
    String currentJobFunction;

    @JsonProperty("years_of_experience")
    Long yearsOfExperience;

    @JsonProperty("job_end_at")
    LocalDateTime jobEndAt;

    String note;

    @JsonProperty("expected_date")
    LocalDateTime expectedDate;

    @JsonProperty("apply_date")
    LocalDateTime applyDate;

    @JsonProperty("is_active")
    boolean isActive;

    //job
    @JsonProperty("job_name")
    String jobName;

    @JsonProperty("job_salary")
    String jobSalary;

    @JsonProperty("job_locations")
    String jobLocations;

    @JsonProperty("job_description")
    String description;

    @JsonProperty("job_function_id")
    Long jobFunctionId;

    @JsonProperty("job_is_active")
    boolean jobIsActive;

    //company
    @JsonProperty("company_name")
    String companyName;

    @JsonProperty("company_logo")
    String companyLogo;

    @JsonProperty("company_location")
    String companyLocation;

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
                .companyLocation(appliedJob.getJob().getCompany().getLocation())
                .jobEndAt(appliedJob.getJob().getEndAt())
                .appliedJobCVS(appliedJob.getAppliedJobCVList().stream()
                        .map(AppliedJobCVResponse::fromAppliedJobCV)
                        .collect(Collectors.toList()))
                .build();
        appliedJobResponse.setApplyDate(appliedJob.getApplyDate());
        return appliedJobResponse;
    }
}

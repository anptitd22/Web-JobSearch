package com.project.webIT.response.appliedJob;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.project.webIT.models.AppliedJob;
import lombok.*;

import java.time.LocalDateTime;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AppliedJobResponse {
    @JsonProperty("user_id")
    private Long userId;

    @JsonProperty("fullname")
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

    public static AppliedJobResponse fromAppliedJob (AppliedJob appliedJob){
        AppliedJobResponse appliedJobResponse = AppliedJobResponse.builder()
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
                .build();
        appliedJobResponse.setApplyDate(appliedJob.getApplyDate());
        return appliedJobResponse;
    }
}

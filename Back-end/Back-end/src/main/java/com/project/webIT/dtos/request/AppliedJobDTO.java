package com.project.webIT.dtos.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.time.LocalDateTime;

@Setter
@Getter
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AppliedJobDTO {

    @JsonProperty("user_id")
    private Long userId;

    @JsonProperty("full_name")
    @NotBlank(message = "Name is require")
    private String fullName;

    @NotBlank(message = "nationality is require")
    private String nationality;

    @NotBlank(message = "Email is require")
    private String email;

    @JsonProperty("phone_number")
    @NotBlank(message = "Phone number is require")
    private String phoneNumber;

    @NotBlank(message = "address is require")
    private String address;

    @JsonProperty("job_id")
    private Long jobId;

    @JsonProperty("job_title")
    private String jobTitle;

    @JsonProperty("current_job_level")
    @NotBlank(message = "Not Blank !,You are 'Intern/Student, Fresher/Entry level, Experienced(non-manager), Manager, Director and above' ?")
    private String currentJobLevel;

    @JsonProperty("highest_education")
    @NotBlank(message = "Highest Education is require")
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
}

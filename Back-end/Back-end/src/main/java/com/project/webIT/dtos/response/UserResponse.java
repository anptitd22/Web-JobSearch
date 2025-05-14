package com.project.webIT.dtos.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.project.webIT.models.AppliedJob;
import com.project.webIT.models.User;
import com.project.webIT.models.UserPayment;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class UserResponse {
    private Long id;

    private String avatar;

    @JsonProperty("public_id_images")
    private String publicIdImages;

    @JsonProperty("full_name")
    private String fullName;

    @JsonProperty("first_name")
    private String firstName;

    @JsonProperty("last_name")
    private String lastName;

    @JsonProperty("phone_number")
    private String phoneNumber;

    private String email;

    private String address;

    @JsonProperty("date_of_birth")
    private Date dateOfBirth;

    @JsonProperty("facebook_account_id")
    private String facebookAccountId;

    @JsonProperty("google_account_id")
    private String googleAccountId;

    @JsonProperty("user_is_active")
    private boolean isActive;

    @JsonProperty("role_id")
    private Long roleID;

    @JsonProperty("highest_education")
    private String highestEducation;

    @JsonProperty("current_industry")
    private String currentIndustry;

    @JsonProperty("current_job_function")
    private String currentJobFunction;

    @JsonProperty("current_job_function_id")
    private Long currentJobFunctionId;

    @JsonProperty("years_of_experience")
    private Long yearsOfExperience;

    @JsonProperty("current_job_level")
    private String currentJobLevel;

    @JsonProperty("job_title")
    private String jobTitle;

    @JsonProperty("note")
    private String note;

    @JsonProperty("nationality")
    private String nationality;

    @JsonProperty("gender")
    private String gender;

    @JsonProperty("marital_status")
    private String maritalStatus;

    @JsonProperty("user_updated_at")
    private LocalDateTime updatedAt;

    @JsonProperty("target")
    private String target;

    public static UserResponse fromUser(User user){
        return UserResponse.builder()
                .avatar(user.getAvatar())
                .publicIdImages(user.getPublicIdImages())
                .id(user.getId())
                .fullName(user.getFullName())
                .phoneNumber(user.getPhoneNumber())
                .email(user.getEmail())
                .address(user.getAddress())
                .dateOfBirth(user.getDateOfBirth())
                .facebookAccountId(user.getFacebookAccountId())
                .googleAccountId(user.getGoogleAccountId())
                .isActive(user.isActive())
                .highestEducation(user.getHighestEducation())
                .currentIndustry(user.getCurrentIndustry())
                .currentJobFunction(user.getCurrentJobFunction() != null ? user.getCurrentJobFunction().getName() : null)
                .currentJobFunctionId(user.getCurrentJobFunction() != null ? user.getCurrentJobFunction().getId() : null)
                .yearsOfExperience(user.getYearsOfExperience())
                .jobTitle(user.getJobTitle())
                .note(user.getNote())
                .nationality(user.getNationality())
                .gender(user.getGender())
                .maritalStatus(user.getMaritalStatus())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .currentJobLevel(user.getCurrentJobLevel())
                .roleID(user.getRole().getId())
                .updatedAt(user.getUpdatedAt())
                .target(user.getTarget())
                .build();
    }
}

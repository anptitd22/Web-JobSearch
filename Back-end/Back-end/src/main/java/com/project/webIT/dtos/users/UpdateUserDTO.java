package com.project.webIT.dtos.users;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.Date;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UpdateUserDTO {
    @JsonProperty("avatar")
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

    private String password;

    @JsonProperty("retype_password")
    private String retypePassword;

    @JsonProperty("date_of_birth")
    private Date dateOfBirth;

    @JsonProperty("facebook_account_id")
    private int facebookAccountId;

    @JsonProperty("google_account_id")
    private int googleAccountId;

    @JsonProperty("highest_education")
    private String highestEducation;

    @JsonProperty("current_industry")
    private String currentIndustry;

    @JsonProperty("current_job_function")
    private String currentJobFunction;

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
}

package com.project.webIT.dtos.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data // từ Lombok
@NoArgsConstructor
@AllArgsConstructor
public class UserInputDTO {
    private String current_job_function;
    private String current_job_level;
    private String current_industry;
    private int years_of_experience;
    private String job_title;
    private String address;
}

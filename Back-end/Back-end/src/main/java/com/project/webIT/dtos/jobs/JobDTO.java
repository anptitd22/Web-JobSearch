package com.project.webIT.dtos.jobs;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class JobDTO {
    @NotBlank(message = "Title is required")
    @Size(min = 2, max = 200, message = "Title must be between 3 and 200 characters")
    private String name;

    @NotBlank(message = "Salary is required")
    private String salary;

    @JsonProperty("salary_numeric")
    private Float salaryNumeric;

    @JsonProperty("type_of_work")
    @NotBlank(message = "typeOfWork is required")
    private String typeOfWork;

    private String jobLocations;
    private String thumbnail;
    private String description;

    @JsonProperty("job_function_id")
    private Long jobFunctionId;

    @JsonProperty("company_id")
    private Long companyId;
}

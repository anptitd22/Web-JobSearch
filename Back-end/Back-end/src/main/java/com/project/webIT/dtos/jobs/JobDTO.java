package com.project.webIT.dtos.jobs;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDateTime;

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
    @NotNull(message = "Salary is not null")
    private Float salaryNumeric;

    @JsonProperty("type_of_work")
    @NotBlank(message = "type of work is required")
    private String typeOfWork;

    @JsonProperty("job_locations")
    private String jobLocations;

    private String description;

    @JsonProperty("job_function_id")
    private Long jobFunctionId;

    @JsonProperty("end_at")
    private LocalDateTime endAt;

    @JsonProperty("company_id")
    private Long companyId;
}

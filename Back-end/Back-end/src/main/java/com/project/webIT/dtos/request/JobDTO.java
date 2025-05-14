package com.project.webIT.dtos.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.project.webIT.constant.JobStatus;
import jakarta.persistence.Column;
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
    @NotBlank(message = "location is required")
    private String jobLocations;

    @JsonProperty("description")
    private String description;

    @JsonProperty("job_function_id")
    @NotNull(message = "job function is required")
    private Long jobFunctionId;

    @JsonProperty("status")
    private JobStatus jobStatus;

    @JsonProperty("end_at")
    @NotNull(message = "end at is required")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime endAt;

    @NotBlank(message = "job_level is required")
    @JsonProperty("job_level")
    private String jobLevel;

    @JsonProperty("company_id")
    @NotNull(message = "company id is required")
    private Long companyId;
}

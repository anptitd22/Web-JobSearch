package com.project.webIT.dtos.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.project.webIT.models.Company;
import com.project.webIT.models.Job;
import com.project.webIT.models.JobImage;
import com.project.webIT.models.BaseEntity;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class JobResponse extends BaseEntity {

    private Long id;
    private String name;
    private String salary;

    @JsonProperty("salary_numeric")
    private Float salaryNumeric;

    @JsonProperty("updated_at")
    private LocalDateTime updatedAt;

    @JsonProperty("job_locations")
    private String jobLocations;

    private String description;

    @JsonProperty("job_function_id")
    private Long jobFunctionId;

    @JsonProperty("is_active")
    private boolean isActive;

    @JsonProperty("job_images")
    private List<JobImage> jobImages = new ArrayList<>();

    @JsonProperty("company")
    private Company company;

    @JsonProperty("view")
    private Long view;

    public static JobResponse fromJob(Job job){
        JobResponse jobResponse = JobResponse.builder()
                .id(job.getId())
                .name(job.getName())
                .salary(job.getSalary())
                .jobLocations(job.getJobLocations())
                .description(job.getDescription())
                .jobFunctionId(job.getJobFunction().getId())
                .isActive(job.isActive())
                .jobImages(job.getJobImages())
                .company(job.getCompany())
                .view(job.getView())
                .salaryNumeric(job.getSalaryNumeric())
                .updatedAt(job.getUpdatedAt())
                .build();
        jobResponse.setCreatedAt(job.getCreatedAt());
        jobResponse.setUpdatedAt(job.getUpdatedAt());
        return jobResponse;
    }
}

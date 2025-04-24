package com.project.webIT.dtos.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.project.webIT.constant.JobStatus;
import com.project.webIT.models.*;
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
    @JsonProperty("end_at")
    private LocalDateTime endAt;

    @JsonProperty("job_locations")
    private String jobLocations;

    @JsonProperty("job_description")
    private String description;

    @JsonProperty("job_function_name")
    private String jobFunctionName;

    @JsonProperty("job_function_id")
    private Long jobFunctionId;

    @JsonProperty("job_is_active")
    private boolean isActive;

    @JsonProperty("job_images")
    private List<JobImage> jobImages = new ArrayList<>();

    @JsonProperty("company")
    private Company company;

    @JsonProperty("view")
    private Long view;

    @JsonProperty("job_level")
    private String jobLevel;

    @JsonProperty("type_of_work")
    private String typeOfWork;

    @JsonProperty("status")
    private JobStatus jobStatus;

    public static JobResponse fromJob(Job job){
        JobResponse jobResponse = JobResponse.builder()
                .id(job.getId())
                .name(job.getName())
                .salary(job.getSalary())
                .jobLocations(job.getJobLocations())
                .description(job.getDescription())
                .jobFunctionName(job.getJobFunction().getName())
                .isActive(job.isActive())
                .jobImages(job.getJobImages())
                .company(job.getCompany())
                .view(job.getView())
                .salaryNumeric(job.getSalaryNumeric())
                .updatedAt(job.getUpdatedAt())
                .endAt(job.getEndAt())
                .jobFunctionId(job.getJobFunction().getId())
                .jobLevel(job.getJobLevel())
                .typeOfWork(job.getTypeOfWork())
                .jobStatus(job.getJobStatus())
                .build();
        jobResponse.setCreatedAt(job.getCreatedAt());
        jobResponse.setUpdatedAt(job.getUpdatedAt());
        return jobResponse;
    }
}

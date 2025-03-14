package com.project.webIT.response.jobs;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.project.webIT.models.Company;
import com.project.webIT.models.Job;
import com.project.webIT.models.JobImage;
import com.project.webIT.models.JobViewHistory;
import com.project.webIT.utils.ViewEntity;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class JobViewHistoryResponse extends ViewEntity {

    @JsonProperty("is_active")
    private boolean isActive;

    @JsonProperty("view_count")
    private Long viewCount;

    //job
    @JsonProperty("job_id")
    private Long jobId;

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

    @JsonProperty("view")
    private Long view;

    //company
    @JsonProperty("company_name")
    private String companyName;

    @JsonProperty("company_logo")
    private String companyLogo;

    public static JobViewHistoryResponse fromJobViewHistoryResponse(JobViewHistory jobViewHistory){
        JobViewHistoryResponse jobViewHistoryResponse = JobViewHistoryResponse.builder()
                .name(jobViewHistory.getJob().getName())
                .salary(jobViewHistory.getJob().getSalary())
                .salaryNumeric(jobViewHistory.getJob().getSalaryNumeric())
                .updatedAt(jobViewHistory.getJob().getUpdatedAt())
                .jobLocations(jobViewHistory.getJob().getJobLocations())
                .description(jobViewHistory.getJob().getDescription())
                .jobFunctionId(jobViewHistory.getJob().getJobFunction().getId())
                .isActive(jobViewHistory.isActive())
                .view(jobViewHistory.getJob().getView())
                .companyName(jobViewHistory.getJob().getCompany().getName())
                .companyLogo(jobViewHistory.getJob().getCompany().getLogo())
                .viewCount(jobViewHistory.getViewCount())
                .jobId(jobViewHistory.getJob().getId())
                .build();
        jobViewHistoryResponse.setViewedAt(jobViewHistory.getViewedAt());
        return jobViewHistoryResponse;
    }
}

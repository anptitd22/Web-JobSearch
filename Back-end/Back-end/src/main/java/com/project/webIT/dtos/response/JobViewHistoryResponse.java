package com.project.webIT.dtos.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.project.webIT.models.JobViewHistory;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class JobViewHistoryResponse{

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

    @JsonProperty("job_locations")
    private String jobLocations;

    private String description;

    @JsonProperty("job_function_id")
    private Long jobFunctionId;

    @JsonProperty("view")
    private Long view;

    @JsonProperty("update_at")
    private LocalDateTime updateAt;

    @JsonProperty("end_at")
    private LocalDateTime endAt;

    //company
    @JsonProperty("company_name")
    private String companyName;

    @JsonProperty("company_logo")
    private String companyLogo;

    public static JobViewHistoryResponse fromJobViewHistoryResponse(JobViewHistory jobViewHistory){
        return JobViewHistoryResponse.builder()
                .name(jobViewHistory.getJob().getName())
                .salary(jobViewHistory.getJob().getSalary())
                .salaryNumeric(jobViewHistory.getJob().getSalaryNumeric())
                .jobLocations(jobViewHistory.getJob().getJobLocations())
                .description(jobViewHistory.getJob().getDescription())
                .jobFunctionId(jobViewHistory.getJob().getJobFunction().getId())
                .isActive(jobViewHistory.isActive())
                .view(jobViewHistory.getJob().getView())
                .companyName(jobViewHistory.getJob().getCompany().getName())
                .companyLogo(jobViewHistory.getJob().getCompany().getLogo())
                .viewCount(jobViewHistory.getViewCount())
                .jobId(jobViewHistory.getJob().getId())
                .endAt(jobViewHistory.getJob().getEndAt())
                .build();
    }
}

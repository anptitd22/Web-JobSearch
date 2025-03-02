package com.project.webIT.response.jobs;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class JobListResponse {
    private List<JobResponse> jobs;
    private int totalPages;
    private long totalJob;
}

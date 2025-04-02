package com.project.webIT.services.IService;

import com.project.webIT.models.JobViewHistory;

import java.util.List;

public interface JobViewHistoryService {
    List<JobViewHistory> jobViewHistories(Long userId);

    JobViewHistory saveJobViewHistory(Long userId, Long jobId) throws Exception;
}

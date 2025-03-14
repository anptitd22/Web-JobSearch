package com.project.webIT.services.IService;

import com.project.webIT.dtos.appliedJob.AppliedJobDTO;
import com.project.webIT.exception.DataNotFoundException;
import com.project.webIT.models.AppliedJob;
import com.project.webIT.models.Job;
import com.project.webIT.response.appliedJob.AppliedJobResponse;

import java.util.List;

public interface IAppliedJobService {
    AppliedJob createAppliedJob(AppliedJobDTO appliedJobDTO) throws Exception;

    List<AppliedJob> checkAppliedJob(Long userId, Long jobId);

    AppliedJob updateAppliedJob(Long id,AppliedJobDTO appliedJobDTO) throws Exception;

    List<AppliedJob> getAppliedJobFromUser(Long userId);

    List<AppliedJob> findByJobId(Long jobId);

    AppliedJob getAppliedJob(Long id) throws Exception;

    void deleteApplied (Long id) throws DataNotFoundException;
}

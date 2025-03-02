package com.project.webIT.services.IService;

import com.project.webIT.dtos.appliedJob.AppliedJobDTO;
import com.project.webIT.exception.DataNotFoundException;
import com.project.webIT.models.AppliedJob;
import com.project.webIT.models.Job;

import java.util.List;

public interface IAppliedJobService {
    AppliedJob createAppliedJob(AppliedJobDTO appliedJobDTO) throws Exception;

    AppliedJob getAppliedJob(Long id);

    AppliedJob updateAppliedJob(Long id,AppliedJobDTO appliedJobDTO) throws Exception;

    List<AppliedJob> findByUserId(Long userId);

    List<Job> findByJobId(Long jobId);

    void deleteApplied (Long id) throws DataNotFoundException;
}

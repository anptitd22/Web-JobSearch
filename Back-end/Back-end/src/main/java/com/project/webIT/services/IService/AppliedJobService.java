package com.project.webIT.services.IService;

import com.project.webIT.dtos.appliedJob.AppliedJobDTO;
import com.project.webIT.exceptions.DataNotFoundException;
import com.project.webIT.models.AppliedJob;

import java.util.List;

public interface AppliedJobService {
    AppliedJob createAppliedJob(AppliedJobDTO appliedJobDTO) throws Exception;

    List<AppliedJob> checkAppliedJob(Long userId, Long jobId);

    AppliedJob updateAppliedJob(Long id,AppliedJobDTO appliedJobDTO) throws Exception;

    List<AppliedJob> getAppliedJobFromUser(Long userId);

    List<AppliedJob> findByJobId(Long jobId);

    AppliedJob getAppliedJob(Long id) throws Exception;

    void deleteApplied (Long id) throws DataNotFoundException;
}

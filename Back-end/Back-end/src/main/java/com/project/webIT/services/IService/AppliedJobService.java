package com.project.webIT.services.IService;

import com.project.webIT.constant.AppliedJobStatus;
import com.project.webIT.dtos.request.AppliedJobDTO;
import com.project.webIT.dtos.response.AppliedJobResponse;
import com.project.webIT.exceptions.DataNotFoundException;
import com.project.webIT.models.AppliedJob;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.List;

public interface AppliedJobService{

    AppliedJob createAppliedJob(AppliedJobDTO appliedJobDTO) throws Exception;

    List<AppliedJob> checkAppliedJob(Long userId, Long jobId);

    AppliedJob updateAppliedJob(Long id, AppliedJobDTO appliedJobDTO) throws Exception;

    List<AppliedJob> getAppliedJobFromUser(Long userId);

    Page<AppliedJobResponse> getAllAppliedJob(String keyword, Long jobId, Long companyId, PageRequest pageRequest, AppliedJobStatus status);

    List<AppliedJob> findByJobId(Long jobId);

    AppliedJob getAppliedJob(Long id) throws Exception;

    void deleteApplied (Long id) throws DataNotFoundException;
}

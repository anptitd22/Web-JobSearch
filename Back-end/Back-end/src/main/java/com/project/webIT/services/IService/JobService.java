package com.project.webIT.services.IService;

import com.project.webIT.dtos.jobs.JobDTO;
import com.project.webIT.dtos.jobs.JobImageDTO;
import com.project.webIT.models.Job;
import com.project.webIT.models.JobImage;
import com.project.webIT.response.jobs.JobResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.Authentication;

public interface JobService {
    Job createJob(JobDTO jobDTO) throws Exception;

    Job getJobById(long id) throws Exception;

    Page<JobResponse> getAllJobs(String keyword,Long jobFunctionId, PageRequest pageRequest);

    Job updateJob(long id, JobDTO jobDTO) throws Exception;

    void overJob(long id) throws Exception;

    void deleteJob(long id);

    boolean existByName(String name);

    JobImage createJobImage(Long jobId, JobImageDTO jobImageDTO) throws Exception;

    Job increaseViews (Long jobId, Authentication authentication) throws Exception;
}

package com.project.webIT.services.IService;

import com.project.webIT.dtos.request.JobFunctionDTO;
import com.project.webIT.models.JobFunction;

import java.util.List;

public interface JobFunctionService {
    JobFunction createJobFunction(JobFunctionDTO jobFunctionDTO);

    JobFunction getJobFunctionById(long id);

    List<JobFunction> getAllJobFunctions();

    JobFunction updateJobFunction(long jobFunctionId, JobFunctionDTO jobFunctionDTO);

    void deleteJobFunction(long id);
}

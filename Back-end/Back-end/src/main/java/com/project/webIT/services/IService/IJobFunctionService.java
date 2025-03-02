package com.project.webIT.services.IService;

import com.project.webIT.dtos.jobs.JobFunctionDTO;
import com.project.webIT.models.JobFunction;

import java.util.List;

public interface IJobFunctionService {
    JobFunction createJobFunction(JobFunctionDTO jobFunctionDTO);

    JobFunction getJobFunctionById(long id);

    List<JobFunction> getAllJobFunctions();

    JobFunction updateJobFunction(long jobFunctionId, JobFunctionDTO jobFunctionDTO);

    void deleteJobFunction(long id);
}

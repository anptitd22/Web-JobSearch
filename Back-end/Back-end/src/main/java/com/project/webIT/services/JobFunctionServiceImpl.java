package com.project.webIT.services;

import com.project.webIT.dtos.request.JobFunctionDTO;
import com.project.webIT.models.JobFunction;
import com.project.webIT.repositories.JobFunctionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class JobFunctionServiceImpl implements com.project.webIT.services.IService.JobFunctionService {

    private final JobFunctionRepository jobFunctionRepository;

    @Override
    public JobFunction createJobFunction(JobFunctionDTO jobFunctionDTO) {
        JobFunction newJobFunction = JobFunction
                .builder()
                .name(jobFunctionDTO.getName())
                .build();
        return jobFunctionRepository.save(newJobFunction); //luu vao db
    }

    @Override
    public JobFunction getJobFunctionById(long id) {
        return jobFunctionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Job Function not found"));
    }

    @Override
    public List<JobFunction> getAllJobFunctions() {
        return jobFunctionRepository.findAll();
    }

    @Override
    public JobFunction updateJobFunction(long jobFunctionId, JobFunctionDTO jobFunctionDTO) {
        JobFunction existingJobFunctionsEntity = getJobFunctionById(jobFunctionId);
        existingJobFunctionsEntity.setName(jobFunctionDTO.getName());
        jobFunctionRepository.save(existingJobFunctionsEntity);
        return existingJobFunctionsEntity;
    }

    @Override
    public void deleteJobFunction(long id) {
        //xoa cung
        Optional<JobFunction> optionalJobFunction = jobFunctionRepository.findById(id);
        if(optionalJobFunction.isPresent()){
            jobFunctionRepository.deleteById(id);
        }
    }
}

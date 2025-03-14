package com.project.webIT.services;

import com.project.webIT.dtos.jobs.JobDTO;
import com.project.webIT.dtos.jobs.JobImageDTO;
import com.project.webIT.exception.DataNotFoundException;
import com.project.webIT.exception.InvalidParamException;
import com.project.webIT.models.Company;
import com.project.webIT.models.Job;
import com.project.webIT.models.JobFunction;
import com.project.webIT.models.JobImage;
import com.project.webIT.repositories.CompanyRepository;
import com.project.webIT.repositories.JobFunctionRepository;
import com.project.webIT.repositories.JobImageRepository;
import com.project.webIT.repositories.JobRepository;
import com.project.webIT.response.jobs.JobResponse;
import com.project.webIT.services.IService.IJobService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class JobService implements IJobService {
    private final JobRepository jobRepository;
    private final JobFunctionRepository jobFunctionRepository;
    private final ModelMapper modelMapper;
    private final CompanyRepository companyRepository;
    private final JobImageRepository jobImageRepository;

    @Override
    public Job createJob(JobDTO jobDTO) throws Exception {
        JobFunction existingJobFunction = jobFunctionRepository.findById(jobDTO.getJobFunctionId())
                .orElseThrow(() ->
                        new DataNotFoundException("Cannot find Job Function with id = "
                                +jobDTO.getJobFunctionId()));
        Company existingCompany = companyRepository.findById(jobDTO.getCompanyId())
                .orElseThrow(() ->
                        new DataNotFoundException("Cannot find Company with id = "
                                +jobDTO.getCompanyId()));
        modelMapper.typeMap(JobDTO.class, Job.class)
                .addMappings(mapper ->
                        mapper.skip(Job::setId));
        Job newJob = new Job();
        modelMapper.map(jobDTO, newJob);
        newJob.setJobFunction(existingJobFunction);
        newJob.setCompany(existingCompany);
        newJob.setActive(true);
        newJob.setView((long)0);
        return jobRepository.save(newJob);
    }

    @Override
    public Job getJobById(long id) throws Exception {
        Job job =  jobRepository.findById(id)
                .orElseThrow(() ->
                        new DataNotFoundException("Cannot find Job with id = "+id));
        job.setView(job.getView()+1);
        return jobRepository.save(job);
    }

    @Override
    public Page<JobResponse> getAllJobs(String keyword, Long jobFunctionId, PageRequest pageRequest) { //page va limit
        //lay danh sach cong viec theo trang(page) va gioi han(limit)
        Page<Job> jobPage = jobRepository.searchJobs(jobFunctionId, keyword, pageRequest);
        return jobPage.map(JobResponse::fromJob);
    }

    @Override
    public Job updateJob(long id, JobDTO jobDTO) throws Exception {
        Job existingJob = getJobById(id);
        //copy cac thuoc tinh tu DTO -> Job
        //co the dung ModelMapper
        if(existingJob != null){
            JobFunction existingJobFunction = jobFunctionRepository.findById(jobDTO.getJobFunctionId())
                    .orElseThrow(() ->
                            new DataNotFoundException("Cannot find Job Function with id = "
                                    +jobDTO.getJobFunctionId()));
            Company existingCompany = companyRepository.findById(jobDTO.getCompanyId())
                    .orElseThrow(() ->
                            new DataNotFoundException("Cannot find Company with id = "
                                    +jobDTO.getCompanyId()));
            modelMapper.typeMap(JobDTO.class, Job.class)
                    .addMappings(mapper ->
                            mapper.skip(Job::setId));
            modelMapper.map(jobDTO, existingJob);
            existingJob.setJobFunction(existingJobFunction);
            existingJob.setCompany(existingCompany);
            existingJob.setUpdatedAt(LocalDateTime.now());
            return jobRepository.save(existingJob);
        }
        return null;
    }

    @Override
    public void overJob(long id) throws Exception {
        Job existingJob = jobRepository.findById(id).orElse(null);
        if (existingJob != null){
            boolean active = existingJob.isActive();
            existingJob.setActive(!active);
            jobRepository.save(existingJob);
        }
    }

    @Override
    public void deleteJob(long id) {
        Optional<Job> optionalJob = jobRepository.findById(id);
        if(optionalJob.isPresent()) {
            jobRepository.deleteById(id);
        }
    }

    @Override
    public boolean existByName(String name) {
        return jobRepository.existsByName(name);
    }

    @Override
    public JobImage createJobImage(Long jobId, JobImageDTO jobImageDTO) throws Exception {
        Job existingJob = jobRepository.findById(jobId)
                .orElseThrow(() ->
                        new DataNotFoundException("Cannot find Job Function with id = "
                                +jobImageDTO.getJobId()));
        JobImage newJobImage = JobImage.builder()
                .job(existingJob)
                .imageUrl(jobImageDTO.getImageUrl())
                .build();
        //khong cho insert qua 5 anh
        int size = jobImageRepository.findByJobId(jobId).size();
        if(size >= JobImage.MAXIMUM_IMAGES_PER_JOB){
            throw new InvalidParamException("Number of Image must be <= " +
                    JobImage.MAXIMUM_IMAGES_PER_JOB);
        }
        return jobImageRepository.save(newJobImage);
    }

    @Override
    public Job increaseViews(Long jobId, Authentication authentication) throws Exception {
        Job job = jobRepository.findById(jobId)
                .orElseThrow( () -> new DataNotFoundException("job not found"));
        String currentUsername = authentication.getName();
        if (job.getCompany().getName().equals(currentUsername)) {
            throw new InvalidParamException("Bạn không thể tự tăng lượt xem sản phẩm của mình");
        }
        job.setView(job.getView() + 1);
        return jobRepository.save(job);
    }
}

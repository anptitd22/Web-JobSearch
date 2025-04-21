package com.project.webIT.services;


import com.project.webIT.dtos.request.AppliedJobDTO;
import com.project.webIT.dtos.response.AppliedJobResponse;
import com.project.webIT.exceptions.DataNotFoundException;
import com.project.webIT.models.AppliedJob;
import com.project.webIT.models.Job;
import com.project.webIT.models.User;
import com.project.webIT.repositories.AppliedJobRepository;
import com.project.webIT.repositories.JobRepository;
import com.project.webIT.repositories.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AppliedJobServiceImpl implements com.project.webIT.services.IService.AppliedJobService {
    private final UserRepository userRepository;
    private final AppliedJobRepository appliedJobRepository;
    private final JobRepository jobRepository;
    private final ModelMapper modelMapper;

    @Override
    public AppliedJob createAppliedJob(AppliedJobDTO appliedJobDTO) throws Exception {
        User user = userRepository.findById(appliedJobDTO.getUserId())
                .orElseThrow(() ->
                        new DataNotFoundException("Cannot find user with id = "+appliedJobDTO.getUserId()));
        Job job = jobRepository.findById(appliedJobDTO.getJobId())
                .orElseThrow(() ->
                        new DataNotFoundException("Cannot find job with id = "+appliedJobDTO.getJobId()));
        //convert DTO-> model
        modelMapper.getConfiguration().setAmbiguityIgnored(true); //tat anh xa tu dong tranh nham lan jobId voi userId
        modelMapper.typeMap(AppliedJobDTO.class, AppliedJob.class)
                .addMappings(mapper -> mapper.skip(AppliedJob::setId));
        AppliedJob appliedJob = new AppliedJob();
        modelMapper.map(appliedJobDTO, appliedJob);
        appliedJob.setUser(user);
        appliedJob.setJob(job);
        appliedJob.setApplyDate(LocalDateTime.now());
        appliedJob.setStatus(AppliedJob.PENDING);
        LocalDateTime expectedDate = appliedJobDTO.getExpectedDate();

        if (expectedDate != null && expectedDate.isBefore(LocalDateTime.now())){
            throw new DataNotFoundException("Date must be least today!");
        }

        appliedJob.setActive(true);
        job.setApplicationCount(job.getApplicationCount()+1);
        jobRepository.save(job);
        return appliedJobRepository.save(appliedJob);
    }

    @Override
    public List<AppliedJob> checkAppliedJob(Long user_id, Long job_id){
        return appliedJobRepository.findByUserIdAndJobId(user_id, job_id);
    }

    @Override
    public AppliedJob updateAppliedJob(Long id, AppliedJobDTO appliedJobDTO) throws Exception {
        AppliedJob existingAppliedJob = appliedJobRepository.findById(id)
                .orElseThrow(() ->
                        new DataNotFoundException("Cannot find applied job with id = "+id));
        User existingUser = userRepository.findById(appliedJobDTO.getUserId())
                .orElseThrow(() ->
                        new DataNotFoundException("Cannot find user with id = " + appliedJobDTO.getUserId()));
        Job existingJob = jobRepository.findById(appliedJobDTO.getJobId())
                .orElseThrow(() ->
                        new DataNotFoundException("Cannot find job with id = "+appliedJobDTO.getJobId()));
        modelMapper.getConfiguration().setAmbiguityIgnored(true);
        modelMapper.typeMap(AppliedJobDTO.class, AppliedJob.class)
                .addMappings(mapper -> mapper.skip(AppliedJob::setId));
        modelMapper.map(appliedJobDTO, existingAppliedJob);
        existingAppliedJob.setUser(existingUser);
        existingAppliedJob.setJob(existingJob);
        return appliedJobRepository.save(existingAppliedJob);
    }

    @Override
    public List<AppliedJob> getAppliedJobFromUser(Long userId) {
        return appliedJobRepository.findByUserId(userId);
    }

    @Override
    public Page<AppliedJobResponse> getAllAppliedJob(String keyword, Long jobId, Long companyId, PageRequest pageRequest) {
        var appliedJobPage = appliedJobRepository.searchAppliedJobs(jobId,companyId, keyword, pageRequest);
        return appliedJobPage.map(AppliedJobResponse::fromAppliedJob);
    }

    @Override
    public List<AppliedJob> findByJobId(Long jobId) {
        return appliedJobRepository.findByJobId(jobId);
    }

    @Override
    public AppliedJob getAppliedJob(Long id) throws Exception{
        return appliedJobRepository.findById(id)
                .orElseThrow(() -> new DataNotFoundException("not found applied job"));
    }

    @Override
    public void deleteApplied(Long id) throws DataNotFoundException {
        Optional<AppliedJob> appliedJobOptional = appliedJobRepository.findById(id);
        if(appliedJobOptional.isPresent()){
            Job job = jobRepository.findById(appliedJobOptional.get().getJob().getId())
                    .orElseThrow(() ->
                            new DataNotFoundException("Cannot find job with id = "
                                    +appliedJobOptional.get().getJob().getId()));
            appliedJobRepository.deleteById(id);
        }
    }
}

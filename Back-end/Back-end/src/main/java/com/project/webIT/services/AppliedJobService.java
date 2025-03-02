package com.project.webIT.services;

import com.project.webIT.dtos.appliedJob.AppliedJobDTO;
import com.project.webIT.exception.DataNotFoundException;
import com.project.webIT.models.AppliedJob;
import com.project.webIT.models.Status.AppliedJobStatus;
import com.project.webIT.models.Job;
import com.project.webIT.models.User;
import com.project.webIT.repositories.AppliedJobRepository;
import com.project.webIT.repositories.JobRepository;
import com.project.webIT.repositories.UserRepository;
import com.project.webIT.services.IService.IAppliedJobService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AppliedJobService implements IAppliedJobService {
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
        appliedJob.setStatus(AppliedJobStatus.PENDING);
//        if (!CurrentJobLevelStatus.currentJobLevel.contains(appliedJob.getStatus().toUpperCase())){
//            throw new InvalidParamException("Only choice of 'INTERN/STUDENT, FRESHER/ENTRY LEVEL, EXPERIENCED (NON-MANAGER), MANAGER, DIRECTOR AND ABOVE'");
//        }
        LocalDateTime expectedDate = appliedJobDTO.getExpectedDate();
        if (expectedDate != null && expectedDate.isBefore(LocalDateTime.now())){
            throw new DataNotFoundException("Date must be least today!");
        }
        jobRepository.save(job);
        appliedJob.setActive(true);
        return appliedJobRepository.save(appliedJob);
    }

    @Override
    public AppliedJob getAppliedJob(Long id) {
        return appliedJobRepository.findById(id).orElse(null);
    }

    @Override
    public AppliedJob updateAppliedJob(Long id, AppliedJobDTO appliedJobDTO) throws Exception {
        AppliedJob existingAppliedJob = appliedJobRepository.findById(id)
                .orElseThrow(() ->
                        new DataNotFoundException("Cannot find applied job with id = "+id));
        User existingUser = userRepository.findById(appliedJobDTO.getUserId())
                .orElseThrow(() ->
                        new DataNotFoundException("Cannot find user with id = " + appliedJobDTO.getUserId()));
        Job existingjob = jobRepository.findById(appliedJobDTO.getJobId())
                .orElseThrow(() ->
                        new DataNotFoundException("Cannot find job with id = "+appliedJobDTO.getJobId()));
        modelMapper.getConfiguration().setAmbiguityIgnored(true);
        modelMapper.typeMap(AppliedJobDTO.class, AppliedJob.class)
                .addMappings(mapper -> mapper.skip(AppliedJob::setId));
        modelMapper.map(appliedJobDTO, existingAppliedJob);
        existingAppliedJob.setUser(existingUser);
        existingAppliedJob.setJob(existingjob);
        return appliedJobRepository.save(existingAppliedJob);
    }

    @Override
    public List<AppliedJob> findByUserId(Long userId) {
        return appliedJobRepository.findByUserId(userId);
    }

    @Override
    public List<Job> findByJobId(Long jobId) {
        return appliedJobRepository.findByJobId(jobId);
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

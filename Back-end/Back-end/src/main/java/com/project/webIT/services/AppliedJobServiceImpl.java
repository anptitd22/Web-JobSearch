package com.project.webIT.services;

import com.project.webIT.dtos.request.AppliedJobDTO;
import com.project.webIT.exceptions.DataNotFoundException;
import com.project.webIT.models.AppliedJob;
import com.project.webIT.models.Job;
import com.project.webIT.models.User;
import com.project.webIT.repositories.AppliedJobRepository;
import com.project.webIT.repositories.JobRepository;
import com.project.webIT.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
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
    public List<AppliedJob> getAppliedJobFromUser(Long userId) {
        return appliedJobRepository.findByUserId(userId);
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

    @Override
    public AppliedJob create(AppliedJobDTO data) throws Exception{
        User user = userRepository.findById(data.getUserId())
                .orElseThrow(() ->
                        new DataNotFoundException("Cannot find user with id = "+data.getUserId()));
        Job job = jobRepository.findById(data.getJobId())
                .orElseThrow(() ->
                        new DataNotFoundException("Cannot find job with id = "+data.getJobId()));
        //convert DTO-> model
        modelMapper.getConfiguration().setAmbiguityIgnored(true); //tat anh xa tu dong tranh nham lan jobId voi userId
        modelMapper.typeMap(AppliedJobDTO.class, AppliedJob.class)
                .addMappings(mapper -> mapper.skip(AppliedJob::setId));
        AppliedJob appliedJob = new AppliedJob();
        modelMapper.map(data, appliedJob);
        appliedJob.setUser(user);
        appliedJob.setJob(job);
        appliedJob.setApplyDate(LocalDateTime.now());
        appliedJob.setStatus(AppliedJob.PENDING);
        LocalDateTime expectedDate = data.getExpectedDate();
        if (expectedDate != null && expectedDate.isBefore(LocalDateTime.now())){
            throw new DataNotFoundException("Date must be least today!");
        }
        jobRepository.save(job);
        appliedJob.setActive(true);
        return appliedJobRepository.save(appliedJob);
    }

    @Override
    public AppliedJob update(AppliedJobDTO data) {
        return null;
    }

    @Override
    public AppliedJob getAll() {
        return null;
    }

    @Override
    public AppliedJob getById(Long aLong) {
        return null;
    }

    @Override
    public String deleteById(Long aLong) {
        return "";
    }

    @Override
    public String deleteByListId(List<Long> listId) {
        return "";
    }
}

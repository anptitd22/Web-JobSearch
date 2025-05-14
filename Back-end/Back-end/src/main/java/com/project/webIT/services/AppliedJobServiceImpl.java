package com.project.webIT.services;


import com.project.webIT.constant.AppliedJobStatus;
import com.project.webIT.dtos.request.AppliedJobDTO;
import com.project.webIT.dtos.response.AppliedJobResponse;
import com.project.webIT.exceptions.DataNotFoundException;
import com.project.webIT.models.AppliedJob;
import com.project.webIT.models.CompanyDashBoard;
import com.project.webIT.models.Job;
import com.project.webIT.models.User;
import com.project.webIT.repositories.*;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AppliedJobServiceImpl implements com.project.webIT.services.IService.AppliedJobService {
    private final UserRepository userRepository;
    private final CompanyDashBoardRepository companyDashBoardRepository;
    private final CompanyRepository companyRepository;
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
        appliedJob.setAppliedJobStatus(AppliedJobStatus.Pending);
        LocalDateTime expectedDate = appliedJobDTO.getExpectedDate();

        if (expectedDate != null && expectedDate.isBefore(LocalDateTime.now())){
            throw new DataNotFoundException("Date must be least today!");
        }
        updateAppliedJob(job.getCompany().getId());

        appliedJob.setActive(true);

        if(job.getApplicationCount() == null){
            job.setApplicationCount(0L);
        }
        job.setApplicationCount(job.getApplicationCount()+1);

        jobRepository.save(job);



        return appliedJobRepository.save(appliedJob);
    }

    public void updateAppliedJob(Long companyId) throws Exception {
        var existingCompany = companyRepository.findById(companyId)
                .orElseThrow(() -> new DataNotFoundException("company not found"));

        String currentMonth = LocalDate.now().format(DateTimeFormatter.ofPattern("MMM-yyyy"));
        Optional<CompanyDashBoard> existingRecord = companyDashBoardRepository.findByCompanyIdAndMonth(companyId, currentMonth);

        if (existingRecord.isPresent()) {
            // Nếu bản ghi đã tồn tại, cập nhật số lượng total_jobs
            CompanyDashBoard record = existingRecord.get();
            record.setAppliedJobs(record.getAppliedJobs() + 1);
            companyDashBoardRepository.save(record);
        } else {
            // Nếu bản ghi chưa tồn tại, tạo bản ghi mới
            CompanyDashBoard newRecord = new CompanyDashBoard();
            newRecord.setCompany(existingCompany);
            newRecord.setMonth(currentMonth);
            newRecord.setTotalJobs(0L);
            newRecord.setAppliedJobs(1L);
            newRecord.setAppliedJobAccept(0L);
            companyDashBoardRepository.save(newRecord);
        }
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
    public Page<AppliedJobResponse> getAllAppliedJob(String keyword, Long jobId, Long companyId, PageRequest pageRequest, AppliedJobStatus status) {
        var appliedJobPage = appliedJobRepository.searchAppliedJobs(jobId,companyId, keyword, pageRequest, status);
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

    public Map<String, Long> getCompanyStatsMap(Long companyId) {
        Long totalApplied = appliedJobRepository.countTotalAppliedJobByCompanyId(companyId);
        Long totalAccepted = appliedJobRepository.countTotalAcceptedAppliedJobByCompanyId(companyId);

        Map<String, Long> stats = new HashMap<>();
        stats.put("totalAppliedJob", totalApplied);
        stats.put("totalAcceptedAppliedJob", totalAccepted);

        return stats;
    }
}

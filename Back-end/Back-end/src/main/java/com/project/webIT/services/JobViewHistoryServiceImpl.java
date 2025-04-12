package com.project.webIT.services;

import com.project.webIT.exceptions.DataNotFoundException;
import com.project.webIT.models.Job;
import com.project.webIT.models.JobViewHistory;
import com.project.webIT.models.User;
import com.project.webIT.repositories.JobRepository;
import com.project.webIT.repositories.JobViewHistoryRepository;
import com.project.webIT.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class JobViewHistoryServiceImpl implements com.project.webIT.services.IService.JobViewHistoryService {
    private final JobViewHistoryRepository jobViewHistoryRepository;
    private final UserRepository userRepository;
    private final JobRepository jobRepository;

    @Override
    public List<JobViewHistory> jobViewHistories(Long userId) {
        return jobViewHistoryRepository.findByUserIdOrderByViewedAtDesc(userId);
    }

    @Override
    public JobViewHistory saveJobViewHistory(Long userId, Long jobId) throws Exception {
        Optional<JobViewHistory> jobViewHistoryOptional = jobViewHistoryRepository.findByUserIdAndJobId(userId, jobId);

        User existingUser = userRepository.findById(userId)
                .orElseThrow(() -> new DataNotFoundException("user not found"));

        Job existingJob = jobRepository.findById(jobId)
                .orElseThrow(() -> new DataNotFoundException("job not found"));

        if (jobViewHistoryOptional.isPresent()){
            JobViewHistory jobViewHistory = jobViewHistoryOptional.get();
            jobViewHistory.setViewCount(jobViewHistory.getViewCount()+1);
            jobViewHistory.setViewedAt(LocalDateTime.now());
            return jobViewHistoryRepository.save(jobViewHistory);
        }else{
            JobViewHistory jobViewHistory = new JobViewHistory();
            jobViewHistory.setUser(existingUser);
            jobViewHistory.setJob(existingJob);
            jobViewHistory.setViewCount((long)1);
            jobViewHistory.setActive(true);
            return jobViewHistoryRepository.save(jobViewHistory);
        }
    }
}

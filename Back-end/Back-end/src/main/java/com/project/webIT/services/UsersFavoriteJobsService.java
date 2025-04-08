package com.project.webIT.services;

import com.project.webIT.exceptions.DataNotFoundException;
import com.project.webIT.models.Job;
import com.project.webIT.models.User;
import com.project.webIT.models.UsersFavoriteJobs;
import com.project.webIT.repositories.JobRepository;
import com.project.webIT.repositories.UserRepository;
import com.project.webIT.repositories.UsersFavoriteJobsRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class UsersFavoriteJobsService implements com.project.webIT.services.IService.UsersFavoriteJobsService {

    private final UsersFavoriteJobsRepository usersFavoriteJobsRepository;
    private final UserRepository userRepository;
    private final JobRepository jobRepository;

    @Override
    public UsersFavoriteJobs saveFavoriteJob(Long userId, Long jobId) throws Exception {
        Optional<UsersFavoriteJobs> existingJob = usersFavoriteJobsRepository.findByUserIdAndJobId(userId, jobId);

        User existingUser = userRepository.findById(userId)
                .orElseThrow(() -> new DataNotFoundException("user not found"));

        Job exisJob = jobRepository.findById(jobId)
                .orElseThrow(() -> new DataNotFoundException("job not found"));

        if (existingJob.isPresent()) {
            UsersFavoriteJobs job = existingJob.get();
            job.setActive(!job.isActive()); // Đảo trạng thái isDeleted
            return usersFavoriteJobsRepository.save(job);
        } else {
            UsersFavoriteJobs newJob = new UsersFavoriteJobs();
            newJob.setUser(existingUser);
            newJob.setJob(exisJob);
            newJob.setActive(true);
            newJob.setUpdatedAt(LocalDateTime.now());
            return usersFavoriteJobsRepository.save(newJob);
        }
    }

    @Override
    public List<UsersFavoriteJobs> getUserFavorites(Long userId) {
        return usersFavoriteJobsRepository.findByUserIdOrderByUpdatedAtDesc(userId);
    }

    @Override
    public List<UsersFavoriteJobs> getUserFavoritesDefault(Long userId) {
        return usersFavoriteJobsRepository.findByUserId(userId);
    }
}

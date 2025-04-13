package com.project.webIT.services;

import com.project.webIT.exceptions.DataNotFoundException;
import com.project.webIT.models.Job;
import com.project.webIT.models.User;
import com.project.webIT.models.UserFavoriteJob;
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
public class UsersFavoriteJobsServiceImpl implements com.project.webIT.services.IService.UsersFavoriteJobsService {

    private final UsersFavoriteJobsRepository usersFavoriteJobsRepository;
    private final UserRepository userRepository;
    private final JobRepository jobRepository;

    @Override
    public UserFavoriteJob saveFavoriteJob(User user, Long jobId) throws Exception {
        Optional<UserFavoriteJob> existingJob = usersFavoriteJobsRepository.findByUserIdAndJobId(user.getId(), jobId);

        Job exisJob = jobRepository.findById(jobId)
                .orElseThrow(() -> new DataNotFoundException("job not found"));

        if (existingJob.isPresent()) {
            var job = existingJob.get();
            job.setActive(!job.isActive()); // Đảo trạng thái isDeleted
            return usersFavoriteJobsRepository.save(job);
        } else {
            var newJob = new UserFavoriteJob();
            newJob.setUser(user);
            newJob.setJob(exisJob);
            newJob.setActive(true);
            newJob.setUpdatedAt(LocalDateTime.now());
            return usersFavoriteJobsRepository.save(newJob);
        }
    }

    @Override
    public List<UserFavoriteJob> getUserFavorites(Long userId) {
        return usersFavoriteJobsRepository.findByUserIdOrderByUpdatedAtDesc(userId);
    }

    @Override
    public List<UserFavoriteJob> getUserFavoritesDefault(Long userId) {
        return usersFavoriteJobsRepository.findByUserId(userId);
    }
}

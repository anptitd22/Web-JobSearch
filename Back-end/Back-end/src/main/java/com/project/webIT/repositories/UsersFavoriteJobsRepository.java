package com.project.webIT.repositories;

import com.project.webIT.models.UsersFavoriteJobs;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UsersFavoriteJobsRepository extends JpaRepository<UsersFavoriteJobs, Long> {
    List<UsersFavoriteJobs> findByUserIdOrderByUpdatedAtDesc(Long userId);

    List<UsersFavoriteJobs> findByUserId(Long userId);

    Optional<UsersFavoriteJobs> findByUserIdAndJobId(Long userId, Long jobId);
}

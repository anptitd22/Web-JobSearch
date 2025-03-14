package com.project.webIT.repositories;

import com.project.webIT.models.JobViewHistory;
import com.project.webIT.models.UsersFavoriteJobs;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface JobViewHistoryRepository extends JpaRepository<JobViewHistory, Long> {
    List<JobViewHistory> findByUserIdOrderByViewedAtDesc(Long userId);

    Optional<JobViewHistory> findByUserIdAndJobId(Long userId, Long jobId);
}

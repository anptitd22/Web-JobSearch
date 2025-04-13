package com.project.webIT.repositories;

import com.project.webIT.models.JobViewHistory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface JobViewHistoryRepository extends JpaRepository<JobViewHistory, Long> {
    List<JobViewHistory> findByUserIdOrderByUpdatedAtDesc(Long userId);

    Optional<JobViewHistory> findByUserIdAndJobId(Long userId, Long jobId);
}

package com.project.webIT.repositories;

import com.project.webIT.models.UserFavoriteJob;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UsersFavoriteJobsRepository extends JpaRepository<UserFavoriteJob, Long> {
    List<UserFavoriteJob> findByUserIdAndJobIsActiveTrueOrderByUpdatedAtDesc(Long userId);

    List<UserFavoriteJob> findByUserId(Long userId);

    List<UserFavoriteJob> findByJobId(Long jobId);

    Optional<UserFavoriteJob> findByUserIdAndJobId(Long userId, Long jobId);
}

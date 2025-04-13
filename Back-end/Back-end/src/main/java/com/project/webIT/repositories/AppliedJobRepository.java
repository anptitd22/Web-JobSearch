package com.project.webIT.repositories;

import com.project.webIT.models.AppliedJob;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AppliedJobRepository extends JpaRepository<AppliedJob, Long> { //<class, id>
    List<AppliedJob> findByUserId(Long userId);

    List<AppliedJob> findByJobId(Long jobId);

//    @Query("SELECT a FROM AppliedJob a WHERE a.user.id = :userId AND a.job.id = :jobId AND a.isActive = true")
    List<AppliedJob> findByUserIdAndJobId(Long userId, Long jobId);
}

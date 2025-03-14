package com.project.webIT.repositories;

import com.project.webIT.models.AppliedJob;
import com.project.webIT.models.Job;
import com.project.webIT.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface AppliedJobRepository extends JpaRepository<AppliedJob, Long> { //<class, id>
    List<AppliedJob> findByUserId(Long userId);

    List<AppliedJob> findByJobId(Long jobId);

//    @Query("SELECT a FROM AppliedJob a WHERE a.user.id = :userId AND a.job.id = :jobId AND a.isActive = true")
    List<AppliedJob> findByUserIdAndJobId(Long userId,Long jobId);
}

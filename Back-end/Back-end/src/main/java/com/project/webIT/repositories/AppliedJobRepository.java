package com.project.webIT.repositories;

import com.project.webIT.models.AppliedJob;
import com.project.webIT.models.Job;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AppliedJobRepository extends JpaRepository<AppliedJob, Long> { //<class, id>
    List<AppliedJob> findByUserId(Long userId);

    List<Job> findByJobId(Long jobId);
}

package com.project.webIT.repositories;

import com.project.webIT.models.JobImage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface JobImageRepository extends JpaRepository<JobImage, Long> {
    List<JobImage> findByJobId(Long jobId);
}

package com.project.webIT.repositories;

import com.project.webIT.models.AppliedJobCV;
import com.project.webIT.models.UserCV;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AppliedJobCVRepository extends JpaRepository<AppliedJobCV, Long> {
    List<AppliedJobCV> findByAppliedJobIdAndIsActiveTrueOrderByUpdatedAtDesc(Long appliedJobId);
}

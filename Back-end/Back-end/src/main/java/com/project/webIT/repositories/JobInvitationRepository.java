package com.project.webIT.repositories;

import com.project.webIT.models.JobInvitation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface JobInvitationRepository extends JpaRepository<JobInvitation, Long> {
    Optional<JobInvitation> findByUserIdAndJobId(Long userId, Long jobId);
}

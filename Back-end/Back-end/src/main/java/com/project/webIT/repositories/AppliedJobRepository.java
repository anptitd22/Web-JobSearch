package com.project.webIT.repositories;

import com.project.webIT.models.AppliedJob;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AppliedJobRepository extends JpaRepository<AppliedJob, Long> { //<class, id>
    List<AppliedJob> findByUserId(Long userId);

    List<AppliedJob> findByJobId(Long jobId);

    @Query("SELECT a FROM AppliedJob a " +
            "JOIN a.job j " +
            "JOIN j.company c " +
            "WHERE a.isActive = true " +
            "AND (j.company.id = :companyId) " +
            "AND (:jobId IS NULL OR :jobId = 0 OR j.id = :jobId) " +
            "AND (:keyword IS NULL OR :keyword = '' OR LOWER(j.name) LIKE LOWER(CONCAT('%', :keyword, '%')))")
    Page<AppliedJob> searchAppliedJobs
            (@Param("jobId") Long jobId,
             @Param("companyId") Long companyId,
             @Param("keyword") String keyword,
             PageRequest pageable);
//    List<AppliedJob> findByIsActive();

//    @Query("SELECT a FROM AppliedJob a WHERE a.user.id = :userId AND a.job.id = :jobId AND a.isActive = true")
    List<AppliedJob> findByUserIdAndJobId(Long userId, Long jobId);
}

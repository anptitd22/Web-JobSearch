package com.project.webIT.repositories;

import com.project.webIT.constant.AppliedJobStatus;
import com.project.webIT.models.AppliedJob;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AppliedJobRepository extends JpaRepository<AppliedJob, Long> { //<class, id>
    @Query(value = """
        SELECT COUNT(*) 
        FROM applied_job a 
        JOIN jobs j ON a.job_id = j.id 
        WHERE j.company_id = :companyId
    """, nativeQuery = true)
    Long countTotalAppliedJobByCompanyId(@Param("companyId") Long companyId);

    @Query(value = """
        SELECT COUNT(*) 
        FROM applied_job a 
        JOIN jobs j ON a.job_id = j.id 
        WHERE j.company_id = :companyId AND a.status = 'Accept'
    """, nativeQuery = true)
    Long countTotalAcceptedAppliedJobByCompanyId(@Param("companyId") Long companyId);

    List<AppliedJob> findByUserId (Long userId);

    List<AppliedJob> findByJobId(Long jobId);

    @Query("SELECT a FROM AppliedJob a " +
            "JOIN a.job j " +
            "JOIN j.company c " +
            "JOIN a.user u " +
            "WHERE (a.appliedJobStatus = :status OR :status IS NULL OR :status = '') " +
            "AND (j.company.id = :companyId) " +
            "AND (:jobId IS NULL OR :jobId = 0 OR j.id = :jobId) " +
            "AND (:keyword IS NULL OR :keyword = '' OR LOWER(u.fullName) LIKE LOWER(CONCAT('%', :keyword, '%')))")
    Page<AppliedJob> searchAppliedJobs
            (@Param("jobId") Long jobId,
             @Param("companyId") Long companyId,
             @Param("keyword") String keyword,
             PageRequest pageable,
             @Param("status")AppliedJobStatus status);
//    List<AppliedJob> findByIsActive();

//    @Query("SELECT a FROM AppliedJob a WHERE a.user.id = :userId AND a.job.id = :jobId AND a.isActive = true")
    List<AppliedJob> findByUserIdAndJobId(Long userId, Long jobId);
}

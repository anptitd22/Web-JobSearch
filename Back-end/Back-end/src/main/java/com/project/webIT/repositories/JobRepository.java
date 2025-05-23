package com.project.webIT.repositories;

import com.project.webIT.constant.JobStatus;
import com.project.webIT.models.Job;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface JobRepository extends JpaRepository<Job, Long> {
    List<Job> findByIdIn(List<Integer> ids);

    List<Job> findByEndAtBeforeAndIsActiveTrue(LocalDateTime dateTime);

    boolean existsByName(String name);

//    Page<Job> findAll(Pageable pageable); //phan trang cac job
    @Query("SELECT j From Job j where  j.company.id = :companyId AND "+
            "(:jobStatus IS NULL OR :jobStatus = '' OR j.jobStatus = :jobStatus) " +
            "AND (:jobFunctionId IS NULL OR :jobFunctionId = 0 OR j.jobFunction.id = :jobFunctionId) "+
            "AND (:keyword IS NULL OR :keyword = '' OR j.name LIKE %:keyword%)")
    Page<Job> searchJobsFromCompany
            (@Param("companyId") Long companyId,
             @Param("keyword") String keyword,
             @Param("jobFunctionId") Long jobFunctionId,
             PageRequest pageable,
             @Param("jobStatus") JobStatus jobStatusEnum);

    @Query("SELECT j FROM Job j WHERE " +
            "j.isActive = true "+
            "AND (:jobFunctionId IS NULL OR :jobFunctionId = 0 OR j.jobFunction.id = :jobFunctionId) " +
            "AND (:keyword IS NULL OR :keyword = '' OR j.name LIKE %:keyword% OR j.description LIKE %:keyword%)")
    Page<Job> searchJobs
            (@Param("jobFunctionId") Long jobFunctionId,
             @Param("keyword") String keyword,
             Pageable pageable);

    @Query("SELECT j FROM Job j WHERE " +
            "(:jobStatus IS NULL OR :jobStatus = '' OR j.jobStatus = :jobStatus) " +
            "AND (:jobFunctionId IS NULL OR :jobFunctionId = 0 OR j.jobFunction.id = :jobFunctionId) " +
            "AND (:keyword IS NULL OR :keyword = '' OR j.name LIKE %:keyword% OR j.description LIKE %:keyword%)")
    Page<Job> searchJobsFromAdmin
            (@Param("jobFunctionId") Long jobFunctionId,
             @Param("keyword") String keyword,
             @Param("jobStatus") JobStatus jobStatusEnum,
             Pageable pageable);
}

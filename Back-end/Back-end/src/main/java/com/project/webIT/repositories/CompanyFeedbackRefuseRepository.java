package com.project.webIT.repositories;

import com.project.webIT.models.CompanyFeedbackRefuse;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompanyFeedbackRefuseRepository extends JpaRepository<CompanyFeedbackRefuse, Long> {
    CompanyFeedbackRefuse findByAppliedJobId (Long appliedJobId);
}

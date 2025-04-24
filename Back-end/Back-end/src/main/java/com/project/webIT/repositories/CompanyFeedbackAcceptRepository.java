package com.project.webIT.repositories;

import com.project.webIT.models.CompanyFeedbackAccept;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CompanyFeedbackAcceptRepository extends JpaRepository<CompanyFeedbackAccept,Long> {
    CompanyFeedbackAccept findByAppliedJobId (Long appliedJobId);
}

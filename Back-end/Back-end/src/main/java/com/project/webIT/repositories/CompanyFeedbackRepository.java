package com.project.webIT.repositories;

import com.project.webIT.models.CompanyFeedback;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CompanyFeedbackRepository extends JpaRepository<CompanyFeedback,Long> {
    List<CompanyFeedback> findByCompanyId(Long companyId);
}

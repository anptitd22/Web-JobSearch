package com.project.webIT.services.IService;

import com.project.webIT.dtos.request.CompanyFeedbackDTO;
import com.project.webIT.models.CompanyFeedback;

import java.util.List;

public interface CompanyFeedbackService {
    CompanyFeedback createCompanyFeedback(CompanyFeedbackDTO companyFeedbackDTO) throws Exception;

    CompanyFeedback getCompanyFeedback(Long id) throws Exception;

    List<CompanyFeedback> findByCompanyId(Long companyId);

    void deleteCompanyFeedback (Long id);
}

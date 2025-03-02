package com.project.webIT.services.IService;

import com.project.webIT.dtos.companies.CompanyFeedbackDTO;
import com.project.webIT.models.CompanyFeedback;

import java.util.List;

public interface ICompanyFeedbackService {
    CompanyFeedback createCompanyFeedback(CompanyFeedbackDTO companyFeedbackDTO) throws Exception;

    CompanyFeedback getCompanyFeedback(Long id) throws Exception;

    List<CompanyFeedback> findByCompanyId(Long companyId);

    void deleteCompanyFeedback (Long id);
}

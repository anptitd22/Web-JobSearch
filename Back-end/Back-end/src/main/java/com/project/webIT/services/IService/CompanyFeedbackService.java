package com.project.webIT.services.IService;

import com.project.webIT.dtos.request.CompanyFeedbackAcceptDTO;
import com.project.webIT.dtos.request.CompanyFeedbackRefuseDTO;
import com.project.webIT.models.CompanyFeedbackAccept;
import com.project.webIT.models.CompanyFeedbackRefuse;

public interface CompanyFeedbackService {
    CompanyFeedbackAccept createFeedbackAccept (CompanyFeedbackAcceptDTO companyFeedbackAcceptDTO) throws Exception;

    CompanyFeedbackAccept getFeedbackAccept (Long id) throws Exception;

    CompanyFeedbackRefuse createFeedbackRefuse (CompanyFeedbackRefuseDTO companyFeedbackRefuseDTO) throws Exception;

    CompanyFeedbackRefuse getFeedbackRefuse (Long id) throws Exception;
}

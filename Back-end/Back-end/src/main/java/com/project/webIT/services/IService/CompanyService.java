package com.project.webIT.services.IService;

import com.project.webIT.constant.JobStatus;
import com.project.webIT.dtos.request.CompanyDTO;
import com.project.webIT.dtos.request.CompanyImageDTO;
import com.project.webIT.dtos.request.CompanyLoginDTO;
import com.project.webIT.dtos.response.JobResponse;
import com.project.webIT.models.Company;
import com.project.webIT.models.CompanyImage;
import com.project.webIT.models.Job;
import com.project.webIT.dtos.response.CompanyResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.List;
import java.util.Map;

public interface CompanyService {

    String loginCompany(CompanyLoginDTO companyLoginDTO) throws Exception;

    Company createCompany(CompanyDTO companyDTO);

    Company getCompanyById(long id) throws Exception;

    Page<JobResponse> getJobs (Long id, String keyword, Long jobFunctionId, PageRequest pageRequest, JobStatus jobStatus);

    Page<CompanyResponse> getAllCompanies(String keyword, Long industryId, PageRequest pageRequest);

    String getPublicId(Long companyId) throws Exception;

    Company updateCompany(long id, CompanyDTO companyDTO) throws Exception;

    Company getCompanyDetail (String token) throws Exception;

    void deleteCompany(long id);

    Map<String, Object> getLast12MonthsData(Long companyId);

    boolean existByName(String name);

    CompanyImage createCompanyImage(Long companyId, CompanyImageDTO companyImageDTO) throws Exception;

//    Company createCompanyLogo(Long companyId, String url, String publicId) throws Exception;
}

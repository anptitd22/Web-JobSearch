package com.project.webIT.services.IService;

import com.project.webIT.dtos.companies.CompanyDTO;
import com.project.webIT.dtos.companies.CompanyImageDTO;
import com.project.webIT.models.Company;
import com.project.webIT.models.CompanyImages;
import com.project.webIT.models.Job;
import com.project.webIT.response.companies.CompanyResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.List;

public interface ICompanyService {
    Company createCompany(CompanyDTO companyDTO);

    Company getCompanyById(long id) throws Exception;

    List<Job> getJobsByCompanyId (Long companyId);

    Page<CompanyResponse> getAllCompanies(String keyword, Long industryId, PageRequest pageRequest);

    String getPublicId(Long companyId) throws Exception;

    Company updateCompany(long id, CompanyDTO companyDTO) throws Exception;

    void deleteCompany(long id);

    void closeCompany(long id);

    boolean existByName(String name);

    CompanyImages createCompanyImage(Long companyId, CompanyImageDTO companyImageDTO) throws Exception;

//    Company createCompanyLogo(Long companyId, String url, String publicId) throws Exception;
}

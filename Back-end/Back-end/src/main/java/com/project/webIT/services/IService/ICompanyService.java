package com.project.webIT.services.IService;

import com.project.webIT.dtos.companies.CompanyDTO;
import com.project.webIT.dtos.companies.CompanyImageDTO;
import com.project.webIT.models.Company;
import com.project.webIT.models.CompanyImage;
import com.project.webIT.response.companies.CompanyResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

public interface ICompanyService {
    Company createCompany(CompanyDTO companyDTO);

    Company getCompanyById(long id) throws Exception;

    Page<CompanyResponse> getAllCompanies(PageRequest pageRequest);

    Company updateCompany(long id, CompanyDTO companyDTO) throws Exception;

    void deleteCompany(long id);

    void closeCompany(long id);

    boolean existByName(String name);

    CompanyImage createCompanyImage(Long companyId, CompanyImageDTO companyImageDTO) throws Exception;
}

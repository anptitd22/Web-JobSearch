package com.project.webIT.repositories;

import com.project.webIT.models.Company;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompanyRepository extends JpaRepository<Company,Long> {
    boolean existsByName (String name);
//    List<Company> findByCompanyId(Long companyId);
}

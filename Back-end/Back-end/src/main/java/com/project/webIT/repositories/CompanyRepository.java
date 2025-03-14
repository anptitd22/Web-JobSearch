package com.project.webIT.repositories;

import com.project.webIT.models.Company;
import com.project.webIT.models.Job;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CompanyRepository extends JpaRepository<Company,Long> {
    boolean existsByName (String name);
//    List<Company> findByCompanyId(Long companyId);

    @Query("SELECT c From Company c WHERE "+
            ":keyword IS NULL OR :keyword = '' OR c.name LIKE %:keyword%")
    Page<Company> searchCompanies
            (@Param("keyword") String keyword,
             Pageable pageable);
}

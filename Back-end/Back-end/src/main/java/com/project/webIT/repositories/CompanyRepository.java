package com.project.webIT.repositories;

import com.project.webIT.models.Company;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CompanyRepository extends JpaRepository<Company,Long> {
    boolean existsByName (String name);

    Optional<Company> findByAccount (String account);

    @Query("SELECT c From Company c WHERE "+
            "(:industryId IS NULL OR :industryId = 0 OR c.industry.id = :industryId) " +
            "AND (:keyword IS NULL OR :keyword = '' OR c.name LIKE %:keyword%)")
    Page<Company> searchCompanies
            (@Param("keyword") String keyword,
             @Param("industryId") Long industryId,
             Pageable pageable);
}

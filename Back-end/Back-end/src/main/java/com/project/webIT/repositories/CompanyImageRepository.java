package com.project.webIT.repositories;

import com.project.webIT.models.CompanyImages;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CompanyImageRepository extends JpaRepository<CompanyImages, Long> {
    List<CompanyImages> findByCompanyId (Long companyId);
}

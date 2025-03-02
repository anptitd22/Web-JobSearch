package com.project.webIT.repositories;

import com.project.webIT.models.CompanyImage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CompanyImageRepository extends JpaRepository<CompanyImage, Long> {
    List<CompanyImage> findByCompanyId (Long companyId);
}

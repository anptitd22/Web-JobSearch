package com.project.webIT.repositories;

import com.project.webIT.models.UsersFavoriteCompanies;
import com.project.webIT.models.UsersFavoriteJobs;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UsersFavoriteCompaniesRepository extends JpaRepository<UsersFavoriteCompanies, Long> {
    List<UsersFavoriteCompanies> findByUserId(Long userId);

    Optional<UsersFavoriteCompanies> findByUserIdAndCompanyId(Long userId, Long companyId);
}

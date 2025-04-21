package com.project.webIT.repositories;

import com.project.webIT.models.UserFavoriteCompany;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UsersFavoriteCompaniesRepository extends JpaRepository<UserFavoriteCompany, Long> {
    List<UserFavoriteCompany> findByUserId(Long userId);

    List<UserFavoriteCompany> findByCompanyId(Long companyId);

    Optional<UserFavoriteCompany> findByUserIdAndCompanyId(Long userId, Long companyId);
}

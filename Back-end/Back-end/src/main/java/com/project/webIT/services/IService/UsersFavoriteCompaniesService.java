package com.project.webIT.services.IService;

import com.project.webIT.models.UsersFavoriteCompanies;

import java.util.List;

public interface UsersFavoriteCompaniesService {
    UsersFavoriteCompanies saveFavoriteCompany(Long userId, Long companyId) throws Exception;

    List<UsersFavoriteCompanies> getUserFavorites(Long userId);
}

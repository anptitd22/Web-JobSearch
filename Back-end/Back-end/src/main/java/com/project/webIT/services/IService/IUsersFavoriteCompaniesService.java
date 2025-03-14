package com.project.webIT.services.IService;

import com.project.webIT.models.UsersFavoriteCompanies;
import com.project.webIT.models.UsersFavoriteJobs;

import java.util.List;

public interface IUsersFavoriteCompaniesService {
    UsersFavoriteCompanies saveFavoriteCompany(Long userId, Long companyId) throws Exception;

    List<UsersFavoriteCompanies> getUserFavorites(Long userId);
}

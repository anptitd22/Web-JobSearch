package com.project.webIT.services.IService;

import com.project.webIT.models.User;
import com.project.webIT.models.UserFavoriteCompany;

import java.util.List;

public interface UsersFavoriteCompaniesService {
    UserFavoriteCompany saveFavoriteCompany(User user, Long companyId) throws Exception;

    List<UserFavoriteCompany> getUserFavorites(Long userId);
}

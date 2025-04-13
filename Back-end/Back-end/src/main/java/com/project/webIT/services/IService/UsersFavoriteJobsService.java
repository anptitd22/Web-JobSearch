package com.project.webIT.services.IService;

import com.project.webIT.models.User;
import com.project.webIT.models.UserFavoriteJob;

import java.util.List;

public interface UsersFavoriteJobsService {
    UserFavoriteJob saveFavoriteJob(User user, Long jobId) throws Exception;

    List<UserFavoriteJob> getUserFavorites(Long userId);

    List<UserFavoriteJob> getUserFavoritesDefault(Long userId);
}

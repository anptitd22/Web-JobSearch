package com.project.webIT.services.IService;

import com.project.webIT.models.UsersFavoriteJobs;

import java.util.List;

public interface IUserFavoriteJobsService {
    UsersFavoriteJobs saveFavoriteJob(Long userId, Long jobId) throws Exception;

    List<UsersFavoriteJobs>  getUserFavorites(Long userId);
}

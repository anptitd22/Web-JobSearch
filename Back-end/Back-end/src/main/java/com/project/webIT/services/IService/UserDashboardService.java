package com.project.webIT.services.IService;

import com.project.webIT.models.UserDashboard;

import java.util.Map;

public interface UserDashboardService {
    Map<String, Object> getLast12MonthsData (Long userId);

    UserDashboard updateAppliedJobs (Long userId) throws Exception;

    UserDashboard updateJobViews (Long userId) throws Exception;

    UserDashboard updateJobSearches (Long userId) throws Exception;
}

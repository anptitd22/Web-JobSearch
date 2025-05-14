package com.project.webIT.repositories;

import com.project.webIT.models.UserDashboard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserDashboardRepository extends JpaRepository<UserDashboard, Long> {
    Optional<UserDashboard> findByUserIdAndMonth (Long userId, String currentMonth);

    @Query(value = """
        WITH Last12Months AS (
            SELECT DATE_FORMAT(DATE_SUB(NOW(), INTERVAL n MONTH), '%b-%Y') AS month
            FROM (SELECT 0 AS n UNION SELECT 1 UNION SELECT 2 UNION SELECT 3 UNION SELECT 4
                  UNION SELECT 5 UNION SELECT 6 UNION SELECT 7 UNION SELECT 8 UNION SELECT 9
                  UNION SELECT 10 UNION SELECT 11) AS months
        )
        SELECT lm.month AS month,
               COALESCE(ud.applied_jobs, 0) AS appliedJobEntities,
               COALESCE(ud.job_views, 0) AS jobViews,
               COALESCE(ud.job_searches, 0) AS jobSearches
        FROM Last12Months lm
        LEFT JOIN user_dashboards ud
            ON lm.month = ud.month AND ud.user_id = :userId
        ORDER BY STR_TO_DATE(CONCAT('01-', lm.month), '%d-%b-%Y')
        """, nativeQuery = true)
    List<Object[]> findLast12MonthsData(@Param("userId") Long userId);
}

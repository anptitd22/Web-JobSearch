package com.project.webIT.repositories;

import com.project.webIT.models.AdminDashboardActivity;
import com.project.webIT.models.CompanyDashBoard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface AdminDashboardActivityRepository extends JpaRepository<AdminDashboardActivity, Long> {
    Optional<AdminDashboardActivity> findByAdminIdAndMonth (Long adminId, String currentMonth);

    @Query(value = """
        WITH Last12Months AS (
            SELECT DATE_FORMAT(DATE_SUB(NOW(), INTERVAL n MONTH), '%b-%Y') AS month
            FROM (SELECT 0 AS n UNION SELECT 1 UNION SELECT 2 UNION SELECT 3 UNION SELECT 4
                  UNION SELECT 5 UNION SELECT 6 UNION SELECT 7 UNION SELECT 8 UNION SELECT 9
                  UNION SELECT 10 UNION SELECT 11) AS months
        )
        SELECT lm.month AS month,
               COALESCE(a.total_jobs, 0) AS totalJob,
               COALESCE(a.total_company, 0) AS totalCompany,
               COALESCE(a.total_user, 0) AS totalUser
        FROM Last12Months lm
        LEFT JOIN admin_dashboard_activity a
            ON lm.month = a.month AND a.admin_id = 1
        ORDER BY STR_TO_DATE(CONCAT('01-', lm.month), '%d-%b-%Y')
        """, nativeQuery = true)
    List<Object[]> findLast12MonthsData();
}

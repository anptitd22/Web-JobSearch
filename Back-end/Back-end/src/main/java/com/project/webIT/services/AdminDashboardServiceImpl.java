package com.project.webIT.services;

import com.project.webIT.repositories.AdminDashboardActivityRepository;
import com.project.webIT.repositories.AdminDashboardRevenueRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AdminDashboardServiceImpl {

    private final AdminDashboardActivityRepository adminDashboardActivityRepository;
    private final AdminDashboardRevenueRepository adminDashboardRevenueRepository;

    public Map<String, Object> getLast12MonthsActivity() {
        List<Object[]> data = adminDashboardActivityRepository.findLast12MonthsData();

        List<String> months = data.stream().map(row -> (String) row[0]).toList();
        List<Long> totalJob = data.stream().map(row -> (Long) row[1]).toList();
        List<Long> totalCompany = data.stream().map(row -> (Long) row[2]).toList();
        List<Long> totalUser = data.stream().map(row -> (Long) row[3]).toList();

        return Map.of(
                "months", months,
                "totalJob", totalJob,
                "totalCompany", totalCompany,
                "totalUser", totalUser
        );
    }

    public Map<String, Object> getLast12MonthsRevenue() {
        List<Object[]> data = adminDashboardRevenueRepository.findLast12MonthsData();

        List<String> months = data.stream().map(row -> (String) row[0]).toList();
        List<Long> totalRevenue = data.stream().map(row -> (Long) row[1]).toList();

        return Map.of(
                "months", months,
                "totalRevenue", totalRevenue
        );
    }
}

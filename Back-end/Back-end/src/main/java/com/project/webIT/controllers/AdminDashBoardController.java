package com.project.webIT.controllers;

import com.project.webIT.dtos.response.ObjectResponse;
import com.project.webIT.models.Admin;
import com.project.webIT.models.User;
import com.project.webIT.services.AdminDashboardServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("${api.prefix}/admin")
@RequiredArgsConstructor
@Slf4j
public class AdminDashBoardController {
    private final AdminDashboardServiceImpl adminDashboardService;

    @GetMapping("activity")
    public ResponseEntity<ObjectResponse<Map<String, Object>>> getDashboardActivity(
            @AuthenticationPrincipal Admin admin
    ) throws Exception {
        Map<String, Object> dashboardData = adminDashboardService.getLast12MonthsActivity();
        return ResponseEntity.ok(
                ObjectResponse.<Map<String, Object>>builder()
                        .status(HttpStatus.OK)
                        .message("Fetched user dashboard data successfully")
                        .data(dashboardData)
                        .build()
        );
    }

    @GetMapping("revenue")
    public ResponseEntity<ObjectResponse<Map<String, Object>>> getDashboard(
            @AuthenticationPrincipal Admin admin
    ) throws Exception {
        Map<String, Object> dashboardData = adminDashboardService.getLast12MonthsRevenue();
        return ResponseEntity.ok(
                ObjectResponse.<Map<String, Object>>builder()
                        .status(HttpStatus.OK)
                        .message("Fetched user dashboard data successfully")
                        .data(dashboardData)
                        .build()
        );
    }
}

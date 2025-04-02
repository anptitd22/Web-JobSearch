package com.project.webIT.controllers;

import com.project.webIT.models.User;
import com.project.webIT.models.UserDashboard;
import com.project.webIT.services.UserDashboardService;
import com.project.webIT.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("${api.prefix}/dashboard")
@RequiredArgsConstructor
public class UserDashboardController {

    private final UserService userService;

    private final UserDashboardService userDashboardService;

    @PostMapping("/update-applied-jobs")
    public ResponseEntity<?> updateAppliedJobs(@RequestHeader("Authorization") String authorizationHeader) {
        try {
            String extractedToken = authorizationHeader.substring(7);
            User user = userService.getUserDetailsFromToken(extractedToken);

            UserDashboard userDashboard = userDashboardService.updateAppliedJobs(user.getId());
            return ResponseEntity.ok().body(userDashboard);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    /**
     * Endpoint để cập nhật số lượng lần xem công việc.
     */
    @PostMapping("/update-job-views")
    public ResponseEntity<?> updateJobViews(@RequestHeader("Authorization") String authorizationHeader) {
        try {
            String extractedToken = authorizationHeader.substring(7);
            User user = userService.getUserDetailsFromToken(extractedToken);
            UserDashboard userDashboard =userDashboardService.updateJobViews(user.getId());
            return ResponseEntity.ok().body(userDashboard);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    /**
     * Endpoint để cập nhật số lượng lần tìm kiếm công việc.
     */
    @PostMapping("/update-job-searches")
    public ResponseEntity<?> updateJobSearches(@RequestHeader("Authorization") String authorizationHeader) {
        try {
            String extractedToken = authorizationHeader.substring(7);
            User user = userService.getUserDetailsFromToken(extractedToken);
            // Giả sử lấy userId từ token
            UserDashboard userDashboard = userDashboardService.updateJobSearches(user.getId());
            return ResponseEntity.ok().body(userDashboard);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

//    @GetMapping("")
//    public ResponseEntity<?> getDashboardData(
//            @RequestHeader("Authorization") String authorizationHeader
//    ) {
//        try{
//            String extractedToken = authorizationHeader.substring(7);
//            User user = userService.getUserDetailsFromToken(extractedToken);
//
//            // Lấy danh sách 12 tháng gần nhất
//            List<String> months = new ArrayList<>();
//            List<Integer> appliedJobs = new ArrayList<>();
//            List<Integer> jobViews = new ArrayList<>();
//            List<Integer> jobSearches = new ArrayList<>();
//
//            LocalDate today = LocalDate.now();
//            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM-yyyy");
//
//            for (int i = 11; i >= 0; i--) {
//                LocalDate month = today.minusMonths(i);
//                months.add(month.format(formatter));
//
//                // Giả lập dữ liệu theo user
//                appliedJobs.add((int) (Math.random() * 10));  // Số công việc đã ứng tuyển
//                jobViews.add((int) (Math.random() * 50));     // Số lần xem công việc
//                jobSearches.add((int) (Math.random() * 40));  // Số lần tìm kiếm
//            }
//
//            Map<String, Object> response = new HashMap<>();
//            response.put("username", user.getLastName());
//            response.put("months", months);
//            response.put("appliedJobs", appliedJobs);
//            response.put("jobViews", jobViews);
//            response.put("jobSearches", jobSearches);
//
//            return ResponseEntity.ok(response);
//        } catch (Exception e) {
//            return ResponseEntity.badRequest().body(e.getMessage());
//        }
//    }

    @GetMapping("")
    public ResponseEntity<?> getDashboardData(@RequestHeader("Authorization") String authorizationHeader) {
        try {
            String extractedToken = authorizationHeader.substring(7);
            User user = userService.getUserDetailsFromToken(extractedToken);

            return ResponseEntity.ok(userDashboardService.getLast12MonthsData(user.getId()));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}

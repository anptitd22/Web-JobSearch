package com.project.webIT.controllers;

import com.project.webIT.models.User;
import com.project.webIT.models.UserDashboard;
import com.project.webIT.dtos.response.ObjectResponse;
import com.project.webIT.services.UserDashboardServiceImpl;
import com.project.webIT.services.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("${api.prefix}/dashboard")
@RequiredArgsConstructor
public class UserDashboardController {

    private final UserServiceImpl userServiceImpl;

    private final UserDashboardServiceImpl userDashboardServiceImpl;

    @PostMapping("/update-applied-jobs")
    public ResponseEntity<ObjectResponse<UserDashboard>> updateAppliedJobs(
            @RequestHeader("Authorization") String authorizationHeader
    ) throws Exception {
        String extractedToken = authorizationHeader.substring(7);
        User user = userServiceImpl.getUserDetailsFromToken(extractedToken);
        UserDashboard userDashboard = userDashboardServiceImpl.updateAppliedJobs(user.getId());

        return ResponseEntity.ok(
                ObjectResponse.<UserDashboard>builder()
                        .status(HttpStatus.OK)
                        .message("Increased number of applied jobs successfully")
                        .data(userDashboard)
                        .build()
        );
    }

    /**
     * Endpoint to update job view count.
     */
    @PostMapping("/update-job-views")
    public ResponseEntity<ObjectResponse<UserDashboard>> updateJobViews(
            @RequestHeader("Authorization") String authorizationHeader
    ) throws Exception {
        String extractedToken = authorizationHeader.substring(7);
        User user = userServiceImpl.getUserDetailsFromToken(extractedToken);
        UserDashboard userDashboard = userDashboardServiceImpl.updateJobViews(user.getId());

        return ResponseEntity.ok(
                ObjectResponse.<UserDashboard>builder()
                        .status(HttpStatus.OK)
                        .message("Increased number of job views successfully")
                        .data(userDashboard)
                        .build()
        );
    }

    /**
     * Endpoint to update job search count.
     */
    @PostMapping("/update-job-searches")
    public ResponseEntity<ObjectResponse<UserDashboard>> updateJobSearches(
            @RequestHeader("Authorization") String authorizationHeader
    ) throws Exception {
        String extractedToken = authorizationHeader.substring(7);
        User user = userServiceImpl.getUserDetailsFromToken(extractedToken);
        UserDashboard userDashboard = userDashboardServiceImpl.updateJobSearches(user.getId());

        return ResponseEntity.ok(
                ObjectResponse.<UserDashboard>builder()
                        .status(HttpStatus.OK)
                        .message("Increased number of job searches successfully")
                        .data(userDashboard)
                        .build()
        );
    }

    @GetMapping("")
    public ResponseEntity<ObjectResponse<Map<String, Object>>> getDashboardData(
            @RequestHeader("Authorization") String authorizationHeader
    ) throws Exception {
        String extractedToken = authorizationHeader.substring(7);
        User user = userServiceImpl.getUserDetailsFromToken(extractedToken);
        Map<String, Object> dashboardData = userDashboardServiceImpl.getLast12MonthsData(user.getId());
        return ResponseEntity.ok(
                ObjectResponse.<Map<String, Object>>builder()
                        .status(HttpStatus.OK)
                        .message("Fetched user dashboard data successfully")
                        .data(dashboardData)
                        .build()
        );
    }

    //    @GetMapping("")
//    public ResponseEntity<?> getDashboardData(
//            @RequestHeader("Authorization") String authorizationHeader
//    ) {
//        try{
//            String extractedToken = authorizationHeader.substring(7);
//            User user = userServiceImpl.getUserDetailsFromToken(extractedToken);
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
}

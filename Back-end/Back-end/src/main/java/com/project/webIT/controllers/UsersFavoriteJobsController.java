package com.project.webIT.controllers;

import com.project.webIT.models.UsersFavoriteJobs;
import com.project.webIT.dtos.response.ObjectResponse;
import com.project.webIT.dtos.response.UsersFavoriteJobsResponse;
import com.project.webIT.services.UsersFavoriteJobsServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("${api.prefix}/my-career-center")
@RequiredArgsConstructor
public class UsersFavoriteJobsController {
    private final UsersFavoriteJobsServiceImpl favoriteJobService;

    @PostMapping("{userId}/{jobId}")
    public ResponseEntity<ObjectResponse<UsersFavoriteJobsResponse>> saveFavorite(
            @PathVariable("userId") Long userId,
            @PathVariable("jobId") Long jobId
    ) throws Exception {
        UsersFavoriteJobs usersFavoriteJobs = favoriteJobService.saveFavoriteJob(userId, jobId);

        return ResponseEntity.ok(
                ObjectResponse.<UsersFavoriteJobsResponse>builder()
                        .status(HttpStatus.OK)
                        .message("Thêm danh sách công việc yêu thích")
                        .data(UsersFavoriteJobsResponse.fromUserFavoriteJobs(usersFavoriteJobs))
                        .build()
        );
    }

    @GetMapping("my-jobs/{userId}")
    public ResponseEntity<ObjectResponse<List<UsersFavoriteJobsResponse>>> getFavorites(
            @PathVariable("userId") Long userId
    ) {
        List<UsersFavoriteJobs> usersFavoriteJobsList = favoriteJobService.getUserFavorites(userId);
        List<UsersFavoriteJobsResponse> responseList = usersFavoriteJobsList.stream()
                .map(UsersFavoriteJobsResponse::fromUserFavoriteJobs)
                .collect(Collectors.toList());

        return ResponseEntity.ok(
                ObjectResponse.<List<UsersFavoriteJobsResponse>>builder()
                        .status(HttpStatus.OK)
                        .message("Công việc yêu thích")
                        .data(responseList)
                        .build()
        );
    }

    @GetMapping("my-jobs/default/{userId}")
    public ResponseEntity<ObjectResponse<List<UsersFavoriteJobsResponse>>> getFavoritesDefault(
            @PathVariable("userId") Long userId
    ) {
        List<UsersFavoriteJobs> usersFavoriteJobsList = favoriteJobService.getUserFavoritesDefault(userId);
        List<UsersFavoriteJobsResponse> responseList = usersFavoriteJobsList.stream()
                .map(UsersFavoriteJobsResponse::fromUserFavoriteJobs)
                .collect(Collectors.toList());

        return ResponseEntity.ok(
                ObjectResponse.<List<UsersFavoriteJobsResponse>>builder()
                        .status(HttpStatus.OK)
                        .message("Bạn đã lấy danh sách công ty yêu thích")
                        .data(responseList)
                        .build()
        );
    }
}

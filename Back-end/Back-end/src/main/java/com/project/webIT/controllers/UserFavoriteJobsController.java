package com.project.webIT.controllers;

import com.project.webIT.models.UsersFavoriteJobs;
import com.project.webIT.response.ResponseObject;
import com.project.webIT.response.users.UsersFavoriteJobsResponse;
import com.project.webIT.services.UsersFavoriteJobsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("${api.prefix}/my-career-center")
@RequiredArgsConstructor
public class UserFavoriteJobsController {
    private final UsersFavoriteJobsService favoriteJobService;

    @PostMapping("{userId}/{jobId}")
    public ResponseEntity<ResponseObject> saveFavorite(
            @PathVariable("userId") Long userId,
            @PathVariable("jobId") Long jobId
    ) throws Exception {
        UsersFavoriteJobs usersFavoriteJobs = favoriteJobService.saveFavoriteJob(userId, jobId);
        return ResponseEntity.ok().body(
                ResponseObject.builder()
                        .status(HttpStatus.OK)
                        .message("Bạn đã thêm công việc yêu thích")
                        .data(UsersFavoriteJobsResponse.fromUserFavoriteJobs(usersFavoriteJobs))
                        .build()
        );
    }

    @GetMapping("my-jobs/{userId}")
    public ResponseEntity<ResponseObject> getFavorites(@PathVariable("userId") Long userId){
        List<UsersFavoriteJobs> usersFavoriteJobsList = favoriteJobService.getUserFavorites(userId);
        List<UsersFavoriteJobsResponse> responseList = usersFavoriteJobsList.stream()
                .map(UsersFavoriteJobsResponse::fromUserFavoriteJobs)
                .collect(Collectors.toList());
        return ResponseEntity.ok().body(
                ResponseObject.builder()
                        .status(HttpStatus.OK)
                        .message("Bạn đã lấy danh sách công ty yêu thích")
                        .data(responseList)
                        .build()
        );
    }

    @GetMapping("my-jobs/default/{userId}")
    public ResponseEntity<ResponseObject> getFavoritesDefault(@PathVariable("userId") Long userId){
        List<UsersFavoriteJobs> usersFavoriteJobsList = favoriteJobService.getUserFavoritesDefault(userId);
        List<UsersFavoriteJobsResponse> responseList = usersFavoriteJobsList.stream()
                .map(UsersFavoriteJobsResponse::fromUserFavoriteJobs)
                .collect(Collectors.toList());
        return ResponseEntity.ok().body(
                ResponseObject.builder()
                        .status(HttpStatus.OK)
                        .message("Bạn đã lấy danh sách công ty yêu thích")
                        .data(responseList).build()
        );
    }
}

package com.project.webIT.controllers;

import com.project.webIT.models.User;
import com.project.webIT.models.UserFavoriteJob;
import com.project.webIT.dtos.response.ObjectResponse;
import com.project.webIT.dtos.response.UsersFavoriteJobsResponse;
import com.project.webIT.services.UserServiceImpl;
import com.project.webIT.services.UsersFavoriteJobsServiceImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("${api.prefix}/my-career-center")
@RequiredArgsConstructor
@Slf4j
public class UserFavoriteJobController {
    private final UsersFavoriteJobsServiceImpl favoriteJobService;
    private final UserServiceImpl userService;

    @PostMapping("save/{jobId}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<ObjectResponse<UsersFavoriteJobsResponse>> saveFavorite(
            @AuthenticationPrincipal User user,
            @Valid @PathVariable("jobId") Long jobId
    ) throws Exception {
//        String extractedToken = authorizationHeader.substring(7);
//        User user = userService.getUserDetailsFromToken(extractedToken);
        UserFavoriteJob userFavoriteJob = favoriteJobService.saveFavoriteJob(user, jobId);
        return ResponseEntity.ok(
                ObjectResponse.<UsersFavoriteJobsResponse>builder()
                        .status(HttpStatus.OK)
                        .message("Thêm danh sách công việc yêu thích")
                        .data(UsersFavoriteJobsResponse.fromUserFavoriteJobs(userFavoriteJob))
                        .build()
        );
    }

    @GetMapping("my-jobs")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<ObjectResponse<List<UsersFavoriteJobsResponse>>> getFavorites(
            @Valid @RequestHeader("Authorization") String authorizationHeader
    ) throws Exception{
        String extractedToken = authorizationHeader.substring(7);
        User user = userService.getUserDetailsFromToken(extractedToken);
        List<UserFavoriteJob> userFavoriteJobList = favoriteJobService.getUserFavorites(user.getId());
        List<UsersFavoriteJobsResponse> responseList = userFavoriteJobList.stream()
                .map(UsersFavoriteJobsResponse::fromUserFavoriteJobs)
                .collect(Collectors.toList());
        log.info(extractedToken);

        return ResponseEntity.ok(
                ObjectResponse.<List<UsersFavoriteJobsResponse>>builder()
                        .status(HttpStatus.OK)
                        .message("Công việc yêu thích")
                        .data(responseList)
                        .build()
        );
    }

    @GetMapping("my-jobs/default")
    public ResponseEntity<ObjectResponse<List<UsersFavoriteJobsResponse>>> getFavoritesDefault(
            @Valid @RequestHeader("Authorization") String authorizationHeader
    ) throws Exception{
        String extractedToken = authorizationHeader.substring(7);
        User user = userService.getUserDetailsFromToken(extractedToken);
        List<UserFavoriteJob> userFavoriteJobList = favoriteJobService.getUserFavoritesDefault(user.getId());
        List<UsersFavoriteJobsResponse> responseList = userFavoriteJobList.stream()
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
